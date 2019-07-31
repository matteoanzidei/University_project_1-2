import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PentrisInterface implements PentrisFunc{
	public void newGame(){
		JFrame f = new JFrame("Pentris");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(false);
		final Pentris game = new Pentris();
		game.initialize_it();
		f.add(game);
		f.setVisible(true);
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP: game.rotator("Clockwise"); break;
					case KeyEvent.VK_DOWN: game.rotator("CounterClockwise"); break;
					case KeyEvent.VK_LEFT: game.move(-1); break;
					case KeyEvent.VK_RIGHT: game.move(+1); break;
					case KeyEvent.VK_SHIFT: game.dropDown(); break;
					case KeyEvent.VK_SPACE: game.dropItLikeItsHot(); break;
					case KeyEvent.VK_TAB: game.switchHeldPiece(); break;
				}
			}
		});

		new Thread(){
			@Override public void run(){
				while(!game.gameOver()){
					try{
						Thread.sleep(1000);
						game.dropDown();
					}
					catch(InterruptedException e){}
				}
				System.out.println("Game Over!");
			}
		}.start();
	}

	public void perfectGame(){
		JFrame f = new JFrame("Pentris: Perfect Game");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(false);
		final PerfectGame game = new PerfectGame();
		game.initialize_perfect_game();
		f.add(game);
		f.setVisible(true);

		new Thread(){
			@Override public void run(){
				while(true){
					try{
						Thread.sleep(1000);
						game.placePiece();
					} catch(InterruptedException e){}
				}
			}
		}.start();
	}

	public void letTheBotPlay(){
		JFrame f = new JFrame("Pentris");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(false);
		final Bot Botman = new Bot("RL");
		Botman.initialize_it();
		f.add(Botman);
		f.setVisible(true);

		new Thread(){
			@Override public void run(){
				while(!Botman.gameOver()){
					try{
						Thread.sleep(1000);
						Botman.dropDown();
					}
					catch(InterruptedException e){}
				}
				System.out.println("Game Over!");
			}
		}.start();

		new Thread(){
			@Override public void run(){
				String s = Botman.getState();
				while(!Botman.gameOver()){
					try{
						Thread.sleep(400);
						int a = Botman.getAction(s);
						double r = Botman.GoGoGadgetMove(a);
						String _s = Botman.getState();
						Botman.update(s, a, r, _s);
						s = _s;
					}
					catch(InterruptedException e){}
				}
			}
		}.start();
	}

	public void stats(){
		JFrame f = new JFrame("Pentris: Stats");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(false);
		final Stats game = new Stats();
		f.add(game);
		f.setVisible(true);
	}
}