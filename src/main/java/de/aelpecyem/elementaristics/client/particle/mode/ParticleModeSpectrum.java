package de.aelpecyem.elementaristics.client.particle.mode;

import de.aelpecyem.elementaristics.client.particle.MagicParticle;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.client.particle.IParticleRenderType;

import java.awt.*;

public class ParticleModeSpectrum extends ParticleMode {
    @Override
    public void setup(MagicParticle particle) {
        int addition = particle.getRandom().nextInt(200);
        particle.setAge(particle.getAge() + addition);
        particle.setMaxAge(particle.getMaxAge() + addition);
        particle.setColor(0, 0, 1);
    }

    @Override
    public void update(MagicParticle particle) {
        float process = (particle.getAge() % 300) / 300F;
        Color color = Color.getHSBColor(process, 1, 1);
        particle.setColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
    }

    @Override
    public IParticleRenderType getRenderType(MagicParticle particle) {
        return ModParticles.RenderTypes.BRIGHT;
    }
}
