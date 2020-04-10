package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.client.particle.ParticleHandler;
import de.aelpecyem.elementaristics.client.proxy.ClientProxy;
import de.aelpecyem.elementaristics.common.proxy.CommonProxy;
import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class Elementaristics {
    public static final Logger LOGGER = LogManager.getLogger();
    public static CommonProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());
    public static ParticleHandler particles = new ParticleHandler();

    public static final ItemGroup TAB = new ItemGroup("elementaristics") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };
    public Elementaristics() {
        final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
        BUS.addListener(this::setup);
        ModItems.ITEMS.register(BUS);
        ModBlocks.BLOCKS.register(BUS);
        ModWorld.BIOMES.register(BUS);
        ModWorld.DIMENSIONS.register(BUS);
        ModEntities.ENTITY_TYPES.register(BUS);
        ModPotions.POTION_EFFECTS.register(BUS);
        ModWorld.FEATURES.register(BUS);
        ModWorld.SURFACE_BUILDERS.register(BUS);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("elementaristics-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("elementaristics-common.toml"));
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModRegistry());
    }

    private void setup(final FMLCommonSetupEvent event){
        ModRegistry.init();
        LOGGER.info("Setting up...");
        proxy.init();
    }
}
