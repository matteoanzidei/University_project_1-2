
import java.awt.Point;

public class PentoPiece {

	private int[][] shape;

	/**
	 * Class to hold all the parameters and methods related
     * to 2D Pentominoes
	 * @param shape
	 */
	public PentoPiece(int[][] shape) {this.shape = shape;}

	public int[][] getShape() {return shape;}

    /**
     * Finds the first non-zero element of the shape
     * @return 2D Point
     */
	public Point getPinPoint() {

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				if (shape[i][j] != 0) {return new Point(i, j);}
			}
		} return null;
	}

    /**
     * Rotates a Pentomino by 90 degrees
     * @param rotation
     * @return rotated shape of the Pentomino
     */
	public int[][] rotate(int rotation) {

		if (rotation == 0) return shape;

		int[][] ans;
		if (rotation == 1 || rotation == 3) ans = new int[shape[0].length][shape.length];
		else ans = new int[shape.length][shape[0].length];

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				switch (rotation) {
					case 1: ans[j][(shape.length - 1) - i] = shape[i][j]; break; // turn 90 degrees
					case 2: ans[(shape.length - 1) - i][(shape[i].length - 1) - j] = shape[i][j]; break; // turn 180 degrees
					case 3: ans[(shape[i].length - 1) - j][i] = shape[i][j]; break;  // turn 270 degrees
				}
			}
		} return ans;
	}

    /**
     * Mirrors a Pentomino
     * @return mirrored Pentomino
     */
	public int[][] reflect() {

		int[][] np = new int[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				np[i][(shape[i].length - 1) - j] = shape[i][j];
			}
		} return np;
	}
}
