package de.aelpecyem.elementaristics.client.particle;

import de.aelpecyem.elementaristics.lib.ColorUtil;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.TexturedParticle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class ModParticle extends TexturedParticle {
    public ModParticle(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public ModParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    public abstract ResourceLocation getTexture();

    @Override
    protected float getMinU() {
        return 0f;
    }

    @Override
    protected float getMaxU() {
        return 1f;
    }

    @Override
    protected float getMinV() {
        return 0f;
    }

    @Override
    protected float getMaxV() {
        return 1f;
    }

    public IParticleRenderType getRenderType() {
        return ColorUtil.isDark(particleRed, particleGreen, particleBlue) ? ModParticles.RenderTypes.DARKEN : ModParticles.RenderTypes.BRIGHT;
    }
}
