import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Stats extends Pentris{
	public Stats(){
		readScoreFromFile();
	}

	public void customPainting(Graphics g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 2000, 2000);
		g.setColor(Color.WHITE);
		g.fillRect(x_offset, y_offset, 14*spongeBob-x_offset, 25*spongeBob-y_offset);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("High Score: " + highScore + " " + highScorer, 500, 70);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Pentris: Stats");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(false);
		final Stats game = new Stats();
		f.add(game);
		f.setVisible(true);
	}
}