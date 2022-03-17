package com.zetyun.dingo.customer;

import com.zetyun.dingo.customer.model.DataTypeEnum;
import com.zetyun.dingo.customer.table.SqlMyOperatorTable;
import com.zetyun.dingo.customer.table.Table;
import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.hep.HepPlanner;
import org.apache.calcite.plan.hep.HepProgramBuilder;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelDistributionTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.rel2sql.RelToSqlConverter;
import org.apache.calcite.rel.rel2sql.SqlImplementor;
import org.apache.calcite.rel.rules.PruneEmptyRules;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.rel.type.RelDataTypeSystemImpl;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.BasicSqlType;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.RelDecorrelator;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.RelBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CalciteUtils {

    public static String optimizationSql(String sql, String type, List<Table> tables) throws Exception {
        SchemaPlus rootSchema = registerSchemas(tables);
        final FrameworkConfig frameworkConfig = Frameworks
                .newConfigBuilder()
                .parserConfig(SqlParser.Config.DEFAULT)
                .defaultSchema(rootSchema)
                .traitDefs(ConventionTraitDef.INSTANCE, RelDistributionTraitDef.INSTANCE)
                .build();

        HepProgramBuilder builder = new HepProgramBuilder();
        builder.addRuleInstance(PruneEmptyRules.PROJECT_INSTANCE);
        HepPlanner planner = new HepPlanner(builder.build());

        try {
            SqlTypeFactoryImpl factory = new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
            SqlParser parser = SqlParser.create(sql, SqlParser.Config.DEFAULT);
            SqlNode parseStmt = parser.parseStmt();

            CalciteCatalogReader reader = new CalciteCatalogReader(
                    CalciteSchema.from(rootSchema),
                    CalciteSchema.from(rootSchema).path(null),
                    factory,
                    new CalciteConnectionConfigImpl(new Properties())
            );

            SqlValidator validator = SqlValidatorUtil.newValidator(
                    SqlMyOperatorTable.getInstance(),
                    reader,
                    factory,
                    SqlValidator.Config.DEFAULT
            );

            SqlNode validated = validator.validate(parseStmt);
            final RexBuilder rexBuilder = new RexBuilder(factory);
            final RelOptCluster cluster = RelOptCluster.create(planner, rexBuilder);

            final SqlToRelConverter.Config config = SqlToRelConverter.config();

            // SqlNode 转成 RelNode
            final SqlToRelConverter sqlToRelConverter = new SqlToRelConverter(
                    new ViewExpanderImpl(),
                    validator,
                    reader,
                    cluster,
                    frameworkConfig.getConvertletTable(),
                    config
            );

            RelRoot relRoot = sqlToRelConverter.convertQuery(validated, false, true);
            relRoot = relRoot.withRel(sqlToRelConverter.flattenTypes(relRoot.rel, true));

            final RelBuilder relBuilder = config.getRelBuilderFactory().create(cluster, null);
            relRoot = relRoot.withRel(RelDecorrelator.decorrelateQuery(relRoot.rel, relBuilder));
            RelNode relNode = relRoot.rel;

            // 寻找最优路径
            planner.setRoot(relNode);
            relNode = planner.findBestExp();

            RelToSqlConverter relToSqlConverter = new RelToSqlConverter(OracleSqlDialect.DEFAULT);
            SqlImplementor.Result visit = relToSqlConverter.visitRoot(relNode);
            SqlNode sqlNode = visit.asStatement();
            return sqlNode.toSqlString(OracleSqlDialect.DEFAULT).getSql();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Huzx===");
        }
    }

    private static SchemaPlus registerSchemas(List<Table> tables) {
        SchemaPlus schemaPlus = Frameworks.createRootSchema(true);
        List<Table> noSchemaTables = tables
                .stream()
                .filter(t -> StringUtils.isEmpty(t.getSchema()))
                .collect(Collectors.toList());
        for (Table table : noSchemaTables) {
            schemaPlus.add(
                    table.getSchema().toUpperCase(Locale.ROOT),
                    new AbstractTable() {
                        @Override
                        public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
                            RelDataTypeFactory.Builder builder = relDataTypeFactory.builder();
                            table.getFields().forEach(x -> {
                                builder.add(
                                        x.getName().toUpperCase(Locale.ROOT),
                                        new BasicSqlType(new RelDataTypeSystemImpl() {
                                        }, SqlTypeName.VARCHAR)
                                );
                            });
                            return builder.build();
                        }
                    });
        }


        Map<String, List<Table>> schemaTables = tables.stream()
                .filter(t -> StringUtils.isNotEmpty(t.getSchema()))
                .collect(Collectors.groupingBy(Table::getSchema));

        for (String key : schemaTables.keySet()) {
            List<Table> tableList = schemaTables.get(key);
            SchemaPlus schema = Frameworks.createRootSchema(true);
            for (Table lTable : tableList) {
                schema.add(lTable.getTable().toUpperCase(Locale.ROOT),
                        new AbstractTable() {
                            @Override
                            public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
                                RelDataTypeFactory.Builder builder = relDataTypeFactory.builder();
                                lTable.getFields().forEach(x -> {
                                    builder.add(
                                            x.getName().toUpperCase(Locale.ROOT),
                                            new BasicSqlType(new RelDataTypeSystemImpl() {}, getSqlTypeByName(x.getType()))
                                    );
                                });
                                return builder.build();
                            }
                        });
            }
            schemaPlus.add(key.toUpperCase(Locale.ROOT), schema);
        }

        return schemaPlus;
    }

    private static SqlTypeName getSqlTypeByName(String type) {
        DataTypeEnum dataTypeEnum = DataTypeEnum.getByCode(type);
        if (null == dataTypeEnum) {
            return SqlTypeName.VARCHAR;
        }

        switch (dataTypeEnum) {
            case CHAR:
            case CLOB:
            case TEXT:
            case BLOB:
            case STRING:
                return SqlTypeName.VARCHAR;
            case DECIMAL:
            case NUMBER:
                return SqlTypeName.DECIMAL;
            case INTEGER:
                return SqlTypeName.INTEGER;
            case DATE:
            case TIMESTAMP:
                return SqlTypeName.DATE;
            default:
                return SqlTypeName.VARCHAR;
        }

    }

    public static void main(String[] args) throws Exception {
        String sql = "select id, name from school.student where create_time > to_date('2021-03-14 12:31:12', 'yyyy-mm-dd hh24:mm:ss')";
        List<Table> tables = new ArrayList<>();
        List<Table.Field> fields = new ArrayList<>();
        fields.add(new Table.Field("id", DataTypeEnum.NUMBER.getType()));
        fields.add(new Table.Field("name", DataTypeEnum.STRING.getType()));
        fields.add(new Table.Field("create_time", DataTypeEnum.DATE.getType()));
        tables.add(new Table("school", "student", fields));
        sql = CalciteUtils.optimizationSql(sql, "ORACLE", tables);
        System.out.println(sql);
    }

    private static class ViewExpanderImpl implements RelOptTable.ViewExpander {
        @Override
        public RelRoot expandView(RelDataType relDataType, String s, List<String> list, List<String> list1) {
            return null;
        }
    }

}
