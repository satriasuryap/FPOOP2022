package BB;

import java.awt.*;

public class Ball extends MapGenerator implements Constants {
    //Variables
    private boolean onScreen;
    private int xDir = 1, yDir = -1;

    //Constructor
    public Ball(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        setOnScreen(true);
    }

    //Draw the ball
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    //Moves the ball
    public void move() {
        x += xDir;
        y += yDir;
    }

    //Resets the ball to original position at center of screen
    public void reset() {
        x = BALL_X_START;
        y = BALL_Y_START;
        xDir = 1;
        yDir = -1;
    }

    //Mutator methods
    public void setXDir(int xDir) {
        this.xDir = xDir;
    }

    public void setYDir(int yDir) {
        this.yDir = yDir;
    }

    public void setOnScreen(boolean onScreen) {
        this.onScreen = onScreen;
    }

    //Accessor methods
    public int getXDir() {
        return xDir;
    }

    public int getYDir() {
        return yDir;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

}
