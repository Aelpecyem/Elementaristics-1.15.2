package de.aelpecyem.elementaristics.client.particle.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.aelpecyem.elementaristics.client.particle.MagicParticle;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.command.impl.ParticleCommand;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagicParticleData implements IParticleData {
    public static final IDeserializer<MagicParticleData> DESERIALIZER = new IDeserializer<MagicParticleData>() {
        public MagicParticleData deserialize(ParticleType<MagicParticleData> p_197544_1_, StringReader reader) {
            return new MagicParticleData(p_197544_1_, MagicParticleInfo.read(reader));
        }

        public MagicParticleData read(ParticleType<MagicParticleData> data, PacketBuffer buffer) {
            return new MagicParticleData(data, MagicParticleInfo.read(buffer));
        }
    };

    private final ParticleType<MagicParticleData> particleType;
    private final MagicParticleInfo info;

    public MagicParticleData(ParticleType<MagicParticleData> data, MagicParticleInfo info) {
        this.particleType = data;
        this.info = info;
    }

    public void write(PacketBuffer buffer) {
        info.write(buffer);
    }

    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + info.toString();
    }

    public ParticleType<MagicParticleData> getType() {
        return this.particleType;
    }

    public MagicParticleInfo getInfo() {
        return info;
    }
}
