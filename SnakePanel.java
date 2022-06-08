import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class SnakePanel extends JPanel implements ActionListener {

	static final int SCREEN_HEIGH = 500;
	static final int SCREEN_WIDTH = 400;
	static final int UNIT = 20;
	char Direction = 'R';
	int MaxLength;
	int CurLength;
	POINT[] snake;
	POINT Apple;
	boolean running;
	Timer timer;
	Random random;
	int score = 0;
	int speed = 100;

	public SnakePanel() {

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGH));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter(this));
		this.setVisible(false);
		StartGame();
	}

	public void newGame() {

		MaxLength = 100;
		CurLength = 10;
		snake = new POINT[MaxLength];
		for (int i = CurLength - 1; i >= 0; i--) {
			snake[i] = new POINT((CurLength - i) * UNIT, 0);
		}
		score = 0;
		speed = 100;
		Direction = 'R';
		running = true;
		Apple = newApple();
		timer.start();
	}

	public void continueGame() {

		timer.start();
	}

	public void StartGame() {
		timer = new Timer(speed, this);
		random = new Random();
		newGame();
	}

	public POINT newApple() {

		while (true) {
			int x = random.nextInt(SCREEN_WIDTH / UNIT) * UNIT;
			int y = random.nextInt(SCREEN_HEIGH / UNIT) * UNIT;
			POINT newPoint = new POINT(x, y);
			Boolean isStop = true;
			for (int i = 0; i < CurLength; i++) {
				if (snake[i].isEqua(newPoint)) {
					isStop = false;
					break;
				}

			}
			if (isStop)
				return newPoint;
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		g.setColor(new Color(10, 10, 10));
		for (int i = 0; i <= SCREEN_WIDTH / UNIT; i++) {
			g.drawLine(UNIT * i, 0, UNIT * i, SCREEN_HEIGH);
		}
		for (int i = 0; i <= SCREEN_HEIGH / UNIT; i++) {
			g.drawLine(0, UNIT * i, SCREEN_WIDTH, UNIT * i);
		}
		g.setColor(Color.DARK_GRAY);
		g.drawLine(SCREEN_WIDTH, 0, SCREEN_WIDTH, SCREEN_HEIGH);
		drawScore(g);
		if (!running) {
			drawGameOver(g);
			return;
		}

		drawApple(g);
		drawSnake(g);

	}

	public void drawApple(Graphics g) {

		g.setColor(Color.RED);
		g.fillOval(Apple.x, Apple.y, UNIT, UNIT);
	}

	public void drawScore(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("SCORE: " + Integer.toString(score),
				SCREEN_WIDTH + 200 / 2 - metrics.stringWidth("SCORE: " + Integer.toString(score)) / 2, UNIT * 5);

	}

	public void drawGameOver(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 60));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", SCREEN_WIDTH / 2 - metrics.stringWidth("Game Over") / 2, SCREEN_HEIGH / 2);

	}

	public void drawSnake(Graphics g) {

		g.setColor(new Color(45, 108, 0));
		for (int i = 1; i < CurLength; i++) {
			g.fillRect(snake[i].x + 1, snake[i].y + 1, UNIT - 2, UNIT - 2);
		}
		g.setColor(Color.green);
		g.fillRect(snake[0].x + 1, snake[0].y + 1, UNIT - 2, UNIT - 2);
	}

	public void move() {
		for (int i = CurLength - 1; i >= 0; i--) {
			snake[i + 1] = (POINT) snake[i].clone();
		}
		int x = 0, y = 0;

		switch (Direction) {
			case 'R':
				x = snake[0].x + UNIT;
				snake[0].x = x >= SCREEN_WIDTH ? 0 : x;
				break;
			case 'L':
				x = snake[0].x - UNIT;
				snake[0].x = x < 0 ? SCREEN_WIDTH - UNIT : x;
				break;
			case 'U':
				y = snake[0].y - UNIT;
				snake[0].y = y < 0 ? SCREEN_HEIGH - UNIT : y;
				break;
			case 'D':
				y = snake[0].y + UNIT;
				snake[0].y = y >= SCREEN_HEIGH ? 0 : y;
				break;

		}

	}

	public void checkCollision() {
		for (int i = 1; i < CurLength; i++) {
			if (snake[i].isEqua(snake[0]))
				running = false;
		}
	}

	public void checkEatApple() {
		// TODO Auto-generated method stub
		if (snake[0].isEqua(this.Apple)) {
			this.Apple = newApple();
			this.CurLength++;
			score += 1;
			speed -= 0.5;
			timer.setDelay(speed);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("1");
		if (running) {
			move();
			checkCollision();
			checkEatApple();

		} else {

			timer.stop();

		}
		repaint();

	}

	public void HandleEvent(JPanel Start) {

		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent evt) {
				Start.setVisible(false);
			}

			public void componentHidden(ComponentEvent evt) {
				Start.setVisible(true);
			}
		});

	}

	public class MyKeyAdapter extends KeyAdapter {
		Object o;

		MyKeyAdapter(Object o) {
			this.o = o;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_UP && Direction != 'D') {
				Direction = 'U';
			}
			if (key == KeyEvent.VK_DOWN && Direction != 'U') {
				Direction = 'D';
			}
			if (key == KeyEvent.VK_LEFT && Direction != 'R') {
				Direction = 'L';
			}
			if (key == KeyEvent.VK_RIGHT && Direction != 'L') {
				Direction = 'R';
			}
			if (key == KeyEvent.VK_ESCAPE) {
				((JPanel) o).setVisible(false);
				timer.stop();
			}
		}
	}

}

class POINT {
	public int x = 0;
	public int y = 0;

	POINT(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public POINT clone() {
		return new POINT(this.x, this.y);
	}

	public Boolean isEqua(POINT A) {
		if (this.x != A.x || this.y != A.y)
			return false;
		return true;
	}
}
