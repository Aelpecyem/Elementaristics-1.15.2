package de.aelpecyem.elementaristics.common.world.dimensions;

import com.google.common.collect.ImmutableSet;
import de.aelpecyem.elementaristics.common.world.dimensions.layer.LayerCreator;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MindBiomeProvider extends BiomeProvider {
    private static final List<Biome> SPAWN = Collections.singletonList(ModWorld.MIND_MEADOWS);
    private static final Set<Biome> BIOMES = ImmutableSet.of(ModWorld.MIND_MEADOWS, ModWorld.DREAMY_THICKET, ModWorld.DECADENT_QUAGS);
    private final Layer genBiomes;
    public MindBiomeProvider(MindBiomeProviderSettings settings) {
        super(BIOMES);
        genBiomes = LayerCreator.makeLayers(settings.getSeed());
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return this.genBiomes.func_215738_a(x, z);
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return SPAWN;
    }

    @Override
    public boolean hasStructure(Structure<?> p_205004_1_) {
        return super.hasStructure(p_205004_1_);
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for(Biome biome : biomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }

        return this.topBlocksCache;
    }


}
