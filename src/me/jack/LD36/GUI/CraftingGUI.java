package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.*;
import me.jack.LD36.Inventory.Item.Shelters.ItemAdvancedTent;
import me.jack.LD36.Inventory.Item.Shelters.ItemTent;
import me.jack.LD36.Inventory.Item.Tools.*;
import me.jack.LD36.Level.Level;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Jack on 27/08/2016.
 */
public class CraftingGUI {


    private static int width = 600, height = 300;

    private static String[] categories = new String[]{"Tools", "Weapons", "Shelter"};

    private static String[][] items = new String[categories.length][];

    private static Item[][] itemObjects = new Item[categories.length][];

    private static CraftingRequirements[][] crafting = new CraftingRequirements[categories.length][];
    public static Rectangle catRect = null;
    public static Rectangle itemRect = null;

    static int categoryIndex = 0;
    static int itemIndex = 0;

    static {
        items[0] = new String[]{"Wood Axe", "Wood Pick", "Stone Axe", "Stone Pick", "Iron Axe", "Iron Pick"};
        items[1] = new String[]{"Wood Sword", "Stone Sword", "Iron Pick"};
        items[2] = new String[]{"Basic Tent", "Advance Tent"};

        itemObjects[0] = new Item[]{new ItemWoodAxe(), new ItemWoodPick(), new ItemStoneAxe(), new ItemStonePick(), new ItemIronAxe(), new ItemIronPick()};
        itemObjects[1] = new Item[]{new ItemWoodSword(), new ItemStoneSword(), new ItemIronSword()};
        itemObjects[2] = new Item[]{new ItemTent(), new ItemAdvancedTent()};

        crafting[0] = new CraftingRequirements[]{
                new CraftingRequirements(new ItemStack[]{new ItemStack(5, new ItemStick())}),

                new CraftingRequirements(new ItemStack[]{new ItemStack(10, new ItemStick())}),

                new CraftingRequirements(new ItemStack[]{new ItemStack(5, new ItemStick()), new ItemStack(2, new ItemStone())}),

                new CraftingRequirements(new ItemStack[]{new ItemStack(10, new ItemStick()), new ItemStack(4, new ItemStone())}),

                new CraftingRequirements(new ItemStack[]{new ItemStack(5, new ItemStick()), new ItemStack(2, new ItemIronBar())}),

                new CraftingRequirements(new ItemStack[]{new ItemStack(10, new ItemStick()), new ItemStack(4, new ItemIronBar())}),
        };

        crafting[1] = new CraftingRequirements[]{
                new CraftingRequirements(new ItemStack[]{new ItemStack(10, new ItemStick())}),
                new CraftingRequirements(new ItemStack[]{new ItemStack(8, new ItemStick()), new ItemStack(10, new ItemStone())}),
                new CraftingRequirements(new ItemStack[]{new ItemStack(10, new ItemStick()), new ItemStack(10, new ItemIronBar())})
        };

        crafting[2] = new CraftingRequirements[]{

                new CraftingRequirements(new ItemStack[]{new ItemStack(30, new ItemStick()), new ItemStack(10, new ItemLeather())}),

                new CraftingRequirements(new ItemStack[]{new ItemStack(100, new ItemStick()), new ItemStack(15, new ItemLeather()), new ItemStack(10, new ItemStone())})
        };

        catRect = new Rectangle(110, 100, 74, categories.length * 32);
        itemRect = new Rectangle(184, 110, 120, items[categoryIndex].length * 30);

    }


    static Rectangle close = new Rectangle(653, 50, 80, 25);

    public static void renderGUI(Graphics g) {
        g.fillRect(100, 50, width, height);
        g.setColor(Color.black);

        g.drawString("Close", 653, 50);

        g.drawString("Crafting", (400) - g.getFont().getWidth("Crafting") / 2, 50);
        int x = 110;
        int y = 100;
        for (String s : categories) {
            if (s.equals(categories[categoryIndex])) {
                g.setColor(Color.red);
            }
            g.drawRect(x, y, 64, 30);
            g.setColor(Color.black);
            g.drawString(s, x + 32 - g.getFont().getWidth(s) / 2, y + 15 - g.getFont().getHeight(s) / 2);
            y += 32;
        }
        g.drawLine(x + 74, 50, x + 74, 350);


        x = 184;
        y = 110;
        String[] itemList = items[categoryIndex];
        for (String s : itemList) {
            if (s.equals(itemList[itemIndex])) {
                g.setColor(Color.red);
            }
            g.drawRect(x + 10, y, 100, 20);
            g.setColor(Color.black);
            g.drawString(s, x + 60 - g.getFont().getWidth(s) / 2, y);
            y += 30;
        }
        g.drawLine(x + 120, 50, x + 120, 350);

        y = 110;
        x = 314;


        Item currentItem = itemObjects[categoryIndex][itemIndex];
        g.drawImage(currentItem.getIcon(), x, y);


        g.drawString(currentItem.getName(), x + 40, y);
        String requirementsString = "";

        CraftingRequirements requirements = crafting[categoryIndex][itemIndex];
        for (ItemStack stack : requirements.getStack()) {
            requirementsString += stack.getStackSize() + " " + stack.getItem().getName() + "/s,";
        }
        g.drawString("Crafting:" + requirementsString, x-6, y + 40);

        g.drawString("Description:", x, y + 80);
        g.drawString(currentItem.getDescription(), x, y + 100);

        g.drawRect(x, y + 140, 60, 32);
        g.drawString("Craft", x, y + 140);


        g.setColor(Color.white);
    }

    static Rectangle craftButton = new Rectangle(314, 250, 60, 32);

    public static void updateGUI() {

    }


    public static void mouseClicked(int button, int x, int y, Level level) {
        if (button == 0) {
            if (catRect.contains(x, y)) {
                System.out.println("Clicked inside cat");
                int rY = (int) (y - catRect.getY());
                int pos = rY / 32;
                categoryIndex = pos;
                itemIndex = 0;
                itemRect = new Rectangle(184, 110, 120, items[categoryIndex].length * 30);
            } else if (itemRect.contains(x, y)) {
                System.out.println("Clicked inside items");
                int rY = (int) (y - itemRect.getY());
                int pos = rY / 30;
                itemIndex = pos;

            } else if (craftButton.contains(x, y)) {
                Inventory inv = level.getPlayer().getInventory();
                CraftingRequirements requirements = crafting[categoryIndex][itemIndex];
                boolean canAfford = true;
                for (ItemStack stack : requirements.getStack()) {
                    if (!inv.contains(stack)) {
                        canAfford = false;
                    }
                }

                if (canAfford) {
                    for (ItemStack stack : requirements.getStack()) {
                        inv.remove(stack);
                    }
                    Item currentItem = itemObjects[categoryIndex][itemIndex];
                    inv.addItem(currentItem, 1);
                }
            } else if (close.contains(x, y)) {
                InGameState.showingCrafting = false;
            }
        }
    }

    public static void keyPressed(int key) {

    }
}
