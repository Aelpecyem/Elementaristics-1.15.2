package de.aelpecyem.elementaristics.reg;


import de.aelpecyem.elementaristics.client.render.entity.RenderPlayerDummy;
import de.aelpecyem.elementaristics.common.entity.EntityPlayerDummy;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.lib.Util;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.aelpecyem.elementaristics.lib.Constants.EntityNames;
@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    @ObjectHolder(EntityNames.PLAYER_DUMMY) public static EntityType<EntityPlayerDummy> PLAYER_DUMMY;

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(PLAYER_DUMMY, RenderPlayerDummy::new);
    }

    @SubscribeEvent
    public static void register(final RegistryEvent.Register<EntityType<?>> event){
        IForgeRegistry<EntityType<?>> r = event.getRegistry();
        Util.register(r, EntityType.Builder.create((EntityType.IFactory<EntityPlayerDummy>) (entityType, world) -> new EntityPlayerDummy(world), EntityClassification.MISC).
                setShouldReceiveVelocityUpdates(false).size(0.6F, 1.8F).build(EntityNames.PLAYER_DUMMY), EntityNames.PLAYER_DUMMY);
    }
}
