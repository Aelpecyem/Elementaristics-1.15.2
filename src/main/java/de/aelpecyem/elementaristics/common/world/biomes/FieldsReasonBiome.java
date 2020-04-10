package de.aelpecyem.elementaristics.common.world.biomes;

import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

public class FieldsReasonBiome extends ModBiome {
    public FieldsReasonBiome() {
        super(ModWorld.s_flat, Category.PLAINS, 0, 0.2F, 0.8F, 0x2F2878, 0x0ef9cd);
        carvers.forEach((carving, list) -> list.clear());
    }

    @Override
    public void addFeatures() {
        addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModWorld.WEIRD_SHAPES.get().configure(new BlockStateFeatureConfig(Blocks.LIGHT_BLUE_CONCRETE_POWDER.getDefaultState())).createDecoratedFeature(Placement.CHANCE_TOP_SOLID_HEIGHTMAP.configure(new ChanceConfig(Config.WEIRD_CUBES_FREQUENCY.get()))));

        super.addFeatures();
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
