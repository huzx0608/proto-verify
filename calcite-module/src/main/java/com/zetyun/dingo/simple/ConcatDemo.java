package com.zetyun.dingo.simple;

import org.apache.calcite.sql.parser.SqlParseException;
import java.util.StringJoiner;

public class ConcatDemo {

    public static void main(String[] args) throws SqlParseException {
        // System.out.println("abcd".substring(0, "abcd".length()));
        /*
        String sql = "select concat('test-', upper(name)) from test limit 3";
        SqlParser parser = SqlParser.create(sql);
        SqlNode stmt = parser.parseStmt();
        FunctionExtractor functionExtractor = new FunctionExtractor();
        stmt.accept(functionExtractor);
        // [CONCAT, UPPER]
        System.out.println(functionExtractor.getFunctions());
        SqlNode sqlNode = parser.parseQuery(sql);
        System.out.println(sqlNode.toSqlString(new SqlDialect(SqlDialect.EMPTY_CONTEXT)));
         */

        StringJoiner joiner = new StringJoiner("#", "[", "]");
        joiner.add("1").add("2").add("3");
        System.out.println("Joiner=>" + joiner.toString());

        StringJoiner joiner1 = new StringJoiner("=");
        joiner1.add("1");
        System.out.println("Joiner=>" + joiner1.toString());

    }
}
