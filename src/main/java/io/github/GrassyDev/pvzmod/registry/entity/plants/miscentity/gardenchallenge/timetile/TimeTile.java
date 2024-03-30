package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.timetile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeTime;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


public class TimeTile extends TileEntity {
	public TimeTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
		this.setNoGravity(true);
	}



	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TYPE, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("type", this.getTimeType());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(TYPE, tag.getInt("type"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> TYPE =
			DataTracker.registerData(TimeTile.class, TrackedDataHandlerRegistry.INTEGER);


	private int getTimeType() {
		return this.dataTracker.get(TYPE);
	}

	public ChallengeTime getTime() {
		return ChallengeTime.byId(this.getTimeType() & 255);
	}

	public void setTimeType(ChallengeTime variant) {
		this.dataTracker.set(TYPE, variant.getId() & 255);
	}
}
