package tarea4breakingbad;

/**
 * Main
 * 
 * The driver class that holds the Main method to start the game.
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 30/Jan/2019
 * @version 1.0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game("Tarea4 - Breaking Bad", 800, 600);
        game.start();
    } 
}
