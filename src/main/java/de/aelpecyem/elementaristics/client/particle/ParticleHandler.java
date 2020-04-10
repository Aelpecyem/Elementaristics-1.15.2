package de.aelpecyem.elementaristics.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import de.aelpecyem.elementaristics.client.particle.mode.ParticleMode;
import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
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


    //@Todo: add particles properly, just as intended
    public void spawnParticle(GlowParticle particle) {
        Handler.spawnParticle(() -> particle);
    }

    public void spawnStandardParticle(World world, double x, double y, double z, int color) {
        GlowParticle particle = new GlowParticle(world, x, y, z, world.rand.nextGaussian() * 0.02D,
                world.rand.nextGaussian() * 0.02D,
                world.rand.nextGaussian() * 0.02D, 120, color, 0.9F, 0.8F, 0, true, true, GlowParticle.EnumFadeMode.OUT); // out
        spawnParticle(particle);
    }

    public void spawnStandardParticle(World world, double x, double y, double z, @Nullable ParticleMode mode, double speedMult) {
        GlowParticle particle = new GlowParticle(world, x, y, z, world.rand.nextGaussian() * speedMult,
                world.rand.nextGaussian() * speedMult,
                world.rand.nextGaussian() * speedMult, 400, 16711680, 0.9F, 0.8F, 0, true, false, GlowParticle.EnumFadeMode.OUT); //in out
        if (mode != null)
            particle.setMode(mode);
        spawnParticle(particle);
    }

    public void spawnEntityParticles(Entity entityIn, int color, @Nullable ParticleMode mode, double speedMult) {
        double motionX = entityIn.world.rand.nextGaussian() * speedMult;
        double motionY = entityIn.world.rand.nextGaussian() * speedMult;
        double motionZ = entityIn.world.rand.nextGaussian() * speedMult;
        GlowParticle particle = new GlowParticle(
                entityIn.world,
                entityIn.getPositionVec().x + entityIn.world.rand.nextFloat() * entityIn.getWidth()
                        * 2.0F - entityIn.getWidth(),
                entityIn.getPositionVec().y + 0.5D + entityIn.world.rand.nextFloat()
                        * entityIn.getHeight(),
                entityIn.getPositionVec().z + entityIn.world.rand.nextFloat() * entityIn.getWidth()
                        * 2.0F - entityIn.getWidth(),
                motionX,
                motionY,
                motionZ,
                160, color, 1F, 0.1F + entityIn.world.rand.nextFloat() / 40F, 0F, true, true, GlowParticle.EnumFadeMode.OUT);
        if (mode != null)
            particle.setMode(mode);
        spawnParticle(particle);
    }

    public void spawnAmbientBlockParticles(World world, BlockPos pos, int color) {
        double motionX = world.rand.nextGaussian() * 0.001D;
        double motionY = world.rand.nextGaussian() * 0.001D;
        double motionZ = world.rand.nextGaussian() * 0.001D;
        GlowParticle particle = new GlowParticle(world, pos.getX() + world.rand.nextFloat(), pos.getY() + world.rand.nextFloat(), pos.getZ() + world.rand.nextFloat(), motionX, motionY, motionZ, 120, color, 0.9F, 0.1F, 0, false, false, GlowParticle.EnumFadeMode.IN_OUT);
        spawnParticle(particle);
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

    public static class RenderTypes {
        public static IParticleRenderType BRIGHT = new IParticleRenderType() {
            @Override
            public void beginRender(BufferBuilder bufferBuilder, TextureManager textureManager) {
                GlStateManager.enableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.alphaFunc(516, 0.003921569F);
                GlStateManager.disableCull();
                GlStateManager.depthMask(false);

                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.field_225655_p_, GlStateManager.DestFactor.ONE.field_225654_o_);
                bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            }

            @Override
            public void finishRender(Tessellator tessellator) {
                tessellator.draw();

                GlStateManager.enableCull();
                GlStateManager.depthMask(true);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.field_225655_p_, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.field_225654_o_); //ONE_MINUS_SRC_ALPHA
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
            }

            @Override
            public String toString() {
                return "ElEM_BRIGHT";
            }
        };

        public static IParticleRenderType DARKEN = new IParticleRenderType() {
            @Override
            public void beginRender(BufferBuilder bufferBuilder, TextureManager textureManager) {
                GlStateManager.enableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.alphaFunc(516, 0.003921569F);
                GlStateManager.disableCull();
                GlStateManager.depthMask(false);

                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.field_225655_p_, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.field_225654_o_);
                bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            }

            @Override
            public void finishRender(Tessellator tessellator) {
                tessellator.draw();

                GlStateManager.enableCull();
                GlStateManager.depthMask(true);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.field_225655_p_, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.field_225654_o_); //ONE_MINUS_SRC_ALPHA
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
            }
        };
    }
}
