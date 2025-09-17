package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;

public class Shadow extends StatusEffect {
    public Shadow() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entity
                0x3249B8); // color in RGB
		final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.US_ASCII));
		final UUID MAX_STRENGTH_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.US_ASCII));
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of("minecraft", "movement_speed"), -0.115375, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,  Identifier.of("minecraft", "attack_damage"), -0.25, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
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
