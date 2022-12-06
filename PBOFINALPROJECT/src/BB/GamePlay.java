package BB;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    public static boolean play = false;
    public static int score = 0;
    public static int totalBricks;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    public static int ballposX = 400;
    public static int ballposY = 400;
    public static int ballXdir = -1;
    public static int ballYdir = -2;
    public static MapGenerator map;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Main.bg_color);
        g.fillRect(1, 1, 692, 592);

        g.setColor(Main.player_color);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Main.ball_color);
        g.fillOval(ballposX, ballposY, 20, 20);

        map.draw((Graphics2D) g);

        g.setColor(Main.player_color);
        g.drawString("" + score, 640, 35);

        if (ballposY > 550) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Main.player_color);
            g.drawString("Game Over, Score : " + score, 300, 300);
            g.setColor(Main.block_color);
            g.drawString("Press Enter to restart : ", 300, 350);
        }
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Main.ball_color);
            g.drawString("You Won!, Score : " + score, 300, 300);
            g.setColor(Main.block_color);
            g.drawString("Press Enter to restart : ", 300, 350);
        }

        g.dispose();
    }

    public void moveLeft() {
        play = true;
        playerX -= 10;
    }

    public void moveRight() {
        play = true;
        playerX += 10;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 0) {
                playerX = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 400;
                ballposY = 400;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                map = new MapGenerator(4, 7);
                }
                repaint();
            }
        }


    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            Rectangle r1 = new Rectangle(ballposX, ballposY, 20, 20);
            Rectangle r2 = new Rectangle(playerX, 550, 100, 8);
            if (r1.intersects(r2)) {
                ballYdir = -ballYdir;
            }

            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[i].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
//
        }

        repaint();
    }
}
