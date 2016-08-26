package uk.co.jdpatrick.JEngine.ui;

import java.util.ArrayList;

/**
 * @author Jack Patrick
 */
public class UIView {

    /** A list of the UIElements in the view  */
    private ArrayList<UIElement> elements = new ArrayList<UIElement>();


    /**
     * Adds a new UIElement to the view
     *
     * @param element The UIElement to add
     */
    public void addElement(UIElement element){
        elements.add(element);
    }

    /**
     * Remove an UIElement from the view
     *
     * @param element The UIElement to remove
     */
    public void removeElement(UIElement element){
        elements.remove(element);
    }


    /**
     * Get all the UIElements in the view
     *
     * @return An ArrayList of the UIElements
     */
    public ArrayList<UIElement> getElements(){
        return  elements;
    }
}
