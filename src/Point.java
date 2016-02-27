/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x
     *            the <em>x</em>-coordinate of the point
     * @param y
     *            the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to
     * standard draw.
     *
     * @param that
     *            the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally,
     * if the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0)
     * / (x1 - x0). For completness, the slope is defined to be +0.0 if the line
     * segment connecting the two points is horizontal; Double.POSITIVE_INFINITY
     * if the line segment is vertcal; and Double.NEGATIVE_INFINITY if (x0, y0)
     * and (x1, y1) are equal.
     *
     * @param that
     *            the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        int deltaY = that.y - this.y;
        // horizontal?
        if (deltaY == 0) {
            return +0.0;
        }

        // vertical
        int deltaX = that.x - this.x;
        if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return ((double) deltaY) / ((double) deltaX);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that
     *            the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point
     *         (x0 = x1 and y0 = y1); a negative integer if this point is less
     *         than the argument point; and a positive integer if this point is
     *         greater than the argument point
     */
    public int compareTo(Point that) {

        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return +1;
        } else if (this.x < that.x) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
        }
        return 0;

    }

    /**
     * Compares two points by the slope they make with this point. The slope is
     * defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder(this);
    }

    /**
     * Returns a string representation of this point. This method is provide for
     * debugging; your program should not rely on the format of the string
     * representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // public int hashCode() {
    //
    // return (new Integer(x).toString() + new Integer(y).toString())
    // .hashCode();
    // }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        testCompareTo();
        testSlope();
        testSlopeOrder1();
        testSlopeOrder2();
    }

    private static void testSlopeOrder2() {
        /*
         * Failed on trial 1921 of 100000 p = (59, 156) q = (442, 415) r = (241,
         * 279) student p.compare(q, r) = 0 reference p.compare(q, r) = 1
         * reference p.slopeTo(q) = 0.6762402088772846 reference p.slopeTo(r) =
         * 0.6758241758241759
         */

        System.out.println("TestSlopeOrder2");

        Point p = new Point(59, 156);
        Point q = new Point(442, 415);
        Point r = new Point(241, 279);

        assert p.slopeOrder().compare(q, r) == 1;
        assert p.slopeTo(q) == 0.6762402088772846;
        assert p.slopeTo(r) == 0.6758241758241759;

        /*
         * p = (29504, 24430) q = (5678, 14885) r = (22193, 21501) student
         * p.compare(q, r) = 0 reference p.compare(q, r) = -1 reference
         * p.slopeTo(q) = 0.4006127759590363 reference p.slopeTo(r) =
         * 0.40062918889344823
         */

        p = new Point(29504, 24430);
        q = new Point(5678, 14885);
        r = new Point(22193, 21501);

        assert p.slopeOrder().compare(q, r) == -1;
        assert p.slopeTo(q) == 0.4006127759590363;
        assert p.slopeTo(r) == 0.40062918889344823;
    }

    private static void testSlopeOrder1() {
        System.out.println("TestSlopeOrder1");

        Point p = new Point(179, 499);
        Point q = new Point(179, 393);
        assert p.slopeOrder().compare(q, q) == 0;

        p = new Point(386, 462);
        q = new Point(461, 90);
        Point r = new Point(301, 366);
        assert p.slopeOrder().compare(q, r) == -1;
        assert p.slopeTo(q) == -4.96;
        assert p.slopeTo(r) == 1.1294117647058823;

        p = new Point(28006, 12942);
        q = new Point(19212, 19594);
        r = new Point(15993, 16152);
        assert p.slopeOrder().compare(q, r) == -1;
        assert p.slopeTo(q) == -0.756424835114851;
        assert p.slopeTo(r) == -0.2672105219345709;

        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        Point p3 = new Point(1, 10);
        Point p4 = new Point(2, 10);
        Point p5 = new Point(3, 10);
        Point p6 = new Point(10, 10);

        // vertical
        assert p0.slopeOrder().compare(p1, p2) == 0;
        assert p0.slopeOrder().compare(p2, p3) == 0;
        assert p0.slopeOrder().compare(p1, p3) == 0;
        assert p0.slopeOrder().compare(p2, p1) == 0;
        assert p0.slopeOrder().compare(p3, p2) == 0;
        assert p0.slopeOrder().compare(p3, p1) == 0;

        // horizontal
        assert p3.slopeOrder().compare(p4, p5) == 0;
        assert p3.slopeOrder().compare(p5, p6) == 0;
        assert p3.slopeOrder().compare(p4, p6) == 0;
        assert p3.slopeOrder().compare(p5, p4) == 0;
        assert p3.slopeOrder().compare(p6, p5) == 0;
        assert p3.slopeOrder().compare(p6, p4) == 0;

        assert p0.slopeOrder().compare(p6, p5) == -1;
        assert p0.slopeOrder().compare(p5, p4) == -1;
        assert p0.slopeOrder().compare(p4, p3) == -1;

        assert p0.slopeOrder().compare(p5, p6) == 1;
        assert p0.slopeOrder().compare(p4, p5) == 1;
        assert p0.slopeOrder().compare(p3, p4) == 1;

    }

    private static void testCompareTo() {
        System.out.println("TestCompareTo");

        Point p0 = new Point(1, 2);
        Point p1 = new Point(2, 4);

        Point p0_equal = new Point(1, 2);
        Point p0_yequal = new Point(2, 2);

        assert p0.compareTo(p1) < 0;
        assert p1.compareTo(p0) > 0;
        assert p0.compareTo(p0_equal) == 0;
        assert p0.compareTo(p0_yequal) < 0;
        assert p0_yequal.compareTo(p0) > 0;

    }

    private static void testSlope() {
        System.out.println("TestSlope");

        Point p0 = new Point(1, 2);
        Point p0_equal = new Point(1, 2);
        Point p0_vertical = new Point(1, 5);
        Point p0_horizontal = new Point(5, 2);
        Point p1 = new Point(2, 4);

        double slope;

        slope = p0.slopeTo(p1);
        assert slope == 2.0;

        slope = p0.slopeTo(p0_equal);
        assert slope == Double.NEGATIVE_INFINITY;

        slope = p0.slopeTo(p0_vertical);
        assert slope == Double.POSITIVE_INFINITY;

        slope = p0.slopeTo(p0_horizontal);
        assert slope == +0.0;

    }

    private class SlopeOrder implements Comparator<Point> {

        private Point centre;

        public SlopeOrder(Point centre) {
            this.centre = centre;
        }

        @Override
        public int compare(Point arg0, Point arg1) {

            double s0 = centre.slopeTo(arg0);
            double s1 = centre.slopeTo(arg1);

            // System.out.println("SlopeOrder: " + centre + ", " + arg0 + "(" +
            // s0
            // + ")" + ", " + arg1 + "(" + s1 + ")");

            double diff = s0 - s1;
            if ((Math.abs(diff) < 0.0000001)
                    || (s0 == Double.POSITIVE_INFINITY && s1 == Double.POSITIVE_INFINITY)
                    || (s0 == Double.NEGATIVE_INFINITY && s1 == Double.NEGATIVE_INFINITY)) {
                return 0;
            } else {
                return diff < 0 ? -1 : 1;
            }

        }

    }
}
