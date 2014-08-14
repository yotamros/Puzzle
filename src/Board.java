import java.util.Arrays;
import java.util.Stack;

public class Board {
    
    private int dim;
    int[][] boardBlocks;
    int[][] goalBoard;
    
    /*
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        
        dim = blocks.length;
        boardBlocks = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                boardBlocks[i][j] = blocks[i][j];
            }
        }
        
        goalBoard = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == dim-1 && j == dim-1) {
                    goalBoard[i][j] = 0;
                } else {
                    goalBoard[i][j] = (i*dim) + j+1;
                }
            }
        }
    }
    
    /*
     * Board dimension N
     */
    public int dimension() {
        return dim;
    }
    
    /*
     * Number of blocks out of place
     */
    public int hamming() {
        int misplacedBlocks = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (boardBlocks[i][j] == 0) {
                } else {
                    if (boardBlocks[i][j] != goalBoard[i][j]) {
                        misplacedBlocks++;
                    } 
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
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (boardBlocks[i][j] == 0) {
                } else {
                    if (boardBlocks[i][j] != goalBoard[i][j]) {
                        int xGoal = boardBlocks[i][j]/dim;
                        int yGoal = boardBlocks[i][j] - (dim*xGoal) -1;
                        misplacedBlocks += Math.abs(xGoal-i) + Math.abs(yGoal-j);
                    }
                }
            }
        }
        return misplacedBlocks;
    }
    
    /*
     * Is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    /*
     * A board obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
        int rowOfBlank = 0;
        int rowToEdit = 0;
        int [][] twin = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                twin[i][j] = boardBlocks[i][j];
                if(boardBlocks[i][j] == 0) {
                    rowOfBlank = i;
                }
            }
        }
        if (rowOfBlank > 0) {
            rowToEdit = rowOfBlank -1;
        } else {
            rowToEdit = rowOfBlank +1;
        }
        int blockA = twin[rowToEdit][0];
        int blockB = twin[rowToEdit][1];
        twin[rowToEdit][0] = blockB;
        twin[rowToEdit][1] = blockA;
        Board twinBoard = new Board(twin);
        twinBoard.toString();
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
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        Board newThat = (Board) that;
        if (this.dim != newThat.dim) {
            return false;
        }
        if (!Arrays.deepEquals(this.boardBlocks, newThat.boardBlocks)) {
            return false;
        }
        return true;
    }
    
    /*
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> queue = new Stack<Board>();
        
        int[] zero = this.findZero();
        int row = zero[0];
        int col = zero[1];
        
        if (row > 0) {
            //swap with block below
            queue.push(new Board(this.swap(this.copy(), row, col, row-1, col)));
        }
        if (row < dim-1) {
            //swap with block above
            queue.push(new Board(this.swap(this.copy(), row, col, row+1, col)));
        }
        if (col > 0) {
            //swap with block to left
            queue.push(new Board(this.swap(this.copy(), row, col, row, col-1)));
        }
        if (col < dim-1) {
            //swap with block to right
            queue.push(new Board(this.swap(this.copy(), row, col, row, col+1)));
        }
        return queue;
    }
    
    private int[][] copy() {
        int[][] blocksCopy = new int[dim][dim];
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
     * Find the location of zero.  Return an array with two values for i and j.
     */
    private int[] findZero() {
        int[] zeroLocation = {-1, -1};
        for (int i = 0; i < boardBlocks.length; i++) {
            for (int j = 0; j < boardBlocks.length; j++) {
                if(boardBlocks[i][j] == 0) {
                    zeroLocation[0] = i;
                    zeroLocation[1] = j;
                    return zeroLocation;
                }
            }
        }
        return zeroLocation;
    }
    
    /*
     * (non-Javadoc) String representation of the board 
     * (in the output format specified below)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dim + "\n");
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