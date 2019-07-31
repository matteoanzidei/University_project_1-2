
import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Parcel {

    /**
     * All pieces' 3D representations
     */
    public static int[][][] F = {{
            {0, 1, 1},
            {1, 1, 0},
            {0, 1, 0}}};

    public static int[][][] I = {{
            {2},
            {2},
            {2},
            {2},
            {2}}};

    public static int[][][] L = {{
            {3, 0},
            {3, 0},
            {3, 0},
            {3, 3}}};

    public static int[][][] N = {{
            {0, 4},
            {0, 4},
            {4, 4},
            {4, 0}}};

    public static int[][][] P = {{
            {5, 5},
            {5, 5},
            {5, 0}}};

    public static int[][][] T = {{
            {6, 6, 6},
            {0, 6, 0},
            {0, 6, 0}}};

    public static int[][][] U = {{
            {7, 0, 7},
            {7, 7, 7}}};

    public static int[][][] V = {{
            {0, 0, 8},
            {0, 0, 8},
            {8, 8, 8}}};

    public static int[][][] W = {{
            {0, 0, 9},
            {0, 9, 9},
            {9, 9, 0}}};

    public static int[][][] X = {{
            {0, 10, 0},
            {10, 10, 10},
            {0, 10, 0}}};

    public static int[][][] Y = {{
            {0, 11},
            {11, 11},
            {0, 11},
            {0, 11}}};

    public static int[][][] Z = {{
            {12, 12, 0},
            {0, 12, 0},
            {0, 12, 12}}};

    public static int[][][] A = {
            {{13, 13}, {13, 13}},
            {{13, 13}, {13, 13}},
            {{13, 13}, {13, 13}},
            {{13, 13}, {13, 13}}};

    public static int[][][] B = {
            {{14, 14}, {14, 14}, {14, 14}},
            {{14, 14}, {14, 14}, {14, 14}},
            {{14, 14}, {14, 14}, {14, 14}},
            {{14, 14}, {14, 14}, {14, 14}}};

    public static int[][][] C = {
            {{15, 15, 15}, {15, 15, 15}, {15, 15, 15}},
            {{15, 15, 15}, {15, 15, 15}, {15, 15, 15}},
            {{15, 15, 15}, {15, 15, 15}, {15, 15, 15}}};

    int weight, identifier, shape[][][];

    /**
     * Class to hold all the parameters and methods related
     * to 3D Boxes and Pentominoes
     * @param shape
     */
    public Parcel(int[][][] shape) {

        this.shape = shape;

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[0].length; y++) {
                for (int z = 0; z < shape[0][0].length; z++) {
                    if (shape[x][y][z] != 0) {
                        this.identifier = shape[x][y][z];
                        break;
                    }
                }
            }
        }
    }

    /**
     * Constructor used to set values for the Parcel created
     * @param shape
     * @param weight -> value
     */
    public Parcel(int[][][] shape, int weight) {

        this.shape = shape;
        this.weight = weight;

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[0].length; y++) {
                for (int z = 0; z < shape[0][0].length; z++) {
                    if (shape[x][y][z] != 0) {
                        this.identifier = shape[x][y][z];
                        break;
                    }
                }
            }
        }
    }

    public int[][][] getShape() {return shape;}

    /**
     * Returns all the possible rotations of a piece
     * @return ans
     */
    public List<Parcel> getRotations() {

        List<Parcel> ans = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++)
                    ans.add(rotate2D(k));
                ans.add(rotate3D(j));
            } ans.add(reflect(i));
        } return ans;
    }

    /**
     * Finds the start of a piece
     * @return Point3D
     */
    public Point3D findStart() {

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++)
                    if (shape[i][j][k] != 0) return new Point3D(i, j, k);
            }
        } return null;
    }

    /**
     * Rotates a Parcel in 2D
     * @param pos
     * @return rotated Parcel
     */
    public Parcel rotate2D(int pos) {

        int[][][] ans;
        if (pos == 0) return this;
        else if (pos == 2) ans = new int[shape.length][shape[0].length][shape[0][0].length];
        else ans = new int[shape[0].length][shape.length][shape[0][0].length];

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    switch (pos) {
                        case 1: ans[(shape[0].length - 1) - j][i][k] = shape[i][j][k]; break;
                        case 2: ans[(shape.length - 1) - i][(shape[0].length - 1) - j][k] = shape[i][j][k]; break;
                        case 3: ans[j][(shape.length - 1) - i][k] = shape[i][j][k];
                    }
                }
            }
        } return new Parcel(ans);
    }

    /**
     * Rotates a Parcel in 3D
     * @param pos
     * @return rotated Parcel
     */
    public Parcel rotate3D(int pos) {

        int[][][] ans;
        if (pos == 0) return this;
        else if (pos == 2) ans = new int[shape.length][shape[0].length][shape[0][0].length];
        else ans = new int[shape[0][0].length][shape[0].length][shape.length];

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    switch (pos) {
                        case 1: ans[(shape[0][0].length - 1) - k][j][i] = shape[i][j][k]; break;
                        case 2: ans[(shape.length - 1) - i][j][(shape[0][0].length - 1) - k] = shape[i][j][k]; break;
                        case 3: ans[k][j][(shape.length - 1) - i] = shape[i][j][k];
                    }
                }
            }
        } return new Parcel(ans);
    }

    /**
     * Mirrors a Parcel
     * @param pos
     * @return mirrored Parcel
     */
    public Parcel reflect(int pos) {

        int[][][] ans;
        if (pos == 0) return this;
        else if (pos == 2) ans = new int[shape.length][shape[0].length][shape[0][0].length];
        else ans = new int[shape.length][shape[0][0].length][shape[0].length];

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    switch (pos) {
                        case 1: ans[i][(shape[0][0].length - 1) - k][j] = shape[i][j][k]; break;
                        case 2: ans[i][(shape[0].length - 1) - j][(shape[0][0].length - 1) - k] = shape[i][j][k]; break;
                        case 3: ans[i][k][(shape[0].length - 1) - j] = shape[i][j][k];
                    }
                }
            }
        } return new Parcel(ans);
    }
}
