package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.common.misc.potions.EffectIntoxicated;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.lib.Util;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.aelpecyem.elementaristics.lib.Constants.PotionNames;

@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPotions {
    @ObjectHolder(PotionNames.INTOXICATED) public static Effect intoxicated;

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Effect> event) {
        Elementaristics.LOGGER.info("Registering potion effects...");

        IForgeRegistry<Effect> r = event.getRegistry();

        Util.register(r, new EffectIntoxicated(), PotionNames.INTOXICATED);
    }
}
