package tarea4breakingbad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Game
 * Handles everything that the game needs to function correctly.
 *
 * @author César Barraza A01176786
 * Date 30/Jan/2019
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
        ball = new Ball(getWidth() / 2, getHeight() - 60, 9, 9, this);
        player = new Player(400, 500, 100, 50, this);
        blocks = new Block[4][7];
        lives = 3;
        int tempY = 40;
        for(int y = 0; y < 4; y++) {
            int tempX = 55;
            for(int x = 0; x < 7; x++) {
                int w = 71;
                int h = 34;
                int posX = tempX + w * x;
                int posY = tempY + h * y;
                blocks[y][x] = new Block(posX, posY, w, h);
                
                tempX += 30;
            }
            tempY += 30;
        }
        
        collisionTimer = new Timer(0.05d);
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

    /**
     * Updates the game every frame.
     */
    private void update() {
        ball.update();
        player.update();
        
        // bounce ball on player
       if(player.intersects(ball)) {
           int center = player.getX() + (player.getWidth()/2);
           int centerBall = ball.getX() + (ball.getWidth()/2);
           if(ball.getVelX() == 0){
               ball.setVelX(5);
           }
            if (centerBall >= center - 20 && centerBall <= center + 20) {
                ball.setVelX(0);
                ball.setVelY(ball.getVelY() * -1);
            }
            else if(centerBall < center) {
                ball.setVelX(Math.abs(ball.getVelX()) * -1);
                ball.setVelY(ball.getVelY() * -1);
            }
            else if(centerBall > center){
                ball.setVelX(Math.abs(ball.getVelX()));
                ball.setVelY(ball.getVelY() * -1);
            }
        }
       
       // bounce on blocks
       collisionTimer.update();
       if(collisionTimer.isActivated()) {
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 7; x++) {
                    Block block = blocks[y][x];
                    if(block.getLives() == 0) {
                        continue;
                    }
                    int centerX = ball.getX() + ball.getWidth() / 2;
                    int centerY = ball.getY() + ball.getHeight() / 2;
                    if(ball.intersects(block)) {
                        if(centerY >= block.getY() + block.getHeight() || centerY <= block.getY()) {
                            ball.setVelY(ball.getVelY() * -1);
                        } else if(centerX < block.getX() || centerX > block.getX()) {
                            ball.setVelX(ball.getVelX() * -1);
                        } else {
                            if(ball.getVelX() == 0) {
                                ball.setVelY(ball.getVelY() * -1);
                            }
                        }
                        collisionTimer.restart();
                        block.setLives(block.getLives() - 1);
                    }
                }
            }
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
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 7; x++) {
                    if(blocks[y][x].getLives() == 0) {
                        continue;
                    }
                    blocks[y][x].render(g);
                }
            }
            if(lives != 0) {
                int width = Assets.barrel.getWidth();
                for(int i = 0; i < getLives(); i++) {
                    g.drawImage(Assets.barrel, 30 + (i * Assets.barrel.getWidth() + (i * 10)), 530, width, 50, null);

                }
            }
            
            // actually render the whole scene
            bs.show();
            g.dispose();
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
        while(isRunning) {
            // calculate delta
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            
            // make sure game always plays at desired fps
            if(delta >= 1.0d) {
                update();
                render();
                delta--;
            }
        }
        
        // once game loop is over, close the game
        stop();
    }
}
