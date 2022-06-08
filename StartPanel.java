import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartPanel extends JPanel {
    public JButton bt_new;
    public JButton bt_contiue;
    public JButton bt_about;
    public JButton bt_exit;
    final int WIDTH = 600;
    final int HEIGHT = 500;
    StartPanel _this;

    StartPanel() {
        this.prepareUI();
        // EventClickButton();
    }

    void prepareUI() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        bt_new = new JButton("NEW GAME");
        bt_contiue = new JButton("CONTINUE");
        bt_about = new JButton("ABOUT");
        bt_exit = new JButton("QUIT");
        bt_new.setBounds((WIDTH - 200) / 2, 150, 200, 40);
        bt_contiue.setBounds((WIDTH - 200) / 2, 200, 200, 40);
        bt_about.setBounds((WIDTH - 200) / 2, 250, 200, 40);
        bt_exit.setBounds((WIDTH - 200) / 2, 300, 200, 40);
        // bt_contiue.setBounds(130, 100, 100, 40);
        this.add(bt_new);
        this.add(bt_contiue);
        this.add(bt_about);
        this.add(bt_exit);
        this.setLayout(null);
        _this = this;
        this.setVisible(true);

    }

    public void HandleEvent(SnakePanel Board) {

        bt_new.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // panel = new SnakePanel();
                // _this.setVisible(false);
                Board.setVisible(true);
                Board.newGame();
            }
        });
        bt_contiue.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // panel = new SnakePanel();
                // _this.setVisible(false);
                Board.setVisible(true);
                Board.continueGame();
            }
        });

        bt_about.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFrame fabout = new JFrame();
                fabout.setSize(400, 300);
                // fabout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fabout.setResizable(false);

                fabout.setLocationRelativeTo(null);
                JLabel label = new JLabel("Author: Hoàng Anh Quân ");
                label.setPreferredSize(new Dimension(150, 100));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                fabout.add(label);
                fabout.setVisible(true);

            }
        });

        bt_exit.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                // panel = new SnakePanel();
                System.exit(0);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 60));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("SNAKE GAME", WIDTH / 2 - metrics.stringWidth("SNAKE GAME") / 2,
                100);
    }
}