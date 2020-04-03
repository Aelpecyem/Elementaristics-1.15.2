package de.aelpecyem.elementaristics.common.world.biomes;

import de.aelpecyem.elementaristics.reg.ModBlocks;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public abstract class ModBiome extends Biome {
    private static final BlockState GRASS = Blocks.GRASS.getDefaultState(); //short grass
    private static final BlockState DAPPLED_GRASS = Blocks.FERN.getDefaultState(); //higher grass with white spots

    public static final BlockClusterFeatureConfig GRASS_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GRASS), new SimpleBlockPlacer()).tries(32).build();
    public static final BlockClusterFeatureConfig DENSE_GRASS_CONFIG = new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addState(GRASS, 3).addState(DAPPLED_GRASS, 1), new SimpleBlockPlacer()).tries(32).build();
    public static final BlockClusterFeatureConfig NEUROTIC_WEEDS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILY_OF_THE_VALLEY.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();


    public static final SurfaceBuilderConfig MIND_SURFACE_CONFIG = new SurfaceBuilderConfig(ModBlocks.soil.getDefaultState(), ModBlocks.soil_hardened.getDefaultState(), ModBlocks.grey_matter.getDefaultState());

    public ModBiome(SurfaceBuilder<SurfaceBuilderConfig> surface, Category category, float depth, float scale, float temp, int waterColor, int fogColor) {
        super(new Biome.Builder().surfaceBuilder(surface, MIND_SURFACE_CONFIG)
                .precipitation(RainType.NONE)
                .category(category)
                .depth(depth)
                .scale(scale)
                .temperature(temp)
                .downfall(0.0F)
                .waterColor(waterColor)
                .waterFogColor(fogColor));
        addFeatures();
    }

    public void addFeatures(){
        ModWorld.addGrass(this);
        ModWorld.addStandardFlora(this);
    }

    @Override
    public int getSkyColor() {
        return 0x0ef9cd;
    }
}
