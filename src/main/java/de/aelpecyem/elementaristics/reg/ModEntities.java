package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.client.render.entity.RenderPlayerDummy;
import de.aelpecyem.elementaristics.common.entity.EntityPlayerDummy;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static de.aelpecyem.elementaristics.lib.Constants.EntityNames;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Constants.MOD_ID);

    public static RegistryObject<EntityType<EntityPlayerDummy>> PLAYER_DUMMY = ENTITY_TYPES.register("player_dummy", () -> EntityType.Builder.create((EntityType.IFactory<EntityPlayerDummy>) (entityType, world) -> new EntityPlayerDummy(world), EntityClassification.MISC).
            setShouldReceiveVelocityUpdates(false).size(0.6F, 1.8F).build(EntityNames.PLAYER_DUMMY));

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(PLAYER_DUMMY.get(), RenderPlayerDummy::new);
    }
}
