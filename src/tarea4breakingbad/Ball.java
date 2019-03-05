/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea4breakingbad;

import java.awt.Graphics;

/**
 *
 * @author Cesar Barraza
 */
public class Ball extends Item {
    private Game game;
    private int velX;
    private int velY;
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
        velY = -5;
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

    public boolean isBottom() {
        return bottom;
    }

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
            setVelY(getVelY() * -1);
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
