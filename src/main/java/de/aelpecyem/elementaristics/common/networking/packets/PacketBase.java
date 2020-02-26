package de.aelpecyem.elementaristics.common.networking.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class PacketBase {
    public PacketBase(PacketBuffer buf){

    }

    public abstract void toBytes(PacketBuffer buf);

    public PacketBase() {
    }

    public abstract void handle(Supplier<NetworkEvent.Context> ctx);
}
