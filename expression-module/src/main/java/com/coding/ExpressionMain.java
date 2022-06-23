package com.coding;

import com.udojava.evalex.AbstractFunction;
import com.udojava.evalex.AbstractOperator;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.List;

public class ExpressionMain {

    public static void main(String[] args) {
        int totalCnt = 1;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < totalCnt; i++) {
            Expression exp1 = new Expression( i + " + 1 / 3");
            BigDecimal result1 = exp1.eval();
            if (i % 100 == 0) {
                System.out.println(result1);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Math.Expression AvgTimeCost is:" + (endTime - startTime)  * 1.0 / totalCnt);


        BigDecimal result = BigDecimal.ZERO;
        long logicalStartTime = System.currentTimeMillis();
        for (int i = 0; i < totalCnt; i++) {
            /*
            Expression exp2 = new Expression("SQRT(a^2 + b^2 + c * 3) > 100")
                    .with("a", new BigDecimal(i + 1))
                    .and("b", new BigDecimal(i + 2))
                    .and("c", new BigDecimal(i + 3));
            result = exp2.eval();
            */


            Expression exp3 = new Expression("MAX(a, b) == a || MIN(a, b) == b || c * random() > 1")
                    .with("a", new BigDecimal(i))
                    .and("b", new BigDecimal(i + 1))
                    .and("c", new BigDecimal(i + 2));
            result = exp3.eval();

            if (i != 0 && i % 200 == 0) {
                System.out.println(result);
            }
        }
        long logicalEndTime = System.currentTimeMillis();
        System.out.println("Logical.Expression AvgTimeCost is:" + (logicalEndTime - logicalStartTime)  * 1.0 / totalCnt);


        // 3. self-define operator
        Expression exp3 = new Expression("2.12345 >> 2");
        exp3.addOperator(new AbstractOperator(">>", 30, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                return v1.movePointRight(v2.toBigInteger().intValue());
            }
        });
        result = exp3.eval();
        System.out.println("Abstrct operator result is:" + result.toString());

        // 4. self-define operator
        Expression exp4 = new Expression("4!");
        exp4.addOperator(new AbstractOperator("!", Expression.OPERATOR_PRECEDENCE_POWER_HIGHER + 1, true, false, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                if (v1==null) {
                    throw new ArithmeticException("Operand may not be null");
                }
                if (v1.remainder(BigDecimal.ONE) != BigDecimal.ZERO) {
                    throw new ArithmeticException("Operand must be an integer");
                }

                BigDecimal factorial=v1;
                v1=v1.subtract(BigDecimal.ONE);
                if (factorial.compareTo(BigDecimal.ZERO)==0 || factorial.compareTo(BigDecimal.ONE) == 0) {
                    return BigDecimal.ONE;
                } else {
                    while (v1.compareTo(BigDecimal.ONE) >0 ) {
                        factorial = factorial.multiply(v1);
                        v1 = v1.subtract(BigDecimal.ONE);
                    }
                    return factorial;
                }
            }
        });

        result = exp4.eval();
        System.out.println("Exp4 result is :" + result);

        Expression exp5 = new Expression("2 * average(12, 4, 8)");
        exp5.addFunction(new AbstractFunction("average", -1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                if (parameters.size() == 0) {
                    throw new Expression.ExpressionException("average function at least one parameter");
                }

                BigDecimal avg = new BigDecimal(0);
                for (BigDecimal param: parameters) {
                    avg = avg.add(param);
                }
                return avg.divide(new BigDecimal(parameters.size()));
            }
        });
        System.out.println("Exp5 result is:" + exp5.eval());

    }
}
