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
 * @author Isabel Cruz
 */
public class Player extends Item {
    private Game game;
    
    public Player(int x, int y, int width, int height, Game game){
        super(x,y,width,height);
        this.game = game;
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
    }
    
}
