/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea4breakingbad;

<<<<<<< Updated upstream
import java.awt.AlphaComposite;
=======
import java.awt.Color;
>>>>>>> Stashed changes
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author anaisabelcruz
 */
public class Block extends Item{    

    private int lives;
    private Ball ball;
    
    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        lives = 2;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        float alpha = 1.0f;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2d.setComposite(ac);
        g2d.drawImage(Assets.block, getX(), getY(), getX() + getWidth(), getY() + getHeight(), 6, 16, 77, 50, null);
    }
}
