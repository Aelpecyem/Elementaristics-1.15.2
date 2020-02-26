package de.aelpecyem.elementaristics.common.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CommonProxy {
    public void init(){

    }

    public World getWorld(Supplier<NetworkEvent.Context> ctx) {
        return ctx.get().getSender().world;
    }

    public PlayerEntity getPlayer(Supplier<NetworkEvent.Context> ctx) {
        return ctx.get().getSender();
    }
}
