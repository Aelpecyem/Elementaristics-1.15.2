package de.aelpecyem.elementaristics.reg;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.aelpecyem.elementaristics.client.particle.MagicParticle;
import de.aelpecyem.elementaristics.client.particle.data.MagicParticleData;
import de.aelpecyem.elementaristics.client.particle.data.MagicParticleInfo;
import de.aelpecyem.elementaristics.client.particle.mode.ParticleMode;
import de.aelpecyem.elementaristics.client.particle.mode.ParticleModeSpectrum;
import de.aelpecyem.elementaristics.client.particle.mode.ParticleModeVaporwave;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {
    public static final List<MagicParticleInfo> PARTICLE_MODES = new ArrayList<>();
    public static final ParticleType<MagicParticleData> MAGIC = (ParticleType<MagicParticleData>) register(Constants.MOD_ID + ":" + "magic", MagicParticleData.DESERIALIZER);

    private static <T extends IParticleData> ParticleType<T> register(String key, IParticleData.IDeserializer<T> deserializer) {
        return (ParticleType) Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, key, new ParticleType<>(true, deserializer));
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(MAGIC, MagicParticle.Factory::new);
    }


    public static class Modes {
        public static final ParticleMode VAPORWAVE = new ParticleModeVaporwave(); //:^)
        public static final ParticleMode RAINBOWS = new ParticleModeSpectrum();
    }

    public static class Handler {
        public static void spawnStandardParticle(World world, double x, double y, double z, int color) {
            world.addParticle(new MagicParticleData(ModParticles.MAGIC, MagicParticleInfo.create(color).scale(0.2F).shrink().collide().fadeMode(MagicParticle.EnumFadeMode.OUT)), x, y, z, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D);
        }

        public static void spawnEntityParticles(Entity entityIn, int color) {
            spawnEntityParticles(entityIn, color, 0.02D, null);
        }

        public static void spawnEntityParticles(Entity entityIn, int color, double speedMult, @Nullable ParticleMode mode) {
            entityIn.world.addParticle(new MagicParticleData(ModParticles.MAGIC, MagicParticleInfo.create(color).maxAge(160).shrink().scale(0.2F).setMode(mode)),
                    entityIn.getPositionVec().x + entityIn.world.rand.nextFloat() * entityIn.getWidth()
                            * 2.0F - entityIn.getWidth(),
                    entityIn.getPositionVec().y + 0.5D + entityIn.world.rand.nextFloat()
                            * entityIn.getHeight(),
                    entityIn.getPositionVec().z + entityIn.world.rand.nextFloat() * entityIn.getWidth()
                            * 2.0F - entityIn.getWidth(),
                    entityIn.world.rand.nextGaussian() * speedMult,
                    entityIn.world.rand.nextGaussian() * speedMult,
                    entityIn.world.rand.nextGaussian() * speedMult);
        }
    }

    public static class RenderTypes {
        public static IParticleRenderType BRIGHT = new IParticleRenderType() {
            @Override
            public void beginRender(BufferBuilder bufferBuilder, TextureManager textureManager) {
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
                RenderSystem.disableLighting();

                textureManager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
                textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE).setBlurMipmap(true, false);

                bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            }

            @Override
            public void finishRender(Tessellator tessellator) {
                tessellator.draw();
                Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE).restoreLastBlurMipmap();
                RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
            }
        };

        public static IParticleRenderType DARKEN = new IParticleRenderType() {
            @Override
            public void beginRender(BufferBuilder bufferBuilder, TextureManager textureManager) {
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
                //    RenderSystem.disableLighting();

                textureManager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
                textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE).setBlurMipmap(true, false);

                bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            }

            @Override
            public void finishRender(Tessellator tessellator) {
                tessellator.draw();
                Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE).restoreLastBlurMipmap();
                RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
            }
        };
    }

}

