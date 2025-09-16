package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;

import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_MATERIAL;

public class Acid extends StatusEffect {
    public Acid() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entity
                0x70BD84); // color in RGB
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean shouldApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh").equals("metallic") || ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh").equals("electronic")) {
            entity.damage(entity.getDamageSources().generic(), 5F);
        }
    return true;
    }
}
