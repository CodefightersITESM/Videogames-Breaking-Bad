/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea4breakingbad;

import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class Animation {
    private BufferedImage image;
    private int width;
    private int height;
    private Timer timer;
    private int speed;
    private int frame;
    
    public Animation(BufferedImage image, int width, int height, int speed) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = 0;
        this.timer = new Timer(speed);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getFrame() {
        return frame;
    }
    
    public void setFrame(int frame) {
        this.frame = frame;
    }
    
    public void update() {
        timer.update();
        if(timer.isActivated()) {
            frame++;
            //if()
        }
    }
    
//    public BufferedImage getImageFrame(int column) {
//        
//    }
}
