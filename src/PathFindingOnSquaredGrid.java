import javafx.scene.paint.Stop;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class PathFindingOnSquaredGrid {

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;
    
        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }
    	
        return full;
    }
    
    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row1

        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i+1, j);   // down
        flow(open, full, i, j+1);   // right
        flow(open, full, i, j-1);   // left
        flow(open, full, i-1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;
    	
        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N-1][j]) return true;
        }
    	
        return false;
    }
    
 // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;
    	
        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
        	if (full[N-1][j]) {
        		// StdOut.println("Hello");
        		directPerc = 1;
        		int rowabove = N-2;
        		for (int i = rowabove; i >= 0; i--) {
        			if (full[i][j]) {
        				// StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
        				directPerc++;
        			}
        			else break;
        		}
        	}
        }
    	
        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true; 
        else return false;
    }
    
    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                	StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }

    //draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                	if ((i == x1 && j == y1) ||(i == x2 && j == y2)) {
                		StdDraw.circle(j, N-i-1, .5);
                	}
                	else StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }
    
    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }



    // test client
    public static void main(String[] args) {
        ArrayList<Dijkstra.Node> path=null;
        Stopwatch timer;
        long[] timeSet=new long[3];
        // boolean[][] open = StdArrayIO.readBoolean2D();


    	// The following will generate a 10x10 squared grid with relatively few obstacles in it
    	// The lower the second parameter, the more obstacles (black cells) are generated
    	boolean[][] randomlyGenMatrix = random(10, 0.8);
    	
    	StdArrayIO.print(randomlyGenMatrix);
    	show(randomlyGenMatrix, true);
    	
    	System.out.println();
    	//System.out.println("The system percolates: " + percolates(randomlyGenMatrix));
    	StdDraw.textLeft(10-0.4, (10/2)-2,"");
        StdDraw.textRight(10-0.4, (10/2)-2,"");
    	System.out.println();
    	//System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
    	System.out.println();
    	
    	// Reading the coordinates for points A and B on the input squared grid.
    	
    	// THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
    	// Start the clock ticking in order to capture the time being spent on inputting the coordinates
    	// You should position this command accordingly in order to perform the algorithmic analysis
    	Stopwatch timerFlow = new Stopwatch();
    	
    	Scanner in = new Scanner(System.in);
        System.out.println("Enter i for (Y) A > ");
        int Ai = in.nextInt();
        
        System.out.println("Enter j for (X) A > ");
        int Aj = in.nextInt();
        
        System.out.println("Enter i for (Y) B > ");
        int Bi = in.nextInt();
        
        System.out.println("Enter j for (X) B > ");
        int Bj = in.nextInt();

       // if(Bj==Aj|| Ai==Bi){
         //   System.out.println("Input point is a the same point");
           // return;
        //}

     // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
    	// You should position this command accordingly in order to perform the algorithmic analysis
    	StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
        
        // System.out.println("Coordinates for A: [" + Ai + "," + Aj + "]");
        // System.out.println("Coordinates for B: [" + Bi + "," + Bj + "]");
        
        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

        StdDraw.setPenColor(Color.GREEN);

        int selection=1;
        while(selection!=0){
            System.out.println("Enter one of following real distance: "+"\n\t1. Manhattan Distance"+"\n\t2. Euclidean distance"+"\n\t3. Chebyshev distance"+"\n\t0. Exit");
            selection=in.nextInt();
            if(selection==1){
                StdDraw.setPenColor(Color.red);
            }
            if(selection==2){
                StdDraw.setPenColor(Color.green);
            }
            if(selection==3){
                StdDraw.setPenColor(Color.orange);
            }
            if(selection==0){
                System.exit(0);
            }
            timer=new Stopwatch();
            path = new Dijkstra(selection).distance(randomlyGenMatrix, Ai, Aj, Bi, Bj);
            System.out.println("Elapsed time"+timer.elapsedTime());
            for(int i= 0;i<path.size()-1;i++){
                    Dijkstra.Node node = path.get(i);
                    Dijkstra.Node nextNode = path.get(i+1);

                    StdDraw.line(node.y,9- node.x, nextNode.y,9- nextNode.x);
            }

        }


    }

}



