package tarea4breakingbad;

import java.awt.image.BufferedImage;

/**
 * Animation
 * 
 * Class that handles the sub-imaging of a spritesheet to simulate an animation.
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 6/Mar/2019
 * @version 1.0
 */
public class Animation {
    private BufferedImage image;
    private int width;
    private int height;
    private Timer timer;
    private double speed;
    private int frame;
    private int maxFrames;
    
    /**
     * Creates a new animation.
     * @param image sprite sheet to use
     * @param width width of a single frame of the sprite sheet
     * @param height height of a single frame of the sprite sheet
     * @param speed speed at which the frame change
     * @param maxFrames determines when to reset frames back to 0
     */
    public Animation(BufferedImage image, int width, int height, double speed, int maxFrames) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = 0;
        this.timer = new Timer(speed);
        this.maxFrames = maxFrames;
    }
    
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * @param speed speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    /**
     * @return the frame
     */
    public int getFrame() {
        return frame;
    }
    
    /**
     * @param frame the frame to set
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }
    
    /**
     * Handles the update of the animation per-frame.
     */
    public void update() {
        timer.update();
        if(timer.isActivated()) {
            frame++;
            
            // if we reach max frames, restart to 0
            if(frame == maxFrames) {
                frame = 0;
            }
            timer.restart();
        }
    }
    
    /**
     * Crops a part of the image depending on the column desired.
     * @param column the column in the sprite sheet
     * @return a cropped image from the sprite sheet
     */
    public BufferedImage getImageFrame(int column) {
        return image.getSubimage(width * column, 0, width, height);
    }
}
