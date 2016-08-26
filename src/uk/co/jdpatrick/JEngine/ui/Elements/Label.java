package uk.co.jdpatrick.JEngine.ui.Elements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.ui.UIElement;

/**
 * Created by Jack on 12/10/2014.
 */
public class Label extends UIElement{


    /**
     * The text to draw
     */
    private String text;


    /**
     * The x to draw the text at
     */
    private int renderX;

    /**
     * The y to draw the text at
     */
    private int renderY;
    /**
     * Create a new UIElement
     *
     * @param x      The X position of the element
     * @param y      The Y positon of the element
     * @param width  The width of the element
     * @param height The height of the element
     */
    public Label(int x, int y, int width, int height,String text) {
        super(x, y, width, height);
        this.text = text;
    }

    /**
     * Calculate the x and y position to draw the text
     * @param g The graphics object the text will be rendered with
     */
    private void calculateRenderPosition(Graphics g){
        this.renderX = getX() + (getWidth()/2) + (g.getFont().getWidth(text)/2);
        this.renderY = getY() + (getHeight()/2) + (g.getFont().getHeight(text)/2);
    }

    @Override
    public void render(Graphics g) {
            g.drawString(text,getX(),getY());
    }

    @Override
    public void update(GameContainer container) {

    }
}
