package de.aelpecyem.client.particle.mode;

import de.aelpecyem.client.particle.GlowParticle;
import net.minecraft.client.particle.IParticleRenderType;

public abstract class ParticleMode {
    public abstract void tick(GlowParticle particle);

    public abstract void setUp(GlowParticle particle);

    public abstract boolean overridesCompletely();

    public IParticleRenderType renderType(GlowParticle particle) {
        return null;
    }
}
