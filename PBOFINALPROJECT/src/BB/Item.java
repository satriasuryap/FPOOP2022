package BB;

import java.awt.*;

//Class definition
public class Item extends MapGenerator implements Constants {
    //Variables
    private int type;

    //Constructor
    public Item(int x, int y, int width, int height, Color color, int type) {
        super(x, y, width, height, color);
        setType(type);
    }

    //Draw an item
    public void draw(Graphics g) {
        if(type == 3) {
            return;
        }
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    //Drop the item down towards the paddle at slow pace
    public void drop() {
        y += 1;
    }

    //Resize the paddle, depending on which item is caught. Changes in increments of 15 until min/max width is reached.
    public void resizePaddle(Player p) {
        if (getType() == 1 && p.getWidth() < PLAYER_MAX) {
            p.setWidth(p.getWidth() + 15);
        }
        else if (getType() == 2 && p.getWidth() > PLAYER_MIN) {
            p.setWidth(p.getWidth() - 15);
        }
    }

    //Set the item's type
    public void setType(int type) {
        this.type = type;
    }

    //Get the item's type
    public int getType() {
        return type;
    }
}

