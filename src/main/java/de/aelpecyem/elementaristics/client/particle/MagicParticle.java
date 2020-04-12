package de.aelpecyem.elementaristics.client.particle;

import de.aelpecyem.elementaristics.client.particle.data.MagicParticleData;
import de.aelpecyem.elementaristics.client.particle.data.MagicParticleInfo;
import de.aelpecyem.elementaristics.lib.ColorUtil;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.client.particle.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class MagicParticle extends SpriteTexturedParticle {
    private static final Random random = new Random();
    private final IAnimatedSprite sprite;
    private final MagicParticleInfo info;
    private float desiredScale;
    private float desiredAlpha;

    protected MagicParticle(World world, double x, double y, double z, double velX, double velY, double velZ, IAnimatedSprite sprite, MagicParticleData data) {
        super(world, x, y, z); //maybe add particle mode compatibility later, or replace everything in the info with (only one referrential thing, all else is regulated in the mode)
        this.sprite = sprite;
        this.info = data.getInfo();
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;

        this.desiredScale = (float) (info.scale * (1 + Math.random() * 0.5F));
        this.particleScale = desiredScale;
        this.desiredAlpha = (float) (info.alpha - (Math.random() * 0.8F));
        this.particleAlpha = desiredAlpha;
        this.maxAge = (int) ((double) info.maxAge * (1 + Math.random() * 0.7D)); //(int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.canCollide = info.canCollide; //todo, fix that (particles don't appear, but are in fact spawned, so the max age is probably too low or the alpha too low
        setColor((((data.getInfo().color >> 16) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F), (((data.getInfo().color >> 8) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F), ((data.getInfo().color & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F));
        this.selectSpriteWithAge(sprite);

        if (info.mode != null)
            info.mode.setup(this);
    }

    public int getAge() {
        return age;
    }

    public Random getRandom() {
        return random;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void tick() {
        if (info.mode != null)
            info.mode.update(this);
        float lifeRatio = (float) this.age / (float) this.maxAge; //in-out stuff
        lifeRatio = info.fadeMode == MagicParticle.EnumFadeMode.IN_OUT ? 1 - (0.5F - Math.abs(0.5F - lifeRatio)) * 1.8F : info.fadeMode == MagicParticle.EnumFadeMode.IN ? 1 - lifeRatio : lifeRatio;

        if (this.info.fadeMode != MagicParticle.EnumFadeMode.NONE)
            this.particleAlpha = desiredAlpha * (1F - (lifeRatio));
        if (this.info.shrink)
            this.particleScale = desiredScale * (1F - (lifeRatio));
        super.tick();
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    @Override
    public IParticleRenderType getRenderType() { //fix this
        return info.mode != null ? info.mode.getRenderType(this) : ColorUtil.isDark(particleRed, particleGreen, particleBlue) ? ModParticles.RenderTypes.DARKEN : ModParticles.RenderTypes.BRIGHT; //todo use the Glow particle's stuff
    }

    @Override
    protected int getBrightnessForRender(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
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

    public enum EnumFadeMode {
        OUT(0),
        IN(1),
        IN_OUT(2),
        NONE(3);

        private byte id;

        EnumFadeMode(int id) {
            this.id = (byte) id;
        }

        public byte toId() {
            return id;
        }

        public static EnumFadeMode fromId(byte id) {
            return EnumFadeMode.values()[id];
        }
    }

}
