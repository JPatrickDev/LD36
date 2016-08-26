package uk.co.jdpatrick.JEngine;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

/**
 * JEngine class with static methods for starting a game
 *
 * @author Jack Patrick
 */
public class JEngine {

    /**
     * The width of the window
     */
    private static int width = 0;

    /**
     * The height of the window
     */
    private static int height = 0;

    /**
     * Start a game
     *
     * @param game The {@code Game} to start
     */
    public static void start(Game game) {
        start(game, 800, 600);
    }

    /**
     * Start a game with the specified window width and height
     *
     * @param game   The {@code Game} to start
     * @param width  The width of the game window
     * @param height The height of the game window
     */
    public static void start(Game game, int width, int height) {
        start(game, width, height, 60);
    }

    /**
     * Start a game with the specified window width and height, and set a target FPS
     * Set the fps variable to 0 to have an unset target FPS
     *
     * @param game   The {@code Game} to start
     * @param width  The width of the game window
     * @param height The height of the game window
     * @param fps    The target FPS
     */
    public static void start(Game game, int width, int height, int fps) {
        try {
            AppGameContainer agc = new AppGameContainer(game);
            agc.setDisplayMode(width, height, false);
            if (fps != 0)
                agc.setTargetFrameRate(fps);

            agc.setUpdateOnlyWhenVisible(false);
            JEngine.width = width;
            JEngine.height = height;
            agc.start();
        } catch (SlickException e) {
            System.err.println("Error starting game");
            e.printStackTrace();
        }
    }


    /**
     * Get the width of the window
     *
     * @return The width of the game window
     */
    public static int getWidth() {
        return JEngine.width;
    }

    /**
     * Get the height of the window
     *
     * @return The height of the game window
     */
    public static int getHeight() {
        return JEngine.height;
    }
}
