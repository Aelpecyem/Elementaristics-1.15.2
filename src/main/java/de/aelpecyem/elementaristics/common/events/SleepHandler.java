package de.aelpecyem.elementaristics.common.events;

import de.aelpecyem.elementaristics.common.entity.EntityPlayerDummy;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModPotions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SleepHandler {
    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event){
        EffectInstance intoxicated = event.getPlayer().getActivePotionEffect(ModPotions.INTOXICATED.get());
        if (intoxicated != null && !event.getPlayer().world.isRemote){
            PlayerEntity player = event.getPlayer();
            EntityPlayerDummy.createDummyAndSendPlayerAway(player.world, player);
        }
    }
}
