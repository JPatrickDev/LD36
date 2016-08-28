package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Item.*;
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
        if (currentShelter != null)
            currentShelter.tick();
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
            g.drawString("Furnance", 182 + 150 - g.getFont().getWidth("Furnace") / 2, 90);

            g.drawRect(290, 150, 32, 32);
            if (currentShelter.input != null) {
                ItemStack stack = currentShelter.input;
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, 290, 150);
                g.setColor(Color.white);
                g.drawString(stack.getStackSize() + "", 290, 150);
            }
            g.drawString("Input", 282, 130);


            g.drawString("Fuel", 342, 130);
            g.drawRect(182 + 160, 150, 32, 32);
            if (currentShelter.fuel != null) {
                ItemStack stack = currentShelter.fuel;
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, 182 + 160, 150);
                g.setColor(Color.white);
                g.drawString(stack.getStackSize() + "", 182 + 160, 150);
            }


            g.fillRect(290, 150 + 40, currentShelter.timer, 16);

            g.drawString("Output", 290 + 42 - 26, 203 + 32 - 16);
            g.drawRect(290 + 42 - 16, 206 + 32, 32, 32);
            if (currentShelter.output != null) {
                ItemStack stack = currentShelter.output;
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, 290 + 42 - 16, 206 + 32);
                g.setColor(Color.white);
                g.drawString(stack.getStackSize() + "", 290 + 42 - 16, 206 + 32);
            }
        }

        g.setColor(Color.gray);
        g.fillRect(182 + 300 + 5, 85, 100, 24);
        g.setColor(Color.white);
        g.drawString("Sleep", 182 + 300 + 5, 85);


    }

    static Rectangle closeButton = new Rectangle(585, 50, 115, 24);
    static Rectangle sleepButton = new Rectangle(182 + 300 + 5, 85, 100, 24);

    static Rectangle inputBox = new Rectangle(290, 150, 32, 32);
    static Rectangle outputBox = new Rectangle(290 + 42 - 16, 206 + 32, 32, 32);
    static Rectangle fuelBox = new Rectangle(182 + 160, 150, 32, 32);

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

        if (fuelBox.contains(x, y)) {
            if (currentShelter.fuel != null)
                level.getPlayer().getInventory().addStack(currentShelter.fuel);
            currentShelter.fuel = null;
        }
        if (inputBox.contains(x, y)) {
            if (currentShelter.input != null)
                level.getPlayer().getInventory().addStack(currentShelter.input);
            currentShelter.input = null;
        }
        if (outputBox.contains(x, y)) {
            if (currentShelter.output != null)
                level.getPlayer().getInventory().addStack(currentShelter.output);
            currentShelter.output = null;
        }

        for (Rectangle r : storageSlots.keySet()) {
            if (r.contains(x, y)) {
                int i = storageSlots.get(r);
                ItemStack stack = currentShelter.inv.getItems()[i];
                if (stack == null)
                    return;

                if (stack.getItem() instanceof ItemCoal) {
                    if (currentShelter.fuel != null) {
                        currentShelter.fuel.add(stack.getStackSize());
                    } else {
                        currentShelter.fuel = new ItemStack(stack.getStackSize(), stack.getItem());
                    }
                    currentShelter.inv.remove(stack);
                }
                if (stack.getItem() instanceof ItemIronOre) {
                    if (currentShelter.input != null) {
                        currentShelter.input.add(stack.getStackSize());
                    } else {
                        currentShelter.input = new ItemStack(stack.getStackSize(), stack.getItem());
                    }
                    currentShelter.inv.remove(stack);
                }

                if(stack.getItem() instanceof ItemRawPork){
                    if (currentShelter.input != null) {
                        currentShelter.input.add(stack.getStackSize());
                    } else {
                        currentShelter.input = new ItemStack(stack.getStackSize(), stack.getItem());
                    }
                    currentShelter.inv.remove(stack);
                }


                if(stack.getItem() instanceof ItemRawBeef){
                    if (currentShelter.input != null) {
                        currentShelter.input.add(stack.getStackSize());
                    } else {
                        currentShelter.input = new ItemStack(stack.getStackSize(), stack.getItem());
                    }
                    currentShelter.inv.remove(stack);
                }

            }
        }


        if (closeButton.contains(x, y)) {
            InGameState.showingShelter = false;
        }
        if (sleepButton.contains(x, y)) {
            if (level.getTime() > 80 * 1000 || level.getTime() < 20 * 1000) {
                level.setTime(25 * 1000);
                level.getPlayer().setHealth(100);
            }
        }
    }
}
