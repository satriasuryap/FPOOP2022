package BB;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.lang.Thread;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.TreeMap;

public class GamePlay extends JPanel implements Runnable, Constants {
    private Player player;
    private Ball ball;
    private Brick[][] brick = new Brick[10][5];
    private int score = 0;
    private int lives = MAX_LIVES;
    private int bricksLeft = MAX_BRICKS;
    private float waitTime = 3;
    private int xSpeed;
    private int level = 1;
    private String playerName;
    private Thread game;
    private ArrayList<Item> items = new ArrayList<Item>();
    private AtomicBoolean isPaused = new AtomicBoolean(true);
    private Color[] blueColors = {BLUE_BRICK_ONE, BLUE_BRICK_TWO, BLUE_BRICK_THREE, Color.BLACK};
    private Color[] redColors = {RED_BRICK_ONE, RED_BRICK_TWO, RED_BRICK_THREE, Color.BLACK};
    private Color[] purpleColors = {PURPLE_BRICK_ONE, PURPLE_BRICK_TWO, PURPLE_BRICK_THREE, Color.BLACK};
    private Color[] yellowColors = {YELLOW_BRICK_ONE, YELLOW_BRICK_TWO, YELLOW_BRICK_THREE, Color.BLACK};
    private Color[] pinkColors = {PINK_BRICK_ONE, PINK_BRICK_TWO, PINK_BRICK_THREE, Color.BLACK};
    private Color[] grayColors = {GRAY_BRICK_ONE, GRAY_BRICK_TWO, GRAY_BRICK_THREE, Color.BLACK};
    private Color[] greenColors = {GREEN_BRICK_ONE, GREEN_BRICK_TWO, GREEN_BRICK_THREE, Color.BLACK};
    private Color[][] colors = {blueColors, redColors, purpleColors, yellowColors, pinkColors, grayColors, greenColors};

    //private Name<String> playerName;

    public GamePlay(int width, int height) {
        super.setSize(width, height);
        addKeyListener(new BoardListener());
        setFocusable(true);

        makeBricks();
        player = new Player(PLAYER_X_START, PLAYER_Y_START, PLAYER_WIDTH, PLAYER_HEIGHT, Color.BLACK);
        ball = new Ball(BALL_X_START, BALL_Y_START, BALL_WIDTH, BALL_HEIGHT, Color.BLACK);

        //Get the player's name
        playerName = JOptionPane.showInputDialog(null, "Please enter your name:", "Brick Breaker", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null) {
            System.exit(0);
        }
            game = new Thread(this);
            game.start();
            stop();
            isPaused.set(true);

    }


    public void makeBricks() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 5; j++) {
                Random rand = new Random();
                int itemType = rand.nextInt(3) + 1;
                int numLives = 3;
                Color color = colors[rand.nextInt(7)][0];;
                brick[i][j] = new Brick((i * BRICK_WIDTH), ((j * BRICK_HEIGHT) + (BRICK_HEIGHT / 2)), BRICK_WIDTH - 5, BRICK_HEIGHT - 5, color, numLives, itemType);
            }
        }
    }

    public void start() {
        game.resume();
        isPaused.set(false);
    }
    public void stop() {
        game.suspend();
    }

    public void destroy() {
        game.resume();
        isPaused.set(false);
        game.stop();
        isPaused.set(true);
    }
    public void run() {
        xSpeed = 1;
        while(true) {
            int x1 = ball.getX();
            long y1 = (long) ball.getY();

            //Makes sure speed doesnt get too fast/slow
            if (Math.abs(xSpeed) > 1) {
                if (xSpeed > 1) {
                    xSpeed--;
                }
                if (xSpeed < 1) {
                    xSpeed++;
                }
            }

            checkPaddle(x1, y1);
            checkWall(x1);
            checkWall(y1);
            checkBricks(x1, y1);
            checkLives();
            checkIfOut(y1);
            ball.move();
            dropItems();
            checkItemList();
            repaint();

            try {
                game.sleep((int)waitTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    public void addItem(Item i) {
        items.add(i);
    }

    public void dropItems() {
        for (int i = 0; i < items.size(); i++) {
            Item tempItem = items.get(i);
            tempItem.drop();
            items.set(i, tempItem);
        }
    }

    public void checkItemList() {
        for (int i = 0; i < items.size(); i++) {
            Item tempItem = items.get(i);
            if (player.caughtItem(tempItem)) {
                items.remove(i);
            }
            else if (tempItem.getY() > WINDOW_HEIGHT) {
                items.remove(i);
            }
        }
    }
    public void checkLives() {
        if (bricksLeft == NO_BRICKS) {
            ball.reset();
            bricksLeft = MAX_BRICKS;
            makeBricks();
            lives++;
            level++;
            score += 100;
            repaint();
            stop();
            isPaused.set(true);
        }
        if (lives == MIN_LIVES) {
            repaint();
            stop();
            isPaused.set(true);
        }
    }
    public void checkPaddle(int x1, long y1) {
        if (player.hitPaddle(x1, y1) && ball.getXDir() < 0) {
            ball.setYDir(-1);
            xSpeed = -1;
            ball.setXDir(xSpeed);
        }
        if (player.hitPaddle(x1, y1) && ball.getXDir() > 0) {
            ball.setYDir(-1);
            xSpeed = 1;
            ball.setXDir(xSpeed);
        }

        if (player.getX() <= 0) {
            player.setX(0);
        }
        if (player.getX() + player.getWidth() >= getWidth()) {
            player.setX(getWidth() - player.getWidth());
        }
    }
    public void checkWall(int x1) {
        if (x1 >= getWidth() - ball.getWidth()) {
            xSpeed = -Math.abs(xSpeed);
            ball.setXDir(xSpeed);
        }
        if (x1 <= 0) {
            xSpeed = Math.abs(xSpeed);
            ball.setXDir(xSpeed);
        }
    }
    public void checkWall(long y1){
        if (y1 <= 0) {
            ball.setYDir(1);
        }
        if (y1 >= getHeight()) {
            ball.setYDir(-1);
        }
    }
    public void checkBricks(int x1, long y1) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (brick[i][j].hitBottom(x1, y1)) {
                    ball.setYDir(1);
                    if (brick[i][j].isDestroyed()) {
                        bricksLeft--;
                        score += 50;
                        addItem(brick[i][j].item);
                    }
                }
                if (brick[i][j].hitLeft(x1, y1)) {
                    xSpeed = -xSpeed;
                    ball.setXDir(xSpeed);
                    if (brick[i][j].isDestroyed()) {
                        bricksLeft--;
                        score += 50;
                       addItem(brick[i][j].item);
                    }
                }
                if (brick[i][j].hitRight(x1, y1)) {
                    xSpeed = -xSpeed;
                    ball.setXDir(xSpeed);
                    if (brick[i][j].isDestroyed()) {
                        bricksLeft--;
                        score += 50;
                        addItem(brick[i][j].item);
                    }
                }
                if (brick[i][j].hitTop(x1, y1)) {
                    ball.setYDir(-1);
                    if (brick[i][j].isDestroyed()) {
                        bricksLeft--;
                        score += 50;
                        addItem(brick[i][j].item);
                    }
                }
            }
        }
    }
    public void checkIfOut(long y1) {
        if (y1 > PLAYER_Y_START + 10) {
            lives--;
            score -= 100;
            ball.reset();
            repaint();
            stop();
            isPaused.set(true);
        }
    }
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();
        super.paintComponent(g);
        player.draw(g);
        ball.draw(g);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                brick[i][j].draw(g);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString("Lives: " + lives, 10, getHeight() - (getHeight()/10));
        g.drawString("Score: " + score, 10, getHeight() - (2*(getHeight()/10)) + 25);
        g.drawString("Level: " + level, 10, getHeight() - (3*(getHeight()/10)) + 50);
        g.drawString("Player: " + playerName, 10, getHeight() - (4*(getHeight()/10)) + 75);

        for (Item i: items) {
            i.draw(g);
        }

        if (lives == MIN_LIVES) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Name: " + playerName + ", Score: " + score + ", Level: " + level, getWidth()/5, 20);
            g.drawString("Game Over! Did you make it onto the high score table?", getWidth()/5, 50);
            try {
                printScores(g);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            g.drawString("Press the Spacebar twice to play again.", getWidth()/5, getHeight()-20);
        }
    }
    //Makes sure the HighScores.txt file exists
    public void makeTable() throws IOException {
        String filename = "HighScores";
        File f = new File(filename + ".txt");
        if (f.createNewFile()) {
            try {
                writeFakeScores();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        else {
            //do nothing
        }
    }

    //if there was no previous high score table, this one inputs 10 fake players and scores to fill it
    public void writeFakeScores() throws IOException {
        Random rand = new Random();

        int numLines = 10;
        File f = new File("HighScores.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
        for (int i = 1; i <= numLines; i++) {
            int score = rand.nextInt(2000);
            if (numLines - i >= 1) {
                bw.write("Name: " + "Player" + i + ", " + "Score: " + score + "\n");
            }
            else {
                bw.write("Name: " + "Player" + i + ", " + "Score: " + score);
            }
        }
        bw.close();
        try {
            sortTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    //Returns the player's name and score formatted correctly
    public String playerInfo() {
        return "Name: " + playerName + ", Score: " + score;
    }

    //returns the number of lines in the high score file
    public int linesInFile(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
        int lines = 0;
        while (br.readLine() != null) {
            lines++;
        }
        br.close();
        return lines;
    }
    //Add game to high score file by appending it and getting line number from previous method
    public void saveGame() throws IOException {
        File f = new File("HighScores.txt");
        FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append("\n" + playerInfo());
        bw.close();
    }
    //sorts the high score table high to low using maps and other fun things
    public void sortTable() throws IOException {
        File f = new File("HighScores.txt");
        File temp = new File("temp.txt");
        TreeMap<Integer, ArrayList<String>> topTen = new TreeMap<Integer, ArrayList<String>>();
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp.getAbsoluteFile()));


        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            String[] scores = line.split("Score: ");
            Integer score = Integer.valueOf(scores[1]);
            ArrayList<String> players = null;

            //make sure two players with same score are dealt with
            if ((players = topTen.get(score)) == null) {
                players = new ArrayList<String>(1);
                players.add(scores[0]);
                topTen.put(Integer.valueOf(scores[1]), players);
            }
            else {
                players.add(scores[0]);
            }

        }

        for (Integer score : topTen.descendingKeySet()) {
            for (String player : topTen.get(score)) {
                try {
                    bw.append(player + "Score: " + score + "\n");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        br.close();
        bw.close();
        try {
            makeNewScoreTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    //save the sorted table to the high score file
    public void makeNewScoreTable() throws IOException {
        File f = new File("HighScores.txt");
        File g = new File("temp.txt");
        f.delete();
        g.renameTo(f);
    }

    //Print the top 10 scores, but first excecutes all other file-related methods
    public void printScores(Graphics g) throws IOException {
        try {
            makeTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            saveGame();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            sortTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        int h = 100;
        File fileToRead = new File("HighScores.txt");
        LineNumberReader lnr = new LineNumberReader(new FileReader(fileToRead));
        String line = lnr.readLine();
        while (line != null && lnr.getLineNumber() <= 10) {
            int rank = lnr.getLineNumber();
            g.drawString(rank + ". " + line, getWidth()/5, h);
            h += 15;
            line = lnr.readLine();
        }
        lnr.close();
    }
    //Private class that handles gameplay and controls
    private class BoardListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                if (lives > MIN_LIVES) {
                    if (isPaused.get() == false) {
                        stop();
                        isPaused.set(true);
                    }
                    else {
                        start();
                    }
                }
                else {
                    player.setWidth(getWidth()/7);
                    lives = MAX_LIVES;
                    score = 0;
                    bricksLeft = MAX_BRICKS;
                    level = 1;
                    makeBricks();
                    isPaused.set(true);
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 5; j++) {
                            brick[i][j].setDestroyed(false);
                        }
                    }
                }
            }
            if (key == KeyEvent.VK_LEFT) {
                player.setX(player.getX() - 50);
            }
            if (key == KeyEvent.VK_RIGHT) {
                player.setX(player.getX() + 50);
            }
        }
        @Override
        public void keyReleased(KeyEvent ke) {
            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                player.setX(player.getX());
            }
            if (key == KeyEvent.VK_RIGHT) {
                player.setX(player.getX());
            }
        }
    }
}
