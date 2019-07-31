
public class PentoSolver {
	/**
	 * First Block Pentomino Solver
	 */
	public int[][] solution = null;

    /**
     * Fills a given board with a given set of pieces
     * @param pentoPieces
     * @param board
     */
	public void solve(PentoPiece[] pentoPieces, int[][] board) {

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {

				if (board[row][col] == 0) {
					for(PentoPiece pento : pentoPieces){
						for (PentoPiece p : getAllFormats(pento)) {

							if (inBounds(row, col, p, board) && checkPiece(row, col, p, board)) {
								int[][] newBoard = placePiece(row, col, p, board);
								solve(pentoPieces, newBoard);

								if (validate(newBoard)) {
									this.solution = newBoard;
									return;
								}
							}
						}
					} return;

				}
			}
		}
	}

    /**
     * Check if a given piece is within the boundaries of a given board
     * @param x
     * @param y
     * @param p
     * @param board
     * @return boolean
     */
	static boolean inBounds(int x, int y, PentoPiece p, int[][] board) {

		if (x - p.getPinPoint().x < 0 ||
				y - p.getPinPoint().y < 0 ||
				x + p.getShape().length > board.length ||
				y + p.getShape()[0].length - p.getPinPoint().y > board[0].length) {
			return false;
		} return true;
	}

    /**
     *
     * @param x
     * @param y
     * @param p
     * @param board
     * @return boolean
     */
	static boolean checkPiece(int x, int y, PentoPiece p, int[][] board) {

		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0 && board[x + i - p.getPinPoint().x][y + j - p.getPinPoint().y] != 0)
					return false;
			}
		} return true;
	}

    /**
     * Places a given piece on a given board in a given position
     * @param x
     * @param y
     * @param p
     * @param board
     * @return newBoard
     */
	static int[][] placePiece(int x, int y, PentoPiece p, int[][] board) {

		int[][] newBoard = new int[board.length][board[0].length];
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard[0].length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}

		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0)
					newBoard[x + i - p.getPinPoint().x][y + j - p.getPinPoint().y] = p.getShape()[i][j];
			}
		} return newBoard;
	}

    /**
     * Finds all the possible rotations of a given Pentomino
     * @param p
     * @return all rotations of a given piece
     */
	public static PentoPiece[] getAllFormats(PentoPiece p) {

		PentoPiece[] allShapes = new PentoPiece[8];
		int counter = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				allShapes[counter] =
						new PentoPiece(
								p.rotate(j));
				counter++;
			} p.reflect();
		} return allShapes;
	}

    /**
     * Checks if a given board is full
     * @param board
     * @return boolean
     */
	static boolean validate(int[][] board) {

		for (int i = 0; i<board.length; i++) {
			for (int j = 0; j<board[0].length; j++) {
				if (board[i][j] == 0){return false;}
			}
		} return true;
	}
}
