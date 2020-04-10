package de.aelpecyem.elementaristics.client.particle;

import de.aelpecyem.elementaristics.client.particle.data.MagicParticleData;
import de.aelpecyem.elementaristics.lib.ColorUtil;
import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MagicParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite sprite;
    protected MagicParticle(World world, double x, double y, double z, double velX, double velY, double velZ, IAnimatedSprite sprite, MagicParticleData data) {
        super(world, x, y, z); //maybe add particle mode compatibility later, or replace everything in the info with (only one referrential thing, all else is regulated in the mode)
        this.sprite = sprite;

        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;

        this.particleScale *= 0.75F;
        this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.canCollide = false;
        setColor((((data.getInfo().color >> 16) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F), (((data.getInfo().color >> 8) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F), ((data.getInfo().color & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F));
        this.selectSpriteWithAge(sprite);
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT; //todo, fix the ParticleRenderTypes//ColorUtil.isDark(particleRed, particleGreen, particleBlue) ? ParticleHandler.RenderTypes.DARKEN : ParticleHandler.RenderTypes.BRIGHT; //todo use the Glow particle's stuff
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<MagicParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(MagicParticleData magicParticleData, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MagicParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet, magicParticleData);
        }
    }
}
