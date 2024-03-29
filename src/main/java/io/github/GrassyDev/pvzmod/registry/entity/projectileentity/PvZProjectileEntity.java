package io.github.GrassyDev.pvzmod.registry.entity.projectileentity;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


import java.util.ArrayList;
import java.util.List;

public abstract class PvZProjectileEntity extends ThrownItemEntity {

	public PvZProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public PvZProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
		super(entityType, d, e, f, world);
	}

	public PvZProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
		super(entityType, livingEntity, world);
	}

	@Override
	public void tick() {
		this.hitEntities();
		super.tick();
		this.hitEntities.clear();
	}

	public float damageMultiplier = 1;

	public List<Entity> hitEntities = new ArrayList<>();

	public List<Entity> moreEntities = new ArrayList<>();

	public void hitEntities(){
		List<Entity> hit = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox().stretch(0, -0.5, 0));
		List<TileEntity> tileHit = this.getWorld().getNonSpectatingEntities(TileEntity.class, this.getBoundingBox().expand(0, 2, 0));
		hit.addAll(moreEntities);
		hitEntities.addAll(tileHit);
		for (Entity entity : hit){
			if (entity instanceof GraveEntity graveEntity && graveEntity.decorative){

			}
			else {
				hitEntities.add(entity);
			}
		}
	}
}
