package uk.co.jdpatrick.JEngine.ui;

/**
 * @author Jack Patrick
 */
public interface OnClickListener {

    /**
     * Called when the left mouse button is pressed over the button
     *
     * @param element The clicked element
     */
    public void onPress(UIElement element);

    /**
     * Called when the left mouse button is released over the button
     *
     * @param element The clicked element
     */
    public void onRelease(UIElement element);

    /**
     * Called when the left mouse button is released over the button
     *
     * @param element The clicked element
     */
    public void onClick(UIElement element);
}
