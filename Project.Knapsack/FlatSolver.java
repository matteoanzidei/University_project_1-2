
import java.util.*;

public class FlatSolver {
    /**
     * All the boxes and pentominoes' shapes
     */
    public static int[][] F = {
            {0, 1, 1},
            {1, 1, 0},
            {0, 1, 0}};

    public static int[][] I = {
            {2},
            {2},
            {2},
            {2},
            {2}};

    public static int[][] L = {
            {3, 0},
            {3, 0},
            {3, 0},
            {3, 3}};

    public static int[][] N = {
            {0, 4},
            {0, 4},
            {4, 4},
            {4, 0}};

    public static int[][] P = {
            {5, 5},
            {5, 5},
            {5, 0}};

    public static int[][] T = {
            {6, 6, 6},
            {0, 6, 0},
            {0, 6, 0}};

    public static int[][] U = {
            {7, 0, 7},
            {7, 7, 7}};

    public static int[][] V = {
            {0, 0, 8},
            {0, 0, 8},
            {8, 8, 8}};

    public static int[][] W = {
            {0, 0, 9},
            {0, 9, 9},
            {9, 9, 0}};

    public static int[][] X = {
            {0, 10, 0},
            {10, 10, 10},
            {0, 10, 0}};

    public static int[][] Y = {
            {0, 11},
            {11, 11},
            {0, 11},
            {0, 11}};

    public static int[][] Z = {
            {12, 12, 0},
            {0, 12, 0},
            {0, 12, 12}};

    public PentoPiece[] convertedPieces;
    public PentoSolver solver;

    public List<Parcel> pieces;
    public int[][][] space;

    /**
     * Solver class for the Divide and Conquer algorithm
     * @param pieces
     * @param space
     */
    public FlatSolver(List<Parcel> pieces, int[][][] space) {

        this.pieces = pieces;
        this.space = space;
        this.convertedPieces = fetchPiece(pieces);
        this.solver = new PentoSolver();
    }

    /**
     * Tranforms 3D Parcels to 2D PentoPiece (Pentominoes)
     * @param x
     * @return
     */
    public static PentoPiece[] fetchPiece(List<Parcel> x) {

        List<PentoPiece> y = new ArrayList<>();
        for (Parcel p : x) {
            switch(Knapsack.pieceIds[p.identifier]) {
                case "F": y.add(new PentoPiece(F)); break;
                case "I": y.add(new PentoPiece(I)); break;
                case "L": y.add(new PentoPiece(L)); break;
                case "N": y.add(new PentoPiece(N)); break;
                case "P": y.add(new PentoPiece(P)); break;
                case "T": y.add(new PentoPiece(T)); break;
                case "U": y.add(new PentoPiece(U)); break;
                case "V": y.add(new PentoPiece(V)); break;
                case "W": y.add(new PentoPiece(W)); break;
                case "X": y.add(new PentoPiece(X)); break;
                case "Y": y.add(new PentoPiece(Y)); break;
                case "Z": y.add(new PentoPiece(Z)); break;
            }
        } return y.toArray(new PentoPiece[x.size()]);
    }

    /**
     * Attempts to ffind a solution for any side of the cargo
     * Stores answer in the space field
     */
    public void launch() {

        findDivisions(this.space.length, this.space[0].length, this.space[0][0].length);
        findDivisions(this.space[0].length, this.space[0][0].length, this.space.length);
        findDivisions(this.space[0][0].length, this.space.length, this.space[0].length);
    }

    /**
     * Finds potential divisions of the 2D board given by first and second
     * Applies first block code to solve 2D pentomino problem
     * @param first
     * @param second
     * @param third
     */
    public void findDivisions(int first, int second, int third) {

        int num_x, num_y;
        for (int i = 1; i <= first; i++) {
            for (int j = 1; j <= second; j++) {
                if (first % i == 0 && second % j == 0) {
                    this.solver.solve(convertedPieces, new int[i][j]);
                    if (this.solver.solution != null) {
                        num_x = first / i;
                        num_y = second / j;
                        copyArrayNTimes(this.space, i, j);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Translates the 2D solution to fill the target 3D space fully
     * @param target
     * @param width
     * @param height
     */
    public void copyArrayNTimes(int[][][] target, int width, int height) {

        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target[0].length; j++) {
                for (int k = 0; k < target[0][0].length; k++) {
                    target[i][j][k] = this.solver.solution[i %width][j % height];
                }
            }
        }
    }

    public int[][][] getSolution() {return this.space;}

}
