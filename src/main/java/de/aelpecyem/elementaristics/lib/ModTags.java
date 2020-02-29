package de.aelpecyem.elementaristics.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static final Tag<Item> ESSENCE = makeWrapperTagItem("essence");

    public static final Tag<Block> ENRICHED_STONE = makeWrapperTagBlock("enriched_stone");
    public static final Tag<Item> ENRICHED_STONE_ITEM = makeWrapperTagItem("enriched_stone");

    public static Tag<Block> makeWrapperTagBlock(String name) {
        return new BlockTags.Wrapper(new ResourceLocation(name));
    }

    public static Tag<Item> makeWrapperTagItem(String name) {
        return new ItemTags.Wrapper(new ResourceLocation(name));
    }
}
