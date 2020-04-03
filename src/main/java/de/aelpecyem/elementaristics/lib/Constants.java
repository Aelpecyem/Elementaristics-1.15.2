package de.aelpecyem.elementaristics.lib;

import net.minecraft.util.DamageSource;

public class Constants {
    public static final String MOD_ID = "elementaristics";
    public static final String MOD_NAME = "Elementaristics";
    public static final String MOD_VERSION = "1.0";

    public static class DamageSources {
        public static final DamageSource HEART_ATTACK = new DamageSource("heart_attack").setDamageBypassesArmor().setDamageIsAbsolute();
    }

    public static class BlockNames {
        public static final String STONE_ENRICHED = "stone_enriched";
        public static final String STONEBRICKS_ENRICHED = "stonebricks_enriched";
        public static final String SMOOTH_STONE_ENRICHED = "smooth_stone_enriched";

        public static final String GREY_MATTER = "grey_matter";
        public static final String SOIL = "soil";
        public static final String SOIL_HARDENED = "soil_hardened";
        public static final String ROOTS = "roots";

        public static final String MORNING_GLORY = "morning_glory";
        public static final String NEUROSE = "neurose";
    }

    public static class WorldNames {
        public static final String MIND_DIMENSION = "mind";
        public static final String MIND_MEADOWS = "mind_meadows";
        public static final String DREAMY_THICKET = "dreamy_thicket";
        public static final String DECADENT_QUAGS = "decadent_quags";

    }

    public static class EntityNames {
        public static final String PLAYER_DUMMY = "player_dummy";
    }

    public static class PotionNames {
        public static final String INTOXICATED = "intoxicated";
    }
}
