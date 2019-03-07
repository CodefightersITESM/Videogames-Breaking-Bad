package tarea4breakingbad;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Block
 * 
 * Representation of a block that can be broken in the game.
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 6/Mar/2019
 * @version 1.0
 */
public class Block extends Item{    
    /**
     * Determines how many times this block needs to be hit to disappear.
     */
    private int lives;
    
    /**
     * Breaking animation for the block.
     */
    private Animation animation;
    
    /**
     * Determines if this block is disappeared or not.
     */
    private boolean dead;
    private Timer deadTimer;
    
    /**
     * Creates a new block object.
     * @param x x position of the block
     * @param y y position of the block
     * @param width width of the block
     * @param height height of the block
     */
    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        lives = 3;
        this.animation = new Animation(Assets.block, 83, 83, 0.15, 3);
        this.dead = false;
        deadTimer = new Timer(0.45);
    }

    /**
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param lives lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    /**
     * @return the dead state
     */
    public boolean isDead() {
        return dead;
    }
    
    /**
     * @param dead the dead state to set
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    /**
     * Handles the updating of the block per frame.
     */
    @Override
    public void update() {
        // if block dies, make a breaking animation
        if(getLives() <= 0) {
            animation.update();
            deadTimer.update();
            
            // once the animation ends, make the block disappear
            if(deadTimer.isActivated()) {
                dead = true;
                deadTimer.restart();
            }
        }
    }

    /**
     * Handles the block rendering per-frame.
     * @param g graphics object that renders
     */
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        // make the block have translucency depending on its lives
        float alpha = 1.0f;
        if(getLives() == 2){
            alpha = 0.75f;
        }
        else if(getLives() <= 1){
            alpha = 0.5f;
        }
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2d.setComposite(ac);
        
        // render the block depending on the animation if it is breaking
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
