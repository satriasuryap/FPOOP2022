package BB ;

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame implements Constants {
    //Variables
    private static JFrame frame;
    private static GamePlay gamePlay;
    private static Container pane;
    private static Dimension dim;

    //Build and run the game
    public static void main(String[] args) {
        //Set look and feel to that of OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Brick Breaker");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePlay = new GamePlay(WINDOW_WIDTH, WINDOW_HEIGHT);

        pane = frame.getContentPane();
        pane.add(gamePlay);

        //Place frame in the middle of the screen
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        frame.setVisible(true);


       // MenuFrames menuFrames = new MenuFrames();
    }
}