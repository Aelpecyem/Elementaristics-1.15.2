package de.aelpecyem.elementaristics.client.particle.mode;

import de.aelpecyem.elementaristics.client.particle.MagicParticle;
import de.aelpecyem.elementaristics.lib.ColorUtil;
import de.aelpecyem.elementaristics.reg.ModParticles;
import net.minecraft.client.particle.IParticleRenderType;

import java.awt.*;

public class ParticleModeVaporwave extends ParticleMode {
    @Override
    public void setup(MagicParticle particle) {
        int addition = particle.getRandom().nextInt(300);
        particle.setAge(particle.getAge() + addition);
        particle.setMaxAge(particle.getMaxAge() + addition);
        particle.setColor(0, 0, 1);
        particle.setColor(0, 0, 1);
    }

    @Override
    public void update(MagicParticle particle) {
        int tickParts = particle.getAge() % 300;
        float blend = (0.5F - Math.abs(0.5F - tickParts / 300F)) * 2F;//tickParts - 200;
        Color color = ColorUtil.blend(new Color(16711913), new Color(97), blend, 1 - blend);
        particle.setColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
    }

    @Override
    public IParticleRenderType getRenderType(MagicParticle particle) {
        return ModParticles.RenderTypes.DARKEN;
    }
}
