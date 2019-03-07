package tarea4breakingbad;

import java.awt.Graphics;

/**
 * Ball
 * 
 * Represents the ball that bounces in the game.
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 6/Mar/2019
 * @version 1.0
 */
public class Ball extends Item {
    /**
     * Game object of the running game.
     */
        private Game game;

    
    /**
     * Velocity of the ball
     */
    private int velX;
    private int velY;
    
    /**
     * Determines if the ball is at the bottom ready to be shot.
     */
    private boolean bottom;
    
    /**
     * Creates a new ball object with the desired initial position.
     * @param x initial x position 
     * @param y initial y position
     * @param width width of the image
     * @param height height of the image
     * @param game the game this object is running in
     */
    public Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        velX = 0;
        velY = -6;
        bottom = true;
    }
    
    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }
    
    /**
     * @return the velX
     */
    public int getVelX() {
        return velX;
    }
    
    /**
     * @param velX new velX to set
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }
    
    /**
     * @return the velY
     */
    public int getVelY() {
        return velY;
    }
    
    /**
     * @param velY new velY to set
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * @return the bottom
     */
    public boolean isBottom() {
        return bottom;
    }

    /**
     * @param bottom new bottom to set
     */
    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }
    
    /**
     * Handles the update per frame of the ball.
     */
    @Override
    public void update() {
        // bounce on the sides
        if(getX() <= 0 || getX() + getWidth() >= getGame().getWidth()) {
            setVelX(getVelX() * -1);
        }
        
        // bounce on the top
        if(getY() <= 0) {
            setVelY(getVelY() * -1);
        }
        // bounce on bottom
        if(getY() + getHeight() >= getGame().getHeight()) {
            setVelY(-6);
            setVelX(0);
            getGame().setLives(getGame().getLives() - 1);
            setBottom(true);
        }
        
        // update position
        setX(getX() + getVelX());
        setY(getY() + getVelY());
    }
    
    /**
     * Handles the rendering per frame of the ball.
     * @param g the graphics object
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
}
