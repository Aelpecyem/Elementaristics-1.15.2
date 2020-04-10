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
    public static final SurfaceBuilderConfig MIND_SURFACE_CONFIG = new SurfaceBuilderConfig(Blocks.LIGHT_BLUE_CONCRETE_POWDER.getDefaultState(), ModBlocks.soil_hardened.get().getDefaultState(), ModBlocks.grey_matter.get().getDefaultState());

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
        features.forEach((deco, list) -> list.clear());
    }

    @Override
    public int getSkyColor() {
        return 0x0ef9cd;
    }

    public void addFeatures(){

    }
}
