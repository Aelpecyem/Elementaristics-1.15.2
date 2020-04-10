package de.aelpecyem.elementaristics.client.particle.mode;

import de.aelpecyem.elementaristics.client.particle.GlowParticle;
import de.aelpecyem.elementaristics.client.particle.ParticleHandler;
import de.aelpecyem.elementaristics.lib.ColorUtil;
import net.minecraft.client.particle.IParticleRenderType;

import java.awt.*;

public class ParticleModeSteam extends ParticleMode {
    @Override
    public void setUp(GlowParticle particle) {
        int addition = particle.getRandom().nextInt(30);
        particle.setAge(particle.getAge() + addition);
        particle.setMaxAge(particle.getMaxAge() + addition);
    }

    @Override
    public void tick(GlowParticle particle) {
        int tickParts = particle.getAge() % 120;
        float blend = (0.5F - Math.abs(0.5F - tickParts / 120F)) * 2F;//tickParts - 200;
        Color color = ColorUtil.blend(new Color(particle.getRed(), particle.getGreen(), particle.getBlue()), new Color(0xD3FAFF), 1 - blend, blend);
        particle.setColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
    }

    @Override
    public IParticleRenderType renderType(GlowParticle particle) {
        return ParticleHandler.RenderTypes.BRIGHT;
    }

    @Override
    public boolean overridesCompletely() {
        return false;
    }
}
