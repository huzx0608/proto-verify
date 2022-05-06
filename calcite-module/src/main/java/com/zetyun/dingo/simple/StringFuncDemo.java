package com.zetyun.dingo.simple;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.type.*;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelRunners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StringFuncDemo {


//    public static final SqlFunction TIMESTR2LONG = new SqlFunction(
//            new SqlIdentifier("TIMESTR2LONG", SqlParserPos.ZERO),
//            //返回值为Long, 可以为NULL
//            ReturnTypes.cascade(ReturnTypes.explicit(SqlTypeName.BIGINT), SqlTypeTransforms.TO_NULLABLE),
//            //输入类型推测为Varchar, 即java中的String
//            InferTypes.VARCHAR_1024,
//            //类型检查，如果类型不是String, 报错
//            OperandTypes.family(SqlTypeFamily.STRING),
//            Lists.newArrayList(new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT).createSqlType(SqlTypeName.VARCHAR)),
//            SqlFunctionCategory.USER_DEFINED_FUNCTION
//    );

    public static final SqlTypeFactoryImpl TYPE_FACTORY = new SqlTypeFactoryImpl(
            RelDataTypeSystem.DEFAULT);
    public static final RelDataTypeSystem TYPE_SYSTEM = RelDataTypeSystem.DEFAULT;

    public static void main(String[] args) {
        SchemaPlus schemaPlus = Frameworks.createRootSchema(true);

        schemaPlus.add("T", new ReflectiveSchema(new TestSchema()));

        Frameworks.ConfigBuilder configBuilder = Frameworks.newConfigBuilder();
        configBuilder.defaultSchema(schemaPlus);

        FrameworkConfig frameworkConfig = configBuilder
                        .operatorTable(DingoOperatorTable.instance())
                        .build();
        SqlParser.ConfigBuilder paresrCfg = SqlParser.configBuilder(frameworkConfig.getParserConfig());
        paresrCfg.setCaseSensitive(false).setConfig(paresrCfg.build());
        paresrCfg.setConformance(SqlConformanceEnum.MYSQL_5);
        Planner planner = Frameworks.getPlanner(frameworkConfig);

        SqlNode sqlNode;
        RelRoot relRoot = null;
        PreparedStatement run = null;
        ResultSet resultSet = null;

        try {
            // "select a.s as A, count(a.s) from T.rdf a group by a.s"
            // sqlNode = planner.parse("select \"a\".\"s\", count(\"a\".\"s\") from \"T\".\"rdf\" \"a\" group by \"a\".\"s\"");
            // sqlNode = planner.parse("select \"a\".\"s\" || \"a\".\"p\" from \"T\".\"rdf\" \"a\"");
            // sqlNode = planner.parse("SELECT CAST('123' AS INT)");
            String sql = "select reverse(' aaaababca')";
            sqlNode = planner.parse(sql);
            planner.validate(sqlNode);
            relRoot = planner.rel(sqlNode);
            RelNode relNode = relRoot.project();
            System.out.print(RelOptUtil.toString(relNode));

            run = RelRunners.run(relNode);
            resultSet = run.executeQuery();
            System.out.println("Result:");
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getObject(i) + ",");
                }
                System.out.println();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public static class TestSchema {
        public final Triple[] rdf = {
                new Triple("s-huzx", "p-huzx", "o-huzx"),
                new Triple("s-huzx", "p-huzx", "o-huzx01"),
        };
    }
}
