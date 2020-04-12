package de.aelpecyem.elementaristics.common.misc.potions;

import de.aelpecyem.elementaristics.reg.ModParticles;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

public class IntoxicatedEffect extends ModEffect {
    public IntoxicatedEffect() {
        super(EffectType.NEUTRAL, 12858015);
    }

    @Override
    public void performEffect(LivingEntity target, int amplifier) {
        super.performEffect(target, amplifier);

        if (target.dimension != ModWorld.MIND) {
            target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100, 0, false, false, false));
            target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 0, false, false, false));
            target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100, 0, false, false, false));
            //proper distortion effects, probably some blur shader

            if (target instanceof MobEntity && target.getRNG().nextInt(100) <= amplifier) {
                ((MobEntity) target).setAttackTarget(null);
                ((MobEntity) target).setAggroed(false);
            }
            if (target.world.isRemote) {
                ModParticles.Handler.spawnEntityParticles(target, 666, 0.05D, ModParticles.Modes.RAINBOWS);//todo, add the particle modes, ParticleModes.RAINBOWS, 0.002D);
            }
        }
    }

}
