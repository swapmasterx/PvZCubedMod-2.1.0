package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;

public class Cheese extends StatusEffect {
    public Cheese() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entity
                0xE69C26); // color in RGB
		final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.US_ASCII));
		addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, String.valueOf(MAX_SPEED_UUID), -0.115375, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean shouldApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    }
}
