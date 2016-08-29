package me.jack.LD36.States;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 29/08/2016.
 */
public class AboutState extends BasicGameState {
    @Override
    public int getID() {
        return 3;
    }

    public Image bg = null;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        bg = new Image("res/about.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(bg, 0, 0);
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
