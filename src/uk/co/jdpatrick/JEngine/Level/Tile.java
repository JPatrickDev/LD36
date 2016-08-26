package uk.co.jdpatrick.JEngine.Level;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;

/**
 * @author Jack Patrick
 */
public class Tile {

    /**
     * The Tile Image
     */
    private Image tile;
    /**
     * If the Tile is solid or not
     */
    private boolean solid;

    /**
     * Create a new Tile
     *
     * @param image The Tile Image
     * @param solid If the Tile is solid or not
     */
    public Tile(String image, boolean solid) {
        this.tile = ImageUtil.loadImage(image);
        this.solid = solid;
    }

    /**
     * Create a new Tile
     *
     * @param sheet The SpriteSheet to get the Tile Image from
     * @param x     The X coordinate of the Image in the SpriteSheet
     * @param y     The Y coordinate of the Image in the SpriteSheet
     * @param solid If the Tile is solid or not
     */
    public Tile(SpriteSheet sheet, int x, int y, boolean solid) {
        this.tile = sheet.getSprite(x, y);
        this.solid = solid;
    }

    /**
     * Get the Image for the Tile
     *
     * @return The Image
     */
    public Image getTile() {
        return tile;
    }

    /**
     * @return True if the Tile is solid, False if not
     */
    public boolean isSolid() {
        return solid;
    }
}
