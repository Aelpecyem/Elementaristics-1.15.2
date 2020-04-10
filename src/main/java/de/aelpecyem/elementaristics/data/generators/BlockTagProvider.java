package de.aelpecyem.elementaristics.data.generators;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.lib.ModTags;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class BlockTagProvider extends BlockTagsProvider {
    public BlockTagProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void registerTags() {
        Predicate<Block> elem = b -> Constants.MOD_ID.equals(b.getRegistryName().getNamespace());

        getBuilder(BlockTags.STONE_BRICKS).add(ModBlocks.stonebricks_enriched.get());



        getBuilder(ModTags.ENRICHED_STONE).add(ModBlocks.stone_enriched.get());
        getBuilder(ModTags.ENRICHED_STONE).add(ModBlocks.stonebricks_enriched.get());
        getBuilder(ModTags.ENRICHED_STONE).add(ModBlocks.smooth_stone_enriched.get());

        getBuilder(BlockTags.SLABS).add(registry.stream().filter(elem)
                .filter(b -> b instanceof SlabBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));

        getBuilder(BlockTags.STAIRS).add(registry.stream().filter(elem)
                .filter(b -> b instanceof StairsBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));

        getBuilder(BlockTags.WALLS).add(registry.stream().filter(elem)
                .filter(b -> b instanceof WallBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));

        getBuilder(BlockTags.FENCES).add(registry.stream().filter(elem)
                .filter(b -> b instanceof FenceBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));

        getBuilder(BlockTags.SMALL_FLOWERS).add(registry.stream().filter(elem)
                .filter(b -> b instanceof FlowerBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));

    }

    @Nonnull
    @Override
    public String getName() {
        return "Elementaristics block tags";
    }
}
