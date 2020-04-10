package de.aelpecyem.elementaristics.reg;

import com.mojang.datafixers.kinds.Const;
import de.aelpecyem.elementaristics.common.capability.magan.IMaganCapability;
import de.aelpecyem.elementaristics.common.capability.magan.MaganCapability;
import de.aelpecyem.elementaristics.common.networking.PacketHandler;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ModRegistry {
    public static void init(){
        ModWorld.addBiomeTypes();
        ModWorld.addBiomeFeatures();
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


    @SubscribeEvent
    public static void onDimensionRegistry(RegisterDimensionsEvent event) {
        ModWorld.MIND = DimensionManager.registerOrGetDimension(ModWorld.MIND_DIMENSION_ID, ModWorld.MIND_DIMENSION.get(), null, true);
    }


    public static <T extends ForgeRegistryEntry<?>> T register(String name, T entry){
        entry.setRegistryName(new ResourceLocation(Constants.MOD_ID, name));
        return entry;
    }
}
