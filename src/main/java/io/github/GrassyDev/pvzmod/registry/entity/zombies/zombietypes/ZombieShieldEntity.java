package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;


public abstract class ZombieShieldEntity extends ZombiePropEntity{
	protected ZombieShieldEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
}
