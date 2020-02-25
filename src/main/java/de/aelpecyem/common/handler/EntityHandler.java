package de.aelpecyem.common.handler;

import de.aelpecyem.Elementaristics;
import de.aelpecyem.lib.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

    }
}
