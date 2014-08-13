public class Solver {
    
    private class SearchNode implements Comparable<Object> {
        private SearchNode board;
        private SearchNode moves;
        private SearchNode prev;
        
        @Override
        public int compareTo(Object o) {
            // TODO Auto-generated method stub
            return 0;
        }
        
        
        public SearchNode(){
        }
    }
    
    /*
     * Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        MinPQ<SearchNode> priority = new MinPQ<SearchNode>();
        SearchNode node1 = new SearchNode();
        initial.toString();
        initial.neighbors();
        
    }
    
    /*
     * Is the initial board solvable?
     */
    public boolean isSolvable() {
        return false;
    }
    
    /*
     * Min number of moves to solve initial board; -1 if no solution
     */
    public int moves() {
        return 0;
    }
    
    /*
     * sequence of boards in a shortest solution; null if no solution
     */
    public Iterable<Board> solution() {
        return null;
    }
    
    /*
     * Solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
        
     // create initial board from file
//        In in = new In(args[0]);
//        int N = in.readInt();
//        int[][] blocks = new int[N][N];
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++)
//                blocks[i][j] = in.readInt();
        
        int[][] blocks = {{4,1,3},{0,2,6},{7,5,8}};
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
    }
}