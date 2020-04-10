package de.aelpecyem.elementaristics.client.particle.data;

import com.mojang.brigadier.StringReader;
import de.aelpecyem.elementaristics.client.particle.GlowParticle;
import net.minecraft.network.PacketBuffer;

public class MagicParticleInfo {
    public float scale;
    public int color;
    public float alpha;
    public float gravity;
    public GlowParticle.EnumFadeMode fadeMode;
    public boolean shrink;
    public int maxAge;

    protected MagicParticleInfo(float scale, int color, float alpha, float gravity, GlowParticle.EnumFadeMode fadeMode, boolean shrink, int maxAge) {
        this.scale = scale;
        this.color = color;
        this.alpha = alpha;
        this.gravity = gravity;
        this.fadeMode = fadeMode;
        this.shrink = shrink;
        this.maxAge = maxAge;
    }

    public static MagicParticleInfo create(int color){
        return new MagicParticleInfo(3, color, 0.9F, 0,  GlowParticle.EnumFadeMode.OUT, false, 120);
    }

    public void write(PacketBuffer buffer){
        buffer.writeFloat(scale);
        buffer.writeInt(color);
        buffer.writeFloat(alpha);
        buffer.writeFloat(gravity);
        buffer.writeByte(fadeMode.toId());
        buffer.writeBoolean(shrink);
        buffer.writeInt(maxAge);
    }

    public static MagicParticleInfo read(StringReader reader){
        String string = reader.getString();
        String[] params = string.split(" ");
        System.out.println(params);
        return new MagicParticleInfo(Float.valueOf(params[1]), Integer.valueOf(params[2]), Float.valueOf(params[3]), Float.valueOf(params[4]), GlowParticle.EnumFadeMode.fromId(Byte.valueOf(params[5])), Boolean.valueOf(params[6]), Integer.valueOf(params[7]));
    }

    public static MagicParticleInfo read(PacketBuffer buffer){
        return new MagicParticleInfo(buffer.readFloat(), buffer.readInt(), buffer.readFloat(), buffer.readFloat(), GlowParticle.EnumFadeMode.fromId(buffer.readByte()), buffer.readBoolean(), buffer.readInt());
    }

    @Override
    public String toString() {
        return scale + " " + color + " " + alpha + " " + gravity + " " + fadeMode.toId() + " " + shrink + " " + maxAge;
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

    public MagicParticleInfo fadeMode(GlowParticle.EnumFadeMode fadeMode){
        this.fadeMode = fadeMode;
        return this;
    }
}
