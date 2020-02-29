package de.aelpecyem.elementaristics.data.generators;

import de.aelpecyem.elementaristics.lib.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

import javax.annotation.Nonnull;

public class ItemTagProvider extends ItemTagsProvider {
    public ItemTagProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void registerTags() {
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(BlockTags.FENCES, ItemTags.FENCES);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(ModTags.ENRICHED_STONE, ModTags.ENRICHED_STONE_ITEM);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Elementaristics item tags";
    }
}
