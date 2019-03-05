package tarea4breakingbad;

import java.awt.image.BufferedImage;

/**
 * Assets
 * 
 * Helper class to manage all the assets that the game will use.
 * @author César Barraza A01176786
 * Date 30/Jan/2019
 * @version 1.0
 */
public class Assets {
    /**
     * Images that will be used by the game.
     */
    public static BufferedImage background;
    public static BufferedImage player;
    public static BufferedImage ball;
    public static BufferedImage block;
    
    /**
     * Audio that will be used by the game.
     */
    
    /**
     * Loads all the assets that the game needs.
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/op1.png");
        player = ImageLoader.loadImage("/images/player.png");
        ball = ImageLoader.loadImage("/images/ball.png");
        block = ImageLoader.loadImage("/images/pill.png");
    }
}