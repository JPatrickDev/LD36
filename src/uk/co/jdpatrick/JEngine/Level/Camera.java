package uk.co.jdpatrick.JEngine.Level;

/**
 * @author Jack Patrick
 */
public class Camera {

    /**
     * X position of the camera
     */
    public int x;
    /**
     * Y position of the camera
     */
    public int y;

    /**
     * Width of the camera
     */
    int width;
    /**
     * Height of the camera
     */
    int height;

    /**
     * Tilesize of the camera
     */
    int tileSize;

    /**
     * Create a new Camera
     *
     * @param x        Starting x position of the camera
     * @param y        Starting y position of the camera
     * @param width    The width of the camera
     * @param height   The height of the Camera
     * @param tileSize The tilesize to use to get actual width.
     */
    public Camera(int x, int y, int width, int height, int tileSize) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
    }

    /**
     * Get the X position of the Camera
     *
     * @return The x position of the Camera
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y position of the Camera
     *
     * @return The y position of the Camera
     */
    public int getY() {
        return y;
    }

    /**
     * Get the width of the Camera
     *
     * @return The width of the Camera
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the Camera
     *
     * @return The height of the Camera
     */
    public int getHeight() {
        return height;
    }


    /**
     * Set the X position of the Camera
     *
     * @param x The new x position
     */
    public void setX(int x) {
        this.x = x;
    }


    /**
     * Set the Y position of the Camera
     *
     * @param y The new y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Center the camera on the given coordinates
     *
     * @param px      The X coordinate to center on
     * @param py      The Y coordinate to center on
     * @param mWidth  The width of the Level
     * @param mHeight The height of the Level
     */
    public void center(int px, int py, int mWidth, int mHeight) {
        x = px - (width / 2);
        if (x < 0)
            x = 0;
        if (x >= (mWidth * this.tileSize) - width) {
            x = (mWidth * this.tileSize) - width;
        }
        y = py - (height / 2);
        if (y < 0)
            y = 0;
        if (y >= ((mHeight) * this.tileSize) - height) {
            y = ((mHeight) * this.tileSize) - height;
        }
    }

}
