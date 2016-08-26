package uk.co.jdpatrick.JEngine;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import uk.co.jdpatrick.JEngine.ui.UIElement;
import uk.co.jdpatrick.JEngine.ui.UIView;

/**
 * @author Jack Patrick
 */
public abstract class JEngineGame extends BasicGame{

    /** The UIView for the game */
    UIView view = new UIView();

    /**
     * Create a new game
     *
     * @param title The title of the game window
     */
    public JEngineGame(String title) {
        super(title);
    }

    /**
     * Add an element to the UIView
     *
     * @param element The element to add
     */
    public void addElement(UIElement element){
        view.addElement(element);
    }



    @Override
    public void render(GameContainer container, Graphics graphics) throws SlickException {
        for(UIElement e : view.getElements()){
            e.render(graphics);
        }
    }

    @Override
    public void update(GameContainer container, int i) throws SlickException {
        for(UIElement e : view.getElements()){
            e.update(container);
        }
    }
}
