package tarea4breakingbad;

import java.awt.image.BufferedImage;

/**
 * Assets
 * 
 * Helper class to manage all the assets that the game will use.
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 6/Mar/2019
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
    public static BufferedImage barrel;
    
    /**
     * Audio that will be used by the game.
     */
    public static SoundClip bounceClip;
    public static SoundClip breakClip;
    
    /**
     * Loads all the assets that the game needs.
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/op1.png");
        player = ImageLoader.loadImage("/images/calzon.png");
        ball = ImageLoader.loadImage("/images/ball.png");
        block = ImageLoader.loadImage("/images/pill.png");
        barrel = ImageLoader.loadImage("/images/barril.png");
        bounceClip = new SoundClip("/audio/bounce.wav");
        breakClip = new SoundClip("/audio/break.wav");
    }
}