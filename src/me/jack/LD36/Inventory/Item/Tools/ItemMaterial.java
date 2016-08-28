package me.jack.LD36.Inventory.Item.Tools;

/**
 * Created by Jack on 27/08/2016.
 */
public enum ItemMaterial{

    WOOD(1f),STONE(1.5f),IRON(3f);

    private float multiplier;

    ItemMaterial(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }
}
