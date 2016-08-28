package me.jack.LD36.States;

import me.jack.LD36.Entity.Entity;
import me.jack.LD36.Entity.EntityPlayer;
import me.jack.LD36.GUI.CraftingGUI;
import me.jack.LD36.GUI.HUD;
import me.jack.LD36.Inventory.Item.Item;
import me.jack.LD36.Inventory.Item.ItemStick;
import me.jack.LD36.Level.Level;
import me.jack.LD36.Level.LevelGenerator;
import me.jack.LD36.Level.Tile.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 27/08/2016.
 */
public class InGameState extends BasicGameState {

    @Override
    public int getID() {
        return 0;
    }


    Level level;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.init();
        Item.init();
        level = LevelGenerator.generateLevel(100, 100);
        level.getPlayer().getInventory().addItem(new ItemStick(), 5);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        level.render(graphics);
        HUD.render(graphics, this);
        if (showingCrafting)
            CraftingGUI.renderGUI(graphics);
    }

    public static boolean showingCrafting = false;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (showingCrafting) {
            CraftingGUI.updateGUI();
        } else {
            level.update(i);
            HUD.update(this);
            if (level.getPlayer().getHealth() <= 0) {
                stateBasedGame.enterState(1);
            }
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        if (showingCrafting) {
            CraftingGUI.mouseClicked(button, x, y, level);
        } else {
            level.getPlayer().action(level);
            HUD.mouseClick(button, x, y);
        }
    }

    @Override
    public void mouseWheelMoved(int newValue) {
      if(newValue > 0){
          getPlayer().getInventory().itemInHandForwards();
      }else{
          getPlayer().getInventory().itemInHandBackwards();
      }
    }

    public EntityPlayer getPlayer() {
        return level.getPlayer();
    }

    public Level getLevel() {
        return level;
    }
}
