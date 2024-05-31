package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.future;


import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class FutureZombieEntity extends BrowncoatEntity {
	public FutureZombieEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
			itemStack = ModItems.FUTURECONEEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
			itemStack = ModItems.FUTUREBUCKETEGG.getDefaultStack();
		}
			else if (this.getVariant().equals(BrowncoatVariants.HOLOHEAD) || this.getVariant().equals(BrowncoatVariants.HOLOHEADHYPNO)){
			itemStack = ModItems.HOLOHEADEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.FUTUREZOMBIEEGG.getDefaultStack();
		}
		return itemStack;
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFutureAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.14D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.futureH());
	}
}
