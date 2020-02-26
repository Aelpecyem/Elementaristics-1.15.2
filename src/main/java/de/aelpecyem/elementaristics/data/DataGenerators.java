package de.aelpecyem.elementaristics.data;

import de.aelpecyem.elementaristics.data.generators.*;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

//from https://github.com/Vazkii/Botania/tree/1.15/src/main/java/vazkii/botania/data
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new BlockLootProvider(generator));
        generator.addProvider(new BlockTagProvider(generator));
        generator.addProvider(new ItemTagProvider(generator));
        generator.addProvider(new RecipeProvider(generator));
        generator.addProvider(new SmeltingProvider(generator));
        generator.addProvider(new StonecuttingProvider(generator));

        //generator.addProvider(new BlockstateProvider(generator, event.getExistingFileHelper()));
    }
}
