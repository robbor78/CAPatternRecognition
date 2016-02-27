import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segmentsArr;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        /*
         * Corner cases. Throw a java.lang.NullPointerException either the
         * argument to the constructor is null or if any point in the array is
         * null. Throw a java.lang.IllegalArgumentException if the argument to
         * the constructor contains a repeated point.
         */

        if (points == null) {
            throw new java.lang.NullPointerException("No points.");
        }
        if (Arrays.asList(points).contains(null)) {
            throw new java.lang.NullPointerException("Found null in points.");
        }

        int length = points.length;

        Arrays.sort(points);

        List<LineSegment> segmentList = new ArrayList<LineSegment>();
        List<TempLineSegment> tmpList = new ArrayList<TempLineSegment>();

        for (int a = 0; a < length; a++) {
            // System.out.println("a(" + a + "): " + points[a]);
            for (int b = a + 1; b < length; b++) {
                if (points[a].compareTo(points[b]) == 0) {
                    throw new java.lang.IllegalArgumentException(
                            "Duplicate point.");
                }
                double slopeAB = points[a].slopeTo(points[b]);
                // System.out.println("b(" + b + "): " + points[b] +
                // " slopeAB: "
                // + slopeAB);

                for (int c = b + 1; c < length; c++) {
                    if (points[b].compareTo(points[c]) == 0) {
                        throw new java.lang.IllegalArgumentException(
                                "Duplicate point.");
                    }
                    double slopeBC = points[b].slopeTo(points[c]);
                    // System.out.println("c(" + c + "): " + points[c]
                    // + " slopeAB: " + slopeBC);

                    if (isSlopesEqual(slopeAB, slopeBC)) {
                        for (int d = c + 1; d < length; d++) {
                            if (points[c].compareTo(points[d]) == 0) {
                                throw new java.lang.IllegalArgumentException(
                                        "Duplicate point.");
                            }

                            double slopeCD = points[c].slopeTo(points[d]);
                            // System.out.println("d(" + d + "): " + points[d]
                            // + " slopeAB: " + slopeCD);

                            if (isSlopesEqual(slopeBC, slopeCD)) {

                                LineSegment ls = new LineSegment(points[a],
                                        points[d]);

                                TempLineSegment lsTmp = new TempLineSegment();
                                lsTmp.p = points[a];
                                lsTmp.q = points[d];

                                if (!contains(tmpList, lsTmp)) {
                                    // set.add(lsTmp);
                                    tmpList.add(lsTmp);
                                    segmentList.add(ls);
                                }

                                // System.out.println("New line segment: "
                                // + points[a] + " " + points[d]);

                                // a = d;
                                // b = c = d = length;
                            }
                        }
                    }
                }
            }
        }

        segmentsArr = new LineSegment[segmentList.size()];
        segmentsArr = segmentList.toArray(segmentsArr);

    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsArr.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segmentsArr, numberOfSegments());
    }

    private boolean isSlopesEqual(double s1, double s2) {
        return Math.abs(s1 - s2) < 0.001
                || (s1 == Double.POSITIVE_INFINITY && s2 == Double.POSITIVE_INFINITY)
                || (s1 == Double.NEGATIVE_INFINITY && s2 == Double.NEGATIVE_INFINITY);

    }

    private boolean contains(List<TempLineSegment> tmpList,
            TempLineSegment lsTmp) {
        for (TempLineSegment s : tmpList) {
            if ((s.p.compareTo(lsTmp.p) == 0 && s.q.compareTo(lsTmp.q) == 0)
                    || (s.p.compareTo(lsTmp.q) == 0 && s.q.compareTo(lsTmp.p) == 0)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        // test1();
        // test2();
        // testSquare();
        // testVerticalLine();

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private static void testSquare() {
        Point[] points = new Point[] { new Point(1, 1), new Point(1, 2),
                new Point(1, 4), new Point(1, 10), new Point(2, 10),
                new Point(3, 10), new Point(10, 10), new Point(10, 9),
                new Point(10, 7), new Point(10, 1), new Point(8, 1),
                new Point(7, 1) };

        StdDraw.show(0);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);
        StdDraw.setPenColor(0, 0, 0);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        assert collinear.numberOfSegments() == 4;
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private static void testVerticalLine() {
        Point[] points = new Point[] { new Point(1, 1), new Point(1, 2),
                new Point(1, 4), new Point(1, 10) };

        StdDraw.show(0);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);
        StdDraw.setPenColor(0, 0, 0);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        assert collinear.numberOfSegments() == 1;
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private static void test2() {
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1),
                new Point(3, 2), new Point(3, 3) };
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        assert collinear.numberOfSegments() == 0;
    }

    private static void test1() {
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1),
                new Point(2, 2), new Point(3, 3) };
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        assert collinear.numberOfSegments() == 1;
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }

    }

    private class TempLineSegment {
        private Point p;
        private Point q;

        // public int hashCode() {
        // // throw new UnsupportedOperationException();
        // return (new Integer(p.hashCode()).toString() + new Integer(
        // q.hashCode()).toString()).hashCode();
        // }
        //
        // public boolean equals(Object obj) {
        // TempLineSegment other = obj == null ? null : (TempLineSegment) obj;
        // if (other == null) {
        // return false;
        // }
        //
        // return this.p.compareTo(other.p) == 0
        // && this.q.compareTo(other.q) == 0;
        // }
    }

}
