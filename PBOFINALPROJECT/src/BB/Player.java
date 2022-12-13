package BB;

//This "Paddle" class extends the "Structure" class. It is used for the player's paddle in the game.

//Imports
import java.awt.*;
import java.awt.event.*;

//Class definition
public class Player extends MapGenerator implements Constants {
    //Variables
    int xSpeed;

    //Constructor
    public Player(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    //Draws the paddle
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    //Places the paddle back in starting position at center of screen
    public void reset() {
        x = PLAYER_X_START;
        y = PLAYER_Y_START;
    }

    //Checks if the ball hit the paddle
    public boolean hitPaddle(int ballX, long ballY) {
        if ((ballX >= x) && (ballX <= x + width) && ((ballY >= y) && (ballY <= y + height))) {
            return true;
        }
        return false;
    }
    public boolean caughtItem(Item i) {
        if ((i.getX() < x + width) && (i.getX() + i.getWidth() > x) && (y == i.getY() || y == i.getY() - 1)) {
            i.resizePaddle(this);
            return true;
        }
        return false;
    }
}


