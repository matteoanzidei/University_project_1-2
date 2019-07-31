import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Point;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class PerfectGame extends Pentris{
	public static int[] order;
	public static int index;

	public PerfectGame(){ super(); }

	public void initialize_perfect_game(){
		well = new Color[14][25];
		for(int i = 0; i < well.length; i++){
			for(int j = 0; j < well[0].length; j++){
				if(i == 0 || i == well.length-1 || j == well[0].length - 1)
					well[i][j] = Color.GRAY;
				else
					well[i][j] = Color.WHITE;
			}
		}
		score = 0;
		getFirstPiece();
	}

	public void getFirstPiece(){
		//current order:  V, X, U, P,  Z, F, T, W,  N,  Y,  L,  I, null
		order = new int[]{5, 4, 15, 9, 12, 8, 3, 6, 17, 14, 2, 0, 18};
		index = 0;
		currentPiece = Pentominoes.Pentomino_array[order[index]];
		currentColor = Pentominoes.color_array[rand.nextInt(10)];
		pieceOrigin = new Point(6, 2);
	}

	public void getNextPiece(){
		index += 1;
		currentPiece = Pentominoes.Pentomino_array[order[index]];
		currentColor = Pentominoes.color_array[rand.nextInt(10)];
		pieceOrigin = new Point(6, 2);
	}

	public boolean dropUDown(){
		Point test_location = new Point(pieceOrigin);
		test_location.y += 1;
		if(!collides(test_location, currentPiece))
			pieceOrigin.y += 1;
		else
			return true;
		return false;
	}

	public void dropItLikeItsPerfectlyHot(){
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
		meanGirls();
	}

	public void meanGirls(){
		for(Point p: currentPiece)
			well[p.x + pieceOrigin.x][p.y + pieceOrigin.y] = currentColor;
		rowChecked();
		getNextPiece();
	}

	public void placePiece(){
		switch(index){
			case 0: placePiece1(); break;
			case 1: placePiece2(); break;
			case 2: placePiece3(); break;
			case 3: placePiece4(); break;
			case 4: placePiece5(); break;
			case 5: placePiece6(); break;
			case 6: placePiece7(); break;
			case 7: placePiece8(); break;
			case 8: placePiece9(); break;
			case 9: placePiece10(); break;
			case 10: placePiece11(); break;
			case 11: placePiece12(); break;
			case 12: break;
		}
	}
	//V
	public void placePiece1(){
		rotator("Clockwise");
		rotator("Clockwise");
		move(6);
		dropItLikeItsPerfectlyHot();
	}
	//X
	public void placePiece2(){
		move(3);
		dropItLikeItsPerfectlyHot();
	}
	//U
	public void placePiece3(){
		while(true)
			if(dropUDown())
				break;
		move(1);
		dropItLikeItsPerfectlyHot();
	}
	//P
	public void placePiece4(){
		rotator("Clockwise");
		move(-3);
		dropItLikeItsPerfectlyHot();
	}
	//Z
	public void placePiece5(){
		move(-1);
		dropItLikeItsPerfectlyHot();
	}
	//F
	public void placePiece6(){
		rotator("Clockwise");
		rotator("Clockwise");
		move(5);
		dropItLikeItsPerfectlyHot();
	}
	//T
	public void placePiece7(){
		rotator("Clockwise");
		rotator("Clockwise");
		dropItLikeItsPerfectlyHot();
	}
	//W
	public void placePiece8(){
		move(-3);
		dropItLikeItsPerfectlyHot();
	}
	//N
	public void placePiece9(){
		rotator("CounterClockwise");
		while(pieceOrigin.x != 11)
			move(1);
		dropItLikeItsPerfectlyHot();
	}
	//Y
	public void placePiece10(){
		rotator("Clockwise");
		move(1);
		dropItLikeItsPerfectlyHot();
	}
	//L
	public void placePiece11(){
		rotator("Clockwise");
		move(-3);
		dropItLikeItsPerfectlyHot();
	}
	//I
	public void placePiece12(){
		move(-5);
		dropItLikeItsPerfectlyHot();
	}

	public void customPainting(Graphics g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 2000, 2000);
		g.setColor(Color.BLACK);
		g.fillRect(x_offset, y_offset, well.length*spongeBob-x_offset, well[0].length*spongeBob-y_offset);
		for(int i = 0; i < well.length; i++){
			for(int j = 0; j < well[0].length; j++){
				g.setColor(well[i][j]);
				g.fillRect(i*spongeBob + x_offset,
						   j*spongeBob + y_offset, 
						   spongeBob-1, 
						   spongeBob-1);
			}
		}
		drawCurrentPiece(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("Score: " + score, 500, 30);
		g.drawString("High Score: " + highScore, 500, 70);
	}
}