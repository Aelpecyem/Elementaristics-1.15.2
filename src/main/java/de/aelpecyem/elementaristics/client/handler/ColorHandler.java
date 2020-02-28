package de.aelpecyem.elementaristics.client.handler;

import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ColorHandler {
    @SubscribeEvent
    public static void colorBlocks(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, light, pos, layer) -> light != null && pos != null ? BiomeColors.getGrassColor(light, pos) : GrassColors.get(0.5D, 1.0D), Blocks.GRASS_BLOCK);
    }

    @SubscribeEvent
    public static void colorItems(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, layer) ->  {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
        return event.getBlockColors().getColor(blockstate, null, null, layer);
    }, Blocks.GRASS_BLOCK, Blocks.GRASS, Blocks.FERN, Blocks.VINE, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.LILY_PAD);
    }
}
