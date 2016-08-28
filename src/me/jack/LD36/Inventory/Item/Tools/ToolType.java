package me.jack.LD36.Inventory.Item.Tools;

/**
 * Created by Jack on 28/08/2016.
 */
public enum ToolType {

    AXE(new int[]{4}),PICK(new int[]{6,13,11}),SWORD(new int[]{-1});

    private int[] effectiveAgainst;
    ToolType(int[] effectiveAgainst){
        this.effectiveAgainst = effectiveAgainst;
    }

    public int[] getEffectiveAgainst() {
        return effectiveAgainst;
    }
}
