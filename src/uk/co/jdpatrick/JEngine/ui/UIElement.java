package uk.co.jdpatrick.JEngine.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author Jack Patrick
 */
public abstract class UIElement{

    /** The X coordinate of the element */
    private int x;

    /** The Y coordinate of the element */
    private int y;

    /** The width of the element */
    private int width;

    /** The height of the element */
    private int height;


    /**
     * Create a new UIElement
     *
     * @param x The X position of the element
     * @param y The Y positon of the element
     * @param width The width of the element
     * @param height The height of the element
     */
    public UIElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    /**
     * Get the X position of the Element
     *
     * @return The X position of the Element
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y position of the Element
     *
     * @return The Y position of the Element
     */
    public int getY() {
        return y;
    }

    /**
     * Get the width of the Element
     *
     * @return The width of the Element
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the Element
     *
     * @return The height of the Element
     */
    public int getHeight() {
        return height;
    }

    /**
     * Draw the element
     *
     * @param g The Graphics object to use for rendering
     */
    public abstract void render(Graphics g);

    /**
     * Updates the element
     *
     * @param container The GameContainer the element is a part of
     */
    public abstract void update(GameContainer container);

}
