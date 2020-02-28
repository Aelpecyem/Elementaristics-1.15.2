package de.aelpecyem.elementaristics.common.world.biomes;

import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class DreamyThicketBiome extends ModBiome {
    public DreamyThicketBiome() {
        super(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG, Category.FOREST, 0.23F, 0.1F, 0.8F, 0x2F2878, 0x00B99C);
    }

    @Override
    public int getGrassColorAt(double p_225528_1_, double p_225528_3_) {
        return 1783155;
    }

    @Override
    public int getFoliageColor() {
        return 1773371;
    }

    @Override
    public void addFeatures() {
        super.addFeatures();
        ModWorld.addDenseGrass(this);
        ModWorld.addDenseTrees(this);
    }
}
