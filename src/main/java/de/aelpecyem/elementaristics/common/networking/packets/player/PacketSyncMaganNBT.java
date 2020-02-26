package de.aelpecyem.elementaristics.common.networking.packets.player;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.common.capability.magan.IMaganCapability;
import de.aelpecyem.elementaristics.common.capability.magan.MaganCapability;
import de.aelpecyem.elementaristics.common.networking.packets.PacketBase;
import de.aelpecyem.elementaristics.lib.ByteBufUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncMaganNBT extends PacketBase{
    private final CompoundNBT data;

    public PacketSyncMaganNBT(PacketBuffer buf) {
        this.data = ByteBufUtils.readNBTTag(buf);
    }

    public PacketSyncMaganNBT(CompoundNBT data) {
        this.data = data;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        ByteBufUtils.writeNBTTag(buf, data);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = Elementaristics.proxy.getPlayer(ctx);
            IMaganCapability cap = MaganCapability.Util.getCapability(player);
            MaganCapability.Util.readNBT(cap, data);
            cap.setVeryDirty(false);
            cap.setDirty(false);
        });
        ctx.get().setPacketHandled(true);
    }
}
