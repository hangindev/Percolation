import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int gridLength;
 
    private boolean[] isOpen;
 
    private int virtualTopIndex;
    private int virtualBottomIndex;
 
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullness;
 
    private int siteIndex(int i, int j) {
        checkBound(i, j);
        return (i-1)*gridLength+j;
    }
 
    private void checkBound(int i, int j) {
        if (i > gridLength || i < 1)
        {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j > gridLength || j < 1)
        {
            throw new IndexOutOfBoundsException("column index j out of bounds");
        }
    }
 
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1)
        {
            throw new IllegalArgumentException();
        }
        gridLength = n;
        int arraySize = n*n+2;
        isOpen = new boolean[arraySize];
        virtualTopIndex = 0;
        virtualBottomIndex = arraySize-1;
        
        isOpen[virtualTopIndex] = true;
        isOpen[virtualBottomIndex] = true;
        
        percolation = new WeightedQuickUnionUF(arraySize);
        fullness = new WeightedQuickUnionUF(arraySize);
        
        for (int j = 1; j < gridLength + 1; j++)
        {
            int i = 1;
            percolation.union(virtualTopIndex, siteIndex(i, j));
            fullness.union(virtualTopIndex, siteIndex(i, j));
            
            i = gridLength;
            percolation.union(virtualBottomIndex, siteIndex(i, j));
        }
    };
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkBound(i, j);
        int siteIndex = siteIndex(i, j);
        if (!isOpen[siteIndex])
        {
            isOpen[siteIndex] = true;
            
            // connect with left neighbor if left neighbor is openned
            if (j > 1 && isOpen(i, j-1))
            {
                percolation.union(siteIndex(i, j-1), siteIndex(i, j));
                fullness.union(siteIndex(i, j-1), siteIndex(i, j));
            }
            
            // connect with right neighbor if left neighbor is openned
            if (j < gridLength  && isOpen(i, j+1))
            {
                percolation.union(siteIndex(i, j+1), siteIndex(i, j));
                fullness.union(siteIndex(i, j+1), siteIndex(i, j));
            }
            	
			// connect with above neighbor if left neighbor is openned
            if (i > 1  && isOpen(i-1, j))
            {
                percolation.union(siteIndex(i-1, j), siteIndex(i, j));
                fullness.union(siteIndex(i-1, j), siteIndex(i, j));
            }
            
            // connect with below neighbor if left neighbor is openned
            if (i < gridLength && isOpen(i+1, j))
            {
                percolation.union(siteIndex(i+1, j), siteIndex(i, j));
                fullness.union(siteIndex(i+1, j), siteIndex(i, j));
            }
        }
    };
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return isOpen[siteIndex(i, j)];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return (fullness.connected(virtualTopIndex, siteIndex(i, j)) 
                && isOpen(i, j));
    }
    
    // does the system percolate?
    public boolean percolates() {
        if (gridLength > 1)
        {
            return (percolation.connected(virtualTopIndex, virtualBottomIndex));
        } 
        else
        {
            return (isOpen(1, 1));
        }
    }
    
    /** test client (optional)
    public static void main(String[] args) 
    {
        Percolation percolation = new Percolation(1);
        StdOut.println(percolation.percolates());
        percolation.open(0,0);
        StdOut.println(percolation.percolates());
        Percolation percolation2 = new Percolation(3);
        StdOut.println(percolation2.percolates());
        percolation2.open(1,1);
        StdOut.println(percolation2.percolates());
        percolation2.open(2,1);
        StdOut.println(percolation2.percolates());
    }*/
    
}