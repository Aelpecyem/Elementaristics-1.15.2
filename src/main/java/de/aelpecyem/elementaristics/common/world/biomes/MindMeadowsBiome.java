package de.aelpecyem.elementaristics.common.world.biomes;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class MindMeadowsBiome extends ModBiome {
    public MindMeadowsBiome() {
        super(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG, Category.PLAINS, 0.17F, 0.15F, 0.8F, 0x2F2878, 0x0ef9cd);
    }

    @Override
    public int getGrassColorAt(double p_225528_1_, double p_225528_3_) {
        return 0x0ef9cd;
    }

    @Override
    public int getFoliageColor() {
        return 0x0ef9cd;
    }
}
