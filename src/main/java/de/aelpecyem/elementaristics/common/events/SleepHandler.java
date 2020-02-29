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
    /*When the player goes to sleep, an effect check takes place
    Should the player be intoxicated, they are immediately sent to the Mind, with a dummy replacing their body.
    The dummy loads the chunk it's in, and is given the intoxicated effect of the player with the same amplifier.
    The duration is calculated with a minimum value of one minute, multiplied with the effect amplifier (+1)
    So it looks like this: Math.max(duration, 1200) * (amplifier + 1)
    */
    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event){
        EffectInstance intoxicated = event.getPlayer().getActivePotionEffect(ModPotions.intoxicated);
        if (intoxicated != null && !event.getPlayer().world.isRemote){
            PlayerEntity player = event.getPlayer();
            EntityPlayerDummy.createDummyAndSendPlayerAway(player.world, player);
        }
    }
}
