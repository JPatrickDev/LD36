package me.jack.LD36.States;

import me.jack.LD36.Entity.Entity;
import me.jack.LD36.Entity.EntityPlayer;
import me.jack.LD36.GUI.CraftingGUI;
import me.jack.LD36.GUI.HUD;
import me.jack.LD36.Inventory.Item.Item;
import me.jack.LD36.Inventory.Item.ItemStick;
import me.jack.LD36.Level.Level;
import me.jack.LD36.Level.LevelGenerator;
import me.jack.LD36.Level.LevelOverworld;
import me.jack.LD36.Level.LevelUnderworld;
import me.jack.LD36.Level.Tile.Portal;
import me.jack.LD36.Level.Tile.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Jack on 27/08/2016.
 */
public class InGameState extends BasicGameState {

    @Override
    public int getID() {
        return 0;
    }


    LevelOverworld overworld;
    LevelUnderworld underworld;

    public EntityPlayer player;

    private HashMap<Rectangle, Portal> portals = new HashMap<>();

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.init();
        Item.init();
        overworld = (LevelOverworld) LevelGenerator.generateLevel(100, 100);
        underworld = (LevelUnderworld) LevelGenerator.generateUnderworld(100, 100);

        overworld.postCreate(this);
        underworld.postCreate(this);

        for (int i = 0; i != 100; i++) {
            attemptPlacement();
        }
    }

    Random r = new Random();

    private void attemptPlacement() {
        int x = r.nextInt(overworld.getW());
        int y = r.nextInt(overworld.getH());

        int oX = -1, oY = -1;
        if (overworld.getTileAt(x, y) == 1 && overworld.getTileAtTop(x, y) == 0) {
            oX = x;
            oY = y;
        }

        int uX = -1, uY = -1;

        if (oX != -1) {
            x = r.nextInt(underworld.getW());
            y = r.nextInt(underworld.getH());

            if (underworld.getTileAt(x, y) == 5 && underworld.getTileAtTop(x, y) == 0) {
                uX = x;
                uY = y;
            }
        }

        if (oX != -1 && uX != -1) {
            System.out.println("Placed");
            Portal overworld = new Portal(uX * 32, uY * 32, 0);
            Rectangle overworldHitbox = new Rectangle(oX * 32, oY * 32, 32, 32);
            this.overworld.setTile(oX, oY, 10);
            portals.put(overworldHitbox, overworld);


            Portal underworld = new Portal(oX * 32, oY * 32, 1);
            Rectangle underworldHitbox = new Rectangle(uX * 32, uY * 32, 32, 32);
            this.underworld.setTile(uX, uY, 10);
            portals.put(underworldHitbox, underworld);

        }
    }

    public int currentWorld = 0;

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (currentWorld == 0) {
            overworld.render(graphics);
        } else {
            underworld.render(graphics);
        }
        Input i = gameContainer.getInput();
        HUD.render(graphics, this,i.getMouseX(),i.getMouseY());
        if (showingCrafting)
            CraftingGUI.renderGUI(graphics);
    }

    public static boolean showingCrafting = false;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (showingCrafting) {
            CraftingGUI.updateGUI();
        } else {
            if (currentWorld == 0) {
                overworld.update(i);
                if (overworld.getPlayer().getHealth() <= 0) {
                    stateBasedGame.enterState(1);
                }
            } else if (currentWorld == 1) {
                underworld.update(i);
                if (underworld.getPlayer().getHealth() <= 0) {
                    stateBasedGame.enterState(1);
                }
            }

            HUD.update(this);

        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        if (showingCrafting) {
            CraftingGUI.mouseClicked(button, x, y, getLevel());
        } else {
            overworld.getPlayer().action(getLevel(), button);
            HUD.mouseClick(button, x, y);
        }
    }

    @Override
    public void mouseWheelMoved(int newValue) {
        if (newValue > 0) {
            getPlayer().getInventory().itemInHandForwards();
        } else {
            getPlayer().getInventory().itemInHandBackwards();
        }
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public Level getLevel() {
        if (currentWorld == 0)
            return overworld;
        return underworld;
    }

    public HashMap<Rectangle, Portal> getPortals() {
        return portals;
    }
}
