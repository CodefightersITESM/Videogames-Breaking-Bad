/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea4breakingbad;

import com.sun.glass.events.KeyEvent;
import java.awt.Graphics;

/**
 * Player
 * Represents a player that acts as a bar in the game.
 * 
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 6/Mar/2019
 * @version 1.0
 */
public class Player extends Item {
    /**
     * Current game object for running game.
     */
    private Game game;
    
    /**
     * Creates a new player object.
     * @param x the x coordinate of the player
     * @param y the y coordinate of the player 
     * @param width the width of the player
     * @param height the height of the player
     * @param game the current running game
     */
    public Player(int x, int y, int width, int height, Game game) {
        super(x,y,width,height);
        this.game = game;
    }
    
    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }
    
    /**
     * Handles updating the player per-frame.
     */
    @Override
    public void update() {
        // moving horizontally
        if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_LEFT)) {
          setX(getX() - 5);  
        }
        else if (getGame().getKeyManager().isKeyDown(KeyEvent.VK_RIGHT)) {
          setX(getX() + 5);  
        }
        
        // border collisions
        if(getX() + getWidth() >= getGame().getWidth()) {
            setX(getGame().getWidth() - getWidth());
        }
        else if(getX() <= 0) {
           setX(0); 
        }
    }

    /**
     * Handles rendering the player per-frame.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }    
}