import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segmentsArr;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

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

        List<LineSegment> segmentList = new ArrayList<LineSegment>();
        List<TempLineSegment> tmpList = new ArrayList<TempLineSegment>();

        for (int a = 0; a < length - 3; a++) {

            Point pA = points[a];
            Comparator<Point> cmp = pA.slopeOrder();

            Arrays.sort(points, a + 1, length, cmp); // to index is exclusive

            int i = a + 1;
            double prevSlope = pA.slopeTo(points[i]);
            i++;
            int equalCount = 1;
            Vector<Point> colinVec = new Vector<Point>();
            colinVec.add(pA);
            for (; i < length; i++) {

                Point pI = points[i];
                double currSlope = pA.slopeTo(pI);

                if (isSlopesEqual(currSlope, prevSlope)) {
                    if (equalCount == 1) {
                        colinVec.add(points[i - 1]);
                        equalCount++;
                    }
                    colinVec.add(pI);
                    equalCount++;
                } else {

                    storeColinear(equalCount, colinVec, tmpList, segmentList);

                    colinVec.clear();
                    colinVec.add(pA);
                    equalCount = 1;

                }
                prevSlope = currSlope;
            }

            storeColinear(equalCount, colinVec, tmpList, segmentList);

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
        return Math.abs(s1 - s2) <= 0.0000001
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

    private void storeColinear(int equalCount, Vector<Point> colinVec,
            List<TempLineSegment> tmpList, List<LineSegment> segmentList) {
        if (equalCount >= 4) {
            Point[] collinear = colinVec.toArray(new Point[colinVec.size()]);

            Arrays.sort(collinear);

            LineSegment ls = new LineSegment(collinear[0],
                    collinear[equalCount - 1]);

            TempLineSegment lsTmp = new TempLineSegment();
            lsTmp.p = collinear[0];
            lsTmp.q = collinear[equalCount - 1];

            if (!contains(tmpList, lsTmp)) {
                tmpList.add(lsTmp);
                segmentList.add(ls);
            }
        }
    }

    public static void main(String[] args) {

        // test1();
        // test2();
        // testSquare();
        // testVerticalLine();
        // testInput8();

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
        int count = 0;
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
            count++;
        }
        System.out.println(collinear.segments().length + " " + count);

    }

    private static void testInput8() {
        // Point[] points = new Point[] {
        // new Point(20000, 21000),
        // new Point(3000, 4000),
        // new Point(14000, 15000),
        // new Point(6000, 7000) };

        // Point[] points = new Point[] {
        // new Point(10000, 0),
        // new Point(0, 10000),
        // new Point(3000, 7000),
        // new Point(7000, 3000) };

        Point[] points = new Point[] { new Point(20000, 21000),
                new Point(3000, 4000), new Point(14000, 15000),
                new Point(6000, 7000), new Point(10000, 0),
                new Point(0, 10000), new Point(3000, 7000),
                new Point(7000, 3000) };

        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(0, 0, 0);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // assert collinear.numberOfSegments() == 2;
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

        // Point[] points = new Point[] { new Point(1, 1), new Point(1, 2),
        // new Point(1, 4), new Point(1, 10), new Point(2, 10),
        // new Point(3, 10), new Point(10, 10) };

        StdDraw.show(0);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);
        StdDraw.setPenColor(0, 0, 0);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assert collinear.numberOfSegments() == 1;
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private static void test2() {
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1),
                new Point(3, 2), new Point(3, 3) };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assert collinear.numberOfSegments() == 0;
    }

    private static void test1() {
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1),
                new Point(2, 2), new Point(3, 3) };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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
