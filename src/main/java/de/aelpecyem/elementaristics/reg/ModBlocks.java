package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.common.blocks.mind.BlockGreyMatter;
import de.aelpecyem.elementaristics.common.blocks.plant.BlockMorningGlory;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.lib.Util;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.aelpecyem.elementaristics.lib.Constants.BlockNames;

@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    @ObjectHolder(BlockNames.STONE_ENRICHED) public static Block stone_enriched;
    @ObjectHolder(BlockNames.STONEBRICKS_ENRICHED) public static Block stonebricks_enriched;
    @ObjectHolder(BlockNames.SMOOTH_STONE_ENRICHED) public static Block smooth_stone_enriched;
    @ObjectHolder(BlockNames.STONE_ENRICHED + "_slab") public static SlabBlock stone_enriched_slab;
    @ObjectHolder(BlockNames.STONEBRICKS_ENRICHED + "_slab") public static SlabBlock stonebricks_enriched_slab;
    @ObjectHolder(BlockNames.SMOOTH_STONE_ENRICHED + "_slab") public static SlabBlock smooth_stone_enriched_slab;
    @ObjectHolder(BlockNames.STONE_ENRICHED + "_stairs") public static StairsBlock stone_enriched_stairs;
    @ObjectHolder(BlockNames.STONEBRICKS_ENRICHED + "_stairs") public static StairsBlock stonebricks_enriched_stairs;
    @ObjectHolder(BlockNames.SMOOTH_STONE_ENRICHED + "_stairs") public static StairsBlock smooth_stone_enriched_stairs;


    @ObjectHolder(BlockNames.GREY_MATTER) public static Block grey_matter;

    @ObjectHolder(BlockNames.MORNING_GLORY) public static Block morning_glory;
    @ObjectHolder(BlockNames.NEUROSE) public static Block neurose; //TODO: add MIND-Blocks


    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        Elementaristics.LOGGER.info("Registering blocks...");

        IForgeRegistry<Block> r = event.getRegistry();

        Block.Properties builder = Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(5).harvestLevel(1).harvestTool(ToolType.PICKAXE);
        Util.register(r, new Block(builder), BlockNames.STONE_ENRICHED);
        Util.register(r, new Block(builder), BlockNames.STONEBRICKS_ENRICHED);
        Util.register(r, new Block(builder), BlockNames.SMOOTH_STONE_ENRICHED);
        Util.register(r, new SlabBlock(builder), BlockNames.STONE_ENRICHED + "_slab");
        Util.register(r, new SlabBlock(builder), BlockNames.STONEBRICKS_ENRICHED + "_slab");
        Util.register(r, new SlabBlock(builder), BlockNames.SMOOTH_STONE_ENRICHED + "_slab");
        Util.register(r, new StairsBlock(() -> stone_enriched.getDefaultState(), builder), BlockNames.STONE_ENRICHED + "_stairs");
        Util.register(r, new StairsBlock(() -> stonebricks_enriched.getDefaultState(), builder), BlockNames.STONEBRICKS_ENRICHED + "_stairs");
        Util.register(r, new StairsBlock(() -> smooth_stone_enriched.getDefaultState(), builder), BlockNames.SMOOTH_STONE_ENRICHED + "_stairs");

        Util.register(r, new BlockGreyMatter(), BlockNames.GREY_MATTER);

        Util.register(r, new BlockMorningGlory(), BlockNames.MORNING_GLORY);
        builder = Block.Properties.from(Blocks.POPPY);
        Util.register(r, new FlowerBlock(Effects.GLOWING, 10, builder.lightValue(8)), BlockNames.NEUROSE);
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        Elementaristics.LOGGER.info("Registering item-blocks...");
        Item.Properties properties = new Item.Properties()
                .group(Elementaristics.TAB);

        IForgeRegistry<Item> r = event.getRegistry();

        Util.register(r, new BlockItem(stone_enriched, properties), BlockNames.STONE_ENRICHED);
        Util.register(r, new BlockItem(stonebricks_enriched, properties), BlockNames.STONEBRICKS_ENRICHED);
        Util.register(r, new BlockItem(smooth_stone_enriched, properties), BlockNames.SMOOTH_STONE_ENRICHED);
        Util.register(r, new BlockItem(stone_enriched_slab, properties), BlockNames.STONE_ENRICHED + "_slab");
        Util.register(r, new BlockItem(stonebricks_enriched_slab, properties), BlockNames.STONEBRICKS_ENRICHED + "_slab");
        Util.register(r, new BlockItem(smooth_stone_enriched_slab, properties), BlockNames.SMOOTH_STONE_ENRICHED + "_slab");
        Util.register(r, new BlockItem(stone_enriched_stairs, properties), BlockNames.STONE_ENRICHED + "_stairs");
        Util.register(r, new BlockItem(stonebricks_enriched_stairs, properties), BlockNames.STONEBRICKS_ENRICHED + "_stairs");
        Util.register(r, new BlockItem(smooth_stone_enriched_stairs, properties), BlockNames.SMOOTH_STONE_ENRICHED + "_stairs");


        Util.register(r, new BlockItem(grey_matter, properties), BlockNames.GREY_MATTER);

        Util.register(r, new BlockItem(morning_glory, properties), BlockNames.MORNING_GLORY);
        Util.register(r, new BlockItem(neurose, properties), BlockNames.NEUROSE);
    }
}
