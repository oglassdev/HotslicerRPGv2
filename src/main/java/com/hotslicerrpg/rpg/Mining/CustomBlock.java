package com.hotslicerrpg.rpg.Mining;

import org.bukkit.Location;
import org.bukkit.Material;

public enum CustomBlock {
    STONE_0(Material.STONE,(byte) 0,100,1,Material.BEDROCK,(byte)0,4),
    TEST_0(Material.LAPIS_BLOCK,(byte) 0,300,2,Material.BEDROCK,(byte)0,4),
    TEST_1(Material.STAINED_GLASS,(byte) 5,500,3,Material.BEDROCK,(byte)0,4),
    TEST_3(Material.STAINED_GLASS,(byte) 3,1000,5,Material.BEDROCK,(byte)0,4),
    /*STONE_1(Material.STONE,(byte) 5,110,1,Material.BEDROCK,(byte)0,4),
    STONE_2(Material.STAINED_CLAY,(byte) 9,120,1,Material.BEDROCK,(byte)0,5),
    COAL(Material.COAL_ORE,(byte) 0,160,2, Material.BEDROCK,(byte)0,10)*/
    ;

    private final Material material;
    private final byte data;
    private final int hardness;
    private final int requiredPower;
    private final int xp;
    private final Material replaceMaterial;
    private final byte replaceData;

    CustomBlock(Material material, byte data, int hardness, int requiredPower, Material replaceMaterial, byte replaceData, int xp) {
        this.material = material;
        this.data = data;
        this.hardness = hardness;
        this.requiredPower = requiredPower;
        this.replaceMaterial = replaceMaterial;
        this.replaceData = replaceData;
        this.xp = xp;
    }

    public Material getMaterial() {
        return material;
    }
    public byte getData() {
        return data;
    }
    public int getHardness() {
        return hardness;
    }
    public int getRequiredPower() {
        return requiredPower;
    }

    public boolean isReplaceable() {
        return replaceMaterial != null;
    }

    public byte getReplaceData() {
        return replaceData;
    }

    public Material getReplaceMaterial() {
        return replaceMaterial;
    }

    public int getXP() {
        return xp;
    }

    @Override
    public String toString() {
        return "CustomBlock{" +
                "material=" + material +
                ", data=" + data +
                ", hardness=" + hardness +
                ", xp=" + xp +
                ", replaceMaterial=" + replaceMaterial +
                ", replaceData=" + replaceData +
                '}';
    }

    public static CustomBlock getBlock(Location location) {
        return getBlock(location.getBlock().getType(),location.getBlock().getData());
    }

    public static CustomBlock getBlock(Material material, int data) {
        for (CustomBlock block : CustomBlock.values()) {
            if (block.data == data && block.material == material) return block;
        }
        return null;
    }
}
