package de.aelpecyem.elementaristics.common.world.dimensions;

import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class MindBiomeProviderSettings implements IBiomeProviderSettings {
    private long seed;
    private MindBiomeProviderSettings generatorSettings;

    public MindBiomeProviderSettings(WorldInfo info) {
        this.seed = info.getSeed();
    }

    public MindBiomeProviderSettings setGeneratorSettings(MindBiomeProviderSettings settings) {
        this.generatorSettings = settings;
        return this;
    }

    public long getSeed() {
        return seed;
    }

    public MindBiomeProviderSettings getGeneratorSettings() {
        return this.generatorSettings;
    }
}
