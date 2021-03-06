package me.jack.LD36.States;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 27/08/2016.
 */
public class GameOverState extends BasicGameState{
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
            graphics.drawString("You died!",340,300);
            graphics.drawString("Score: " + InGameState.score,345,320);
            graphics.drawString("Press any key to return to the menu",250,340);
    }

    boolean back = false;
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(back){
            back = false;
            stateBasedGame.enterState(2);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        back = true;

    }
}
