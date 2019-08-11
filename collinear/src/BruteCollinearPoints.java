import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private final List<LineSegment> segments = new LinkedList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int n = j + 1; n < points.length; n++) {
                    for (int m = n + 1; m < points.length; m++) {
                        Comparator<Point> c = points[i].slopeOrder();
                        int cmp1 = c.compare(points[j], points[n]);
                        int cmp2 = c.compare(points[n], points[m]);
                        if (cmp1 == 0 && cmp2 == 0) {
                            Point[] linePoints = new Point[4];
                            linePoints[0] = points[i];
                            linePoints[1] = points[j];
                            linePoints[2] = points[n];
                            linePoints[3] = points[m];
                            Arrays.sort(linePoints);
                            segments.add(new LineSegment(linePoints[0], linePoints[3]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] copy = segments.toArray(new LineSegment[segments.size()]);
        return copy;
    }

    /**
     * Throw a java.lang.IllegalArgumentException if the argument to the constructor is null,
     * if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     * @param points
     */
    private void validate(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("input can not be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("points can not be null");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int cmp = points[i].compareTo(points[j]);
                if (cmp == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
            }
        }
    }
}