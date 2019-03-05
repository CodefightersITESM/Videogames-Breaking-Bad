/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea4breakingbad;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author anaisabelcruz
 */
public class Block extends Item{    

    private int lives;
    private Ball ball;
    private Animation animation;
    private boolean dead;
    private Timer deadTimer;
    
    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        lives = 3;
        this.animation = new Animation(Assets.block, 83, 83, 0.15, 3);
        this.dead = false;
        deadTimer = new Timer(0.45);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    @Override
    public void update() {
        if(getLives() <= 0) {
            animation.update();
            deadTimer.update();
            if(deadTimer.isActivated()) {
                dead = true;
                deadTimer.restart();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        float alpha = 1.0f;
        if(getLives() == 2){
            alpha = 0.75f;
        }
        else if(getLives() <= 1){
            alpha = 0.5f;
        }
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2d.setComposite(ac);
        
        int frame = animation.getFrame();
        if(frame == 0) {
            g2d.drawImage(animation.getImageFrame(0), getX(), getY(), getX() + getWidth(), getY() + getHeight(), 6, 16, 77, 50, null);
        } else if(frame == 1) {
            g2d.drawImage(animation.getImageFrame(1), getX() + 2, getY(), getX() + getWidth(), getY() + 83, 0, 0, 78, 83, null);
        } else if(frame == 2) {
            g2d.drawImage(animation.getImageFrame(2), getX() + 2, getY(), getX() + getWidth(), getY() + 83, 0, 0, 83, 83, null);
        }
    }
}
