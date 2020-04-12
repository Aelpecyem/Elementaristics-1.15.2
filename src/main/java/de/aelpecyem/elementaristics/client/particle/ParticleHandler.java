package de.aelpecyem.elementaristics.client.particle;

import de.aelpecyem.elementaristics.client.particle.data.MagicParticleData;
import de.aelpecyem.elementaristics.client.particle.data.MagicParticleInfo;
import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleHandler {
    //public static final BasicParticleType GLOW = register("elementaristics:glow", false);

  /*  private static BasicParticleType register(String key, boolean alwaysShow) {
      //  return (BasicParticleType) Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, key, new BasicParticleType(alwaysShow));
    }*/

    /*@SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(GLOW, GlowParticle.Factory::new);
    }*/

    public void spawnStandardParticle(World world, double x, double y, double z, int color) {
        world.addParticle(new MagicParticleData(ModParticles.MAGIC, MagicParticleInfo.create(color).scale(1).shrink().fadeMode(MagicParticle.EnumFadeMode.OUT)), x, y, z, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D);
    }

    //*   public void spawnStandardParticle(World world, double x, double y, double z, @Nullable ParticleMode mode, double speedMult) {
 /*       GlowParticle particle = new GlowParticle(world, x, y, z, world.rand.nextGaussian() * speedMult,
                world.rand.nextGaussian() * speedMult,
                world.rand.nextGaussian() * speedMult, 400, 16711680, 0.9F, 0.8F, 0, true, false, MagicParticle.EnumFadeMode.OUT); //in out
        if (mode != null)
            particle.setMode(mode);
        spawnParticle(particle);
    }*/


    public void spawnAmbientBlockParticles(World world, BlockPos pos, int color) {
        double motionX = world.rand.nextGaussian() * 0.001D;
        double motionY = world.rand.nextGaussian() * 0.001D;
        double motionZ = world.rand.nextGaussian() * 0.001D;
        GlowParticle particle = new GlowParticle(world, pos.getX() + world.rand.nextFloat(), pos.getY() + world.rand.nextFloat(), pos.getZ() + world.rand.nextFloat(), motionX, motionY, motionZ, 120, color, 0.9F, 0.1F, 0, false, false, MagicParticle.EnumFadeMode.IN_OUT);
    }

    public static class Handler {
        private static final List<Particle> PARTICLES = new CopyOnWriteArrayList<>();

        public static void spawnParticle(Supplier<GlowParticle> particle) {
            if (!Config.REDUCED_PARTICLES.get() || Minecraft.getInstance().world.rand.nextBoolean())
                PARTICLES.add(particle.get());
        }

        public static void updateParticles() {
            updateList(PARTICLES);
        }

        private static void updateList(List<Particle> particles) {
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle particle = particles.get(i);
                particle.tick();
                if (!particle.isAlive())
                    particles.remove(i);
            }
        }

        public static void renderParticle(float parTicks) {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = mc.player;

            if (player != null) {
                ActiveRenderInfo info = mc.getRenderManager().info;
               /* float rotationX = MathHelper.cos(info.getYaw() * 0.017453292F);
                float rotationYZ = MathHelper.sin(info.getYaw() * 0.017453292F);
                float rotationXY = -rotationYZ * MathHelper.sin(info.getPitch() * 0.017453292F);
                float rotationXZ = rotationX * MathHelper.sin(info.getPitch() * 0.017453292F);
                float rotationZ = MathHelper.cos(info.getPitch() * 0.017453292F);*/

                Tessellator tesser = Tessellator.getInstance();
                BufferBuilder buffer = tesser.getBuffer();
                for (Particle particle : PARTICLES) {
                    if (particle instanceof ModParticle)
                        mc.textureManager.bindTexture(((ModParticle) particle).getTexture());
                    particle.getRenderType().beginRender(buffer, mc.textureManager);
                    particle.buildGeometry(buffer.getVertexBuilder(), info, parTicks);//renderParticle(buffer, info, parTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
                    particle.getRenderType().finishRender(tesser);
                }

            }
        }

        public static int getParticleAmount(boolean depth) {
            return PARTICLES.size();
        }

        public static void clearParticles() {
            if (!PARTICLES.isEmpty())
                PARTICLES.clear();
        }
    }
}
