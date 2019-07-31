import java.awt.*;
import java.util.Random;

public class Bot extends Pentris{

	public static RL_Brain Q;
	public static double reward;
	public static int action;
	public static Random rand;

	public Bot(String mode){
		super();
		if(mode.equals("RL")){
			int action_space = 7;
			double alpha = 0.0001;
			double gamma = 0.001;
			double epsilon = 0.9;
			Q = new RL_Brain(action_space, alpha, gamma, epsilon);
		}
		else if(mode.equals("Random")){
			rand = new Random();
		}
	}

	public String getState(){
		int[][] state = new int[this.well.length][this.well[0].length];
		for(int i = 0; i < this.well.length; i++)
			for(int j = 0; j < this.well[0].length; j++)
				if(this.well[i][j] != Color.WHITE) state[i][j] = 1;
				else state[i][j] = 0;
		for(Point p: currentPiece)
			state[p.x+pieceOrigin.x][p.y+pieceOrigin.y] = 1;

		String stateString = "";
		String v;
		for(int[] row: state){
			for(int value: row){
				v = String.valueOf(value);
				stateString = stateString.concat(v);
			}
		}

		return stateString;
	}

	public void calculateReward(){
		return;
	}

	public void update(String s, int a, double r, String _s){
		Q.update(s, a, r, _s);
	}

	public int getAction(String s){
		return Q.action_chooser(s);
	}

	public int getRandomMove(){
		action = rand.nextInt(7);
		return action;
	}

	public double GoGoGadgetMove(int action){
		reward = 0;
		switch(action){
			case 0: break;
			case 1: move(-1); break;
			case 2: move(1); break;
			case 3: rotator("Clockwise"); break;
			case 4: rotator("ClockClockwise"); break;
			case 5: dropDown(); reward = 0.1; break;
			case 6: dropItLikeItsHot(); reward = 1; break;
		}
		return reward;
	}
}
