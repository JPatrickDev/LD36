package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Inventory.Item.ItemStick;
import me.jack.LD36.Inventory.Item.Shelters.Shelter;
import me.jack.LD36.Level.Level;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import java.util.HashMap;

/**
 * Created by Jack on 28/08/2016.
 */
public class ShelterGUI {

    public static Shelter currentShelter = null;

    private static int width = 600, height = 300;

    public static HashMap<Rectangle, Integer> storageSlots = new HashMap<>();

    public static void open(Shelter s) {
        currentShelter = s;
        storageSlots.clear();


        int drawn = 0;
        int x = 105;
        int y = 85;
        while (drawn != currentShelter.getSize()) {
            for (int xx = 0; xx != 2; xx++) {
                storageSlots.put(new Rectangle(x, y, 32, 32), drawn);
                x += 36;
                drawn++;
            }
            x = 105;
            y += 36;
        }
        InGameState.showingShelter = true;
        InGameState.showingCrafting = false;


    }

    public static void updateGUI() {


    }

    public static void renderGUI(Graphics g) {

        g.fillRect(100, 50, width, height);
        g.setColor(Color.black);

        g.drawString("Shelter: " + currentShelter.getName(), 100, 50);

        g.drawString("Exit Shelter", 585, 50);
        g.drawLine(100, 75, 100 + width, 75);

        int drawn = 0;
        int x = 105;
        int y = 85;
        while (drawn != currentShelter.getSize()) {
            for (int xx = 0; xx != 2; xx++) {
                g.setColor(Color.black);
                g.fillRect(x, y, 32, 32);
                if (currentShelter.inv.getItems()[drawn] != null) {
                    ItemStack stack = currentShelter.inv.getItems()[drawn];
                    Image icon = stack.getItem().getIcon();
                    g.drawImage(icon, x, y);
                    g.setColor(Color.white);
                    g.drawString(stack.getStackSize() + "", x, y);
                }
                x += 36;
                drawn++;
            }
            x = 105;
            y += 36;
        }

        g.setColor(Color.gray);
        g.fillRect(182, 85, 300, 255);
        g.setColor(Color.white);
        if (!currentShelter.isFurnace()) {
            g.drawString("No Furnance", 182 + 150 - g.getFont().getWidth("No Furnance") / 2, 85 + 127 - g.getFont().getHeight("No Furnance") / 2);
        } else {

        }

        g.setColor(Color.gray);
        g.fillRect(182 + 300 + 5, 85, 100, 24);
        g.setColor(Color.white);
        g.drawString("Sleep", 182 + 300 + 5, 85);

    }

    static Rectangle closeButton = new Rectangle(585, 50, 115, 24);
    static Rectangle sleepButton = new Rectangle(182+300+5,85,100,24);
    public static void mouseClicked(int button, int x, int y, Level level) {
        if (button != 0) {
            for (Rectangle r : storageSlots.keySet()) {
                if (r.contains(x, y)) {
                    int i = storageSlots.get(r);
                    ItemStack stack = currentShelter.inv.getItems()[i];
                    if (stack == null)
                        return;
                    level.getPlayer().getInventory().addStack(stack);
                    currentShelter.inv.remove(stack);
                }
            }

            for (Rectangle r : HUD.itemSlots.keySet()) {
                if (r.contains(x, y)) {
                    int i = HUD.itemSlots.get(r);
                    ItemStack stack = level.getPlayer().getInventory().getItems()[i];
                    if (stack == null)
                        return;
                    currentShelter.inv.addStack(stack);
                    level.getPlayer().getInventory().remove(stack);
                }
            }

            return;
        }
        if (closeButton.contains(x, y)) {
            InGameState.showingShelter = false;
        }
        if(sleepButton.contains(x,y)){
            if(level.getTime() > 80*1000 || level.getTime() < 20 * 1000){
                level.setTime(25 * 1000);
                level.getPlayer().setHealth(100);
            }
        }
    }
}
