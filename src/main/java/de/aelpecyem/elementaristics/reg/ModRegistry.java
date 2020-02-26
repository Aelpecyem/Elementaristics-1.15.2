package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.common.capability.magan.IMaganCapability;
import de.aelpecyem.elementaristics.common.capability.magan.MaganCapability;
import de.aelpecyem.elementaristics.common.networking.PacketHandler;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ModRegistry {
    public void init(){
        PacketHandler.registerMessages();
        CapabilityManager.INSTANCE.register(IMaganCapability.class, new MaganCapability.Storage(), MaganCapability::new);
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent event){
        if (event.getObject() instanceof PlayerEntity){
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "magan"), new MaganCapability.Provider());
        }
    }

    @SubscribeEvent
    public void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }
}
