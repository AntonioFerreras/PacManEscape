package math;


import java.awt.geom.Point2D;

public class PointGenerator {

    public static final int precision = 400;

    public static Point2D.Double[] generate(String expression) {
        Point2D.Double[] points = new Point2D.Double[precision];

        for(int i = 0; i < precision; i++) {
            double input = Utils.map(i, 0, precision, -5, 5);

            try {
                points[i] = new Point2D.Double(input, Utils.evaluate(expression, input));
            } catch (ArithmeticException ex) {
                points[i] = new Point2D.Double(input, Double.NaN);
            }

        }

        return points;
    }


}
