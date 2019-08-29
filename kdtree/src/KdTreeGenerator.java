/******************************************************************************
 *  Compilation:  javac KdTreeGenerator.java
 *  Execution:    java KdTreeGenerator n
 *  Dependencies: 
 *
 *  Creates n random points in the unit square and print to standard output.
 *
 *  % java KdTreeGenerator 5
 *  0.195080 0.938777
 *  0.351415 0.017802
 *  0.556719 0.841373
 *  0.183384 0.636701
 *  0.649952 0.237188
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeGenerator {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        KdTree p = new KdTree();
        Point2D p1 = new Point2D(2, 3);
        Point2D p2 = new Point2D(4, 2);
        Point2D p3 = new Point2D(4, 5);
        Point2D p4 = new Point2D(3, 3);
        Point2D p5 = new Point2D(4, 4);
        Point2D p6 = new Point2D(1, 5);
        p.insert(p1);
        p.insert(p2);
        p.insert(p3);
        p.insert(p4);
        p.insert(p5);
        p.insert(p6);
        StdOut.println(p.size());
    }
}
