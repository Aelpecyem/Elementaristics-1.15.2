package de.aelpecyem.elementaristics.common.events;

import de.aelpecyem.elementaristics.client.particle.data.MagicParticleData;
import de.aelpecyem.elementaristics.client.particle.data.MagicParticleInfo;
import de.aelpecyem.elementaristics.common.capability.magan.IMaganCapability;
import de.aelpecyem.elementaristics.common.capability.magan.MaganCapability;
import de.aelpecyem.elementaristics.common.networking.PacketHandler;
import de.aelpecyem.elementaristics.common.networking.packets.player.PacketSyncMagan;
import de.aelpecyem.elementaristics.common.networking.packets.player.PacketSyncMaganNBT;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if (player.world.isRemote)
            player.world.addParticle(new MagicParticleData(ModParticles.MAGIC, MagicParticleInfo.create(6969)), player.getPositionVec().x, player.getPositionVec().y, player.getPositionVec().z, player.getRNG().nextGaussian() * 0.005D, player.getRNG().nextGaussian() * 0.005D, player.getRNG().nextGaussian() * 0.005D);

        IMaganCapability cap = player.getCapability(MaganCapability.Provider.MAGAN_CAPABILITY, null).orElse(null);
        if (cap != null && !player.world.isRemote && player instanceof ServerPlayerEntity){
            if (cap.getMagan() < cap.getMaxMagan()) cap.fillMagan(cap.getMaganRegenRate());
            if (cap.isVeryDirty()){
                PacketHandler.INSTANCE.sendTo(new PacketSyncMaganNBT(MaganCapability.Util.writeNBT(cap)), ((ServerPlayerEntity)player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                cap.setDirty(false);
                cap.setVeryDirty(false);
            }else if(cap.isDirty()){
                PacketHandler.INSTANCE.sendTo(new PacketSyncMagan(cap.getMagan()), ((ServerPlayerEntity)player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                cap.setDirty(false);
            }
        }
    }
}
