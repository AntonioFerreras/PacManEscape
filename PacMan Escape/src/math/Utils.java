package math;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Utils {

    private static final String PI_CHAR = "\u03C0";

    public static double evaluate(String expression, double value) {
        Expression e = new ExpressionBuilder(expression).variables("x", PI_CHAR, "e").build().setVariable("x", value)
                .setVariable(PI_CHAR, Math.PI).setVariable("e", Math.E);
        return e.evaluate();
    }

    public static double map(double n, double start1, double stop1, double start2, double stop2) {
        return ((n - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
    }
}
