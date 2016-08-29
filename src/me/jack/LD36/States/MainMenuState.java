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
public class MainMenuState extends BasicGameState {
    @Override
    public int getID() {
        return 2;
    }

    public Image bg = null;

    static Rectangle play = new Rectangle(301, 216, 202, 47);
    static Rectangle about = new Rectangle(264, 313, 271, 47);
    static Rectangle exit = new Rectangle(307, 411, 187, 47);

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        bg = new Image("res/menubg.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(bg, 0, 0);
    }

    boolean playGo = false, aboutGo = false;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (playGo) {
            stateBasedGame.enterState(0);
            playGo = false;
        }

        if (aboutGo) {
            stateBasedGame.enterState(3);
            aboutGo = false;
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        if (play.contains(x, y)) {
            playGo = true;
        } else if (about.contains(x, y)) {
            aboutGo = true;
        } else if (exit.contains(x, y)) {
            System.exit(0);
        }
    }



}
