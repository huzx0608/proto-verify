package com.zetyun.dingo.simple;

import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.type.*;

import java.util.Arrays;

public class DingoOperatorTable extends SqlStdOperatorTable {

    public static final SqlFunction TIMESTR2LONG = new SqlFunction(
            new SqlIdentifier("timestr2long", SqlParserPos.ZERO),
            //返回值为Long, 可以为NULL
            ReturnTypes.cascade(ReturnTypes.explicit(SqlTypeName.BIGINT), SqlTypeTransforms.TO_NULLABLE),
            //输入类型推测为Varchar, 即java中的String
            InferTypes.VARCHAR_1024,
            //类型检查，如果类型不是String, 报错
            OperandTypes.family(SqlTypeFamily.STRING),
            Arrays.asList(new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT).createSqlType(SqlTypeName.VARCHAR)),
            SqlFunctionCategory.USER_DEFINED_FUNCTION);
}
