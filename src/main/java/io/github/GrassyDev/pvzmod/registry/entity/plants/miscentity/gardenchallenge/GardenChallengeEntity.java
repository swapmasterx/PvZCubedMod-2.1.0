package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModBlocks;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rifttile.RiftTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.sunbomb.SunBombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave.DarkAgesGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone.EgyptGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.fairytaleforest.FairyTaleGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave.FutureGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.mausoleum.MausoleumGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave.RoofGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.timetile.TimeTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.weathertile.WeatherTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeTiers;
import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeTime;
import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeWeather;
import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.TypeOfWorld;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.RiftVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle.RockObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class GardenChallengeEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

    private String controllerName = "gardencontroller";

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public GardenChallengeEntity(EntityType<? extends GardenChallengeEntity> entityType, World world) {
        super(entityType, world);

		checkEntities();
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TIERS, 0);
		this.dataTracker.startTracking(WAVES, 0);
		this.dataTracker.startTracking(GRAVESSPAWNED, 0);
		this.dataTracker.startTracking(WAVEINPROGRESS, false);
		this.dataTracker.startTracking(WAVETICKS, 0);
		this.dataTracker.startTracking(WORLD1, 0);
		this.dataTracker.startTracking(WORLD2, 0);
		this.dataTracker.startTracking(WORLD3, 0);
		this.dataTracker.startTracking(WORLD4, 0);
		this.dataTracker.startTracking(WORLD5, 0);
		this.dataTracker.startTracking(WORLD6, 0);
		this.dataTracker.startTracking(WORLD7, 0);
		this.dataTracker.startTracking(WORLD8, 0);
		this.dataTracker.startTracking(MINNIGHT, 0);
		this.dataTracker.startTracking(MINPOOL, 0);
		this.dataTracker.startTracking(MINROOF, 0);
		this.dataTracker.startTracking(MINEGYPT, 0);
		this.dataTracker.startTracking(MINDARKAGES, 0);
		this.dataTracker.startTracking(MINFUTURE, 0);
		this.dataTracker.startTracking(MINFAIRYTALE, 0);
		this.dataTracker.startTracking(MINMAUSOLEUM, 0);
		this.dataTracker.startTracking(LOCKMINCHECK, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("tiers", this.getTierCount());
		tag.putInt("waves", this.getWaveCount());
		tag.putInt("graves", this.getGravesSpawned());
		tag.putBoolean("waveInProgress", this.getWaveInProgress());
		tag.putInt("waveTicks", this.getWaveTicks());
		tag.putInt("world1", this.getWorld1Count());
		tag.putInt("world2", this.getWorld2Count());
		tag.putInt("world3", this.getWorld3Count());
		tag.putInt("world4", this.getWorld4Count());
		tag.putInt("world5", this.getWorld5Count());
		tag.putInt("world6", this.getWorld6Count());
		tag.putInt("world7", this.getWorld7Count());
		tag.putInt("world8", this.getWorld8Count());
		tag.putInt("minNight", this.getMinNight());
		tag.putInt("minPool", this.getMinPool());
		tag.putInt("minRoof", this.getMinRoof());
		tag.putInt("minEgypt", this.getMinEgypt());
		tag.putInt("minDarkAges", this.getMinDarkAges());
		tag.putInt("minFuture", this.getMinFuture());
		tag.putInt("minFairyTale", this.getMinFairyTale());
		tag.putInt("minMausoleum", this.getMinMausoleum());
		tag.putBoolean("lockMinCheck", this.getLockMinCheck());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(TIERS, tag.getInt("tiers"));
		this.dataTracker.set(WAVES, tag.getInt("waves"));
		this.dataTracker.set(GRAVESSPAWNED, tag.getInt("graves"));
		this.dataTracker.set(WAVEINPROGRESS, tag.getBoolean("waveInProgress"));
		this.dataTracker.set(WAVETICKS, tag.getInt("waveTicks"));
		this.dataTracker.set(WORLD1, tag.getInt("world1"));
		this.dataTracker.set(WORLD2, tag.getInt("world2"));
		this.dataTracker.set(WORLD3, tag.getInt("world3"));
		this.dataTracker.set(WORLD4, tag.getInt("world4"));
		this.dataTracker.set(WORLD5, tag.getInt("world5"));
		this.dataTracker.set(WORLD6, tag.getInt("world6"));
		this.dataTracker.set(WORLD7, tag.getInt("world7"));
		this.dataTracker.set(WORLD8, tag.getInt("world8"));
		this.dataTracker.set(MINNIGHT, tag.getInt("minNight"));
		this.dataTracker.set(MINPOOL, tag.getInt("minPool"));
		this.dataTracker.set(MINROOF, tag.getInt("minRoof"));
		this.dataTracker.set(MINEGYPT, tag.getInt("minEgypt"));
		this.dataTracker.set(MINDARKAGES, tag.getInt("minDarkAges"));
		this.dataTracker.set(MINFUTURE, tag.getInt("minFuture"));
		this.dataTracker.set(MINFAIRYTALE, tag.getInt("minFairyTale"));
		this.dataTracker.set(MINMAUSOLEUM, tag.getInt("minMausoleum"));
		this.dataTracker.set(LOCKMINCHECK, tag.getBoolean("lockMinCheck"));
		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}
	}

	@Override
	public void setCustomName(@Nullable Text name) {
		super.setCustomName(name);
		this.bossBar.setName(this.getDisplayName());
		this.waveBar.setName(Text.of("Waves"));
	}

	static {
	}

	private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(this.getDisplayName(), BossBar.Color.GREEN, BossBar.Style.PROGRESS)
			.setDarkenSky(true);

	private final ServerBossBar waveBar = (ServerBossBar)new ServerBossBar(Text.of("Waves"), BossBar.Color.RED, BossBar.Style.PROGRESS);

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> WAVES =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> GRAVESSPAWNED =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> TIERS =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Boolean> WAVEINPROGRESS =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final TrackedData<Boolean> LOCKMINCHECK =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final TrackedData<Integer> WAVETICKS =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD1 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD2 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD3 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD4 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD5 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD6 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD7 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> WORLD8 =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINNIGHT =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINPOOL =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINROOF =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINEGYPT =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINDARKAGES =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINFUTURE =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINFAIRYTALE =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> MINMAUSOLEUM =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int getTierCount() {
		return this.dataTracker.get(TIERS);
	}

	public ChallengeTiers getTier() {
		return ChallengeTiers.byId(this.getTierCount() & 255);
	}

	public void setTier(ChallengeTiers variant) {
		this.dataTracker.set(TIERS, variant.getId() & 255);
	}

	private int getWaveCount() {
		return this.dataTracker.get(WAVES);
	}

	private void setWave(Integer count) {
		this.dataTracker.set(WAVES, count);
	}

	private void addWave(){
		int count = getWaveCount();
		this.dataTracker.set(WAVES, count + 1);
	}

	private int getGravesSpawned() {
		return this.dataTracker.get(GRAVESSPAWNED);
	}

	private void setGravesSpawned(Integer count) {
		this.dataTracker.set(GRAVESSPAWNED, count);
	}

	private void addGravesSpawned(){
		int count = getGravesSpawned();
		this.dataTracker.set(GRAVESSPAWNED, count + 1);
	}

	private int getWaveTicks() {
		return this.dataTracker.get(WAVETICKS);
	}

	private void setWaveticks(Integer count) {
		this.dataTracker.set(WAVETICKS, count);
	}


	public enum WaveInProgress {
		FALSE(false),
		TRUE(true);

		WaveInProgress(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getWaveInProgress() {
		return this.dataTracker.get(WAVEINPROGRESS);
	}

	public void setWaveinprogress(GardenChallengeEntity.WaveInProgress waveinprogress) {
		this.dataTracker.set(WAVEINPROGRESS, waveinprogress.getId());
	}


	public enum LockMinCheck {
		FALSE(false),
		TRUE(true);

		LockMinCheck(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getLockMinCheck() {
		return this.dataTracker.get(LOCKMINCHECK);
	}

	public void setLockMinCheck(GardenChallengeEntity.LockMinCheck lockMinCheck) {
		this.dataTracker.set(LOCKMINCHECK, lockMinCheck.getId());
	}

	private int getWorld1Count() {
		return this.dataTracker.get(WORLD1);
	}

	public TypeOfWorld getWorld1() {
		return TypeOfWorld.byId(this.getWorld1Count() & 255);
	}

	public void setWorld1(TypeOfWorld variant) {
		this.dataTracker.set(WORLD1, variant.getId() & 255);
	}

	private int getWorld2Count() {
		return this.dataTracker.get(WORLD2);
	}

	public TypeOfWorld getWorld2() {
		return TypeOfWorld.byId(this.getWorld2Count() & 255);
	}

	public void setWorld2(TypeOfWorld variant) {
		this.dataTracker.set(WORLD2, variant.getId() & 255);
	}

	private int getWorld3Count() {
		return this.dataTracker.get(WORLD3);
	}

	public TypeOfWorld getWorld3() {
		return TypeOfWorld.byId(this.getWorld3Count() & 255);
	}

	public void setWorld3(TypeOfWorld variant) {
		this.dataTracker.set(WORLD3, variant.getId() & 255);
	}

	private int getWorld4Count() {
		return this.dataTracker.get(WORLD4);
	}

	public TypeOfWorld getWorld4() {
		return TypeOfWorld.byId(this.getWorld4Count() & 255);
	}

	public void setWorld4(TypeOfWorld variant) {
		this.dataTracker.set(WORLD4, variant.getId() & 255);
	}

	private int getWorld5Count() {
		return this.dataTracker.get(WORLD5);
	}

	public TypeOfWorld getWorld5() {
		return TypeOfWorld.byId(this.getWorld5Count() & 255);
	}

	public void setWorld5(TypeOfWorld variant) {
		this.dataTracker.set(WORLD5, variant.getId() & 255);
	}

	private int getWorld6Count() {
		return this.dataTracker.get(WORLD6);
	}

	public TypeOfWorld getWorld6() {
		return TypeOfWorld.byId(this.getWorld6Count() & 255);
	}

	public void setWorld6(TypeOfWorld variant) {
		this.dataTracker.set(WORLD6, variant.getId() & 255);
	}

	private int getWorld7Count() {
		return this.dataTracker.get(WORLD7);
	}

	public TypeOfWorld getWorld7() {
		return TypeOfWorld.byId(this.getWorld7Count() & 255);
	}

	public void setWorld7(TypeOfWorld variant) {
		this.dataTracker.set(WORLD7, variant.getId() & 255);
	}

	private int getWorld8Count() {
		return this.dataTracker.get(WORLD8);
	}

	public TypeOfWorld getWorld8() {
		return TypeOfWorld.byId(this.getWorld8Count() & 255);
	}

	public void setWorld8(TypeOfWorld variant) {
		this.dataTracker.set(WORLD8, variant.getId() & 255);
	}

	private int getMinNight() {
		return this.dataTracker.get(MINNIGHT);
	}

	public void setMinnight(Integer count) {
		this.dataTracker.set(MINNIGHT, count);
	}

	private int getMinPool() {
		return this.dataTracker.get(MINPOOL);
	}

	public void setMinpool(Integer count) {
		this.dataTracker.set(MINPOOL, count);
	}

	private int getMinRoof() {
		return this.dataTracker.get(MINROOF);
	}

	public void setMinroof(Integer count) {
		this.dataTracker.set(MINROOF, count);
	}

	private int getMinEgypt() {
		return this.dataTracker.get(MINEGYPT);
	}

	public void setMinegypt(Integer count) {
		this.dataTracker.set(MINEGYPT, count);
	}

	private int getMinDarkAges() {
		return this.dataTracker.get(MINDARKAGES);
	}

	public void setMindarkages(Integer count) {
		this.dataTracker.set(MINDARKAGES, count);
	}

	private int getMinFuture() {
		return this.dataTracker.get(MINFUTURE);
	}

	public void setMinfuture(Integer count) {
		this.dataTracker.set(MINFUTURE, count);
	}

	private int getMinFairyTale() {
		return this.dataTracker.get(MINFAIRYTALE);
	}

	public void setMinfairytale(Integer count) {
		this.dataTracker.set(MINFAIRYTALE, count);
	}

	private int getMinMausoleum() {
		return this.dataTracker.get(MINMAUSOLEUM);
	}

	public void setMinmausoleum(Integer count) {
		this.dataTracker.set(MINMAUSOLEUM, count);
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("garden.idle"));
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				this.kill();
			}
		}
	}


	@Override
	public void onDeath(DamageSource source) {
		for (GraveEntity graveEntity : currentWorlds){
			if (graveEntity != null) {
				graveEntity.discard();
			}
		}
		List<WeatherTile> weatherTiles = this.getWorld().getNonSpectatingEntities(WeatherTile.class, this.getBoundingBox().expand(25, 5, 25));
		List<TimeTile> timeTiles = this.getWorld().getNonSpectatingEntities(TimeTile.class, this.getBoundingBox().expand(25, 5, 25));
		for (WeatherTile weatherTile : weatherTiles){
			weatherTile.discard();
		}
		for (TimeTile tile : timeTiles){
			tile.discard();
		}
		if (source.getAttacker() instanceof GeneralPvZombieEntity || source.getAttacker() == this) {
			for (int i = 0; i < this.getTierCount() - 1; ++i) {
				this.dropItem(Items.DIAMOND);
				for (int u = 0; u < 4; ++u){
					this.dropItem(ModItems.FERTILIZER);
				}
				for (int u = 0; u < 12; ++u){
					this.dropItem(Items.GOLD_NUGGET);
				}
				for (int u = 0; u < 18; ++u){
					this.dropItem(Items.IRON_NUGGET);
				}
				for (int u = 0; u < 3; ++u){
					Item item = ModItems.SEED_PACKET_LIST.get(getRandom().nextInt(ModItems.SEED_PACKET_LIST.size()));
					this.dropItem(item);
				}
			}
			if (this.getTierCount() >= 5){
				for (int i = 0; i < this.getTierCount() - 1; ++i) {
					this.dropItem(Items.DIAMOND);
				}
				for (int i = 0; i < this.getTierCount() - 4; ++i) {
					this.dropItem(Items.NETHERITE_SCRAP);
					this.dropItem(Items.NETHERITE_SCRAP);
					this.dropItem(Items.NETHERITE_SCRAP);
					this.dropItem(Items.NETHERITE_SCRAP);
				}
			}
			List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(25, 5, 25));
			List<GraveEntity> list2 = this.getWorld().getNonSpectatingEntities(GraveEntity.class, this.getBoundingBox().expand(25, 5, 25));
			for (HostileEntity hostileEntity : list){
				hostileEntity.discard();
			}
			for (GraveEntity graveEntity : list2){
				graveEntity.discard();
			}
		}
		super.onDeath(source);
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	int cooldown;

	int blockBreakCooldown;

	public void tick() {
		List<PlantEntity> list = this.getWorld().getNonSpectatingEntities(PlantEntity.class, this.getBoundingBox().expand(25, 5, 25));
		for (PlantEntity plantEntity : list) {
			float multiplierTimes = 1;
			plantEntity.damageMultiplier = 1;
			for (PlantEntity plantEntity1 : list){
				if (plantEntity1.getType() == plantEntity.getType() && plantEntity1 != plantEntity){
					++multiplierTimes;
					plantEntity1.damageMultiplier = 1;
				}
			}
			for (PlantEntity plantEntity1 : list){
				if (plantEntity1.getType() == plantEntity.getType() && plantEntity1 != plantEntity){
					float multiplier = plantEntity1.damageMultiplier - 0.015f * multiplierTimes;
					if (multiplier <= 0.66){
						multiplier = 0.66f;
					}
					plantEntity1.damageMultiplier = multiplier;
				}
			}
		}
		this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
		float maxWaves = switch (getTier()) {
			case ONE -> maxWaves = 3;
			case TWO -> maxWaves = 4;
			case THREE -> maxWaves = 5;
			case FOUR -> maxWaves = 3;
			case FIVE -> maxWaves = 3;
			case SIX -> maxWaves = 4;
			case SEVEN -> maxWaves = 5;
			case EIGHT -> maxWaves = 6;
			default -> maxWaves = 1;
		};
		this.waveBar.setPercent(this.getWaveCount() / maxWaves);
		if (this.getY() <= this.getWorld().getBottomY() + 10){
			this.kill();
		}
		if (this.age > 1) {
			checkEntities();
			waveManager();
			if (--blockBreakCooldown <= 0) {
				levelField();
				this.blockBreakCooldown = this.random.range(10, 30);
			}
		}
		super.tick();
		this.checkEntities();
		if (currentWeather == null){
			WeatherTile weatherTile = (WeatherTile) PvZEntity.WEATHERTILE.create(getWorld());
			weatherTile.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
			weatherTile.setPersistent();
			weatherTile.setHeadYaw(0);
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				weatherTile.initialize(serverWorld, getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				serverWorld.spawnEntityAndPassengers(weatherTile);
			}
			weatherTile.setWeatherType(ChallengeWeather.CLOUD);
		}
		if (currentTime == null){
			TimeTile timeTile = (TimeTile) PvZEntity.TIMETILE.create(getWorld());
			timeTile.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
			timeTile.setPersistent();
			timeTile.setHeadYaw(0);
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				timeTile.initialize(serverWorld, getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				serverWorld.spawnEntityAndPassengers(timeTile);
			}
			timeTile.setTimeType(ChallengeTime.DAY);
		}
		if (this.currentTime != null) {
			if (this.currentTime.getTime().equals(ChallengeTime.DROUGHT)) {
				List<HostileEntity> list2 = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(25, 5, 25));
				for (HostileEntity hostileEntity : list2) {
					if (!hostileEntity.isWet()) {
						hostileEntity.removeStatusEffect(PvZCubed.WET);
						hostileEntity.removeStatusEffect(PvZCubed.ICE);
						hostileEntity.removeStatusEffect(PvZCubed.FROZEN);
						hostileEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 5, 1)));
					}
				}
			}
		}
		BlockPos blockPos = this.getBlockPos();
		--cooldown;
		if (getWaveInProgress()){
			for (int j = 0; j < 3; ++j) {
				RandomGenerator randomGenerator = this.random;
				double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
				double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
				double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
				this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			}
			this.setWaveticks(this.getWaveTicks() + 1);
		}
		else {
			this.setWaveticks(0);
		}
	}

	@Override
	protected void mobTick() {
		super.mobTick();
	}

	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		this.bossBar.addPlayer(player);
		this.waveBar.addPlayer(player);
	}

	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		this.bossBar.removePlayer(player);
		this.waveBar.removePlayer(player);
	}

	public void tickMovement() {
		super.tickMovement();
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.GARDENCHALLENGE_SPAWN.getDefaultStack();
	}

	protected List<GraveEntity> currentWorlds = new ArrayList<>();
	protected TimeTile currentTime = null;
	protected WeatherTile currentWeather = null;
	protected List<GraveEntity> firsWorldCheck = new ArrayList<>();
	protected List<BlockPos> spawnableSpots = new ArrayList<>();
	protected List<BlockPos> waterSpots = new ArrayList<>();
	protected List<BlockPos> rift1Spots = new ArrayList<>();
	protected List<BlockPos> rift2Spots = new ArrayList<>();
	protected List<BlockPos> rift3Spots = new ArrayList<>();
	protected List<BlockPos> egyptSpots = new ArrayList<>();
	protected List<BlockPos> world2Favorable = new ArrayList<>();
	protected List<BlockPos> world3Favorable = new ArrayList<>();
	protected List<BlockPos> world4Favorable = new ArrayList<>();
	protected List<BlockPos> world5Favorable = new ArrayList<>();
	protected List<BlockPos> world6Favorable = new ArrayList<>();
	protected List<BlockPos> world7Favorable = new ArrayList<>();
	protected List<BlockPos> world8Favorable = new ArrayList<>();
	protected List<Entity> checkEntities = new ArrayList<>();
	protected TypeOfWorld addedWorld = TypeOfWorld.BASIC;

	public void checkEntities(){
		List<Entity> check2Remove = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox().expand(25, 5, 25));
		firsWorldCheck = this.getWorld().getNonSpectatingEntities(GraveEntity.class, this.getBoundingBox());
		List<Entity> check = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox());
		for (Entity entity : check){
			if (entity instanceof WeatherTile weatherTile){
				currentWeather = weatherTile;
			}
			if (entity instanceof TimeTile timeTile){
				currentTime = timeTile;
			}
		}
		currentWorlds.clear();
		for (int u = 0; u < 8; ++u){
			currentWorlds.add(null);
		}
		for (GraveEntity graveEntity : firsWorldCheck) {
			if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1))) {
				currentWorlds.set(0, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ()))){
				currentWorlds.set(1, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1))){
				currentWorlds.set(2, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(),  new BlockPos(new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() - 1)))){
				currentWorlds.set(3, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1))){
				currentWorlds.set(4, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ()))) {
				currentWorlds.set(5, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1))){
				currentWorlds.set(6, graveEntity);
			}
			else if (Objects.equals(graveEntity.getBlockPos(), new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() + 1))){
				currentWorlds.set(7, graveEntity);
			}
		}

		for (Entity entity : check2Remove){
			if (entity instanceof HostileEntity && !(entity instanceof GeneralPvZombieEntity)){
				entity.discard();
			}
			if (entity instanceof TntEntity){
				entity.discard();
			}
		}

		checkEntities = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox().expand(20, 6, 20));
		for (GraveEntity graveEntity : currentWorlds){
			if (graveEntity != null) {
				graveEntity.decorative = true;
				graveEntity.setAiDisabled(true);
				graveEntity.setPersistent();
			}
		}
		for (Entity entity : checkEntities){
			if (entity instanceof TimeTile timeTile){
				currentTime = timeTile;
			}
			if (entity instanceof WeatherTile weatherTile){
				currentWeather = weatherTile;
			}
		}
		Iterator var9 = checkEntities.iterator();
		while (true) {
			Entity entity;
			if (!var9.hasNext()) {
				return;
			}

			entity = (Entity) var9.next();

		}
	}

	public void waveManager(){
		this.addedWorld = TypeOfWorld.BASIC;
		int maxWaves = switch (getTier()) {
			case ONE -> maxWaves = 3;
			case TWO -> maxWaves = 4;
			case THREE -> maxWaves = 5;
			case FOUR -> maxWaves = 3;
			case FIVE -> maxWaves = 3;
			case SIX -> maxWaves = 4;
			case SEVEN -> maxWaves = 5;
			case EIGHT -> maxWaves = 6;
			default -> maxWaves = 1;
		};
		if (this.getWaveCount() > maxWaves && !this.getTier().equals(ChallengeTiers.EIGHT)){
			if (currentWorlds.get(0) != null) {
				this.setTier(ChallengeTiers.byId(this.getTierCount() + 1));
				EntityType<?> entityType;
				List<EntityType<?>> list = new ArrayList<>();
				list.add(PvZEntity.BASICGRAVESTONE);
				list.add(PvZEntity.NIGHTGRAVESTONE);
				list.add(PvZEntity.POOLGRAVESTONE);
				list.add(PvZEntity.ROOFGRAVESTONE);
				list.add(PvZEntity.EGYPTGRAVESTONE);
				list.add(PvZEntity.DARKAGESGRAVESTONE);
				list.add(PvZEntity.FAIRYTALEGRAVESTONE);
				list.add(PvZEntity.MAUSOLEUMGRAVESTONE);
				if (this.getTierCount() >= 2){
					list.add(PvZEntity.FUTUREGRAVESTONE);
				}
				if (this.getTier().equals(ChallengeTiers.TWO)){
					list.remove(PvZEntity.BASICGRAVESTONE);
				}
				else if (this.getTier().equals(ChallengeTiers.THREE)){
					double random = Math.random();
					if (random <= 0.5){
						list.clear();
						if (currentWorlds.get(1) != null) {
							list.add(currentWorlds.get(1).getType());
						}
					}
					else {
						if (currentWorlds.get(1) != null) {
							list.remove(currentWorlds.get(1).getType());
						}
					}
				}
				else if (this.getTier().equals(ChallengeTiers.FOUR)){
					if (currentWorlds.get(0) != null) {
						list.remove(currentWorlds.get(0).getType());
					}
					if (currentWorlds.get(1) != null) {
						list.remove(currentWorlds.get(1).getType());
					}
					if (currentWorlds.get(2) != null) {
						list.remove(currentWorlds.get(2).getType());
					}
				}
				else if (this.getTier().equals(ChallengeTiers.SEVEN)){
					List<EntityType<?>> list2 = new ArrayList<>();
					if (currentWorlds.get(0) != null) {
						list2.add(currentWorlds.get(0).getType());
					}
					if (currentWorlds.get(1) != null) {
						list2.add(currentWorlds.get(1).getType());
					}
					if (currentWorlds.get(2) != null) {
						list2.add(currentWorlds.get(2).getType());
					}
					if (currentWorlds.get(3) != null) {
						list2.add(currentWorlds.get(3).getType());
					}
					if (currentWorlds.get(4) != null) {
						list2.add(currentWorlds.get(4).getType());
					}
					if (currentWorlds.get(5) != null) {
						list2.add(currentWorlds.get(5).getType());
					}
					list.clear();
					Set<EntityType<?>> set = new HashSet<>(list2);
					list.addAll(set);
				}
				entityType = list.get(random.range(0, list.size() - 1));
				if (entityType.equals(PvZEntity.NIGHTGRAVESTONE)){
					this.addedWorld = TypeOfWorld.NIGHT;
				}
				if (entityType.equals(PvZEntity.POOLGRAVESTONE)){
					this.addedWorld = TypeOfWorld.POOL;
				}
				if (entityType.equals(PvZEntity.ROOFGRAVESTONE)){
					this.addedWorld = TypeOfWorld.ROOF;
				}
				if (entityType.equals(PvZEntity.EGYPTGRAVESTONE)){
					this.addedWorld = TypeOfWorld.EGYPT;
				}
				if (entityType.equals(PvZEntity.DARKAGESGRAVESTONE)){
					this.addedWorld = TypeOfWorld.DARKAGES;
				}
				if (entityType.equals(PvZEntity.FUTUREGRAVESTONE)){
					this.addedWorld = TypeOfWorld.FUTURE;
				}
				if (entityType.equals(PvZEntity.FAIRYTALEGRAVESTONE)){
					this.addedWorld = TypeOfWorld.FAIRYTALE;
				}
				if (entityType.equals(PvZEntity.MAUSOLEUMGRAVESTONE)){
					this.addedWorld = TypeOfWorld.MAUSOLEUM;
				}
				if (nextGrave == PvZEntity.BASICGRAVESTONE){
					this.addedWorld = TypeOfWorld.BASIC;
					entityType = PvZEntity.BASICGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.NIGHTGRAVESTONE){
					this.addedWorld = TypeOfWorld.NIGHT;
					entityType = PvZEntity.NIGHTGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.POOLGRAVESTONE){
					this.addedWorld = TypeOfWorld.POOL;
					entityType = PvZEntity.POOLGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.ROOFGRAVESTONE){
					this.addedWorld = TypeOfWorld.ROOF;
					entityType = PvZEntity.ROOFGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.EGYPTGRAVESTONE){
					this.addedWorld = TypeOfWorld.EGYPT;
					entityType = PvZEntity.EGYPTGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.DARKAGESGRAVESTONE){
					this.addedWorld = TypeOfWorld.DARKAGES;
					entityType = PvZEntity.DARKAGESGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.FUTUREGRAVESTONE){
					this.addedWorld = TypeOfWorld.FUTURE;
					entityType = PvZEntity.FUTUREGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.FAIRYTALEGRAVESTONE){
					this.addedWorld = TypeOfWorld.FAIRYTALE;
					entityType = PvZEntity.FAIRYTALEGRAVESTONE;
					this.nextGrave = null;
				}
				if (nextGrave == PvZEntity.MAUSOLEUMGRAVESTONE){
					this.addedWorld = TypeOfWorld.MAUSOLEUM;
					entityType = PvZEntity.MAUSOLEUMGRAVESTONE;
					this.nextGrave = null;
				}
				this.addWorld(entityType);

				if (this.getWorld() instanceof ServerWorld) {
					double nightChance = 0;
					double bombChance = 0;
					double droughtChance = 0;

					double rainChance = 0;
					double thunderChance = 0;

					if (addedWorld == TypeOfWorld.BASIC){
						nightChance = 0;
						rainChance = 0.25;
						thunderChance = 0;
					}
					if (addedWorld == TypeOfWorld.NIGHT){
						nightChance = 1;
						rainChance = 0.3;
						thunderChance = 0.125;
					}
					if (addedWorld == TypeOfWorld.POOL){
						nightChance = 0;
						droughtChance = 0.3;
						rainChance = 0.2;
						thunderChance = 0;
					}
					if (addedWorld == TypeOfWorld.ROOF){
						nightChance = 0.25;
						rainChance = 0.35;
						thunderChance = 0.2;
					}
					if (addedWorld == TypeOfWorld.EGYPT){
						nightChance = 0.25;
						droughtChance = 0.4;
						rainChance = 0;
						thunderChance = 0;
					}
					if (addedWorld == TypeOfWorld.DARKAGES){
						nightChance = 1;
						rainChance = 0.5;
						thunderChance = 0.4;
					}
					if (addedWorld == TypeOfWorld.FUTURE){
						nightChance = 0.3;
						bombChance = 0.25;
						rainChance = 0;
						thunderChance = 0;
					}
					if (addedWorld == TypeOfWorld.FAIRYTALE){
						nightChance = 0.5;
						rainChance = 0.2;
						thunderChance = 0.15;
					}
					if (addedWorld == TypeOfWorld.MAUSOLEUM){
						nightChance = 1;
						rainChance = 0;
						thunderChance = 0.3;
					}
;
					if (currentTime != null && currentWeather != null) {
						double random = this.random.nextDouble();
						currentTime.setTimeType(ChallengeTime.DAY);
						if (random <= nightChance && nightChance > 0) {
							if (this.getWorld().getMoonSize() < 0.1F) {
								currentTime.setTimeType(ChallengeTime.NEWMOON);
							} else if (this.getWorld().getMoonSize() > 0.9F) {
								currentTime.setTimeType(ChallengeTime.FULLMOON);
							} else {
								currentTime.setTimeType(ChallengeTime.HALFMOON);
							}
						}
						if (random <= bombChance && bombChance > 0) {
							currentTime.setTimeType(ChallengeTime.BOMB);
						}
						if (random <= droughtChance && droughtChance > 0) {
							currentTime.setTimeType(ChallengeTime.DROUGHT);
						}

						double random2 = this.random.nextDouble();
						currentWeather.setWeatherType(ChallengeWeather.CLOUD);
						if (random2 <= rainChance && rainChance > 0) {
							currentWeather.setWeatherType(ChallengeWeather.RAIN);
						}
						if (random2 <= thunderChance && thunderChance > 0) {
							currentWeather.setWeatherType(ChallengeWeather.THUNDER);
						}
						if (currentTime.getTime().equals(ChallengeTime.DROUGHT)) {
							currentWeather.setWeatherType(ChallengeWeather.CLOUD);
						}
						if (currentTime.getTime().equals(ChallengeTime.BOMB)) {
							currentWeather.setWeatherType(ChallengeWeather.CLOUD);
						}
					}
				}
			}
			this.setWave(0);
		}
		else if (this.getWaveCount() > maxWaves && this.getTier().equals(ChallengeTiers.EIGHT)){
			this.setTier(ChallengeTiers.NINE);
			this.damage(getDamageSources().mobAttack(this), Integer.MAX_VALUE);
		}
		if (!this.getWaveInProgress()){
			setMinnight(0);
			setMinpool(0);
			setMinroof(0);
			setMinegypt(0);
			setMindarkages(0);
			setMinfuture(0);
			setMinfairytale(0);
			setMinmausoleum(0);
		}
		if (this.getWaveInProgress()){
			if (!this.getLockMinCheck()) {
				for (GraveEntity currentWorld : currentWorlds) {
					if (currentWorld instanceof NightGraveEntity) {
						setMinnight(getMinNight() + 3);
					}
					if (currentWorld instanceof PoolGraveEntity) {
						setMinpool(getMinPool() + 5);
					}
					if (currentWorld instanceof RoofGraveEntity) {
						setMinroof(getMinRoof() + 3);
					}
					if (currentWorld instanceof EgyptGraveEntity) {
						setMinegypt(getMinEgypt() + 3);
					}
					if (currentWorld instanceof DarkAgesGraveEntity) {
						setMindarkages(getMinDarkAges() + 3);
					}
					if (currentWorld instanceof FutureGraveEntity) {
						setMinfuture(getMinFuture() + 3);
					}
					if (currentWorld instanceof FairyTaleGraveEntity) {
						setMinfairytale(getMinFairyTale() + 3);
					}
					if (currentWorld instanceof MausoleumGraveEntity) {
						setMinmausoleum(getMinMausoleum() + 3);
					}
				}
				this.setLockMinCheck(LockMinCheck.TRUE);
			}
			int numOfGraves = 3;
			int numOfAmbushes = 0;
			numOfGraves = numOfGraves + getWaveCount() + getTierCount();
			if (this.getTierCount() >= 3 && !this.getTier().equals(ChallengeTiers.EIGHT)){
				numOfGraves = numOfGraves - 3;
			}
			float timeofWave = 60 * 20;
			timeofWave = timeofWave + (getTierCount() * 30 * 20);
			if (this.getGravesSpawned() >= numOfGraves){
				this.setWaveinprogress(WaveInProgress.FALSE);
				this.setGravesSpawned(0);
				this.setLockMinCheck(LockMinCheck.FALSE);
			}
			float waveInterval = (timeofWave * 0.85f) / numOfGraves;
			List<EntityType<?>> graveEntities = new ArrayList<>();
			for (GraveEntity graveEntity : currentWorlds){
				if (graveEntity instanceof NightGraveEntity){
					if (getMinNight() > 0){
						for (int x = 0; x <= getMinNight() - 1; ++x){
							graveEntities.add(PvZEntity.NIGHTGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof PoolGraveEntity){
					if (getMinPool() > 0){
						for (int x = 0; x <= getMinPool() - 1; ++x){
							graveEntities.add(PvZEntity.POOLGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof RoofGraveEntity){
					if (getMinRoof() > 0){
						for (int x = 0; x <= getMinRoof() - 1; ++x){
							graveEntities.add(PvZEntity.ROOFGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof EgyptGraveEntity){
					if (getMinEgypt() > 0){
						for (int x = 0; x <= getMinEgypt() - 1; ++x){
							graveEntities.add(PvZEntity.EGYPTGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof DarkAgesGraveEntity){
					if (getMinDarkAges() > 0){
						for (int x = 0; x <= getMinDarkAges() - 1; ++x){
							graveEntities.add(PvZEntity.DARKAGESGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof FutureGraveEntity){
					if (getMinFuture() > 0){
						for (int x = 0; x <= getMinFuture() - 1; ++x){
							graveEntities.add(PvZEntity.FUTUREGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof FairyTaleGraveEntity){
					if (getMinFairyTale() > 0){
						for (int x = 0; x <= getMinFairyTale() - 1; ++x){
							graveEntities.add(PvZEntity.FAIRYTALEGRAVESTONE);
						}
					}
				}
				if (graveEntity instanceof MausoleumGraveEntity){
					if (getMinMausoleum() > 0){
						for (int x = 0; x <= getMinMausoleum() - 1; ++x){
							graveEntities.add(PvZEntity.MAUSOLEUMGRAVESTONE);
						}
					}
				}
			}
			if (this.getWaveTicks() >= waveInterval){
				this.setWaveticks(0);
				this.addGravesSpawned();
				if (this.getTierCount() >= 3){
					if (graveEntities.contains(PvZEntity.NIGHTGRAVESTONE)) {
						double bassRandom = this.random.nextDouble();
						double nightChance = 0;
						double locationRandom = this.random.nextDouble();
						for (EntityType<?> entityType : graveEntities){
							if (entityType.equals(PvZEntity.NIGHTGRAVESTONE)){
								nightChance = nightChance + 0.125;
							}
						}
						for (int x = 0; x <= this.getWaveCount(); ++x){
							nightChance = nightChance + 0.05;
						}
						for (int x = 0; x <= this.getTierCount() - 3; ++x){
							nightChance = nightChance + 0.05;
						}
						if (bassRandom <= 0.105 + nightChance) {
							BlockPos getPos;
							if (locationRandom <= 0.33){
								getPos = rift1Spots.get(this.random.range(0, rift1Spots.size() -1));
							}
							else if (locationRandom <= 0.83){
								getPos = rift2Spots.get(this.random.range(0, rift2Spots.size() -1));
							}
							else {
								getPos = rift3Spots.get(this.random.range(0, rift3Spots.size() -1));
							}
							if (this.getWorld() instanceof ServerWorld serverWorld) {
								RiftTile riftTile = (RiftTile) PvZEntity.RIFTTILE.create(this.getWorld());
								riftTile.refreshPositionAndAngles(getPos, 0.0F, 0.0F);
								riftTile.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								riftTile.setVariant(RiftVariants.BASS);
								serverWorld.spawnEntityAndPassengers(riftTile);
							}
						}
					}
					if (graveEntities.contains(PvZEntity.EGYPTGRAVESTONE)) {
						double gargolithRandom = this.random.nextDouble();
						double locationRandom = this.random.nextDouble();
						double egyptChance = 0;
						for (EntityType<?> entityType : graveEntities){
							if (entityType.equals(PvZEntity.EGYPTGRAVESTONE)){
								egyptChance = egyptChance + 0.075;
							}
						}
						for (int x = 0; x <= this.getWaveCount(); ++x){
							gargolithRandom = gargolithRandom + 0.05;
						}
						for (int x = 0; x <= this.getTierCount() - 3; ++x){
							gargolithRandom = gargolithRandom + 0.05;
						}
						if (gargolithRandom <= 0.075 + egyptChance) {
							BlockPos getPos;
							if (locationRandom <= 0.5){
								getPos = rift1Spots.get(this.random.range(0, rift1Spots.size() -1));
							}
							else{
								getPos = rift2Spots.get(this.random.range(0, rift2Spots.size() -1));
							}
							if (this.getWorld() instanceof ServerWorld serverWorld) {
								RiftTile riftTile = (RiftTile) PvZEntity.RIFTTILE.create(this.getWorld());
								riftTile.refreshPositionAndAngles(getPos, 0.0F, 0.0F);
								riftTile.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								riftTile.setVariant(RiftVariants.GARGOLITH);
								serverWorld.spawnEntityAndPassengers(riftTile);
							}
						}
					}
				}
				if (graveEntities.contains(PvZEntity.EGYPTGRAVESTONE)) {
					for (int x = 0; x <= Math.min(this.getWaveCount() + this.getTierCount() / 2, 6); ++x){
						BlockPos getPos = egyptSpots.get(this.random.range(0, egyptSpots.size() -1));
						if (this.getWorld() instanceof ServerWorld serverWorld) {
							RockObstacleEntity tombstone = (RockObstacleEntity) PvZEntity.EGYPTTOMBSTONE.create(this.getWorld());
							tombstone.refreshPositionAndAngles(getPos, 0.0F, 0.0F);
							tombstone.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							serverWorld.spawnEntityAndPassengers(tombstone);
						}
					}
				}
				if (this.getWorld() instanceof ServerWorld serverWorld && this.currentWeather != null && this.currentWeather.getWeather().equals(ChallengeWeather.THUNDER)) {
					if (this.currentWeather.getWeather().equals(ChallengeWeather.RAIN)) {
						for (int x = 0; x <= 2; ++x) {
							BlockPos waterPos = waterSpots.get(this.random.range(0, waterSpots.size() - 1));
							WaterTile waterTile = (WaterTile) PvZEntity.WATERTILE.create(this.getWorld());
							waterTile.refreshPositionAndAngles(waterPos, 0.0F, 0.0F);
							waterTile.initialize(serverWorld, this.getWorld().getLocalDifficulty(waterPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							waterTile.setPersistent();
							serverWorld.spawnEntityAndPassengers(waterTile);
						}
					}
					if (this.currentWeather.getWeather().equals(ChallengeWeather.THUNDER)) {
						for (int x = 0; x <= 3; ++x) {
							BlockPos waterPos = waterSpots.get(this.random.range(0, waterSpots.size() - 1));
							WaterTile waterTile = (WaterTile) PvZEntity.WATERTILE.create(this.getWorld());
							waterTile.refreshPositionAndAngles(waterPos, 0.0F, 0.0F);
							waterTile.initialize(serverWorld, this.getWorld().getLocalDifficulty(waterPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							waterTile.setPersistent();
							serverWorld.spawnEntityAndPassengers(waterTile);
						}
					}
				}
				if (this.getWorld() instanceof ServerWorld serverWorld && this.currentTime != null && this.currentTime.getTime().equals(ChallengeTime.BOMB)) {
					for (int x = 0; x <= 3; ++x) {
						BlockPos waterPos = waterSpots.get(this.random.range(0, waterSpots.size() - 1));
						BlockPos sunPos = waterPos.add(0, 3, 0);
						SunBombEntity sunBomb = (SunBombEntity) PvZEntity.SUNBOMB.create(this.getWorld());
						sunBomb.refreshPositionAndAngles(sunPos, 0.0F, 0.0F);
						sunBomb.initialize(serverWorld, this.getWorld().getLocalDifficulty(sunPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						sunBomb.setPersistent();
						serverWorld.spawnEntityAndPassengers(sunBomb);
					}
					BlockPos getPos = spawnableSpots.get(this.random.range(0, spawnableSpots.size() -1));
					FutureGraveEntity graveEntity = (FutureGraveEntity) PvZEntity.FUTUREGRAVESTONE.create(this.getWorld());
					graveEntity.refreshPositionAndAngles(getPos, 0.0F, 0.0F);
					graveEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					if (this.getTierCount() >= 3){
						graveEntity.setVariant(GraveDifficulty.HARD);
						graveEntity.setUnlockSpecial(GraveEntity.UnlockSpecial.TRUE);
						graveEntity.defenseMultiplier = 1;
						for (int x = 0; x < this.getTierCount() - 3; ++x){
							graveEntity.defenseMultiplier = graveEntity.defenseMultiplier - 0.05f;
						}
					}
					else {
						graveEntity.setVariant(GraveDifficulty.MED);
					}
					graveEntity.setChallenge(GraveEntity.Challenge.TRUE);
					graveEntity.setPersistent();
					serverWorld.spawnEntityAndPassengers(graveEntity);
				}
				BlockPos getPos = spawnableSpots.get(this.random.range(0, spawnableSpots.size() -1));
				if (this.getWorld() instanceof ServerWorld serverWorld) {
					GraveEntity graveEntity = (GraveEntity) PvZEntity.BASICGRAVESTONE.create(this.getWorld());
					graveEntity.refreshPositionAndAngles(getPos, 0.0F, 0.0F);
					graveEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					if (this.getTierCount() >= 3){
						graveEntity.setVariant(GraveDifficulty.HARD);
						graveEntity.setUnlockSpecial(GraveEntity.UnlockSpecial.TRUE);
						graveEntity.defenseMultiplier = 1;
						for (int x = 0; x < this.getTierCount() - 3; ++x){
							graveEntity.defenseMultiplier = graveEntity.defenseMultiplier - 0.05f;
						}
					}
					else {
						graveEntity.setVariant(GraveDifficulty.MED);
					}
					graveEntity.setChallenge(GraveEntity.Challenge.TRUE);
					graveEntity.setPersistent();
					serverWorld.spawnEntityAndPassengers(graveEntity);
					if (!graveEntities.isEmpty()) {
						if (graveEntities.contains(PvZEntity.POOLGRAVESTONE)) {
							BlockPos getPos2 = spawnableSpots.get(this.random.range(0, spawnableSpots.size() - 1));
							GraveEntity graveEntity2 = (GraveEntity) PvZEntity.POOLGRAVESTONE.create(this.getWorld());
							graveEntity2.refreshPositionAndAngles(getPos2, 0.0F, 0.0F);
							graveEntity2.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							if (this.getTierCount() >= 3){
								graveEntity2.setVariant(GraveDifficulty.MEDHARD);
								graveEntity2.defenseMultiplier = 1;
								for (int x = 0; x < this.getTierCount() - 3; ++x){
									graveEntity2.defenseMultiplier = graveEntity2.defenseMultiplier - 0.05f;
								}
							}
							else {
								graveEntity2.setVariant(GraveDifficulty.EASYMED);
							}
							graveEntity2.setChallenge(GraveEntity.Challenge.TRUE);
							graveEntity2.setPersistent();
							serverWorld.spawnEntityAndPassengers(graveEntity2);
							this.setMinpool(this.getMinPool() - 1);
						}
						EntityType<?> entityType = graveEntities.get(random.range(0, graveEntities.size() - 1));
						BlockPos getPos2 = spawnableSpots.get(this.random.range(0, spawnableSpots.size() - 1));
						GraveEntity graveEntity2 = (GraveEntity) entityType.create(this.getWorld());
						graveEntity2.refreshPositionAndAngles(getPos2, 0.0F, 0.0F);
						graveEntity2.initialize(serverWorld, this.getWorld().getLocalDifficulty(getPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						if (this.getTierCount() >= 3){
							graveEntity2.setVariant(GraveDifficulty.HARD);
							graveEntity2.setUnlockSpecial(GraveEntity.UnlockSpecial.TRUE);
							graveEntity2.defenseMultiplier = 1;
							for (int x = 0; x < this.getTierCount() - 3; ++x){
								graveEntity2.defenseMultiplier = graveEntity2.defenseMultiplier - 0.075f;
							}
						}
						else {
							graveEntity2.setVariant(GraveDifficulty.MED);
						}
						graveEntity2.setChallenge(GraveEntity.Challenge.TRUE);
						graveEntity2.setPersistent();
						serverWorld.spawnEntityAndPassengers(graveEntity2);
						if (entityType == PvZEntity.NIGHTGRAVESTONE) {
							this.setMinnight(this.getMinNight() - 1);
						} else if (entityType == PvZEntity.POOLGRAVESTONE) {
							this.setMinpool(this.getMinPool() - 1);
						} else if (entityType == PvZEntity.ROOFGRAVESTONE) {
							this.setMinroof(this.getMinRoof() - 1);
						} else if (entityType == PvZEntity.EGYPTGRAVESTONE) {
							this.setMinegypt(this.getMinEgypt() - 1);
						} else if (entityType == PvZEntity.DARKAGESGRAVESTONE) {
							this.setMindarkages(this.getMinDarkAges() - 1);
						} else if (entityType == PvZEntity.FUTUREGRAVESTONE) {
							this.setMinfuture(this.getMinFuture() - 1);
						} else if (entityType == PvZEntity.FAIRYTALEGRAVESTONE) {
							this.setMinfairytale(this.getMinFairyTale() - 1);
						} else if (entityType == PvZEntity.MAUSOLEUMGRAVESTONE) {
							this.setMinmausoleum(this.getMinMausoleum() - 1);
						}
					}
				}
			}
		}
		/**
		if (this.getWaveInProgress() && this.getWaveTicks() >= 60){
			this.setWaveinprogress(WaveInProgress.FALSE);
		}
		if (this.getWaveInProgress() && this.getWorld() instanceof ServerWorld serverWorld){
			if (getWaveTicks() == 20 && this.getTier().equals(ChallengeTiers.ONE)){
				GraveEntity graveEntity = (GraveEntity) PvZEntity.BASICGRAVESTONE.create(this.getWorld());
				BlockPos blockPos = new BlockPos(this.getBlockPos().getX() + 10, this.getBlockPos().getY(), this.getBlockPos().getZ() + 10);
				graveEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				graveEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				serverWorld.spawnEntityAndPassengers(graveEntity);
			}
			if (getWaveTicks() == 20 && this.getTier().equals(ChallengeTiers.TWO)){
				GraveEntity graveEntity = (GraveEntity) PvZEntity.NIGHTGRAVESTONE.create(this.getWorld());
				BlockPos blockPos = new BlockPos(this.getBlockPos().getX() + 10, this.getBlockPos().getY(), this.getBlockPos().getZ() + 10);
				graveEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				graveEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				serverWorld.spawnEntityAndPassengers(graveEntity);
			}
		}**/
	}

	protected EntityType<?> nextGrave = null;

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.BASICGRAVESPAWN)){
			nextGrave = PvZEntity.BASICGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.NIGHTGRAVESPAWN)){
			nextGrave = PvZEntity.NIGHTGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.POOLGRAVESPAWN)){
			nextGrave = PvZEntity.POOLGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.ROOFGRAVESPAWN)){
			nextGrave = PvZEntity.ROOFGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.EGYPTGRAVESPAWN)){
			nextGrave = PvZEntity.EGYPTGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.DARKAGESGRAVESPAWN)){
			nextGrave = PvZEntity.DARKAGESGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.FUTUREGRAVESPAWN)){
			nextGrave = PvZEntity.FUTUREGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.FAIRYTALEGRAVESPAWN)){
			nextGrave = PvZEntity.FAIRYTALEGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.MAUSOLEUMGRAVESPAWN)){
			nextGrave = PvZEntity.MAUSOLEUMGRAVESTONE;
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.DAY) && currentTime != null && currentWeather != null){
			currentTime.setTimeType(ChallengeTime.DAY);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.NIGHT) && currentTime != null && currentWeather != null){
			if (this.getWorld().getMoonPhase() == 1) {
				currentTime.setTimeType(ChallengeTime.NEWMOON);
			} else if (this.getWorld().getMoonPhase() == 0) {
				currentTime.setTimeType(ChallengeTime.FULLMOON);
			} else {
				currentTime.setTimeType(ChallengeTime.HALFMOON);
			}
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.BOMB) && currentTime != null && currentWeather != null){
			currentTime.setTimeType(ChallengeTime.BOMB);
			currentWeather.setWeatherType(ChallengeWeather.CLOUD);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.DROUGHT) && currentTime != null && currentWeather != null){
			currentTime.setTimeType(ChallengeTime.DROUGHT);
			currentWeather.setWeatherType(ChallengeWeather.CLOUD);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.CLEAR) && currentTime != null && currentWeather != null){
			currentWeather.setWeatherType(ChallengeWeather.CLOUD);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.RAIN) && currentTime != null && currentWeather != null){
			currentWeather.setWeatherType(ChallengeWeather.RAIN);
			if (currentTime.getTime().equals(ChallengeTime.DROUGHT)){
				currentTime.setTimeType(ChallengeTime.DAY);
			}
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.THUNDER) && currentTime != null){
			currentWeather.setWeatherType(ChallengeWeather.THUNDER);
			if (currentTime.getTime().equals(ChallengeTime.DROUGHT)){
				currentTime.setTimeType(ChallengeTime.DAY);
			}
			return ActionResult.SUCCESS;
		}
		else if (currentWorlds.get(0)== null) {
			this.addWorld(PvZEntity.BASICGRAVESTONE);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.FERTILIZER) && this.getWaveInProgress().equals(Boolean.FALSE)) {
			this.addWave();
			itemStack.decrement(1);
			return ActionResult.SUCCESS;
		}
		else if (cooldown <= 0 && this.getWaveInProgress().equals(Boolean.FALSE)) {
			this.cooldown = 10;
			this.addWave();
			this.setWaveinprogress(WaveInProgress.TRUE);
			if (currentTime != null && this.getWorld() instanceof ServerWorld serverWorld){
				if ((currentTime.getTime().equals(ChallengeTime.FULLMOON) || currentTime.getTime().equals(ChallengeTime.NEWMOON) || currentTime.getTime().equals(ChallengeTime.HALFMOON)) &&
						serverWorld.isDay()){
					long l = getWorld().getTimeOfDay() + 24000L;
					serverWorld.setTimeOfDay((l - l % 24000L) + 18000L);
					if (this.getWorld().getMoonPhase() == 1) {
						currentTime.setTimeType(ChallengeTime.NEWMOON);
					} else if (this.getWorld().getMoonPhase() == 0) {
						currentTime.setTimeType(ChallengeTime.FULLMOON);
					} else {
						currentTime.setTimeType(ChallengeTime.HALFMOON);
					}

				}
				if ((currentTime.getTime().equals(ChallengeTime.DAY) || currentTime.getTime().equals(ChallengeTime.DROUGHT) || currentTime.getTime().equals(ChallengeTime.BOMB)) &&
						serverWorld.isNight()){
					long l = getWorld().getTimeOfDay() + 24000L;
					serverWorld.setTimeOfDay(l - l % 24000L);
				}
			}
			if (currentWeather != null && this.getWorld() instanceof ServerWorld serverWorld){
				if (currentWeather.getWeather().equals(ChallengeWeather.CLOUD)) {
					serverWorld.setWeather(0, 0, false, false);
				}
				if (currentWeather.getWeather().equals(ChallengeWeather.RAIN)){
					serverWorld.setWeather(0, 0, true, false);
				}
				if (currentWeather.getWeather().equals(ChallengeWeather.THUNDER)){
					serverWorld.setWeather(0, 0, true, true);
				}
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	public void addWorld(EntityType<?> entityType){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			GraveEntity graveEntity = (GraveEntity) entityType.create(this.getWorld());
			BlockPos blockPos = this.getBlockPos();
			boolean bl = true;
			if (currentWorlds.get(0) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
				this.setWorld1(addedWorld);
			}
			else if (currentWorlds.get(1) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ());
				this.setWorld2(addedWorld);
			}
			else if (currentWorlds.get(2) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
				this.setWorld3(addedWorld);
			}
			else if (currentWorlds.get(3) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
				this.setWorld4(addedWorld);
			}
			else if (currentWorlds.get(4) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
				this.setWorld5(addedWorld);
			}
			else if (currentWorlds.get(5) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ());
				this.setWorld6(addedWorld);
			}
			else if (currentWorlds.get(6) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
				this.setWorld7(addedWorld);
			}
			else if (currentWorlds.get(7) == null) {
				blockPos = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
				this.setWorld8(addedWorld);
			}
			else {
				bl = false;
			}
			if (bl) {
				graveEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				graveEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				graveEntity.setAiDisabled(true);
				graveEntity.decorative = true;
				serverWorld.spawnEntityAndPassengers(graveEntity);
			}
		}
	}

	protected void levelField(){
		Block world2Block = ModBlocks.GRASS_TILE;
		Block world2BlockDark = ModBlocks.DARK_GRASS_TILE;
		Block world3Block = ModBlocks.GRASS_TILE;
		Block world3BlockDark = ModBlocks.DARK_GRASS_TILE;
		Block world4Block = ModBlocks.GRASS_TILE;
		Block world4BlockDark = ModBlocks.DARK_GRASS_TILE;
		Block world5Block = ModBlocks.GRASS_TILE;
		Block world5BlockDark = ModBlocks.DARK_GRASS_TILE;
		Block world6Block = ModBlocks.GRASS_TILE;
		Block world6BlockDark = ModBlocks.DARK_GRASS_TILE;
		Block world7Block = ModBlocks.GRASS_TILE;
		Block world7BlockDark = ModBlocks.DARK_GRASS_TILE;
		Block world8Block = ModBlocks.GRASS_TILE;
		Block world8BlockDark = ModBlocks.DARK_GRASS_TILE;


		if (this.getWorld2().equals(TypeOfWorld.ROOF)){
			world2Block = ModBlocks.ROOF_TILE;
			world2BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld2().equals(TypeOfWorld.EGYPT)){
			world2Block = ModBlocks.EGYPT_TILE;
			world2BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld2().equals(TypeOfWorld.DARKAGES)){
			world2Block = ModBlocks.DARKAGES_TILE;
			world2BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld2().equals(TypeOfWorld.NIGHT)){
			world2Block = ModBlocks.NIGHT_TILE;
			world2BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld2().equals(TypeOfWorld.FUTURE)){
			world2Block = ModBlocks.FUTURE_TILE;
			world2BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld2().equals(TypeOfWorld.FAIRYTALE)){
			world2Block = ModBlocks.FAIRY_TILE;
			world2BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld2().equals(TypeOfWorld.MAUSOLEUM)){
			world2Block = ModBlocks.MAUSOLEUM_TILE;
			world2BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}


		if (this.getWorld3().equals(TypeOfWorld.ROOF)){
			world3Block = ModBlocks.ROOF_TILE;
			world3BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld3().equals(TypeOfWorld.EGYPT)){
			world3Block = ModBlocks.EGYPT_TILE;
			world3BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld3().equals(TypeOfWorld.DARKAGES)){
			world3Block = ModBlocks.DARKAGES_TILE;
			world3BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld3().equals(TypeOfWorld.NIGHT)){
			world3Block = ModBlocks.NIGHT_TILE;
			world3BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld3().equals(TypeOfWorld.FUTURE)){
			world3Block = ModBlocks.FUTURE_TILE;
			world3BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld3().equals(TypeOfWorld.FAIRYTALE)){
			world3Block = ModBlocks.FAIRY_TILE;
			world3BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld3().equals(TypeOfWorld.MAUSOLEUM)){
			world3Block = ModBlocks.MAUSOLEUM_TILE;
			world3BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}


		if (this.getWorld4().equals(TypeOfWorld.ROOF)){
			world4Block = ModBlocks.ROOF_TILE;
			world4BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld4().equals(TypeOfWorld.EGYPT)){
			world4Block = ModBlocks.EGYPT_TILE;
			world4BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld4().equals(TypeOfWorld.DARKAGES)){
			world4Block = ModBlocks.DARKAGES_TILE;
			world4BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld4().equals(TypeOfWorld.NIGHT)){
			world4Block = ModBlocks.NIGHT_TILE;
			world4BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld4().equals(TypeOfWorld.FUTURE)){
			world4Block = ModBlocks.FUTURE_TILE;
			world4BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld4().equals(TypeOfWorld.FAIRYTALE)){
			world4Block = ModBlocks.FAIRY_TILE;
			world4BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld4().equals(TypeOfWorld.MAUSOLEUM)){
			world4Block = ModBlocks.MAUSOLEUM_TILE;
			world4BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}


		if (this.getWorld5().equals(TypeOfWorld.ROOF)){
			world5Block = ModBlocks.ROOF_TILE;
			world5BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld5().equals(TypeOfWorld.EGYPT)){
			world5Block = ModBlocks.EGYPT_TILE;
			world5BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld5().equals(TypeOfWorld.DARKAGES)){
			world5Block = ModBlocks.DARKAGES_TILE;
			world5BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld5().equals(TypeOfWorld.NIGHT)){
			world5Block = ModBlocks.NIGHT_TILE;
			world5BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld5().equals(TypeOfWorld.FUTURE)){
			world5Block = ModBlocks.FUTURE_TILE;
			world5BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld5().equals(TypeOfWorld.FAIRYTALE)){
			world5Block = ModBlocks.FAIRY_TILE;
			world5BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld5().equals(TypeOfWorld.MAUSOLEUM)){
			world5Block = ModBlocks.MAUSOLEUM_TILE;
			world5BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}


		if (this.getWorld6().equals(TypeOfWorld.ROOF)){
			world6Block = ModBlocks.ROOF_TILE;
			world6BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld6().equals(TypeOfWorld.EGYPT)){
			world6Block = ModBlocks.EGYPT_TILE;
			world6BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld6().equals(TypeOfWorld.DARKAGES)){
			world6Block = ModBlocks.DARKAGES_TILE;
			world6BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld6().equals(TypeOfWorld.NIGHT)){
			world6Block = ModBlocks.NIGHT_TILE;
			world6BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld6().equals(TypeOfWorld.FUTURE)){
			world6Block = ModBlocks.FUTURE_TILE;
			world6BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld6().equals(TypeOfWorld.FAIRYTALE)){
			world6Block = ModBlocks.FAIRY_TILE;
			world6BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld6().equals(TypeOfWorld.MAUSOLEUM)){
			world6Block = ModBlocks.MAUSOLEUM_TILE;
			world6BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}


		if (this.getWorld7().equals(TypeOfWorld.ROOF)){
			world7Block = ModBlocks.ROOF_TILE;
			world7BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld7().equals(TypeOfWorld.EGYPT)){
			world7Block = ModBlocks.EGYPT_TILE;
			world7BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld7().equals(TypeOfWorld.DARKAGES)){
			world7Block = ModBlocks.DARKAGES_TILE;
			world7BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld7().equals(TypeOfWorld.NIGHT)){
			world7Block = ModBlocks.NIGHT_TILE;
			world7BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld7().equals(TypeOfWorld.FUTURE)){
			world7Block = ModBlocks.FUTURE_TILE;
			world7BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld7().equals(TypeOfWorld.FAIRYTALE)){
			world7Block = ModBlocks.FAIRY_TILE;
			world7BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld7().equals(TypeOfWorld.MAUSOLEUM)){
			world7Block = ModBlocks.MAUSOLEUM_TILE;
			world7BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}


		if (this.getWorld8().equals(TypeOfWorld.ROOF)){
			world8Block = ModBlocks.ROOF_TILE;
			world8BlockDark = ModBlocks.DARK_ROOF_TILE;
		}
		if (this.getWorld8().equals(TypeOfWorld.EGYPT)){
			world8Block = ModBlocks.EGYPT_TILE;
			world8BlockDark = ModBlocks.DARK_EGYPT_TILE;
		}
		if (this.getWorld8().equals(TypeOfWorld.DARKAGES)){
			world8Block = ModBlocks.DARKAGES_TILE;
			world8BlockDark = ModBlocks.DARK_DARKAGES_TILE;
		}
		if (this.getWorld8().equals(TypeOfWorld.NIGHT)){
			world8Block = ModBlocks.NIGHT_TILE;
			world8BlockDark = ModBlocks.DARK_NIGHT_TILE;
		}
		if (this.getWorld8().equals(TypeOfWorld.FUTURE)){
			world8Block = ModBlocks.FUTURE_TILE;
			world8BlockDark = ModBlocks.DARK_FUTURE_TILE;
		}
		if (this.getWorld8().equals(TypeOfWorld.FAIRYTALE)){
			world8Block = ModBlocks.FAIRY_TILE;
			world8BlockDark = ModBlocks.DARK_FAIRY_TILE;
		}
		if (this.getWorld8().equals(TypeOfWorld.MAUSOLEUM)){
			world8Block = ModBlocks.MAUSOLEUM_TILE;
			world8BlockDark = ModBlocks.DARK_MAUSOLEUM_TILE;
		}

		for (int x = -26; x < 27; ++x) {
			int l = MathHelper.floor(this.getPos().x + x);
			int m = MathHelper.floor(this.getPos().y);
			int n = MathHelper.floor(this.getPos().z);
			BlockPos blockPos2 = new BlockPos(l, m, n);
			BlockPos blockPos3 = new BlockPos(l, m + 1, n);
			BlockPos blockPos4 = new BlockPos(l, m + 2, n);
			BlockPos blockPos5 = new BlockPos(l, m + 3, n);
			BlockPos blockPos6 = new BlockPos(l, m + 4, n);
			BlockPos blockPos22 = new BlockPos(l, m - 1, n);
			for (int z = -26; z < 27; ++z) {
				BlockPos blockPos7 = new BlockPos(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ() + z);
				BlockPos blockPos8 = new BlockPos(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ() + z);
				BlockPos blockPos9 = new BlockPos(blockPos4.getX(), blockPos4.getY(), blockPos4.getZ() + z);
				BlockPos blockPos10 = new BlockPos(blockPos5.getX(), blockPos5.getY(), blockPos5.getZ() + z);
				BlockPos blockPos11 = new BlockPos(blockPos6.getX(), blockPos6.getY(), blockPos6.getZ() + z);
				BlockPos blockPos72 = new BlockPos(blockPos22.getX(), blockPos22.getY(), blockPos22.getZ() + z);
				BlockPos blockPos73 = new BlockPos(blockPos22.getX(), blockPos22.getY() - 1, blockPos22.getZ() + z);
				BlockPos blockPos74 = new BlockPos(blockPos22.getX(), blockPos22.getY() + 1, blockPos22.getZ() + z);
				if (!this.getWorld().getBlockState(blockPos7).isAir() ||
						!this.getWorld().getBlockState(blockPos8).isAir() ||
						!this.getWorld().getBlockState(blockPos9).isAir() ||
						!this.getWorld().getBlockState(blockPos10).isAir() ||
						!this.getWorld().getBlockState(blockPos11).isAir()) {
					this.removeBlock(blockPos7, false);
					this.removeBlock(blockPos8, false);
					this.removeBlock(blockPos9, false);
					this.removeBlock(blockPos10, false);
					this.removeBlock(blockPos11, false);
				}
				if (((z & 1) == 0)) {
					if ((x & 1) == 0) {
						if (!this.getWorld().getBlockState(blockPos72).equals(ModBlocks.GRASS_TILE.getDefaultState())) {
							this.getWorld().setBlockState(blockPos72, ModBlocks.GRASS_TILE.getDefaultState());
							this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
						}
					} else {
						if (!this.getWorld().getBlockState(blockPos72).equals(ModBlocks.DARK_GRASS_TILE.getDefaultState())) {
							this.getWorld().setBlockState(blockPos72, ModBlocks.DARK_GRASS_TILE.getDefaultState());
							this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
						}
						if ((x == 7 || x == -7) && z >= -7 && z <= 7){
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.GREEN_WOOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.GREEN_WOOL.getDefaultState());
								this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
							}
						}
					}
				} else {
					if ((x & 1) == 0) {
						if (!this.getWorld().getBlockState(blockPos72).equals(ModBlocks.DARK_GRASS_TILE.getDefaultState())) {
							this.getWorld().setBlockState(blockPos72, ModBlocks.DARK_GRASS_TILE.getDefaultState());
							this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
						}
						if ((z == 7 || z == -7) && x >= -7 && x <= 7){
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.GREEN_WOOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.GREEN_WOOL.getDefaultState());
								this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
							}
						}
					} else {
						if (!this.getWorld().getBlockState(blockPos72).equals(ModBlocks.GRASS_TILE.getDefaultState())) {
							this.getWorld().setBlockState(blockPos72, ModBlocks.GRASS_TILE.getDefaultState());
							this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
						}
						if ((x == 7 || x == -7) && z >= -7 && z <= 7){
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.LIME_WOOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.LIME_WOOL.getDefaultState());
								this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
							}
						}
						if ((z == 7 || z == -7) && x >= -7 && x <= 7){
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.LIME_WOOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.LIME_WOOL.getDefaultState());
								this.getWorld().setBlockState(blockPos73, Blocks.DIRT.getDefaultState());
							}
						}
					}
				}
				if (!this.getWorld2().equals(TypeOfWorld.BASIC)) {
					if (x >= 23) {
						if (!world2Favorable.contains(blockPos74)) {
							world2Favorable.add(blockPos74);
						}
					}
				}
				if (!this.getWorld3().equals(TypeOfWorld.BASIC)) {
					if (x >= 23 || z <= -23) {
						if (!world3Favorable.contains(blockPos74)) {
							world3Favorable.add(blockPos74);
						}
					}
				}
				if (!this.getWorld4().equals(TypeOfWorld.BASIC)) {
					if (z <= -23) {
						if (!world4Favorable.contains(blockPos74)) {
							world4Favorable.add(blockPos74);
						}
					}
				}
				if (!this.getWorld5().equals(TypeOfWorld.BASIC)) {
					if (z <= -23 || x <= -23) {
						if (!world5Favorable.contains(blockPos74)) {
							world5Favorable.add(blockPos74);
						}
					}
				}
				if (!this.getWorld6().equals(TypeOfWorld.BASIC)) {
					if (x <= -23) {
						if (!world6Favorable.contains(blockPos74)) {
							world6Favorable.add(blockPos74);
						}
					}
				}
				if (!this.getWorld7().equals(TypeOfWorld.BASIC)) {
					if (x <= -23 || z >= 23) {
						if (!world7Favorable.contains(blockPos74)) {
							world7Favorable.add(blockPos74);
						}
					}
				}
				if (!this.getWorld8().equals(TypeOfWorld.BASIC)) {
					if (z >= 23) {
						if (!world8Favorable.contains(blockPos74)) {
							world8Favorable.add(blockPos74);
						}
					}
				}
				if ((x > -23 && x <= -8) || (x < 23 && x >= 8)) {
					if (((z & 1) == 0)) {
						if ((x & 1) == 0) {
							if (!this.getWorld2().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world2Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world2Block.getDefaultState());
									}
									if (this.getWorld2().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld2().equals(TypeOfWorld.POOL)){
									if ((x >= 8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld3().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world3Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world3Block.getDefaultState());
									}
									if (this.getWorld3().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
									if (this.getWorld3().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										this.egyptSpots.add(testPos);
									}
								}
								if (this.getWorld3().equals(TypeOfWorld.POOL)){
									if ((x >= 9) && z <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld5().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world5Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world5Block.getDefaultState());
									}
									if (this.getWorld5().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld5().equals(TypeOfWorld.POOL)){
									if ((z <= -9) && x <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld6().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world6Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world6Block.getDefaultState());
									}
									if (this.getWorld6().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld6().equals(TypeOfWorld.POOL)){
									if ((x <= -8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld7().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z >= 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world7Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world7Block.getDefaultState());
									}
									if (this.getWorld7().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld7().equals(TypeOfWorld.POOL)){
									if ((x <= -9) && z >= 9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						} else {
							if (!this.getWorld2().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world2BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world2BlockDark.getDefaultState());
									}
									if (this.getWorld2().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld2().equals(TypeOfWorld.POOL)){
									if ((x >= 8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld3().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world3BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world3BlockDark.getDefaultState());
									}
									if (this.getWorld3().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld3().equals(TypeOfWorld.POOL)){
									if ((x >= 9) && z <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld5().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world5BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world5BlockDark.getDefaultState());
									}
									if (this.getWorld5().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld5().equals(TypeOfWorld.POOL)){
									if ((z <= -9) && x <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld6().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world6BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world6BlockDark.getDefaultState());
									}
									if (this.getWorld6().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld6().equals(TypeOfWorld.POOL)){
									if ((x <= -8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld7().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z >= 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world7BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world7BlockDark.getDefaultState());
									}
									if (this.getWorld7().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld7().equals(TypeOfWorld.POOL)){
									if ((x <= -9) && z >= 9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						}
					} else {
						if ((x & 1) == 0) {
							if (!this.getWorld2().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world2BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world2BlockDark.getDefaultState());
									}
									if (this.getWorld2().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld2().equals(TypeOfWorld.POOL)){
									if ((x >= 8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld3().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world3BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world3BlockDark.getDefaultState());
									}
									if (this.getWorld3().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld3().equals(TypeOfWorld.POOL)){
									if ((x >= 9) && z <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld5().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world5BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world5BlockDark.getDefaultState());
									}
									if (this.getWorld5().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld5().equals(TypeOfWorld.POOL)){
									if ((z <= -9) && x <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld6().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world6BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world6BlockDark.getDefaultState());
									}
									if (this.getWorld6().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld6().equals(TypeOfWorld.POOL)){
									if ((x <= -8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld7().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z >= 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world7BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world7BlockDark.getDefaultState());
									}
									if (this.getWorld7().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld7().equals(TypeOfWorld.POOL)){
									if ((x <= -9) && z >= 9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						} else {
							if (!this.getWorld2().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world2Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world2Block.getDefaultState());
									}
									if (this.getWorld2().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld2().equals(TypeOfWorld.POOL)){
									if ((x >= 8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld3().equals(TypeOfWorld.BASIC)) {
								if ((x >= 8) && z <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world3Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world3Block.getDefaultState());
									}
									if (this.getWorld3().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld3().equals(TypeOfWorld.POOL)){
									if ((x >= 9) && z <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld5().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x <= -8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world5Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world5Block.getDefaultState());
									}
									if (this.getWorld5().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld5().equals(TypeOfWorld.POOL)){
									if ((z <= -9) && x <= -9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld6().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z > -8 && z < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world6Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world6Block.getDefaultState());
									}
									if (this.getWorld6().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld6().equals(TypeOfWorld.POOL)){
									if ((x <= -8) && z > -3 && z < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld7().equals(TypeOfWorld.BASIC)) {
								if ((x <= -8) && z >= 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world7Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world7Block.getDefaultState());
									}
									if (this.getWorld7().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld7().equals(TypeOfWorld.POOL)){
									if ((x <= -9) && z >= 9) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						}
					}
				}
				else if ((z > -23 && z <= -8) || (z < 23 && z >= 8)) {
					if (((z & 1) == 0)) {
						if ((x & 1) == 0) {
							if (!this.getWorld4().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world4Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world4Block.getDefaultState());
									}
									if (this.getWorld4().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld4().equals(TypeOfWorld.POOL)){
									if ((z <= -8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld8().equals(TypeOfWorld.BASIC)) {
								if ((z >= 8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world8Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world8Block.getDefaultState());
									}
									if (this.getWorld8().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld8().equals(TypeOfWorld.POOL)){
									if ((z >= 8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						} else {
							if (!this.getWorld4().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world4BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world4BlockDark.getDefaultState());
									}
									if (this.getWorld4().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld4().equals(TypeOfWorld.POOL)){
									if ((z <= -8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld8().equals(TypeOfWorld.BASIC)) {
								if ((z >= 8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world8BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world8BlockDark.getDefaultState());
									}
									if (this.getWorld8().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld8().equals(TypeOfWorld.POOL)){
									if ((z >= 8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						}
					} else {
						if ((x & 1) == 0) {
							if (!this.getWorld4().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world4BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world4BlockDark.getDefaultState());
									}
									if (this.getWorld4().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld4().equals(TypeOfWorld.POOL)){
									if ((z <= -8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld8().equals(TypeOfWorld.BASIC)) {
								if ((z >= 8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world8BlockDark.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world8BlockDark.getDefaultState());
									}
									if (this.getWorld8().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld8().equals(TypeOfWorld.POOL)){
									if ((z >= 8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.DARK_UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						} else {
							if (!this.getWorld4().equals(TypeOfWorld.BASIC)) {
								if ((z <= -8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world4Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world4Block.getDefaultState());
									}
									if (this.getWorld4().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld4().equals(TypeOfWorld.POOL)){
									if ((z <= -8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
							if (!this.getWorld8().equals(TypeOfWorld.BASIC)) {
								if ((z >= 8) && x > -8 && x < 8) {
									if (!this.getWorld().getBlockState(blockPos72).equals(world8Block.getDefaultState())) {
										this.getWorld().setBlockState(blockPos72, world8Block.getDefaultState());
									}
									if (this.getWorld8().equals(TypeOfWorld.EGYPT)){
										BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
										if (!egyptSpots.contains(testPos)) {
											this.egyptSpots.add(testPos);
										}
									}
								}
								if (this.getWorld8().equals(TypeOfWorld.POOL)){
									if ((z >= 8) && x > -3 && x < 3) {
										if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.WATER.getDefaultState())) {
											this.getWorld().setBlockState(blockPos72, Blocks.WATER.getDefaultState());
											this.getWorld().setBlockState(blockPos73, ModBlocks.UNDERWATER_TILE.getDefaultState());
										}
									}
								}
							}
						}
					}
				}
				if (x <= -23 || x >= 23) {
					if (((z & 1) == 0)) {
						if ((x & 1) == 0) {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.PODZOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.PODZOL.getDefaultState());
							}
						} else {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.COARSE_DIRT.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.COARSE_DIRT.getDefaultState());
							}
						}
					} else {
						if ((x & 1) == 0) {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.COARSE_DIRT.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.COARSE_DIRT.getDefaultState());
							}
						} else {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.PODZOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.PODZOL.getDefaultState());
							}
						}
					}
				}
				else if (z <= -23 || z >= 23) {
					if (((z & 1) == 0)) {
						if ((x & 1) == 0) {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.PODZOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.PODZOL.getDefaultState());
							}
						} else {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.COARSE_DIRT.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.COARSE_DIRT.getDefaultState());
							}
						}
					} else {
						if ((x & 1) == 0) {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.COARSE_DIRT.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.COARSE_DIRT.getDefaultState());
							}
						} else {
							if (!this.getWorld().getBlockState(blockPos72).equals(Blocks.PODZOL.getDefaultState())) {
								this.getWorld().setBlockState(blockPos72, Blocks.PODZOL.getDefaultState());
							}
						}
					}
				}
				if (x <= -23 || x >= 23 || z <= -23 || z >= 23) {
					BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
					if (!spawnableSpots.contains(testPos)) {
						spawnableSpots.add(testPos);
					}
				}
				if ((x <= -8 || x >= 8 || z <= -8 || z >= 8)  &&
						!(x <= -13 || x >= 13 || z <= -13 || z >= 13)) {
					BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
					if (!rift1Spots.contains(testPos)) {
						rift1Spots.add(testPos);
					}
					if (!waterSpots.contains(testPos)) {
						waterSpots.add(testPos);
					}
				}
				if ((x <= -13 || x >= 13 || z <= -13 || z >= 13)  &&
						!(x <= -18 || x >= 18 || z <= -18 || z >= 18)) {
					BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
					if (!rift2Spots.contains(testPos)) {
						rift2Spots.add(testPos);
					}
					if (!waterSpots.contains(testPos)) {
						waterSpots.add(testPos);
					}
				}
				if ((x <= -18 || x >= 18 || z <= -18 || z >= 18)  &&
						!(x <= -23 || x >= 23 || z <= -23 || z >= 23)) {
					BlockPos testPos = new BlockPos(blockPos72.getX(), blockPos72.getY() + 1, blockPos72.getZ());
					if (!rift3Spots.contains(testPos)) {
						rift3Spots.add(testPos);
					}
					if (!waterSpots.contains(testPos)) {
						waterSpots.add(testPos);
					}
				}
			}
			if (!this.getWorld().getBlockState(blockPos2).isAir() ||
					!this.getWorld().getBlockState(blockPos3).isAir() ||
					!this.getWorld().getBlockState(blockPos4).isAir() ||
					!this.getWorld().getBlockState(blockPos5).isAir() ||
					!this.getWorld().getBlockState(blockPos6).isAir()) {
				this.removeBlock(blockPos2, false);
				this.removeBlock(blockPos3, false);
				this.removeBlock(blockPos4, false);
				this.removeBlock(blockPos5, false);
				this.removeBlock(blockPos6, false);
			}
			BlockPos blockPos = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY() - 1, this.getBlockPos().getZ());
			if (!this.getWorld().getBlockState(blockPos).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPos, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr2 = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY() - 1, this.getBlockPos().getZ());
			if (!this.getWorld().getBlockState(blockPosr2).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr2, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr3 = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY() - 1, this.getBlockPos().getZ() + 1);
			if (!this.getWorld().getBlockState(blockPosr3).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr3, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr4 = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY() - 1, this.getBlockPos().getZ() + 1);
			if (!this.getWorld().getBlockState(blockPosr4).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr4, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr5 = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY() - 1, this.getBlockPos().getZ());
			if (!this.getWorld().getBlockState(blockPosr5).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr5, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr6 = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY() - 1, this.getBlockPos().getZ() - 1);
			if (!this.getWorld().getBlockState(blockPosr6).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr6, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr7 = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY() - 1, this.getBlockPos().getZ() - 1);
			if (!this.getWorld().getBlockState(blockPosr7).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr7, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr8 = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY() - 1, this.getBlockPos().getZ() - 1);
			if (!this.getWorld().getBlockState(blockPosr8).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr8, Blocks.RED_WOOL.getDefaultState());
			}
			BlockPos blockPosr9 = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY() - 1, this.getBlockPos().getZ() + 1);
			if (!this.getWorld().getBlockState(blockPosr9).equals(Blocks.RED_WOOL.getDefaultState())) {
				this.getWorld().setBlockState(blockPosr9, Blocks.RED_WOOL.getDefaultState());
			}

			BlockPos blockPosTop = new BlockPos(this.getBlockPos().getX() + 2, this.getBlockPos().getY() -1, this.getBlockPos().getZ() + 2);
			BlockPos blockPosTop2 = new BlockPos(this.getBlockPos().getX() + 2, this.getBlockPos().getY() -1, this.getBlockPos().getZ() - 2);
			BlockPos blockPosTop3 = new BlockPos(this.getBlockPos().getX() - 2, this.getBlockPos().getY() -1, this.getBlockPos().getZ() + 2);
			BlockPos blockPosTop4 = new BlockPos(this.getBlockPos().getX() - 2, this.getBlockPos().getY() -1, this.getBlockPos().getZ() - 2);
			if (!this.getWorld().getBlockState(blockPosTop).equals(Blocks.CRAFTING_TABLE.getDefaultState())) {
				this.getWorld().setBlockState(blockPosTop, Blocks.CRAFTING_TABLE.getDefaultState());
			}
			if (!this.getWorld().getBlockState(blockPosTop2).equals(Blocks.CRAFTING_TABLE.getDefaultState())) {
				this.getWorld().setBlockState(blockPosTop2, Blocks.CRAFTING_TABLE.getDefaultState());
			}
			if (!this.getWorld().getBlockState(blockPosTop3).equals(Blocks.CRAFTING_TABLE.getDefaultState())) {
				this.getWorld().setBlockState(blockPosTop3, Blocks.CRAFTING_TABLE.getDefaultState());
			}
			if (!this.getWorld().getBlockState(blockPosTop4).equals(Blocks.CRAFTING_TABLE.getDefaultState())) {
				this.getWorld().setBlockState(blockPosTop4, Blocks.CRAFTING_TABLE.getDefaultState());
			}
		}
	}

	public void removeBlock(BlockPos pos, boolean move) {
		FluidState fluidState = this.getWorld().getFluidState(pos);
		this.getWorld().setBlockState(pos, Fluids.EMPTY.getDefaultState().getBlockState(), Block.NOTIFY_ALL | (move ? Block.MOVED : 0));
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGardenAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 90.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {return PvZSounds.SILENCEVENET;}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZSounds.PLANTPLANTEDEVENT;}

	public boolean hurtByWater() {return false;}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {

	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}
}
