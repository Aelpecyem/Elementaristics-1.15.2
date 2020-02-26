package de.aelpecyem.elementaristics.common.networking.packets.player;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.common.capability.magan.IMaganCapability;
import de.aelpecyem.elementaristics.common.capability.magan.MaganCapability;
import de.aelpecyem.elementaristics.common.networking.packets.PacketBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncMagan extends PacketBase{
    private final float maganCount;

    public PacketSyncMagan(PacketBuffer buf) {
        maganCount = buf.readFloat();
    }

    public PacketSyncMagan(float maganCount) {
        this.maganCount = maganCount;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(maganCount);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = Elementaristics.proxy.getPlayer(ctx);
            IMaganCapability cap = MaganCapability.Util.getCapability(player);
            cap.setMagan(maganCount);
            cap.setDirty(false);
        });
        ctx.get().setPacketHandled(true);
    }
}
