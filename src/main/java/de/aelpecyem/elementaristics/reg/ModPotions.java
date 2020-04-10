package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.common.misc.potions.IntoxicatedEffect;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPotions {
    public static final DeferredRegister<Effect> POTION_EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, Constants.MOD_ID);

    public static final RegistryObject<Effect> INTOXICATED = POTION_EFFECTS.register("intoxicated", () -> new IntoxicatedEffect());
}
