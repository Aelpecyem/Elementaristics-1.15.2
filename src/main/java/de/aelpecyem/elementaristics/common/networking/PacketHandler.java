package de.aelpecyem.elementaristics.common.networking;

import de.aelpecyem.elementaristics.common.networking.packets.player.PacketSyncMagan;
import de.aelpecyem.elementaristics.common.networking.packets.player.PacketSyncMaganNBT;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID, "elementaristics"), () -> Constants.MOD_VERSION, s -> true, s -> true);

        INSTANCE.registerMessage(nextID(),
                PacketSyncMagan.class,
                PacketSyncMagan::toBytes,
                PacketSyncMagan::new,
                PacketSyncMagan::handle);
        INSTANCE.registerMessage(nextID(),
                PacketSyncMaganNBT.class,
                PacketSyncMaganNBT::toBytes,
                PacketSyncMaganNBT::new,
                PacketSyncMaganNBT::handle);
    }
}
