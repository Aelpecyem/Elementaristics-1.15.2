package de.aelpecyem.elementaristics.client.particle.mode;

import de.aelpecyem.elementaristics.client.particle.MagicParticle;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.client.particle.IParticleRenderType;

import java.util.ArrayList;
import java.util.List;

public abstract class ParticleMode {
    public static List<ParticleMode> MODES = new ArrayList<>();

    public ParticleMode() {
        MODES.add(this);
    }

    public abstract void setup(MagicParticle particle);

    public abstract void update(MagicParticle particle);

    public IParticleRenderType getRenderType(MagicParticle particle) {
        return ModParticles.RenderTypes.BRIGHT;
    }
}
