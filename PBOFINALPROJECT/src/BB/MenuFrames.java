package BB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrames implements ActionListener, Constants {

    public JButton button;
    public JLabel label;
    public JFrame frame;
    public JFrame frame2;
    public GamePlay gamePlay;
    public Dimension dim;
    public Dimension dim2;

    public Container pane;

        MenuFrames() {

            button = new JButton();
            label = new JLabel();
            frame = new JFrame();

            label.setText("Brick Breaker");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setBackground(Color.WHITE);

            button.setBounds(100, 100, 250, 100);
            button.addActionListener(this);
            button.setText("Play!");
            button.setVerticalTextPosition(JButton.BOTTOM);
            button.setHorizontalTextPosition(JButton.CENTER);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setSize(500,500);
            frame.setVisible(true);
            frame.add(label);
            frame.add(button);

            dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            Intro intro = new Intro();
            intro.instrc();
            intro.guide();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception t) {
                t.printStackTrace();
            }
            frame2 = new JFrame("Brick Breaker");
            frame2.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame2.setResizable(false);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gamePlay = new GamePlay(WINDOW_WIDTH, WINDOW_HEIGHT);

            pane = frame.getContentPane();
            pane.add(gamePlay);

            //Place frame in the middle of the screen
            dim2 = Toolkit.getDefaultToolkit().getScreenSize();
            frame2.setLocation(dim2.width/2-frame.getSize().width/2, dim2.height/2-frame.getSize().height/2);
            frame.dispose();
            frame2.setVisible(true);
        }
    }
}
