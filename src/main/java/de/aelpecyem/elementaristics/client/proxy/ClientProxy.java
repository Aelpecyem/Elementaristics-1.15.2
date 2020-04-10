package de.aelpecyem.elementaristics.client.proxy;

import de.aelpecyem.elementaristics.common.proxy.CommonProxy;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import de.aelpecyem.elementaristics.reg.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientProxy extends CommonProxy{
    @Override
    public void init() {
        super.init();
        ModEntities.registerRenderers();
        registerBlockRenderers();
    }

    public static void registerBlockRenderers() {
        RenderTypeLookup.setRenderLayer(ModBlocks.soil.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.morning_glory.get(), RenderType.getCutoutMipped());

        Minecraft.getInstance().getBlockColors().register((state, light, pos, layer) -> (light != null && pos != null ? BiomeColors.getGrassColor(light, pos) : GrassColors.get(0.5D, 1.0D)), ModBlocks.soil.get());
        Minecraft.getInstance().getBlockColors().register((state, light, pos, layer) -> (light != null && pos != null ? BiomeColors.getFoliageColor(light, pos) : FoliageColors.getDefault()), ModBlocks.morning_glory.get());

        Minecraft.getInstance().getItemColors().register((stack, layer) ->  {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return  Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, layer);
        }, ModBlocks.soil.get());

        Minecraft.getInstance().getItemColors().register((stack, layer) ->  {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return layer == 1 ? 0xFFFFFF :  Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, layer);
        }, ModBlocks.morning_glory.get());
    }
    @Override
    public World getWorld(Supplier<NetworkEvent.Context> ctx) {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getPlayer(Supplier<NetworkEvent.Context> ctx) {
        return Minecraft.getInstance().player;
    }
}
