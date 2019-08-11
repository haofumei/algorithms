import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;


public class FastCollinearPoints {

    private final List<LineSegment> segments = new LinkedList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validate(points);
        for (int i = 0; i < points.length; i++) {
            Point[] copy = Arrays.copyOf(points, points.length);
            Comparator<Point> c = points[i].slopeOrder();
            Arrays.sort(copy, c);
            findLine(copy[0], copy);
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

    private void findLine(Point p, Point[] points) {
        if (points.length < 3) {
            return;
        }
        Stack<Double> temp = new Stack<>();
        for (int i = points.length - 1; i > 0; i--) {
            temp.push(p.slopeTo(points[i]));
        }
        double s = temp.pop();
        int count = 1;
        int from = 1;
        while (temp.size() > 1) {
            while (Double.compare(s, temp.peek()) == 0) {
                count += 1;
                temp.pop();
                if (temp.isEmpty()) {
                    break;
                }
            }
            if (count >= 3) {
                Point[] line = new Point[count + 1];
                line[0] = p;
                System.arraycopy(points, from, line, 1, count);
                Arrays.sort(line);
                int cmp = p.compareTo(line[0]);
                if (cmp == 0) {
                    segments.add(new LineSegment(p, line[count]));
                }
            }
            if (temp.isEmpty()) {
                break;
            }
            s = temp.pop();
            from = from + count;
            count = 1;
        }
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