package BB;

import java.awt.Graphics2D;


public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public static int brickHeight;

    public MapGenerator(int rows, int columns) {
        map = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 75;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Main.block_color);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setColor(Main.bg_color);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int column){
        map[row][column] = value;
    }

}
