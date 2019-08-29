import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

public class KdTree {

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insert(root, p, r, true);
    }

    private Node insert(Node x, Point2D inP, RectHV rect, boolean isX) {
        if (x == null) {
            size += 1;
            return new Node(inP, rect);
        }
        double cmp = compare(inP, x.p, isX);
        if (cmp < 0) {
            if (x.left == null) {
                RectHV newR = resizeL(rect, x.p, isX);
                x.left = insert(x.left, inP, newR, !isX);
            } else {
                x.left = insert(x.left, inP, x.left.r, !isX);
            }
        } else if (cmp > 0) {
            if (x.right == null) {
                RectHV newR = resizeR(rect, x.p, isX);
                x.right = insert(x.right, inP, newR, !isX);
            } else {
                x.right = insert(x.right, inP, x.right.r, !isX);
            }
        } else {
            x.p = inP;
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D point) {
        validate(point);
        Node pointer = root;
        boolean isX = true;
        while (pointer != null) {
            double cmp = compare(point, pointer.p, isX);
            if (cmp < 0) {
                pointer = pointer.left;
            } else if (cmp > 0) {
                pointer = pointer.right;
            } else {
                return true;
            }
            isX = !isX;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, false);
    }

    private void draw(Node x, boolean isX) {
        if (x != null) {
            StdDraw.setPenRadius(0.001);
            if (isX) {
                StdDraw.setPenColor(Color.BLUE);
                Point2D end1 = new Point2D(x.r.xmax(), x.p.y());
                Point2D end2 = new Point2D(x.r.xmin(), x.p.y());
                x.p.drawTo(end1);
                x.p.drawTo(end2);
            } else {
                StdDraw.setPenColor(Color.RED);
                Point2D end1 = new Point2D(x.p.x(), x.r.ymax());
                Point2D end2 = new Point2D(x.p.x(), x.r.ymin());
                x.p.drawTo(end1);
                x.p.drawTo(end2);
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();

            draw(x.left, !isX);
            draw(x.right, !isX);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        if (root == null) {
            return null;
        }
        Stack<Point2D> points = new Stack<>();
        range(rect, root, points);
        return points;
    }

    private void range(RectHV rect, Node x, Stack<Point2D> points) {
        if (rect.contains(x.p)) {
            points.push(x.p);
        }
        if (x.left != null && rect.intersects(x.left.r)) {
            range(rect, x.left, points);
        }
        if (x.right != null && rect.intersects(x.right.r)) {
            range(rect, x.right, points);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (root == null) {
            return null;
        }
        return nearest(p, root.p, root, true);
    }

    private Point2D nearest(Point2D point, Point2D n, Node x, boolean isX) {
        if (point.distanceSquaredTo(x.p) < n.distanceSquaredTo(point)) {
            n = x.p;
        }
        double cmp = compare(point, x.p, isX);
        if (cmp < 0) {
            if (x.left != null) {
                n = nearest(point, n, x.left, !isX);
            }
            if (x.right != null && x.right.r.distanceSquaredTo(point) < n.distanceSquaredTo(point)) {
                n = nearest(point, n, x.right, !isX);
            }
        }
        if (cmp > 0) {
            if (x.right != null) {
                n = nearest(point, n, x.right, !isX);
            }
            if (x.left != null && x.left.r.distanceSquaredTo(point) < n.distanceSquaredTo(point)) {
                n = nearest(point, n, x.left, !isX);
            }
        }
        return n;
    }

    // throw a java.lang.IllegalArgumentException if any argument is null.
    private void validate(Object that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }
    }


    // compare two points, return a positive double if p1 > p2, and on the contrary...
    // return 1.0 if the compare dimensions are equal.
    private double compare(Point2D p1, Point2D p2, boolean isX) {
        double minusX = p1.x() - p2.x();
        double minusY = p1.y() - p2.y();
        if (isX) {
            if (minusX == 0.0 && minusY != 0.0) {
                return 1.0;
            }
            if (minusX == 0.0 && minusY == 0.0) {
                return 0.0;
            }
            return minusX;
        } else {
            if (minusY == 0.0 && minusX != 0.0) {
                return 1.0;
            }
            if (minusY == 0.0 && minusX == 0.0) {
                return 0.0;
            }
            return minusY;
        }
    }

    // resize the rect if cmp < 0
    private RectHV resizeL(RectHV rect, Point2D pivot, boolean isX) {
        if (isX) {
            return new RectHV(rect.xmin(), rect.ymin(), pivot.x(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), pivot.y());
        }
    }

    // resize the rect if cmp > 0
    private RectHV resizeR(RectHV rect, Point2D pivot, boolean isX) {
        if (isX) {
            return new RectHV(pivot.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), pivot.y(), rect.xmax(), rect.ymax());
        }
    }

    private static class Node {

        private Point2D p;
        private final RectHV r;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.p = point;
            this.r = rect;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        Point2D test = new Point2D(0.58, 0.77);
        System.out.println(kdtree.nearest(test));
        kdtree.draw();
    }
}
