import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final int topSite;
    private final int bottomSite;
    private final boolean[] open;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF noBottom;
    private int openSite = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        topSite = n * n;
        bottomSite = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        noBottom = new WeightedQuickUnionUF(n * n + 1);

        open = new boolean[n * n];
        for (int i = 0; i < open.length; i++) {
            open[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int site = xyTo1D(row, col);
        if (!isOpen(row, col)) {
            open[site] = true;
            openSite += 1;

            if (row == 1) { // union to top
                uf.union(site, topSite);
                noBottom.union(site, topSite);
            }
            if (row == this.n) { // union to bottom
                uf.union(site, bottomSite);
            }

            if (row > 1 && isOpen(row - 1, col)) { // union to up site
                uf.union(site, xyTo1D(row - 1, col));
                noBottom.union(site, xyTo1D(row - 1, col));
            }
            if (row < this.n && isOpen(row + 1, col)) { // union to down site
                uf.union(site, xyTo1D(row + 1, col));
                noBottom.union(site, xyTo1D(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) { // union to left site
                uf.union(site, xyTo1D(row, col - 1));
                noBottom.union(site, xyTo1D(row, col - 1));
            }
            if (col < this.n && isOpen(row, col + 1)) { // union to right site
                uf.union(site, xyTo1D(row, col + 1));
                noBottom.union(site, xyTo1D(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int site = xyTo1D(row, col);
        return open[site];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int site = xyTo1D(row, col);
        return noBottom.connected(site, topSite);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(topSite, bottomSite);
    }

    private int xyTo1D(int row, int col) {
        checkArgument(row);
        checkArgument(col);
        return (row - 1) * this.n + (col - 1);
    }

    private void checkArgument(int argument) {
        if (argument <= 0 || argument > this.n) {
            throw new IllegalArgumentException();
        }
    }

    /* test client (optional)
    public static void main(String[] args) {
    }*/
}
