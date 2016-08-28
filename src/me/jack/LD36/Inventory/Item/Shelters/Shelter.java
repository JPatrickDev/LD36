package me.jack.LD36.Inventory.Item.Shelters;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.*;

/**
 * Created by Jack on 28/08/2016.
 */
public class Shelter extends Item {

    boolean furnace;
    public int size;
    public Inventory inv = null;


    public ItemStack input;
    public ItemStack fuel;
    public ItemStack output;

    public int timer = 0;

    public Shelter(int x, int y, int id, String name, String description, boolean furnace, int size) {
        super(x, y, id, name, description);
        this.furnace = furnace;
        this.size = size;
        inv = new Inventory();
        inv.MAX_SIZE = size;
    }

    public boolean isFurnace() {
        return furnace;
    }

    public int getSize() {
        return size;
    }


    public void tick() {
        if (input != null) {
            if (input.getStackSize() == 0) {
                input = null;
                return;
            }
            if (fuel != null) {
                if (fuel.getStackSize() == 0) {
                    fuel = null;
                    return;
                }
                if (fuel.getStackSize() != 0 && input.getStackSize() != 0) {
                    timer++;
                    if (timer >= 84) {
                        if (input.getItem() instanceof ItemIronOre) {
                            if (output != null) {
                                if (output.getItem() instanceof ItemIronBar) {
                                    output.add(1);
                                    fuel.remove(1);
                                    input.remove(1);
                                    timer = 0;
                                }
                            } else {
                                output = new ItemStack(1, new ItemIronBar());
                                fuel.remove(1);
                                input.remove(1);
                                timer = 0;
                            }
                        }
                        if (input.getItem() instanceof ItemRawPork) {
                            if (output != null) {
                                if (output.getItem() instanceof ItemCookedPork) {
                                    output.add(1);
                                    fuel.remove(1);
                                    input.remove(1);
                                    timer = 0;
                                }
                            } else {
                                output = new ItemStack(1, new ItemCookedPork());
                                fuel.remove(1);
                                input.remove(1);
                                timer = 0;
                            }
                        }

                        if (input.getItem() instanceof ItemRawBeef) {
                            if (output != null) {
                                if (output.getItem() instanceof ItemCookedBeef) {
                                    output.add(1);
                                    fuel.remove(1);
                                    input.remove(1);
                                    timer = 0;
                                }
                            } else {
                                output = new ItemStack(1, new ItemCookedBeef());
                                fuel.remove(1);
                                input.remove(1);
                                timer = 0;
                            }
                        }
                    }
                }
            } else {
                timer = 0;
            }
        } else {
            timer = 0;
        }
    }
}
