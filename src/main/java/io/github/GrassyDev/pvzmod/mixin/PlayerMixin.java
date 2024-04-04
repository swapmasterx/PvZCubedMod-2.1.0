package io.github.GrassyDev.pvzmod.mixin;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.items.seedpackets.HeavenlyPeachSeeds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract PlayerInventory getInventory();

	@Shadow
	public abstract boolean isCreative();

	@Inject(method = "tick", at = @At("HEAD"))
    public void pvzmod$tick(CallbackInfo ci) {
		if (this.getInventory().getMainHandStack().getItem() instanceof HeavenlyPeachSeeds){
			List<PlantEntity> plantList = this.getWorld().getNonSpectatingEntities(PlantEntity.class, this.getBoundingBox().expand(15));
			for (PlantEntity plantEntity : plantList){
				if (plantEntity.getHealth() <= plantEntity.getMaxHealth() / 2){
					plantEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 2, 1)));
				}
			}
		}
    }
}
