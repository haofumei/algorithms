import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final double[] fractions;
    private final int n;
    private final double mean;
    private final double stddev;
    private int time;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        fractions = new double[trials];
        Percolation p;
        boolean[] count = new boolean[n * n];
        time = 0;
        while (time < trials) {
            p = new Percolation(n);
            for (int i = 0; i < count.length; i++) {
                count[i] = false;
            }
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            int site = xyTo1D(row, col);
            while (!p.percolates()) {
                if (count[site]) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                    site = xyTo1D(row, col);
                }
                p.open(row, col);
                count[site] = true;
            }
            fractions[time] = (double) p.numberOfOpenSites() / (n * n);
            time += 1;
        }
        mean = StdStats.mean(fractions); // initiate final variable to save timing
        stddev = StdStats.stddev(fractions);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE * stddev / Math.sqrt(time));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE * stddev / Math.sqrt(time));
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * this.n + (col - 1);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(20, 10);
        System.out.println(test.mean());
        System.out.println(test.mean());
        System.out.println(test.mean());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceLo());
    }
}

