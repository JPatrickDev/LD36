package uk.co.jdpatrick.JEngine.ui.Elements;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.ui.OnClickListener;
import uk.co.jdpatrick.JEngine.ui.UIElement;

/**
 * @author Jack Patrick
 */
public class BasicButton extends UIElement {

    /**  The background color of the button */
    private Color bgColor = Color.gray;

    /** The text color */
    private Color textColor = Color.white;

    /** The text content of the button*/
    private String text;

    /** The width of the text, used for centering */
    private int textWidth = 0;

    /** The height of the text, used for centering */
    private int textHeight = 0;

    /** The X position to draw the text */
    private int textX;

    /** The Y position to draw the text */
    private int textY;

    /** The OnClickListener used by the button */
    private OnClickListener listener;

    /**
     * Create a new button
     * @param x The X position of the Button
     * @param y The Y position of the Button
     * @param width The width of the Button
     * @param height The height of the Button
     * @param text The text drawn on the Button
     */
    public BasicButton(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;

    }

    /**
     * Sets the onClickListener for the button.
     *
     * @param listener The Listener
     * @return this, allows addButton(new Button().setOnClickListener());
     */
    public UIElement setOnClickListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Render the Button
     *
     * @param g The Graphics object to use for rendering
     */
    @Override
    public void render(Graphics g) {
        if (textWidth == 0) {
            textWidth = g.getFont().getWidth(text);
            textHeight = g.getFont().getHeight(text);
            textX = getX() + ((getWidth() / 2) - (textWidth / 2));
            textY = getY() + (getHeight() / 2) - (textHeight / 2);
        }
        g.setColor(bgColor);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(textColor);
        g.drawString(text, textX, textY);
        g.setColor(Color.white);
    }

    /** If the button is waiting for the left mouse button to be released */
    boolean waitingForRelease = false;

    /**
     * Update the button
     *
     * @param container The GameContainer the element is a part of
     */
    @Override
    public void update(GameContainer container) {
        int mX = container.getInput().getMouseX();
        int mY = container.getInput().getMouseY();

        if (mX >= getX() && mX <= getX() + getWidth()) {
            if (mY >= getY() && mY <= getY() + getHeight()) {
                if (Mouse.isButtonDown(0)) {
                    waitingForRelease = true;
                    if (listener != null) {
                        listener.onPress(this);
                    }
                } else {
                    if (waitingForRelease) {
                        waitingForRelease = false;
                        if (listener != null) {
                            listener.onRelease(this);
                            listener.onClick(this);
                        }
                    }
                }
            }
        }
    }

}
