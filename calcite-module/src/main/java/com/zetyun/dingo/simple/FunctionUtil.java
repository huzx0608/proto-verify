package com.zetyun.dingo.simple;

import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.type.*;

import java.util.Arrays;

import static com.zetyun.dingo.simple.StringFuncDemo2.TYPE_FACTORY;

public class FunctionUtil {
    public static Integer func1(String s) {
        return s == null ? null : Integer.valueOf(s);
    }

    public static String func2(Integer i) {
        return i == null ? null : String.valueOf(i);
    }

    public static final SqlFunction FUNC1 = new SqlFunction(
            new SqlIdentifier("FUNC1", SqlParserPos.ZERO),
            ReturnTypes.cascade(ReturnTypes.explicit(SqlTypeName.INTEGER), SqlTypeTransforms.TO_NULLABLE),
            InferTypes.VARCHAR_1024,
            OperandTypes.family(SqlTypeFamily.STRING),
            Arrays.asList(TYPE_FACTORY.createSqlType(SqlTypeName.VARCHAR)),
            SqlFunctionCategory.USER_DEFINED_FUNCTION);

    public static final SqlFunction FUNC2 = new SqlFunction(
            new SqlIdentifier("FUNC2", SqlParserPos.ZERO),
            ReturnTypes.cascade(ReturnTypes.explicit(SqlTypeName.VARCHAR), SqlTypeTransforms.TO_NULLABLE),
            InferTypes.FIRST_KNOWN,
            OperandTypes.family(SqlTypeFamily.INTEGER),
            Arrays.asList(TYPE_FACTORY.createSqlType(SqlTypeName.INTEGER)),
            SqlFunctionCategory.USER_DEFINED_FUNCTION);

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

    public static final SqlFunction DINGO_LTRIM = new SqlFunction(
            new SqlIdentifier("ltrim", SqlParserPos.ZERO),
            ReturnTypes.cascade(ReturnTypes.explicit(SqlTypeName.VARCHAR), SqlTypeTransforms.TO_NULLABLE),
            InferTypes.VARCHAR_1024,
            OperandTypes.family(SqlTypeFamily.STRING),
            Arrays.asList(TYPE_FACTORY.createSqlType(SqlTypeName.VARCHAR)),
            SqlFunctionCategory.USER_DEFINED_FUNCTION);
}
