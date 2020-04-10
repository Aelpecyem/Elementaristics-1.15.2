package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.common.blocks.mind.BlockGreyMatter;
import de.aelpecyem.elementaristics.common.blocks.plant.BlockMorningGlory;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static RegistryObject<Block> stone_enriched = register("stone_enriched", new Block(Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<Block> stonebricks_enriched = register("stonebricks_enriched", new Block(Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<Block> smooth_stone_enriched = register("smooth_stone_enriched", new Block(Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<SlabBlock> stone_enriched_slab = register("stone_enriched_slab", new SlabBlock(Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<SlabBlock> stonebricks_enriched_slab = register("stonebricks_enriched_slab", new SlabBlock(Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<SlabBlock> smooth_stone_enriched_slab = register("smooth_stone_enriched_slab", new SlabBlock(Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<StairsBlock> stone_enriched_stairs = register("stone_enriched_stairs", new StairsBlock(() -> stone_enriched.get().getDefaultState(), Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<StairsBlock> stonebricks_enriched_stairs = register("stonebricks_enriched_stairs", new StairsBlock(() -> stonebricks_enriched.get().getDefaultState(), Constants.BlockBuilders.ENRICHED_STONE));
    public static RegistryObject<StairsBlock> smooth_stone_enriched_stairs = register("smooth_stone_enriched_stairs", new StairsBlock(() -> smooth_stone_enriched.get().getDefaultState(), Constants.BlockBuilders.ENRICHED_STONE));

    public static RegistryObject<Block> morning_glory = register("morning_glory", new BlockMorningGlory());

    public static RegistryObject<Block> soil = register("soil", new Block(Block.Properties.from(Blocks.STONE)));
    public static RegistryObject<Block> soil_hardened = register("soil_hardened", new Block(Block.Properties.from(Blocks.STONE)));
    public static RegistryObject<Block> roots = register("roots", new Block(Block.Properties.from(Blocks.STONE)));

    public static RegistryObject<Block> grey_matter = register("grey_matter", new BlockGreyMatter());

    public static <T extends Block> RegistryObject<T> register(String name, T block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(Elementaristics.TAB)));
        return BLOCKS.register(name, () -> block);
    }
}
