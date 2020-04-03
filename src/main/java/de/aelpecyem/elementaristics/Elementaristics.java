package de.aelpecyem.elementaristics;

import com.mojang.datafixers.util.Pair;
import de.aelpecyem.elementaristics.client.particle.ModParticles;
import de.aelpecyem.elementaristics.client.proxy.ClientProxy;
import de.aelpecyem.elementaristics.common.proxy.CommonProxy;
import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.common.multiblock.MultiblockRegistry;

import javax.annotation.Nullable;
import java.util.Collection;

@Mod(Constants.MOD_ID)
public class Elementaristics {
    public static final Logger LOGGER = LogManager.getLogger();
    public static CommonProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());
    public static ModRegistry setup = new ModRegistry();
    public static ModParticles particles = new ModParticles();

    public static final ItemGroup TAB = new ItemGroup("elementaristics") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };
    public Elementaristics() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModRegistry());
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("elementaristics-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("elementaristics-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event){
        LOGGER.info("Setting up...");
        setup.init();
        proxy.init();
    }
}
