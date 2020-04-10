package de.aelpecyem.elementaristics.common.world.dimensions;

import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.world.gen.GenerationSettings;

public class MindGenerationSettings extends GenerationSettings {
    public MindGenerationSettings() {
        super();
        defaultBlock = ModBlocks.roots.get().getDefaultState();
    }
}
