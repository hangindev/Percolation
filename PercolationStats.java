import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    
    private double[] fraction;
    
    private int mtrials;
    
    // perform trials independent experiments on an n-by-n grid 
    public PercolationStats(int n, int trials) {
        
        if (n < 1 || trials < 1)
        {
            throw new IllegalArgumentException("Arguments n and trails must be greater than 1");
        }
        
        mtrials = trials;
        fraction = new double[trials];
        
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            int openSites = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n+1);
                int j = StdRandom.uniform(1, n+1);
                
                if (!perc.isOpen(i, j))
                {
                    perc.open(i, j);
                    openSites++;
                }
            }
            fraction[trial] = (double) openSites/(double) (n*n);
        }
    }  
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }                         
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    } 
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(mtrials);
    } 
    
    // high endpoint of 95% confidence interval
    public double confidenceHi()    {
        return mean() + 1.96*stddev()/Math.sqrt(mtrials);
    }               
    
    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trails);
        StdOut.println("mean = "+ stats.mean());
        StdOut.println("standard deviation = "+ stats.stddev());
        StdOut.println("95% confidence interval = "+ stats.confidenceLo() + " , " + stats.confidenceHi());
  double time = stopwatch.elapsedTime();
  StdOut.println("elapsed time = "+ time);
 }
    
}