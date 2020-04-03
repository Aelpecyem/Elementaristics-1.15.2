package de.aelpecyem.elementaristics.client.proxy;

import de.aelpecyem.elementaristics.common.proxy.CommonProxy;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import de.aelpecyem.elementaristics.reg.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
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
        RenderTypeLookup.setRenderLayer(ModBlocks.soil, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.morning_glory, RenderType.getCutoutMipped());
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
