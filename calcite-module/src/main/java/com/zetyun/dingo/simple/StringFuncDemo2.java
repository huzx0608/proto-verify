package com.zetyun.dingo.simple;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.BasicSqlType;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.sql.util.ChainedSqlOperatorTable;
import org.apache.calcite.sql.util.ListSqlOperatorTable;
import org.apache.calcite.sql.util.SqlOperatorTables;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelRunners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.zetyun.dingo.simple.FunctionUtil.*;
import static org.apache.calcite.sql.SqlExplainLevel.ALL_ATTRIBUTES;

public class StringFuncDemo2 {



    public static final SqlTypeFactoryImpl TYPE_FACTORY = new SqlTypeFactoryImpl(
            RelDataTypeSystem.DEFAULT);
    public static final RelDataTypeSystem TYPE_SYSTEM = RelDataTypeSystem.DEFAULT;
    
    public static void main(String[] args) {
        CalciteSchema schemaPlus = CalciteSchema.createRootSchema(false, false);
        schemaPlus.add("test", new AbstractTable() {
            @Override
            public RelDataType getRowType(RelDataTypeFactory typeFactory) {
                RelDataTypeFactory.Builder builder = new RelDataTypeFactory
                        .Builder(TYPE_FACTORY);
                //???id, ??????int
                builder.add("id", new BasicSqlType(TYPE_SYSTEM, SqlTypeName.INTEGER));
                //???name, ?????????varchar
                builder.add("name", new BasicSqlType(TYPE_SYSTEM, SqlTypeName.VARCHAR));
                builder.add("time_str", new BasicSqlType(TYPE_SYSTEM, SqlTypeName.VARCHAR));
                return builder.build();
            }
        });

        SqlParser.ConfigBuilder builder = SqlParser.configBuilder();
        builder.setQuotedCasing(Casing.TO_UPPER);
        builder.setUnquotedCasing(Casing.TO_UPPER);
        builder.setCaseSensitive(false);

        final FrameworkConfig config = Frameworks.newConfigBuilder()
                .defaultSchema(schemaPlus.plus())
                .parserConfig(builder.build())
                //?????????????????????SqlStdOperatorTable??? ?????????????????????
                //???????????????calcite???SqlStdOperatorTable
                .operatorTable(DingoOperatorTable.instance())
                .build();
        Planner planner = Frameworks.getPlanner(config);

        try {
            // "select a.s as A, count(a.s) from T.rdf a group by a.s"
            // sqlNode = planner.parse("select \"a\".\"s\", count(\"a\".\"s\") from \"T\".\"rdf\" \"a\" group by \"a\".\"s\"");
            // sqlNode = planner.parse("select \"a\".\"s\" || \"a\".\"p\" from \"T\".\"rdf\" \"a\"");
            // sqlNode = planner.parse("SELECT CAST('123' AS INT)");
            // String sql = "select ltrim(' aaaababca')";
            // String sql = "select name, timestr2long(time_str) from test where id < 5";

//            Planner planner1 = Frameworks.getPlanner(config);
//            SqlNode originSqlNode = planner1.parse("select name, timestr2long(time_str) from test where id < 5");
//            SqlNode sqlNode = planner1.validate(originSqlNode);
//            RelRoot root = planner1.rel(sqlNode);
//            System.out.println(RelOptUtil.toString(root.rel, ALL_ATTRIBUTES));

            //now test func
            ListSqlOperatorTable listSqlOperatorTable = new ListSqlOperatorTable();
            listSqlOperatorTable.add(FUNC1);
            listSqlOperatorTable.add(FUNC2);
            listSqlOperatorTable.add(TIMESTR2LONG);
            listSqlOperatorTable.add(DINGO_LTRIM);

            final FrameworkConfig funcConfig = Frameworks.newConfigBuilder()
                    .defaultSchema(schemaPlus.plus())
                    .parserConfig(builder.build())
                    //??????????????????????????????????????? listSqlOperatorTable
                    .operatorTable(SqlOperatorTables.chain(listSqlOperatorTable, SqlStdOperatorTable.instance()))
                    .build();

            Planner planner2 = Frameworks.getPlanner(funcConfig);
            SqlNode func1SqlNodeOrg = planner2.parse("select func1(name) from test where id > 4");
            SqlNode func1SqlNode = planner2.validate(func1SqlNodeOrg);
            RelRoot func1Root = planner2.rel(func1SqlNode);
            System.out.println("-------- func1 test -------");
            System.out.println(RelOptUtil.toString(func1Root.rel, ALL_ATTRIBUTES));

            Planner planner3 = Frameworks.getPlanner(funcConfig);
            SqlNode func2SqlNodeOrg = planner3.parse("select func2(id) from test where id > 4");
            SqlNode func2SqlNode = planner3.validate(func2SqlNodeOrg);
            RelRoot func2Root = planner3.rel(func2SqlNode);
            System.out.println("-------- func2 test -------");
            System.out.println(RelOptUtil.toString(func2Root.rel, ALL_ATTRIBUTES));

            Planner planner1 = Frameworks.getPlanner(funcConfig);
            SqlNode originSqlNode = planner1.parse("select name, timestr2long(time_str) from test where id < 5");
            SqlNode sqlNode = planner1.validate(originSqlNode);
            RelRoot root = planner1.rel(sqlNode);
            System.out.println(RelOptUtil.toString(root.rel, ALL_ATTRIBUTES));

            Planner planner4 = Frameworks.getPlanner(funcConfig);
            SqlNode ltrimSqlNodeOrg = planner4.parse("select ltrim(' AAAA')");
            SqlNode ltrimSqlNode = planner4.validate(ltrimSqlNodeOrg);
            RelRoot ltrimRoot = planner4.rel(ltrimSqlNode);
            System.out.println("-------- ltrim test -------");
            System.out.println(RelOptUtil.toString(ltrimRoot.rel, ALL_ATTRIBUTES));


        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
