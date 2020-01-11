import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.security.Timestamp;

import javax.swing.Timer;
import javax.imageio.ImageIO;

import javax.swing.JComponent;

import javax.swing.JLabel;



public class Game extends JComponent implements KeyListener, ActionListener {
	Timer t;
	int score = 0, fule = 0, fule0 = 200, y = 310, x = 50, V = 0, ag = 0, a = 0, live = 3, round = 1, timer = 1;
	double gravity = 2.5;
	Rectangle[] R = new Rectangle[17];
	boolean fly = false, crash = false, success = false, gameover = false, fire = false, miss = true, eat = true;

	JLabel l = new JLabel("Score : " + score);

	public Game() {

		addKeyListener(this);
		setFocusable(true);
		t = new Timer(80, this);

	}



	public void rocket(Graphics s) {

		s.setColor(Color.BLACK);
		// AffineTransform at = AffineTransform.getTranslateInstance(40, 40);
		Graphics2D k = (Graphics2D) s;
		k.rotate(Math.toRadians(ag), x + 15 / 2, y);

		s.fillRect(x - 6, y + 25, 8, 10);
		s.fillRect(x + 6, y + 25, 8, 10);
		s.fillRect(x - 10, y + 5, 28, 20);
		s.drawRect(x - 3, y - 8, 14, 13);
		s.fillRect(x - 18, y + 10, 8, 6);
		s.fillRect(x + 18, y + 10, 8, 6);
		s.fillOval(x - 1, y - 4, 4, 4);
		s.fillOval(x + 4, y - 4, 4, 4);
		if (fire) {

			s.setColor(Color.YELLOW);

			s.fillRect(x - 6, y + 25, 8, 10);
			s.fillRect(x + 6, y + 25, 8, 10);
			s.fillRect(x - 10, y + 5, 28, 20);
			s.drawRect(x - 3, y - 8, 14, 13);
			s.fillRect(x - 18, y + 10, 8, 6);
			s.fillRect(x + 18, y + 10, 8, 6);
			s.fillOval(x - 1, y - 4, 4, 4);
			s.fillOval(x + 4, y - 4, 4, 4);
		}
	}
	public void fuel(Graphics g) {
		if (fule0 >= 80) {
			g.fillRect(getWidth() - 300, 50, fule0, 10);
		} else {
			g.setColor(Color.red);
			g.fillRect(getWidth() - 300, 50, fule0, 10);
		}
	}

	public void lives(Graphics g) {
		switch (live) {
		case 3: {
			g.setColor(Color.MAGENTA);
			g.drawString("Live : " + live, 20, 20);
			g.fillRect(20, 30, 10, 20);
			g.fillRect(35, 30, 10, 20);
			g.fillRect(50, 30, 10, 20);
			break;
		}
		case 2: {
			g.drawString("Live : " + live, 20, 20);
			g.setColor(Color.MAGENTA);
			g.fillRect(20, 30, 10, 20);
			g.fillRect(35, 30, 10, 20);
			break;
		}
		case 1: {
			g.drawString("Live : " + live, 20, 20);
			g.setColor(Color.MAGENTA);
			g.fillRect(20, 30, 10, 20);
			break;
		}
		}
	}


	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent (g);
		if (round == 1) {
			Graphics copy = g.create();
			map1(copy);
			copy.dispose();
			copy = g.create();
			rocket(copy);
			copy.dispose();
			copy = g.create();
			fuel(copy);
			copy.dispose();
			copy = g.create();
			lives(copy);
			copy.dispose();
			// copy=g.create();
			coins(g);
			// g.dispose();

			t.start();
			if (crash || fule0 < 0 && live > 0) {
				Graphics c = g.create();
				over(c);
				c.dispose();

			} else if (live < 0 || (live < 0 && fule0 <= 0)) {
				gameover = true;
				if (gameover) {
					end(g);
					t.stop();
				}
			}
		} else if (round == 2) {
			Graphics copy = g.create();
			map2(copy);
			copy.dispose();
			copy = g.create();
			rocket(copy);
			copy.dispose();
			copy = g.create();
			fuel(copy);
			copy.dispose();
			copy = g.create();
			lives(copy);
			copy.dispose();
			t.start();
			if (crash || fule0 < 0 && live > 0) {
				Graphics c = g.create();
				over(c);
				c.dispose();

			} else if (live < 0 || (live < 0 && fule0 <= 0)) {
				gameover = true;
				if (gameover) {
					end(g);
					t.stop();
				}
			}

		} else if (round == 0) {
			success(g);
			t.stop();

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int a = e.getKeyCode();

		if (a == KeyEvent.VK_UP) {
			fly = true;
			fire = true;
			V = V + 2;
			fule = 1;
		} else if (a == KeyEvent.VK_DOWN) {
			fly = true;
			fire = true;
			V = V - 2;
			fule = 1;
		} else if (a == KeyEvent.VK_RIGHT) {
			fly = true;
			fire = false;
			ag = ag + 10;
		} else if (a == KeyEvent.VK_LEFT) {
			fly = true;
			fire = false;
			ag = ag - 10;
		} else if (a == KeyEvent.VK_ENTER) {
			crash = false;
			fly = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		V = 0;
		fule = 0;
		gravity = 2.5;
		fire = false;
	}

	public void end(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		Font f = new Font("Time New Roman", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("Your score is " + score, 400, 50);
		BufferedImage rocket = LoadImage("src/gameover.png");
		g.drawImage(rocket, 250, 100, this);
	}

	public BufferedImage LoadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("File cannot find");
		}
		return img;
	}

	public void over(Graphics g) {

		BufferedImage crash = LoadImage("src/crash.jpg");
		// g.drawImage(crash, 250, 100, this);
		g.drawImage(crash, 250, 0, 600, 500, this);
	}

	public void success(Graphics g) {

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		Font f = new Font("Time New Roman", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("Your score is " + score, 400, 50);
		BufferedImage success = LoadImage("src/success.jpg");
		g.drawImage(success, 250, 100, this);

	}

	public void coins(Graphics g) {

		BufferedImage coins1 = LoadImage("src/1.png");
		BufferedImage coins2 = LoadImage("src/2.png");
		BufferedImage coins3 = LoadImage("src/3.png");
		if (miss) {
			if (timer % 6 == 1) {
				g.drawImage(coins1, 700, 200, this);
			} else if (timer % 6 == 3) {
				g.drawImage(coins2, 700, 200, this);
			} else if (timer % 6 == 0) {
				g.drawImage(coins3, 700, 200, this);
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer++;

		// jet motion
		if (fly && !crash && fule0 > 0) {
			x += (V * Math.sin(Math.toRadians(ag)));
			y = (int) (y - (V * Math.cos(Math.toRadians(ag))) + gravity);
			fule0 -= fule;
			repaint();
			gravity = gravity + 0.2;

		}
		repaint();

		if (round == 1) {

			for (int i = 0; i <= 3; i++)
				if (new Rectangle(x, y, 15, 30).intersects(R[i]) || new Rectangle(x + 7, y - 20, 1, 20).intersects(R[i])
						|| fule0 <= 0) {
					System.out.println(R[i] + " " + x + " " + y);
					crash = true;
					live--;
					fule0 = 200;
					ag = 0;
					V = 0;
					x = 50;
					y = 310;
					gravity = 2.5;
					miss = true;

					fly = false;

				} else if (new Rectangle(x, y, 15, 38).intersects(new Rectangle(getWidth() - 150, 350, 150, 170))) {
					round = 2;
					fule0 = 200;
					ag = 0;
					V = 0;
					x = 50;
					y = 310;
					fly = false;
					crash = false;
					score = score + 5;
					miss = true;
				} else if (new Rectangle(x, y, 15, 38).intersects(new Rectangle(700, 200, 50, 50))) {

					miss = false;
					if (eat) {
						score = score + 1;
						System.out.println("Score" + score);
						eat = false;
					}
				}
		} else if (round == 2) {

			for (int i = 0; i <= 15; i++)
				if (new Rectangle(x, y, 15, 38).intersects(R[i]) || new Rectangle(x + 7, y - 20, 1, 20).intersects(R[i])
						|| fule0 <= 0) {

					crash = true;
					live--;
					fule0 = 200;
					ag = 0;
					V = 0;
					x = 50;
					y = 310;
					fly = false;
					gravity = 2.5;
					System.out.println(R[i]);

				} else if (new Rectangle(x, y, 15, 38)
						.intersects(new Rectangle(getWidth() - 150, getHeight() - 90, 150, 170))) {
					round = 0;
					fule0 = 200;
					ag = 0;
					V = 0;
					x = 50;
					y = 310;
					fly = false;
					crash = false;
					success = true;
					score = score + 5;
					System.out.println(score);
				}

		}

	}

	// draw the graphic of the map one
	public void map1(Graphics g) {

		g.setColor(Color.RED);
		R[0] = new Rectangle(0, 350, getWidth() - 150, 690);
		R[1] = new Rectangle(400, 250, 100, 390);
		R[2] = new Rectangle(0, 0, 1000, 90);
		R[3] = new Rectangle(600, 0, 100, 190);

		g.fillRect(0, 350, getWidth() - 150, 700);
		g.fillRect(400, 250, 100, 400);
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1000, 100);
		g.fillRect(600, 0, 100, 200);
		g.setColor(Color.BLACK);
		g.fillRect(getWidth() - 150, 350, 150, 700);

	}

	public void map2(Graphics g) {

		Graphics2D k = (Graphics2D) g;
		R[0] = new Rectangle(0, 0, 80, 100);
		R[1] = new Rectangle(80, 0, 90, 200);
		R[2] = new Rectangle(170, 0, 100, 150);
		R[3] = new Rectangle(270, 0, 100, 300);
		R[4] = new Rectangle(370, 0, 150, 250);
		R[5] = new Rectangle(570, 0, 150, 100);
		R[6] = new Rectangle(620, 0, 200, 200);
		R[7] = new Rectangle(820, 0, 100, 250);
		R[8] = new Rectangle(920, 0, 80, 200);
		R[9] = new Rectangle(100, 370, 90, 230);
		R[10] = new Rectangle(190, 500, 100, 100);
		R[11] = new Rectangle(290, 400, 250, 200);
		R[12] = new Rectangle(540, 350, 200, 250);
		R[13] = new Rectangle(740, 450, 110, 150);
		R[14] = new Rectangle(0, getHeight() - 90, getWidth() - 150, 170);
		R[15] = new Rectangle(0, 350, 100, 50);
		k.setColor(Color.blue);
		g.fillRect(0, 0, 80, 100);g.fillRect(80, 0, 90, 200);g.fillRect(170, 0, 100, 150);g.fillRect(270, 0, 100, 300);g.fillRect(370, 0, 150, 250);
		g.fillRect(520, 0, 150, 100);g.fillRect(620, 0, 200, 200);g.fillRect(820, 0, 100, 250);g.fillRect(100, 370, 90, 230);g.fillRect(920, 0, 80, 200);
		g.fillRect(190, 500, 100, 100);g.fillRect(290, 400, 250, 200);g.fillRect(540, 350, 200, 250);g.fillRect(740, 450, 110, 150);g.fillRect(0, getHeight() - 90, getWidth() - 150, 170);
		g.fillRect(0, 350, 100, 50);g.setColor(Color.BLACK);g.fillRect(getWidth() - 150, getHeight() - 90, 150, 170);

	}

}
