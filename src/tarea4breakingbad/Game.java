package tarea4breakingbad;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Game Handles everything that the game needs to function correctly.
 *
 * @author César Barraza A01176786 Date 30/Jan/2019
 * @version 1.0
 */
public class Game implements Runnable {

    /**
     * The display's properties.
     */
    private String title;
    private int width;
    private int height;

    /**
     * The display that represents the game's window.
     */
    private Display display;

    /**
     * The game's input managers.
     */
    private KeyManager keyManager;
    private MouseManager mouseManager;

    /**
     * The main game loop thread.
     */
    private Thread thread;

    /**
     * Denotes whether or not the game is running.
     */
    private boolean isRunning;

    /**
     * The game items.
     */
    private Ball ball;
    private Player player;
    private Block[][] blocks;
    private boolean paused;

    /**
     * The game timers
     */
    private Timer collisionTimer;
    /**
     * Game lives
     */
    private int lives;

    /**
     * Initializes the game object with the desired display properties.
     *
     * @param title
     * @param width
     * @param height
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isRunning = false;
        this.keyManager = new KeyManager();
        this.mouseManager = new MouseManager();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * @return the mouseManager
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Starts all initializations needed to start the game.
     */
    private void init() {
        // create the display
        display = new Display(getTitle(), getWidth(), getHeight());
        display.getWindow().addKeyListener(getKeyManager());
        display.getWindow().addMouseListener(getMouseManager());
        display.getWindow().addMouseMotionListener(getMouseManager());
        display.getCanvas().addMouseListener(getMouseManager());
        display.getCanvas().addMouseMotionListener(getMouseManager());

        // initialize the game's assets
        Assets.init();

        // start the game
        initItems();
    }

    /**
     * Creates the items that will be used in the game.
     */
    private void initItems() {
        player = new Player((getWidth() / 2) - 50, 500, 100, 50, this);
        ball = new Ball(player.getX() + 45, player.getY() - 10, 9, 9, this);
        blocks = new Block[4][7];
        lives = 3;
        int tempY = 40;
        for (int y = 0; y < 4; y++) {
            int tempX = 55;
            for (int x = 0; x < 7; x++) {
                int w = 71;
                int h = 34;
                int posX = tempX + w * x;
                int posY = tempY + h * y;
                blocks[y][x] = new Block(posX, posY, w, h);

                tempX += 30;
            }
            tempY += 30;
        }

        collisionTimer = new Timer(0.04d);
    }

    /**
     * Starts the main game thread.
     */
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the game thread execution.
     */
    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    int a = 0;

    /**
     * Updates the game every frame.
     */
    private void update() {
      if(getLives() > 0) {
        if(getKeyManager().isKeyPressed(KeyEvent.VK_P)) {
            setPaused(!isPaused());
        }

        if (!isPaused()) {
            if (ball.isBottom()) {
                ball.setX(player.getX() + 45);
                ball.setY(player.getY() - 10);
                player.setX((getWidth() / 2) - 50);
                player.setY(500);
                if (getKeyManager().isKeyPressed(KeyEvent.VK_SPACE)) {
                    ball.setBottom(false);
                }
            } else {
                ball.update();
                player.update();
            }

            // bounce ball on player
            if (player.intersects(ball)) {
                if (ball.getY() <= player.getY()) {
                    int center = player.getX() + (player.getWidth() / 2);
                    int centerBall = ball.getX() + (ball.getWidth() / 2);

                    double percent = Math.abs((centerBall - player.getX()) / (double) (player.getWidth()));
                    if (percent >= 1.0) {
                        percent = 1.0;
                    }
                    double angle = Math.toRadians(180 * percent);
                    int newVelX = (int) Math.round(Math.cos(angle) * 6);
                    int newVelY = (int) Math.round(Math.sin(angle) * 6);
                    if (newVelY <= 0) {
                        newVelX = 1;
                    }

                    ball.setVelX(newVelX * -1);
                    ball.setVelY(Math.abs(newVelY) * -1);
                }
            }

            // bounce on blocks
            collisionTimer.update();
            if (collisionTimer.isActivated()) {
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 7; x++) {
                        Block block = blocks[y][x];
                        block.update();
                        if (block.isDead()) {
                            continue;
                        }
                        int centerX = ball.getX() + ball.getWidth() / 2;
                        int centerY = ball.getY() + ball.getHeight() / 2;
                        if (ball.intersects(block)) {
                            if (centerY >= block.getY() + block.getHeight() || centerY <= block.getY()) {
                                ball.setVelY(ball.getVelY() * -1);
                            } else if (centerX < block.getX() || centerX > block.getX()) {
                                ball.setVelX(ball.getVelX() * -1);
                            } else {
                                if (ball.getVelX() == 0) {
                                    ball.setVelY(ball.getVelY() * -1);
                                }
                            }
                            collisionTimer.restart();
                            block.setLives(block.getLives() - 1);
                        }
                    }
                }
            }
        } else {
            if (getKeyManager().isKeyPressed(KeyEvent.VK_S)) {
                saveGame();
            } else if (getKeyManager().isKeyPressed(KeyEvent.VK_L)) {
                loadGame();
            }
        }
      }
      if(getLives() == 0 && getKeyManager().isKeyPressed(KeyEvent.VK_R)) {
          restart();
      }
       // update input
        getKeyManager().update();
        getMouseManager().update();
    }

    /**
     * Renders the game every frame.
     */
    private void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();

        // if there's no buffer strategy yet, create it
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            // clear screen
            Graphics g = bs.getDrawGraphics();
            g.clearRect(0, 0, getWidth(), getHeight());
            g.drawImage(Assets.background, 0, 0, getWidth(), getHeight(), null);

            ball.render(g);
            player.render(g);
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 7; x++) {
                    if (blocks[y][x].isDead()) {
                        continue;
                    }
                    blocks[y][x].render(g);
                }
            }

            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
            ((Graphics2D) g).setComposite(ac);

            if (lives != 0) {
                for (int i = 0; i < getLives(); i++) {
                    g.drawImage(Assets.barrel, 20 + (i * 40 + (i * 10)), 550, 40, 40, null);
                }
            }
            // string space
            if (ball.isBottom()) {
                g.setColor(new Color(20, 255, 20));
                g.setFont(new Font("Century Gothic", Font.BOLD, 30));
                g.drawString("Press space to shoot ball", 216, 440);
            }

            // render paused screen
            if (isPaused()) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("Century Gothic", Font.BOLD, 40));
                g.drawString("PAUSED", 328, 300);
            }
            
            //game over
            if(getLives() == 0) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("Century Gothic", Font.BOLD, 40));
                g.drawString("Press R to restart", 242, 300);
            }
            
            // actually render the whole scene
            bs.show();
            g.dispose();
        }
    }

    private void saveGame() {
        try {
            FileWriter fw = new FileWriter("save.txt");

            fw.write(String.valueOf(player.getX()) + '\n');
            fw.write(String.valueOf(player.getY()) + '\n');
            fw.write(String.valueOf(getLives()) + '\n');
            fw.write(String.valueOf(ball.getX()) + '\n');
            fw.write(String.valueOf(ball.getY()) + '\n');
            fw.write(String.valueOf(ball.getVelX()) + '\n');
            fw.write(String.valueOf(ball.getVelY()) + '\n');
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 7; x++) {
                    Block block = blocks[y][x];
                    fw.write(String.valueOf(block.getLives()) + '\n');
                    int dead = (block.isDead() ? 1 : 0);
                    fw.write(String.valueOf(dead) + '\n');
                }
            }

            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGame() {        
        try {
            BufferedReader br = new BufferedReader(new FileReader("save.txt"));

            player.setX(Integer.parseInt(br.readLine()));
            player.setY(Integer.parseInt(br.readLine()));
            setLives(Integer.parseInt(br.readLine()));
            ball.setX(Integer.parseInt(br.readLine()));
            ball.setY(Integer.parseInt(br.readLine()));
            ball.setVelX(Integer.parseInt(br.readLine()));
            ball.setVelY(Integer.parseInt(br.readLine()));
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 7; x++) {
                    Block block = blocks[y][x];
                    block.setLives(Integer.parseInt(br.readLine()));
                    int dead = Integer.parseInt(br.readLine());
                    block.setDead(dead == 1);
                }
            }

            br.close();   
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The main game loop.
     */
    @Override
    public void run() {
        init();

        // set up timing constants
        final int maxFPS = 60;
        final double timeTick = 1000000000 / maxFPS;

        // set up timing variables
        double delta = 0.0d;
        long now = 0;
        long lastTime = System.nanoTime();

        // start game loop
        while (isRunning) {
            // calculate delta
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;

            // make sure game always plays at desired fps
            if (delta >= 1.0d) {
                update();
                render();
                delta--;
            }
        }

        // once game loop is over, close the game
        stop();
    }
    /**
     * restart.
     */
    public void restart(){
        initItems();
    }
}
