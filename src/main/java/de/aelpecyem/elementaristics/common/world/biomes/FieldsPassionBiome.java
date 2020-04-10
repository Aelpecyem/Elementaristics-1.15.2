package de.aelpecyem.elementaristics.common.world.biomes;

import com.google.common.collect.ImmutableList;
import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.world.biome.DarkForestBiome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class FieldsPassionBiome extends ModBiome {
    public FieldsPassionBiome() {
        super(SurfaceBuilder.DEFAULT, Category.FOREST, 0.23F, 0.1F, 0.8F, 0x2F2878, 0x00B99C);
    }

    @Override
    public void addFeatures() {
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModWorld.DAPPLED_TREES.get().configure(DefaultBiomeFeatures.DARK_OAK_TREE_CONFIG).createDecoratedFeature(Placement.CHANCE_TOP_SOLID_HEIGHTMAP.configure(new ChanceConfig(Config.WEIRD_CUBES_FREQUENCY.get()))));
        super.addFeatures();
    }

    @Override
    public int getGrassColorAt(double p_225528_1_, double p_225528_3_) {
        return 1783155;
    }

    @Override
    public int getFoliageColor() {
        return 1773371;
    }

}
