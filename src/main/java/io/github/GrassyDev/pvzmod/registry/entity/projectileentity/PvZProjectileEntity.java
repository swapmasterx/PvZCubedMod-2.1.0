package io.github.GrassyDev.pvzmod.registry.entity.projectileentity;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


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
