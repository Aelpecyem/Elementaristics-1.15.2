package de.aelpecyem.elementaristics.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ToolType;

public class Constants {
    public static final String MOD_ID = "elementaristics";
    public static final String MOD_NAME = "Elementaristics";
    public static final String MOD_VERSION = "1.0";

    public static final String MIND_DIMENSION = "mind";

    public static class DamageSources {
        public static final DamageSource HEART_ATTACK = new DamageSource("heart_attack").setDamageBypassesArmor().setDamageIsAbsolute();
    }

    public static class BlockBuilders{
        public static Block.Properties ENRICHED_STONE = Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(5).harvestLevel(1).harvestTool(ToolType.PICKAXE);

    }

    public static class EntityNames {
        public static final String PLAYER_DUMMY = "player_dummy";
    }

}
