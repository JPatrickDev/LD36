package me.jack.LD36;

import me.jack.LD36.States.AboutState;
import me.jack.LD36.States.GameOverState;
import me.jack.LD36.States.InGameState;
import me.jack.LD36.States.MainMenuState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 26/08/2016.
 */
public class LD36 extends StateBasedGame{

    public LD36(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        gameContainer.setUpdateOnlyWhenVisible(false);
        addState(new MainMenuState());
        addState(new InGameState());
        addState(new GameOverState());
        addState(new AboutState());
    }

}
