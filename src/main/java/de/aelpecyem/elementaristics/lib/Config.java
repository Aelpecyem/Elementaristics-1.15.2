package de.aelpecyem.elementaristics.lib;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_SOULS = "souls";

    public static final String CATEGORY_MIND_DIMENSION = "mind_dimension";

    private static final String CATEGORY_WORLD = "world";

    public static ForgeConfigSpec.IntValue MORNING_GLORY_FREQUENCY;

    public static ForgeConfigSpec.IntValue WEIRD_CUBES_FREQUENCY;

    public static ForgeConfigSpec COMMON_CONFIG;
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final String CATEGORY_PARTICLES = "particles";

    public static ForgeConfigSpec.BooleanValue REDUCED_PARTICLES;

    static {
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        COMMON_BUILDER.pop();
        worldGenConfig();
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Soul-mechanic settings").push(CATEGORY_SOULS);
        COMMON_BUILDER.pop();

        particleConfig();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static void worldGenConfig(){
        COMMON_BUILDER.comment("World settings").push(CATEGORY_WORLD);
        MORNING_GLORY_FREQUENCY = COMMON_BUILDER.comment("Determines the chance for Morning Glory plants to spawn in a chunk (1 out of chance)").defineInRange("Morning Glory Chance", 32, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.comment("Mind-dimension settings").push(CATEGORY_MIND_DIMENSION);
        WEIRD_CUBES_FREQUENCY = COMMON_BUILDER.comment("Determines the chance for shapes to spawn in the Fields of Reason").defineInRange("Weird Shapes Chance", 10, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
    }
    public static void particleConfig(){
        CLIENT_BUILDER.comment("Particle settings").push(CATEGORY_PARTICLES);
        REDUCED_PARTICLES = CLIENT_BUILDER.comment("Activate reduced particles").define("Reduced Particles", false);
        CLIENT_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }

}
