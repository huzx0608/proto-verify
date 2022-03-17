package com.zetyun.dingo.customer.function;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.type.OperandTypes;
import org.apache.calcite.sql.type.ReturnTypes;
import org.apache.calcite.sql.type.SqlOperandCountRanges;
import org.apache.calcite.sql.type.SqlOperandTypeChecker;
import org.apache.calcite.sql.validate.SqlMonotonicity;
import org.apache.calcite.sql.validate.SqlValidator;

public class SqlToDateFunc extends SqlFunction {
    public SqlToDateFunc() {
        super(
                "TO_DATE",
                SqlKind.OTHER_FUNCTION,
                ReturnTypes.DATE,
                null,
                OperandTypes.ANY,
                SqlFunctionCategory.TIMEDATE
        );
    }

    @Override
    public SqlSyntax getSyntax() {
        return SqlSyntax.FUNCTION_ID;
    }

    @Override
    public SqlMonotonicity getMonotonicity(SqlOperatorBinding call) {
        return SqlMonotonicity.INCREASING;
    }

    @Override
    public boolean isDynamicFunction() {
        return true;
    }

    @Override
    public SqlOperandCountRange getOperandCountRange() {
        return SqlOperandCountRanges.of(2);
    }

    @Override
    protected void checkOperandCount(SqlValidator validator, SqlOperandTypeChecker argType, SqlCall call) {
        assert call.operandCount() == 2;
    }
}
