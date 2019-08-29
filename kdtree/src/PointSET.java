import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class PointSET {

    private final SET<Point2D> points;
    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p)) {
            points.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validate(p);
        if (points.contains(p)) {
            return true;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        Stack<Point2D> inPoints = new Stack<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                inPoints.push(p);
            }
        }
        return inPoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (this.isEmpty()) {
            return null;
        }
        Iterator<Point2D> i = points.iterator();
        Point2D near = i.next();
        double dist = near.distanceSquaredTo(p);
        while (i.hasNext()) {
            Point2D temp = i.next();
            double d = temp.distanceSquaredTo(p);
            if (d < dist) {
                near = temp;
                dist = d;
            }
        }
        return near;
    }

    // throw a java.lang.IllegalArgumentException if any argument is null.
    private void validate(Object that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
