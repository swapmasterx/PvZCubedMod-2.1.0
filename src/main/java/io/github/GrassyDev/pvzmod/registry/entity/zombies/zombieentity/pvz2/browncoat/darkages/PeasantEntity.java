package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PeasantEntity extends BrowncoatEntity {
	public PeasantEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if (this.hasStatusEffect(PvZCubed.PVZPOISON)){
			this.removeStatusEffect(PvZCubed.PVZPOISON);
		}
		super.tick();
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
			itemStack = ModItems.PEASANTCONEEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
			itemStack = ModItems.PEASANTBUCKETEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.PEASANTKNIGHT) || this.getVariant().equals(BrowncoatVariants.PEASANTKNIGHTHYPNO)){
			itemStack = ModItems.PEASANTKNIGHTEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.PEASANTEGG.getDefaultStack();
		}
		return itemStack;
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPeasantAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.peasantH());
	}
}
