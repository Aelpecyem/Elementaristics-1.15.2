package de.aelpecyem.elementaristics.client.proxy;

import de.aelpecyem.elementaristics.common.proxy.CommonProxy;
import de.aelpecyem.elementaristics.reg.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientProxy extends CommonProxy{
    @Override
    public void init() {
        super.init();
        ModEntities.registerRenderers();
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
