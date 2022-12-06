package BB ;

import java.awt.Color;
import javax.swing.JFrame;

public class Main {

    public static JFrame frame = new JFrame("Brick Breaker");
    public static Color bg_color;
    public static Color block_color;
    public static Color ball_color;
    public static Color player_color;



    public static void main(String[] args) {
        GamePlay gamePlay = new GamePlay();
        bg_color = Color.decode("#F5F5F5");
        block_color = Color.decode("#0C1427");
        ball_color = Color.decode("#6495ED");
        player_color = Color.decode("#DC143C");
        GamePlay.totalBricks = 28;
        MapGenerator.brickHeight = 50;
        GamePlay.map = new MapGenerator(4, 7);

        frame.add(gamePlay);
        frame.setBounds(100, 75, 710, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
