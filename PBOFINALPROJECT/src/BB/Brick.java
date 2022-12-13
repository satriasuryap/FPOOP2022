package BB;

import java.awt.*;
import java.util.Random;

//Class definition
public class Brick extends MapGenerator implements Constants {
    //Variables
    private int lives, hits;
    private boolean destroyed;
    public Item item;
    private Color itemColor;
    private Color[] blueColors = {BLUE_BRICK_ONE, BLUE_BRICK_TWO, BLUE_BRICK_THREE, Color.BLACK};
    private Color[] redColors = {RED_BRICK_ONE, RED_BRICK_TWO, RED_BRICK_THREE, Color.BLACK};
    private Color[] purpleColors = {PURPLE_BRICK_ONE, PURPLE_BRICK_TWO, PURPLE_BRICK_THREE, Color.BLACK};
    private Color[] yellowColors = {YELLOW_BRICK_ONE, YELLOW_BRICK_TWO, YELLOW_BRICK_THREE, Color.BLACK};
    private Color[] pinkColors = {PINK_BRICK_ONE, PINK_BRICK_TWO, PINK_BRICK_THREE, Color.BLACK};
    private Color[] grayColors = {GRAY_BRICK_ONE, GRAY_BRICK_TWO, GRAY_BRICK_THREE, Color.BLACK};
    private Color[] greenColors = {GREEN_BRICK_ONE, GREEN_BRICK_TWO, GREEN_BRICK_THREE, Color.BLACK};
    private Color[][] colors = {blueColors, redColors, purpleColors, yellowColors, pinkColors, grayColors, greenColors};


    //Constructor
    public Brick(int x, int y, int width, int height, Color color, int lives, int itemType) {
        super(x, y, width, height, color);
        setLives(lives);
        setHits(0);
        setDestroyed(false);

        if (itemType == 1) {
            itemColor = Color.GREEN;
        }
        if (itemType == 2) {
            itemColor = Color.RED;
        }

        //Places an item of specified type inside the brick to fall when the brick is destroyed
        item = new Item(x + (width / 4), y + (height / 4), ITEM_WIDTH, ITEM_HEIGHT, itemColor, itemType);

    }

    //Draws a brick
    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }

    //Add a hit to the brick, and destroy the brick when hits == lives
    public void addHit() {
        hits++;
        nextColor();
        if (hits == lives) {
            setDestroyed(true);
        }
    }

    //Change color to get lighter until the brick is destroyed
    public void nextColor() {
        if (color == colors[0][0] || color == colors[0][1] || color == colors[0][2]) {
            color = blueColors[hits];
        }
        if (color == colors[1][0] || color == colors[1][1] || color == colors[1][2]) {
            color = redColors[hits];
        }
        if (color == colors[2][0] || color == colors[2][1] || color == colors[2][2]) {
            color = purpleColors[hits];
        }
        if (color == colors[3][0] || color == colors[3][1] || color == colors[3][2]) {
            color = yellowColors[hits];
        }
        if (color == colors[4][0] || color == colors[4][1] || color == colors[4][2]) {
            color = pinkColors[hits];
        }
        if (color == colors[5][0] || color == colors[5][1] || color == colors[5][2]) {
            color = grayColors[hits];
        }
        if (color == colors[6][0] || color == colors[6][1] || color == colors[6][2]) {
            color = greenColors[hits];
        }
    }


    //Detect if the brick has been hit on its bottom, top, left, or right sides
    public boolean hitBottom(int ballX, long ballY) {
        if ((ballX >= x) && (ballX <= x + width + 1) && (ballY == y + height) && (destroyed == false)) {
            addHit();
            return true;
        }
        return false;
    }

    public boolean hitTop(int ballX, long ballY) {
        if ((ballX >= x) && (ballX <= x + width + 1) && (ballY == y) && (destroyed == false)) {
            addHit();
            return true;
        }
        return false;
    }

    public boolean hitLeft(int ballX, long ballY) {
        if ((ballY >= y) && (ballY <= y + height) && (ballX == x) && (destroyed == false)) {
            addHit();
            return true;
        }
        return false;
    }

    public boolean hitRight(int ballX, long ballY) {
        if ((ballY >= y) && (ballY <= y + height) && (ballX == x + width) && (destroyed == false)) {
            addHit();
            return true;
        }
        return false;
    }

    //Mutator methods
    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    //Accessor methods
    public int getLives() {
        return lives;
    }

    public int getHits() {
        return hits;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
