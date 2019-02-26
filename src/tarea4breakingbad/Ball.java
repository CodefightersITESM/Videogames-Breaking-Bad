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
        velX = 5;
        velY = -5;
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
     * Handles the update per frame of the ball.
     */
    @Override
    public void update() {
        // bounce on the sides
        if(getX() <= 0 || getX() + getWidth() >= getGame().getWidth()) {
            setVelX(getVelX() * -1);
        }
        
        // bounce on the top and bottom
        if(getY() <= 0 || getY() + getHeight() >= getGame().getHeight()) {
            setVelY(getVelY() * -1);
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
