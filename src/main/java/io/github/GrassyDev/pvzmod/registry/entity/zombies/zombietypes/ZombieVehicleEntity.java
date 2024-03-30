package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


public abstract class ZombieVehicleEntity extends MachinePvZombieEntity{

	protected ZombieVehicleEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);

	}

	@Override
	public void tick() {
		GeneralPvZombieEntity zombiePassenger = null;
		for (Entity entity : this.getPassengerList()){
			if (entity instanceof GeneralPvZombieEntity){
				zombiePassenger = (GeneralPvZombieEntity) entity;
			}
		}
		if (zombiePassenger != null){
			if (zombiePassenger.getTarget() != null) {
				this.setTarget(zombiePassenger.getTarget());
			}
			if (zombiePassenger.getHypno()) {
				this.setHypno(IsHypno.TRUE);
			}
			else {
				this.setHypno(IsHypno.FALSE);
			}
		}
		super.tick();
	}

	@Override
	public boolean tryAttack(Entity target) {
		return false;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}
}
