import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.io.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* @author Matteo Anzidei, Ramses Kamanda, Amanda Kane, Philippe Jacob, Andrei Atomei, Jarod Simonet
* @version 1.0 12 Dec 2017
*/

public class Pentris extends JPanel{

	public static Color[][] well;
	public static Point[] currentPiece;
	public static Point[] next_currentPiece;
	public static Color next_currentColor;
	public static Point pieceOrigin;
	public static Point pieceOrigin2 = new Point(3,3);
	public static Color currentColor;
	public static Random rand = new Random();
	public static int spongeBob = 31;
	public static int next_piece_index;
	public static int next_piece_colour;
	public static Color[][] next_pieces_array= new Color[8][10];
	public static Point[] heldPiece;
	public static Color heldColor;
	public static int score;
	public static int highScore;
	public static String highScorer;
	public static int x_offset = 550;
	public static int y_offset = 150;

/** This method creates the game well and border
*	we initalize the well as a Color array with dimensions (12x24)
*	with a gray border
*	+
*	get the first piece of the game.
*/
	public void initialize_it(){
		readScoreFromFile();
		well = new Color[14][25];
		for(int i = 0; i < well.length; i++){
			for(int j = 0; j < well[0].length; j++){
				if(i == 0 || i == well.length-1 || j == well[0].length - 1)
					well[i][j] = Color.GRAY;
				else
					well[i][j] = Color.WHITE;
			}
		}
		for(int i = 0; i < next_pieces_array.length; i++){
			for(int j = 0; j<next_pieces_array[0].length; j++){
					next_pieces_array[i][j] = Color.WHITE;
			}
		}
		score = 0;
		getNewPiece();
		setNewPiece();
		getNewPiece();
	}

	/**
*	Places a piece in coordinates (6, 2)
	*/

	public void setNewPiece(){
		currentPiece = Pentominoes.Pentomino_array[this.next_piece_index];
		currentColor = Pentominoes.color_array[this.next_piece_colour];
		pieceOrigin = new Point(6, 2);
	}

	/**
*	Gets a new piece randomly
	*/
	public void getNewPiece(){
		this.next_piece_index= rand.nextInt(18);
		this.next_piece_colour=  rand.nextInt(10);
		next_currentPiece = Pentominoes.Pentomino_array[this.next_piece_index];
		next_currentColor = Pentominoes.color_array[this.next_piece_colour];
		for(int i = 0; i < next_pieces_array.length; i++){
			for(int j = 0; j<next_pieces_array[0].length; j++){
					next_pieces_array[i][j] = Color.WHITE;
			}
		}
		for(Point p: next_currentPiece){
			next_pieces_array[p.x + pieceOrigin2.x][p.y + pieceOrigin2.y] = next_currentColor;
		}
	}

	public void switchHeldPiece(){
		if(heldPiece != null){
			Point[] pivot = currentPiece;
			Color p = currentColor;
			currentPiece = heldPiece;
			currentColor = heldColor;
			heldPiece = pivot;
			heldColor = p;
		} else {
			heldPiece = currentPiece;
			getNewPiece();
			setNewPiece();
		}
	}

	/**
*	Clones current piece to perform tests on it before placing it
	*/
	public Point[] clone(){
		Point[] test = new Point[currentPiece.length];
		for(int i = 0; i < currentPiece.length; i ++){
			test[i] = new Point(currentPiece[i]);
		}
		return test;
	}

	/**
*	rotates a piece
*	@param direction clockwise or counter-clockwise
	*/
	public void rotator(String direction){
		if(direction.equals("Clockwise")){
			Point[] test = clone();
			for(Point p: test)
				p.setLocation(p.y, -p.x);
			if(!collides(pieceOrigin, test))
				for(Point p: currentPiece)
					p.setLocation(p.y, -p.x);
		}
		else if(direction.equals("CounterClockwise")){
			Point[] test = clone();
			for(Point p: test)
				p.setLocation(-p.y, p.x);
			if(!collides(pieceOrigin, test))
				for(Point p: currentPiece)
					p.setLocation(-p.y, p.x);
		}
		repaint();
	}

	/**
*	moves a piece sideways
*	@param side +1 is right, -1 is left
	*/
	public void move(int side){
		Point test_location = new Point(pieceOrigin);
		test_location.x  += side;
		if(!collides(test_location, currentPiece)){
			pieceOrigin.x += side;
		}
		repaint();
	}

	/**
*	checks for collision with boundaries and other pieces
*	@param origin point of origin of the piece to check
*	@param shape shape to check for collisions
*	@return boolean
	*/
	public boolean collides(Point origin, Point[] shape){
		for(Point p: shape){
			if(p.x + origin.x <= 0 || p.x + origin.x >= well.length - 1 || p.y + origin.y >= well[0].length - 1)
				return true;
			if(well[p.x + origin.x][p.y + origin.y] != Color.WHITE)
				return true;
		}
		return false;
	}

	/**
*	drops a piece by one square
	*/
	public void dropDown(){
		Point test_location = new Point(pieceOrigin);
		test_location.y += 1;
		if(!collides(test_location, currentPiece))
			pieceOrigin.y += 1;
		else
			gossipGirl();
		repaint();
	}

	/**
*	drops a piece as far down as possible
	*/
	public void dropItLikeItsHot(){
		int score_increment = 0;
		Point test_location = new Point(pieceOrigin);
		while(true){
			test_location.y += 1;
			if(!collides(test_location, currentPiece)){
				pieceOrigin.y += 1;
				score_increment += 1;
			} else {
				break;
			}
		}
		score += score_increment*100;
		gossipGirl();
		repaint();
	}

	/**
*	places piece on the board
	*/
	public void gossipGirl(){
		for(Point p: currentPiece){
			well[p.x + pieceOrigin.x][p.y + pieceOrigin.y] = currentColor;
		}
		rowChecked();

		setNewPiece();
		getNewPiece();
	}

	/**
*	checks if row need to be emptied
*	@param row row to be checked
*	@return boolean
	*/
	public boolean emptyRow(int row){
		boolean fully_empty = true;
		for(int i = 1; i < well.length-1; i++)
			if(well[i][row] != Color.WHITE)
				fully_empty = false;
		return fully_empty;
	}

	/**
*	checks for rows to be deleted and deletes them, increasing the score
	*/
	public void rowChecked(){
		int numRows = 0;
		boolean full;
		int row = well[0].length - 2;
		while(!emptyRow(row)){
			full = true;
			for(int i = 0; i < well.length; i++){
				if(well[i][row] == Color.WHITE)
					full = false;
			}
			if(full){
				deleteRow(row);
				numRows++;
			}
			else row--;
		}
		switch(numRows){
			case 0: break;
			case 1: score += 100; break;
			case 2: score += 200; break;
			case 3: score += 400; break;
			case 4: score += 800; break;
			case 5: score += 1600; break;
		}
	}

	/**
*	deletes a given row
*	@param row row to delete
	*/
	public void deleteRow(int row){
		for(int j = row; j > 0; j--)
			for(int i = 0; i < well.length; i++)
				well[i][j] = well[i][j-1];
	}

	/**
*	checks for game over scenario
*	@return boolean
	*/
	public boolean gameOver(){
		for(int i = 1; i < well.length-1; i++)
			if(well[i][1] != Color.WHITE){
				writeScoreToFile();
				return true;
			}
		return false;
	}

	/**
*	reads the current highscore from a file
	*/
	public void readScoreFromFile(){
		String line;
		String[] temp;
		try{
			FileReader f = new FileReader("conversation_file.txt");
			BufferedReader b = new BufferedReader(f);
			if((line = b.readLine()) != null){
				temp = line.split(" ", -1);
				highScore = Integer.parseInt(temp[1]);
				highScorer = temp[0];
			}
		} catch (IOException e){
			System.out.println("File Not Found.");
		}
		System.out.println("File read, highScore " + highScore + " saved.");
	}

	/**
*	writes the new highscore to a file
	*/
	public void writeScoreToFile(){
		try{
			File file = new File("conversation_file.txt");
			FileWriter f = new FileWriter(file.getAbsoluteFile());
			BufferedWriter b = new BufferedWriter(f);
			if(score > highScore)
				b.write(String.valueOf(score));
			else
				b.write(String.valueOf(highScore));
			b.close();
		} catch (IOException e){}
		System.out.println("File written, highScore " + highScore + " or Score " + score + " saved to file.");
	}


	@Override
	public void paintComponent(Graphics g){
		customPainting(g);
	}

	public void customPainting(Graphics g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 2000, 2000);
		for(int i = 0; i < well.length; i++){
			for(int j = 0; j < well[0].length; j++){
				g.setColor(well[i][j]);
				g.fillRect(i*spongeBob + x_offset,
						   j*spongeBob + y_offset, 
						   spongeBob-1, 
						   spongeBob-1);
			}
		}
		for(int k = 0; k < next_pieces_array.length; k++){
			for(int u = 0; u < next_pieces_array[0].length; u++){
				g.setColor(next_pieces_array[k][u]);
				g.fillRect((k*spongeBob)+485 + x_offset,
						   (u*spongeBob)+100 + y_offset,
						   spongeBob-1, 
						   spongeBob-1);
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("Score: " + score, 500, 30);
		g.drawString("High Score: " + highScore, 500, 70);		

		drawCurrentPiece(g);

		if(gameOver()){
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier", Font.PLAIN, 50));
			g.drawString("Game Over!", 630, 530);
		}
	}

	public void drawCurrentPiece(Graphics g){
		g.setColor(currentColor);
		for(Point p: currentPiece){
			g.fillRect((p.x+pieceOrigin.x)*spongeBob + x_offset,
					   (p.y+pieceOrigin.y)*spongeBob + y_offset,
					    spongeBob-1,
					    spongeBob-1);
		}
	}
}
