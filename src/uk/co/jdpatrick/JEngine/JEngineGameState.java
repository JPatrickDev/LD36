package uk.co.jdpatrick.JEngine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import uk.co.jdpatrick.JEngine.ui.UIElement;
import uk.co.jdpatrick.JEngine.ui.UIView;

/**
 * Created by Jack on 11/10/2014.
 */
public abstract class JEngineGameState extends BasicGameState {


    /** The UIView for the game */
    UIView view = new UIView();

    /**
     * Add an element to the UIView
     *
     * @param element The element to add
     */
    public void addElement(UIElement element){
        view.addElement(element);
    }


    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        for(UIElement e : view.getElements()){
            e.render(graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        for(UIElement e : view.getElements()){
            e.update(gameContainer);
        }
    }

}
