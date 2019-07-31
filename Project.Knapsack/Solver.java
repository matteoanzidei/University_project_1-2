
import java.util.*;

/**
 *
 * @outdated The code was used for testing purposes and is currently replaced by Shelfsort
 */

public class Solver {

    public List<Parcel> pieces;
    public int[][][] space, fillSolution;
    public int maxValue;

    /**
     * Class containing the solving algorithms
     * @param pieces
     * @param space
     */
    public Solver(List<Parcel> pieces, int[][][] space) {

        this.pieces = pieces;
        this.space = space;
    }

    /**
     * Calls the Recursive and Backtracking algorithm
     * @return solution
     */
    public int[][][] solveFill() {

        fillSolution = null;
        fillDLX(space, pieces);
        return fillSolution;
    }

    /**
     * Calls the Greedy algorithm
     * @return solution
     */
    public int[][][] solveMax() {

        weightSort(pieces);
        return maximize(space, pieces);
    }

    /**
     * Recursive and Backtracking algorithm
     * @param space
     * @param pieces
     */
    public void fillDLX(int[][][] space, List<Parcel> pieces) {

        for (int x = 0; x < space.length; x++) {
            for (int y = 0; y < space[0].length; y++) {
                for (int z = 0; z < space[0][0].length; z++) {

                    if (space[x][y][z] == 0) {
                        for (Parcel p : pieces) {
                            for (Parcel r : p.getRotations()) {
                                if (checkPiece(x, y, z, r, space)) {

                                    int[][][] newSpace = addPiece(x, y, z, r, space);
                                    fillDLX(newSpace, pieces);

                                    if (fillSolution == null && validate(newSpace)) {
                                        fillSolution = newSpace;
                                        return;
                                    }
                                }
                            }
                        } return;
                    }

                }
            }
        }

    }

    /**
     * Greedy algorithm
     * @param space
     * @param pieces
     * @return solution
     */
    public int[][][] maximize(int[][][] space, List<Parcel> pieces) {

        int count = 0;
        for (int x = 0; x < space.length; x++) {
            for (int y = 0; y < space[0].length; y++) {
                for (int z = 0; z < space[0][0].length; z++) {
                    tries:
                    if (space[x][y][z] == 0)
                        for (Parcel p : pieces) {
                            for (Parcel r : p.getRotations()) {
                                if (checkPiece(x, y, z, r, space)) {
                                    insert(x, y, z, r, space);

                                    count+= p.weight;
                                    break tries;
                                }
                            }
                        }

                }
            }
        } maxValue = count;
        return space;
    }

    /**
     * Checks if a given Parcel fits in a given Board at a given position
     * @param x
     * @param y
     * @param z
     * @param p
     * @param board
     * @return boolean
     */
    public static boolean checkPiece(int x, int y, int z, Parcel p, int[][][] board) {

        if (x - p.findStart().getX() < 0 || y - p.findStart().getY() < 0 || z - p.findStart().getZ() < 0 ||
                x + p.getShape().length  - p.findStart().getX() > board.length ||
                y + p.getShape()[0].length - p.findStart().getY() > board[0].length ||
                z + p.getShape()[0][0].length - p.findStart().getZ() > board[0][0].length) return false;

        for (int i = 0; i < p.getShape().length; i++) {
            for (int j = 0; j < p.getShape()[0].length; j++) {
                for (int k = 0; k < p.getShape()[0][0].length; k++)
                    if (p.getShape()[i][j][k] != 0 && board[(int) (x + i - p.findStart().getX())]
                            [(int) (y + j - p.findStart().getY())][(int) (z + k - p.findStart().getZ())] != 0)
                        return false;
            }
        } return true;
    }

    /**
     * Places a Parcel on a given board at a given position
     * @param x
     * @param y
     * @param z
     * @param p
     * @param board
     * @return board with the new Parcel added
     */
    public static int[][][] addPiece(int x, int y, int z, Parcel p, int[][][] board) {

        int[][][] newBoard = new int[board.length][board[0].length][board[0][0].length];
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                for (int k = 0; k < newBoard[0][0].length; k++)
                    newBoard[i][j][k] = board[i][j][k];
            }
        }

        for (int i = 0; i < p.getShape().length; i++) {
            for (int j = 0; j < p.getShape()[0].length; j++) {
                for (int k = 0; k < p.getShape()[0][0].length; k++)
                    if (p.getShape()[i][j][k] != 0)
                        newBoard[(int) (x + i - p.findStart().getX())][(int) (y + j - p.findStart().getY())]
                                [(int) (z + k - p.findStart().getZ())] = p.getShape()[i][j][k];
            }
        } return newBoard;
    }

    /**
     * Places a given Parcel in a given board at a given position
     * Used in the recursive algorithm to increase memory efficiency
     * @param x
     * @param y
     * @param z
     * @param p
     * @param board
     */
    public static void insert(int x, int y, int z, Parcel p, int[][][] board) {

        for (int i = 0; i < p.getShape().length; i++) {
            for (int j = 0; j < p.getShape()[0].length; j++) {
                for (int k = 0; k < p.getShape()[0][0].length; k++)
                    if (p.getShape()[i][j][k] != 0)
                        board[(int) (x + i - p.findStart().getX())][(int) (y + j - p.findStart().getY())]
                                [(int) (z + k - p.findStart().getZ())] = p.getShape()[i][j][k];
            }
        }
    }

    /**
     * Checks if a given space is full
     * @param space
     * @return boolean
     */
    public static boolean validate(int[][][] space) {

        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[0].length; j++) {
                for (int k = 0; k < space[0][0].length; k++)
                    if (space[i][j][k] == 0) return false;
            }
        } return true;
    }

    /**
     * Sorts a given List of Parcels by value
     * @param pieces
     */
    public static void weightSort(List<Parcel> pieces){

        for (int i = 0; i < pieces.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < pieces.size(); j++)
                if (pieces.get(j).weight > pieces.get(index).weight)
                    index = j;

            Parcel biggest = pieces.get(index);
            pieces.set(index, pieces.get(i));
            pieces.set(i, biggest);
        }
    }
    public int getMaxValue(){
      return this.maxValue;
    }

}
