package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;

public class Disable extends StatusEffect {
    public Disable() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entity
                0xFAE66F); // color in RGB
		this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of("minecraft", "movement_speed"), -0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,  Identifier.of("minecraft", "attack_damage"), -0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
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
        return true;
    }
}
