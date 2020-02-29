package de.aelpecyem.elementaristics.common.misc.potions;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ModEffect extends Effect {
    public ModEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
