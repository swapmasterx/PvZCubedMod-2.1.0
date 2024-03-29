package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
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


import java.util.List;

public abstract class ZombieObstacleEntity extends ZombieShieldEntity{

	public boolean beingEaten = false;
	public boolean dragger = true;
	protected ZombieObstacleEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.noClip = false;
	}

	@Override
	public void tick() {
		if (this.getType().equals(PvZEntity.TRASHCANBIN)){
			dragger = false;
		}
		super.tick();
		this.setCanBurn(CanBurn.TRUE);
		List<GravebusterEntity> list = world.getNonSpectatingEntities(GravebusterEntity.class, entityBox.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		this.beingEaten = !list.isEmpty();
		if (!this.hasVehicle() && this.getHypno() && !this.getType().equals(PvZEntity.HEALSTATION)){
			this.setHypno(IsHypno.FALSE);
		}
	}


	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}
}
