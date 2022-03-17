package com.zetyun.dingo.calcite;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.RelWriter;
import org.apache.calcite.rel.externalize.RelWriterImpl;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelRunners;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CalciteMain {
    public static void main(String[] args) {

        SchemaPlus rootSchema = Frameworks.createRootSchema(true);
        ReflectiveSchema schema = new ReflectiveSchema(new HrSchemaMin());
        SchemaPlus hr = rootSchema.add("HR", schema);

        SqlParser.Config insensitiveParse = SqlParser.configBuilder()
                .setCaseSensitive(false)
                .build();

        FrameworkConfig config = Frameworks.newConfigBuilder()
                .parserConfig(insensitiveParse)
                .defaultSchema(hr)
                .build();

        Planner planner = Frameworks.getPlanner(config);
        try {
            SqlNode sqlNode = planner.parse("select depts.name, count(emps.empid) from emps inner join depts on emps.deptno = depts.deptno group by depts.deptno, depts.name order by depts.name");
            System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
            SqlNode sqlNodeValidated = planner.validate(sqlNode);
            RelRoot relRoot = planner.rel(sqlNodeValidated);
            RelNode relNode = relRoot.project();
            final RelWriter reWriter = new RelWriterImpl(
                    new PrintWriter(System.out),
                    SqlExplainLevel.ALL_ATTRIBUTES,
                    false
            );
            relNode.explain(reWriter);

            // Run it
            PreparedStatement run = RelRunners.run(relNode);
            ResultSet resultSet = run.executeQuery();

            // Print it
            System.out.println("Result:");
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getObject(i)+",");
                }
                System.out.println();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
