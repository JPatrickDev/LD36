package uk.co.jdpatrick.JEngine.Level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;
import uk.co.jdpatrick.JEngine.JEngine;

import java.awt.Dimension;
import java.util.HashMap;

/**
 * @author Jack Patrick
 */
public class TiledLevel extends Level {

    /**
     * Holds the level information
     */
    Color[] tiles;

    /**
     * Stores Tiles with there associated Color
     */
    protected static HashMap<Color, Tile> tileCodes = new HashMap<Color, Tile>();

    /**
     * The Tile size for the level
     */
    public static int TILE_SIZE = 32;

    /**
     * Create an empty TiledLevel
     *
     * @param width  The width of the level
     * @param height The height of the level
     */
    public TiledLevel(int width, int height) {
        super(width, height);
        c = new Camera(0, 0, 800, 600, TILE_SIZE);
        System.out.println("Creating an empty TiledLevel");
    }

    /**
     * Create a new TiledLevel from a file
     *
     * @param file The location of the file to load from
     */
    public TiledLevel(String file) {
        super(file);
        c = new Camera(0, 0, 800, 600, TILE_SIZE);
    }


    /**
     * Loads a TiledLevel from File
     *
     * @param file The location of the file to load from
     */
    @Override
    protected void load(String file) {
        Image i = ImageUtil.loadImage(file);
        Dimension size = ImageUtil.getSize(i);
        width = (int) size.getWidth();
        height = (int) size.getHeight();
        tiles = new Color[width * height];

        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                Color c = i.getColor(x, y);
                tiles[x + y * width] = c;
            }
        }

        tileCodes.put(Color.decode("#267F00")
                , new Tile("/res/grass.png", false));

        tileCodes.put(Color.decode("#404040"), new Tile("/res/stone.png", false));
    }

    /**
     * Draws the TiledLevel
     *
     * @param g The Graphics object to use for drawing
     */
    @Override
    public void render(Graphics g) {
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                Color tile = tiles[x + y * width];
                Tile t = tileCodes.get(tile);
                if (t == null) continue;
                int tX = (x * TILE_SIZE) - c.getX();
                int tY = (y * TILE_SIZE) - c.getY();
                if (tX < -32 || tX > JEngine.getWidth() + 32) continue;
                if (tY < -32 || tY > JEngine.getHeight() + 32) continue;
                g.drawImage(t.getTile(), tX, tY);
            }
        }
    }
}
