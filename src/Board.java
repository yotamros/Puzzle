import java.util.Stack;

public class Board {
    
    int[][] boardBlocks;
    int[][] goalBoard;
    int zeroI;
    int zeroJ;
    
    /*
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
       
        int dim = blocks.length;
        boardBlocks = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
                boardBlocks[i][j] = blocks[i][j];
            }
        }
    }
    
    /*
     * Board dimension N
     */
    public int dimension() {
        return boardBlocks.length;
    }
    
    /*
     * Number of blocks out of place
     */
    public int hamming() {
        int misplacedBlocks = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (boardBlocks[i][j] != 0 && boardBlocks[i][j] != i*dimension()+j+1) {
                    misplacedBlocks++;
                }
            }
        }
        return misplacedBlocks;
    }
    
    /*
     * Sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int misplacedBlocks = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (boardBlocks[i][j] != 0 && boardBlocks[i][j] != i*dimension()+j+1) {
                    int xGoal = boardBlocks[i][j]/dimension();
                    int yGoal = boardBlocks[i][j] - (dimension()*xGoal) -1;
                    misplacedBlocks += Math.abs(xGoal-i) + Math.abs(yGoal-j);
                }
            }
        }
        return misplacedBlocks;
    }
    
    /*
     * Is this board the goal board?
     */
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (boardBlocks[i][j] != 0 && boardBlocks[i][j] != i*dimension()+j+1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
     * A board obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
        int rowToEdit = 0;
        int[][] twin = copy();
        if (zeroI > 0) {
            rowToEdit = zeroI -1;
        } else {
            rowToEdit = zeroI +1;
        }
        int blockA = twin[rowToEdit][0];
        int blockB = twin[rowToEdit][1];
        twin[rowToEdit][0] = blockB;
        twin[rowToEdit][1] = blockA;
        Board twinBoard = new Board(twin);
        return twinBoard;
    }
    
    /*
     * (non-Javadoc) Does this board equal y?
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Board)) {
            return false;
        }
        Board newThat = (Board)that;
        if (this.dimension() != newThat.dimension()) {
            return false;
        }
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (this.boardBlocks[i][j] != newThat.boardBlocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighborsQ = new Stack<Board>();
        
        if (zeroI > 0) {
            //swap with block above
            neighborsQ.push(new Board(swap(copy(), zeroI, zeroJ, zeroI-1, zeroJ)));
        }
        if (zeroI < dimension()-1) {
            //swap with block below
            neighborsQ.push(new Board(swap(copy(), zeroI, zeroJ, zeroI+1, zeroJ)));
        }
        if (zeroJ > 0) {
            //swap with block to left
            neighborsQ.push(new Board(swap(copy(), zeroI, zeroJ, zeroI, zeroJ-1)));
        }
        if (zeroJ < dimension()-1) {
            //swap with block to right
            neighborsQ.push(new Board(swap(copy(), zeroI, zeroJ, zeroI, zeroJ+1)));
        }
        return neighborsQ;
    }
    
    private int[][] copy() {
        int[][] blocksCopy = new int[dimension()][dimension()];
        for (int i = 0; i < blocksCopy.length; i++) {
            for (int j = 0; j < blocksCopy.length; j++) {
                blocksCopy[i][j] = boardBlocks[i][j];
            }
        }
        return blocksCopy;
    }
    
    private int[][] swap(int[][] blocksCopy, int fromRow, int fromCol, 
                        int toRow, int toCol) {
        blocksCopy[toRow][toCol] = boardBlocks[fromRow][fromCol];
        blocksCopy[fromRow][fromCol] = boardBlocks[toRow][toCol];
        return blocksCopy;
    }
    
    /*
     * (non-Javadoc) String representation of the board 
     * (in the output format specified below)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension() + "\n");
        for (int i = 0; i < boardBlocks.length; i++) {
            for (int j = 0; j < boardBlocks.length; j++) {
                    sb.append(String.format("%4s", boardBlocks[i][j]));
            }
            sb.append("\n");
        }
        System.out.println(sb);
        return sb.toString();
    }
}