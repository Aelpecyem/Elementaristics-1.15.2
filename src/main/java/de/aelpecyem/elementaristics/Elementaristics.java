package de.aelpecyem.elementaristics;

import de.aelpecyem.elementaristics.client.particle.ModParticles;
import de.aelpecyem.elementaristics.client.proxy.ClientProxy;
import de.aelpecyem.elementaristics.common.proxy.CommonProxy;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){
        LOGGER.info("Setting up...");
        setup.init();
        proxy.init();
    }
}
