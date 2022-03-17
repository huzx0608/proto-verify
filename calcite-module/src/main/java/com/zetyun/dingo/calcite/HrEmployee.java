package com.zetyun.dingo.calcite;

import java.io.Serializable;

public class HrEmployee  implements Serializable {

    public final int empid;
    public final int deptno;
    public final String name;
    public final float salary;
    public final Integer commission;

    public HrEmployee(int empid,
                      int deptno,
                      String name,
                      float salary,
                      Integer commission) {
        this.empid = empid;
        this.deptno = deptno;
        this.name = name;
        this.salary = salary;
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "Employee [empid: " + empid + ", deptno: " + deptno
                + ", name: " + name + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || obj instanceof HrEmployee
                && empid == ((HrEmployee) obj).empid;
    }
}
