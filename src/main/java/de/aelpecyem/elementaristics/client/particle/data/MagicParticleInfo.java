package de.aelpecyem.elementaristics.client.particle.data;

import com.mojang.brigadier.StringReader;
import de.aelpecyem.elementaristics.client.particle.MagicParticle;
import de.aelpecyem.elementaristics.client.particle.mode.ParticleMode;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;

public class MagicParticleInfo {
    public float scale;
    public int color;
    public float alpha;
    public float gravity;
    public MagicParticle.EnumFadeMode fadeMode;
    public boolean shrink;
    public int maxAge;
    public boolean canCollide;
    public ParticleMode mode;

    protected MagicParticleInfo(float scale, int color, float alpha, float gravity, MagicParticle.EnumFadeMode fadeMode, boolean shrink, boolean collide, int maxAge, @Nullable ParticleMode mode) {
        this.scale = scale;
        this.color = color;
        this.alpha = alpha;
        this.gravity = gravity;
        this.fadeMode = fadeMode;
        this.shrink = shrink;
        this.canCollide = collide;
        this.maxAge = maxAge;
        this.mode = mode;
    }

    public static MagicParticleInfo create(int color){
        return new MagicParticleInfo(3, color, 0.9F, 0, MagicParticle.EnumFadeMode.OUT, false, false, 120, null);
    }

    public void write(PacketBuffer buffer){
        buffer.writeInt(ModParticles.PARTICLE_MODES.indexOf(this));

        buffer.writeFloat(scale);
        buffer.writeInt(color);
        buffer.writeFloat(alpha);
        buffer.writeFloat(gravity);
        buffer.writeByte(fadeMode.toId());
        buffer.writeBoolean(shrink);
        buffer.writeBoolean(canCollide);
        buffer.writeInt(maxAge);
    }

    public static MagicParticleInfo read(StringReader reader){
        String string = reader.getString();
        String[] params = string.split(" ");
        return new MagicParticleInfo(Float.valueOf(params[1]), Integer.valueOf(params[2]), Float.valueOf(params[3]), Float.valueOf(params[4]), MagicParticle.EnumFadeMode.fromId(Byte.valueOf(params[5])), Boolean.valueOf(params[6]), Boolean.valueOf(params[7]), Integer.valueOf(params[8]), Integer.valueOf(params[9]) == -1 ? null : ParticleMode.MODES.get(Integer.valueOf(params[9])));
    }

    public static MagicParticleInfo read(PacketBuffer buffer){
        MagicParticleInfo info = new MagicParticleInfo(buffer.readFloat(), buffer.readInt(), buffer.readFloat(), buffer.readFloat(), MagicParticle.EnumFadeMode.fromId(buffer.readByte()), buffer.readBoolean(), buffer.readBoolean(), buffer.readInt(), null);
        int bufferInt = buffer.readInt();
        if (bufferInt == -1) return info;
        info.mode = ParticleMode.MODES.get(bufferInt);
        return info;
    }

    @Override
    public String toString() {
        return " " + color + " " + alpha + " " + gravity + " " + fadeMode.toId() + " " + shrink + " " + canCollide + " " + maxAge + " " + (mode == null ? -1 : ParticleMode.MODES.indexOf(mode));
    }

    public MagicParticleInfo setMode(ParticleMode mode) {
        this.mode = mode;
        return this;
    }

    public MagicParticleInfo maxAge(int maxAge){
        this.maxAge = maxAge;
        return this;
    }

    public MagicParticleInfo scale(float scale){
        this.scale = scale;
        return this;
    }

    public MagicParticleInfo shrink(){
        this.shrink = true;
        return this;
    }

    public MagicParticleInfo gravity(float gravity){
        this.gravity = gravity;
        return this;
    }

    public MagicParticleInfo alpha(float alpha){
        this.alpha = alpha;
        return this;
    }

    public MagicParticleInfo collide() {
        this.canCollide = true;
        return this;
    }

    public MagicParticleInfo fadeMode(MagicParticle.EnumFadeMode fadeMode) {
        this.fadeMode = fadeMode;
        return this;
    }
}
