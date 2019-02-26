/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea4breakingbad;

import java.awt.Graphics;

/**
 *
 * @author anaisabelcruz
 */
public class Block extends Item{    

    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.block, getX(), getY(), getX() + getWidth(), getY() + getHeight(), 6, 16, 77, 50, null);
    }
}
