package uk.co.jdpatrick.JEngine.Image;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.Dimension;
import java.math.BigInteger;


/**
 * Contains some static methods related to Images
 *
 * @author Jack Patrick
 */
public class ImageUtil {

    /**
     * Loads an Image
     *
     * @param res The filepath to load
     * @return The loaded image or null if there was an error
     */
    public static Image loadImage(String res) {
        Image i = null;
        try {
            i = new Image(res);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Gets the width and height of an Image
     *
     * @param i The image to get the size of
     * @return The Dimension of the Image
     */
    public static Dimension getSize(Image i) {
        Dimension d = new Dimension(i.getWidth(), i.getHeight());
        return d;
    }

    /**
     * Gets the width and height of an Image for a String path
     *
     * @param res The filepath of the image
     * @return The Dimension of the Image
     */
    public static Dimension getSize(String res) {
        return getSize(loadImage(res));
    }

    /**
     * Converts the given RGB values into a hexadecimal color code
     *
     * @param r The red value to convert
     * @param g The green value to convert
     * @param b The blue value to convert
     * @return The converted hexadecimal color code as a string
     */
    public static String getHexString(int r, int g, int b) {
        String hex = String.format("#%02x%02x%02x", r, g, b);
        return hex;
    }


    /**
     * Converts the given RGB values into hexadecimal
     *
     * @param r The red value to convert
     * @param g The green value to convert
     * @param b The blue value to convert
     * @return The converted hexadecimal Integer.
     */
    public static int getHex(int r, int g, int b) {
        return r << 16 | g << 8 | b;
    }

    public static Color hexToColor(int hex) {
        String hexString = Integer.toHexString(hex);
        return Color.decode("#" + hexString);
    }

    public static int fromHexString(String hexString) {
        return new BigInteger(hexString, 16).intValue();
    }
}
