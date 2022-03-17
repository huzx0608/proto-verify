package com.zetyun.dingo.customer.table;

import com.zetyun.dingo.customer.function.SqlToDateFunc;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;

public class SqlMyOperatorTable extends SqlStdOperatorTable {
    private static SqlMyOperatorTable instance;
    public static final SqlFunction TO_DATE = new SqlToDateFunc();

    public static synchronized SqlMyOperatorTable getInstance() {
        if (null == instance) {
            instance = new SqlMyOperatorTable();
            instance.init();
        }
        return instance;
    }
}
