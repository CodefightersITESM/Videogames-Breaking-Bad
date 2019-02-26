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
    
    public Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        velX = 3;
        velY = -1;
    }
    
    public Game getGame() {
        return game;
    }
    
    public int getVelX() {
        return velX;
    }
    
    public void setVelX(int velX) {
        this.velX = velX;
    }
    
    public int getVelY() {
        return velY;
    }
    
    public void setVelY(int velY) {
        this.velY = velY;
    }
    
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
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
}
