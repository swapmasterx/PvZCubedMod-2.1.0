package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


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
