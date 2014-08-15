import java.util.Stack;

public class Solver {
    Node goalBoard = null;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node prev;

        public Node(Board board, Node prev) {
            this.board = board;
            this.prev = prev;
            if (prev == null) {
                this.moves = 0;
            } else {
                this.moves = prev.moves + 1;
            }
        }

        public int compareTo(Node that) {
            return (this.priority() - that.priority());
        }

        private int priority() {
            int priority = this.board.manhattan() + this.moves;
            return priority;
        }
    }
    
    /*
     * Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        Board twin = initial.twin();
        // Create a priority queue and insert the initial and twin nodes
        MinPQ<Node> priority = new MinPQ<Node>();
        Node node = new Node(initial, null);
        Node twinNode = new Node(twin, null);
        priority.insert(node);
        priority.insert(twinNode);

         // Find the node with min priority. Check if this node is the goal board.
         // Create neighbors of this node, insert them to the queue and deque 
         // the node.
        while (!(priority.min().board.isGoal())) {
            Iterable<Board> neighborsQ = priority.min().board.neighbors();
            Node least = priority.delMin();
            for (Board board : neighborsQ) {
                if (least.prev == null || !least.prev.board.equals(board)) {
                    priority.insert(new Node(board, least));
                }
            }
        }
        goalBoard = priority.min();
    }

    /*
     * Is the initial board solvable?
     */
    public boolean isSolvable() {
        return goalBoard != null;
    }

    /*
     * Min number of moves to solve initial board; -1 if no solution
     */
    public int moves() {
        if (goalBoard == null) {
            return -1;
        } else {
            return goalBoard.moves;
        }
    }

    /*
     * sequence of boards in a shortest solution; null if no solution
     */
    public Iterable<Board> solution() {
        if (goalBoard == null) {
            return null;
        } else {
            Stack<Board> solution = new Stack<Board>();
            for (Node node = goalBoard; node != null; node = node.prev) {
                solution.add(node.board);
            }
            return solution;
        }
    }
    
    /*
     * Solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
        System.out.println();

         // Create initial board from file
         In in = new In(args[0]);
         int N = in.readInt();
         int[][] blocks = new int[N][N];
         for (int i = 0; i < N; i++)
         for (int j = 0; j < N; j++)
         blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            board.toString();
        }
    }
}