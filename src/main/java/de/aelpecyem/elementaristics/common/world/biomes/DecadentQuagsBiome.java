package de.aelpecyem.elementaristics.common.world.biomes;

import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class DecadentQuagsBiome extends ModBiome {
    public DecadentQuagsBiome() {
        super(SurfaceBuilder.DEFAULT, Category.SWAMP, -0.25F, 0.08F, 0.8F, 6365237, 6365237);
    }

    @Override
    public int getGrassColorAt(double p_225528_1_, double p_225528_3_) {
        return 4464693;
    }

    @Override
    public int getFoliageColor() {
        return 7020578;
    }

    @Override
    public void addFeatures() {
        super.addFeatures();
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.20285715F)));
        DefaultBiomeFeatures.addLakes(this);
        ModWorld.addDenseGrass(this);
    }
}
