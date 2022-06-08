
import javax.swing.*;

public class SnakeFrame extends JFrame {
	SnakePanel Board;
	StartPanel Start;
	JFrame _this;

	public SnakeFrame() {
		// TODO Auto-generated constructor stub
		this._this = this;
		this.setTitle("Snake");
		// JFrame mainFrame = this;
		Board = new SnakePanel();
		Start = new StartPanel();

		this.add(Start);
		this.pack();
		this.add(Board);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		Start.HandleEvent(Board);
		Board.HandleEvent(Start);
	}

}
