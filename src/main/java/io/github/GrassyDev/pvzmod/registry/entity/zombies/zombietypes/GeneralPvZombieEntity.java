package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.maritile.MariTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds.RoseBudTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper.SuperChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit.OlivePitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.endurian.EndurianEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester.ChesterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card.ShootingCardEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodCountVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.zombieking.ZombieKingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.hovergoat.HoverGoatEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.World;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;
import static io.github.GrassyDev.pvzmod.sound.PvZSounds.*;

public class GeneralPvZombieEntity extends HostileEntity {
	public GeneralPvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setStepHeight(PVZCONFIG.nestedGeneralZombie.zombieStep());
		this.setPathfindingPenalty(PathNodeType.RAIL, 0.0F);
		this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	private MobEntity owner;

	public MobEntity getOwner() {
		return this.owner;
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}

	protected int animationMultiplier = 1;

	public boolean armless;
	public boolean geardmg;
	public boolean geardmg2;
	public boolean gear1less;
	public boolean gear2less;
	public boolean gearless;
	public boolean isFrozen;
	public boolean isIced;
	public boolean isPoisoned;
	public boolean isStunned;
	public int fireSplashTicks;
	public boolean swallowed;

	public boolean doesntBite;

	public boolean inDyingAnimation;
	public int deathTicks;
	public float defenseMultiplier = 1;
	public float damageMultiplier = 1;
	public int damageMultiplierTicks = 1;


	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FLYING_TAG, false);
		this.dataTracker.startTracking(HOVER_TAG, false);
		this.dataTracker.startTracking(CANHYPNO_TAG, true);
		this.dataTracker.startTracking(CANBURN_TAG, true);
		this.dataTracker.startTracking(COVERED_TAG, false);
		this.dataTracker.startTracking(STEALTH_TAG, false);
		this.dataTracker.startTracking(RAINBOW_TAG, false);
		this.dataTracker.startTracking(CHALLENGE_TAG, false);
		this.dataTracker.startTracking(DATA_ID_HYPNOTIZED, false);
		this.dataTracker.startTracking(ARMOR2_ID, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("isFlying", this.isFlying());
		tag.putBoolean("isHovering", this.isHovering());
		tag.putBoolean("canHypno", this.canHypno());
		tag.putBoolean("canBurn", this.canBurn());
		tag.putBoolean("isCovered", this.isCovered());
		tag.putBoolean("isStealth", this.isStealth());
		tag.putBoolean("isRainbow", this.getRainbow());
		tag.putBoolean("isChallenge", this.isChallengeZombie());
		tag.putBoolean("Hypnotized", this.getHypno());
		tag.putInt("Armor2", this.hasArmor2());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(FLYING_TAG, tag.getBoolean("isFlying"));
		this.dataTracker.set(HOVER_TAG, tag.getBoolean("isHovering"));
		this.dataTracker.set(CANHYPNO_TAG, tag.getBoolean("canHypno"));
		this.dataTracker.set(CANBURN_TAG, tag.getBoolean("canBurn"));
		this.dataTracker.set(COVERED_TAG, tag.getBoolean("isCovered"));
		this.dataTracker.set(STEALTH_TAG, tag.getBoolean("isStealth"));
		this.dataTracker.set(RAINBOW_TAG, tag.getBoolean("isRainbow"));
		this.dataTracker.set(CHALLENGE_TAG, tag.getBoolean("isChallenge"));
		this.dataTracker.set(DATA_ID_HYPNOTIZED, tag.getBoolean("Hypnotized"));
		this.dataTracker.set(ARMOR2_ID, tag.getInt("Armor2"));
	}

	static {
	}

	@Override
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 69) {
			for (int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 2.5 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 3);
				double f = this.random.nextDouble() / 2.5 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + d, this.getY() + 0.5 + e, this.getZ() + f, d, e, f);
			}
		}
		if (status == 70) {
			this.isFrozen = true;
			this.isIced = false;
			animationMultiplier = 2;
		}
		else if (status == 71) {
			this.isIced = true;
			this.isFrozen = false;
			animationMultiplier = 1;
		}
		else if (status == 72) {
			this.isIced = false;
			this.isFrozen = false;
		}
		if (status == 75) {
			this.isPoisoned = true;
		}
		else if (status == 76){
			this.isPoisoned = false;
		}
		if (status == 77) {
			this.isStunned = true;
		}
		else if (status == 78){
			this.isStunned = false;
		}
		if (status == 80){
			this.fireSplashTicks = 10;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/


	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		this.setCanHypno(CanHypno.TRUE);
		this.setCanBurn(CanBurn.TRUE);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}


	//Flying Tag

	protected static final TrackedData<Boolean> FLYING_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Flying {
		FALSE(false),
		TRUE(true);

		Flying(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isFlying() {
		return this.dataTracker.get(FLYING_TAG);
	}

	public void setFlying(GeneralPvZombieEntity.Flying flying) {
		this.dataTracker.set(FLYING_TAG, flying.getId());
	}


	//Hover Tag

	protected static final TrackedData<Boolean> HOVER_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Hover {
		FALSE(false),
		TRUE(true);

		Hover(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isHovering() {
		return this.dataTracker.get(HOVER_TAG);
	}

	public void setHover(Hover hover) {
		this.dataTracker.set(HOVER_TAG, hover.getId());
	}


	//Covered Tag

	protected static final TrackedData<Boolean> COVERED_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Covered {
		FALSE(false),
		TRUE(true);

		Covered(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isCovered() {
		return this.dataTracker.get(COVERED_TAG);
	}

	public void setCoveredTag(GeneralPvZombieEntity.Covered coveredTag) {
		this.dataTracker.set(COVERED_TAG, coveredTag.getId());
	}


	//Stealth Tag

	protected static final TrackedData<Boolean> STEALTH_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Stealth {
		FALSE(false),
		TRUE(true);

		Stealth(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isStealth() {
		return this.dataTracker.get(STEALTH_TAG);
	}

	public void setStealthTag(GeneralPvZombieEntity.Stealth stealthTag) {
		this.dataTracker.set(STEALTH_TAG, stealthTag.getId());
	}


	//Rainbow Tag

	protected static final TrackedData<Boolean> RAINBOW_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Rainbow {
		FALSE(false),
		TRUE(true);

		Rainbow(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getRainbow() {
		return this.dataTracker.get(RAINBOW_TAG);
	}

	public void setRainbowTag(GeneralPvZombieEntity.Rainbow rainbowTag) {
		this.dataTracker.set(RAINBOW_TAG, rainbowTag.getId());
	}


	//Challenge Tag

	protected static final TrackedData<Boolean> CHALLENGE_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Challenge {
		FALSE(false),
		TRUE(true);

		Challenge(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isChallengeZombie() {
		return this.dataTracker.get(CHALLENGE_TAG);
	}

	public void setChallengeZombie(GeneralPvZombieEntity.Challenge challenge) {
		this.dataTracker.set(CHALLENGE_TAG, challenge.getId());
	}

	// Hypno Tag

	protected static final TrackedData<Boolean> DATA_ID_HYPNOTIZED =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

		public enum IsHypno {
		FALSE(false),
		TRUE(true);

		IsHypno(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getHypno() {
		return this.dataTracker.get(DATA_ID_HYPNOTIZED);
	}

	public void setHypno(GeneralPvZombieEntity.IsHypno hypno) {
		this.dataTracker.set(DATA_ID_HYPNOTIZED, hypno.getId());
	}

	// Can be Hypnotized Tag

	protected static final TrackedData<Boolean> CANHYPNO_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum CanHypno {
		TRUE(true),
		FALSE(false);

		CanHypno(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean canHypno() {
		return this.dataTracker.get(CANHYPNO_TAG);
	}

	public void setCanHypno(GeneralPvZombieEntity.CanHypno canHypno) {
		this.dataTracker.set(CANHYPNO_TAG, canHypno.getId());
	}

	/** ----------------------------------------------------------------------- **/

	// Can be Burned Tag

	protected static final TrackedData<Boolean> CANBURN_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum CanBurn {
		TRUE(true),
		FALSE(false);

		CanBurn(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean canBurn() {
		return this.dataTracker.get(CANBURN_TAG);
	}

	public void setCanBurn(GeneralPvZombieEntity.CanBurn canBurn) {
		this.dataTracker.set(CANBURN_TAG, canBurn.getId());
	}


	/** ----------------------------------------------------------------------- **/

	// Armor 2

	protected static final TrackedData<Integer> ARMOR2_ID =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public void setArmor2(int entityId) {
		this.dataTracker.set(ARMOR2_ID, entityId);
	}

	public int hasArmor2() {
		return this.dataTracker.get(ARMOR2_ID);
	}

	@Nullable
	public LivingEntity getArmor2() {
		return (LivingEntity) this.getWorld().getEntityById((Integer)this.dataTracker.get(ARMOR2_ID));
	}

	/** ----------------------------------------------------------------------- **/

	public EntityType<? extends HostileEntity> entityBox = PvZEntity.BROWNCOAT;


	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return (this.getHypno()) ? PvZSounds.ZOMBIEBITEEVENT : PvZSounds.SILENCEVENET;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Override
	public void onDeath(DamageSource source) {
		double randomChallenge = 0;
		if (source.getSource() instanceof RoseBudTile roseBudTile && !(this instanceof ZombiePropEntity)){
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				List<MariTile> tileCheck = getWorld().getNonSpectatingEntities(MariTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()).expand(-0.5f, -0.5f, -0.5f));
				if (tileCheck.isEmpty()) {
					MariTile tile = (MariTile) PvZEntity.MARITILE.create(getWorld());
					tile.refreshPositionAndAngles(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 0, 0);
					tile.initialize(serverWorld, getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
					tile.setPersistent();
					tile.setHeadYaw(0);
					if (roseBudTile.getShadowPowered()){
						tile.setShadowPowered(TileEntity.Shadow.TRUE);
					}
					if (roseBudTile.getMoonPowered()){
						tile.setCount(PeapodCountVariants.THREE);
					}
					else {
						if (ZOMBIE_STRENGTH.get(this.getType()).orElse(0) <= 3) {
							tile.setCount(PeapodCountVariants.ONE);
						} else if (ZOMBIE_STRENGTH.get(this.getType()).orElse(0) <= 4) {
							tile.setCount(PeapodCountVariants.TWO);
						} else {
							tile.setCount(PeapodCountVariants.THREE);

						}
					}
					serverWorld.spawnEntityAndPassengers(tile);
				}
			}
		}
		if (this.isChallengeZombie()){
			randomChallenge = Math.random();
		}
		if (randomChallenge <= 0.33333) {
			if (this.getWorld().getGameRules().getBooleanValue(PvZCubed.SHOULD_ZOMBIE_DROP)) {
				if (!(this instanceof ZombiePropEntity)) {
					double random = Math.random();
					float multiplier = ZOMBIE_STRENGTH.get(this.getType()).orElse(1);
					if (multiplier > 9) {
						multiplier = 10;
					}
					double multiplierFinal = Math.pow(multiplier / 5, 2);
					Item item = ModItems.SUN;
					double random2 = Math.random();
					if (random2 <= 0.43){
						item = ModItems.SEED_PACKET_LIST.get(getRandom().nextInt(ModItems.SEED_PACKET_LIST.size()));
					}
					else {
						String zombieWorld = ZOMBIE_WORLD.get(this.getType()).orElse("basic");
						item = switch (zombieWorld) {
							case "night" -> ModItems.NIGHT_SEED_LIST.get(getRandom().nextInt(ModItems.NIGHT_SEED_LIST.size()));
							case "pool" -> ModItems.POOL_SEED_LIST.get(getRandom().nextInt(ModItems.POOL_SEED_LIST.size()));
							case "fog" -> ModItems.FOG_SEED_LIST.get(getRandom().nextInt(ModItems.FOG_SEED_LIST.size()));
							case "roof" -> ModItems.ROOF_SEED_LIST.get(getRandom().nextInt(ModItems.ROOF_SEED_LIST.size()));
							case "egypt" -> ModItems.EGYPT_SEED_LIST.get(getRandom().nextInt(ModItems.EGYPT_SEED_LIST.size()));
							case "pirate" -> ModItems.PIRATE_SEED_LIST.get(getRandom().nextInt(ModItems.PIRATE_SEED_LIST.size()));
							case "wildwest" -> ModItems.WILDWEST_SEED_LIST.get(getRandom().nextInt(ModItems.WILDWEST_SEED_LIST.size()));
							case "future" -> ModItems.FUTURE_SEED_LIST.get(getRandom().nextInt(ModItems.FUTURE_SEED_LIST.size()));
							case "darkages" -> ModItems.DARKAGES_SEED_LIST.get(getRandom().nextInt(ModItems.DARKAGES_SEED_LIST.size()));
							case "beach" -> ModItems.BEACH_SEED_LIST.get(getRandom().nextInt(ModItems.BEACH_SEED_LIST.size()));
							case "frostbite" -> ModItems.FROSTBITE_SEED_LIST.get(getRandom().nextInt(ModItems.FROSTBITE_SEED_LIST.size()));
							case "lostcity" -> ModItems.LOSTCITY_SEED_LIST.get(getRandom().nextInt(ModItems.LOSTCITY_SEED_LIST.size()));
							case "skycity" -> ModItems.SKYCITY_SEED_LIST.get(getRandom().nextInt(ModItems.SKYCITY_SEED_LIST.size()));
							case "fairytale" -> ModItems.FAIRYTALE_SEED_LIST.get(getRandom().nextInt(ModItems.FAIRYTALE_SEED_LIST.size()));
							default -> ModItems.SEED_PACKET_LIST.get(getRandom().nextInt(ModItems.SEED_PACKET_LIST.size()));
						};
						if (item == null){
							item = ModItems.SEED_PACKET_LIST.get(getRandom().nextInt(ModItems.SEED_PACKET_LIST.size()));
						}
					}

					if (random <= 0.7 * multiplierFinal) {
						dropItem(Items.ROTTEN_FLESH);
					}
					if (random <= 1 * multiplierFinal) {
						if (random <= 0.05 * multiplierFinal) {
							dropItem(item);
							playSound(LOOTGIFTDEVENT);
						}
						else if (random <= 0.15 * multiplierFinal) {
							dropItem(Items.GOLD_NUGGET);
							playSound(LOOTNUGGETEVENT);
						}
						else if (random <= 0.4 * multiplierFinal) {
							dropItem(Items.IRON_NUGGET);
							playSound(LOOTNUGGETEVENT);
						}
						dropItem(Items.ROTTEN_FLESH);
					}
					double random3 = Math.random();
					if (random3 <= 0.2 && this.isChallengeZombie()) {
						Item item2 = ModItems.PLANTFOOD_LIST.get(getRandom().nextInt(ModItems.PLANTFOOD_LIST.size()));
						dropItem(item2);
					}
					if (source.getSource() instanceof ShootingCardEntity shootingCardEntity && shootingCardEntity.getGolden()) {
						double random4 = Math.random();
						if (random4 <= 0.4) {
							this.playSound(PvZSounds.LOOTNUGGETEVENT, 0.5f, 1);
							this.dropItem(Items.GOLD_NUGGET);
						}
						else {
							this.playSound(PvZSounds.LOOTNUGGETEVENT, 0.3f, 1);
							this.dropItem(Items.IRON_NUGGET);
						}
					}
				}
			}
		}
		if ((inDyingAnimation && deathTicks <= 1) || ((source.getSource() instanceof SuperChomperEntity ||
				source.getSource() instanceof ChomperEntity ||
				source.getSource() instanceof ChesterEntity ||
				source.getSource() instanceof OlivePitEntity) && this.swallowed)){
			this.discard();
		}
		if ((!inDyingAnimation) || ((source.getSource() instanceof SuperChomperEntity ||
				source.getSource() instanceof ChomperEntity ||
				source.getSource() instanceof ChesterEntity ||
				source.getSource() instanceof OlivePitEntity) && this.swallowed)) {
			super.onDeath(source);
		}
	}



	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.getWorld().getGameRules().getBooleanValue(PvZCubed.SHOULD_ZOMBIE_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().size() < 2;
	}

	public LivingEntity CollidesWithPlant(Float colliderOffsetx, Float colliderOffsetz){
		Vec3d vec3d = new Vec3d((double)colliderOffsetx, 0.0, colliderOffsetz).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		LivingEntity setPlant = null;
		for (LivingEntity plantEntity : list) {
			if (plantEntity instanceof PlantEntity plantEntity1 && !plantEntity1.getImmune()) {
				if (plantEntity instanceof LilyPadEntity lilyPadEntity) {
					if (!(lilyPadEntity.hasPassengers()) && (!lilyPadEntity.getLowProfile() || (lilyPadEntity.getLowProfile() && TARGET_GROUND.get(this.getType()).orElse(false).equals(true)))) {
						setPlant = lilyPadEntity;
					} else {
						setPlant = (PlantEntity) lilyPadEntity.getFirstPassenger();
					}
				} else if ((PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground") || ((PlantEntity) plantEntity).getLowProfile()) && TARGET_GROUND.get(this.getType()).orElse(false).equals(true)) {
					setPlant = plantEntity;
				} else if (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground") || ((PlantEntity) plantEntity).getLowProfile()) {
					setPlant = null;
				} else if (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying") && TARGET_FLY.get(this.getType()).orElse(false).equals(true)) {
					setPlant = plantEntity;
				} else if (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
					setPlant = null;
				} else if (((PlantEntity) plantEntity).getImmune()) {
					setPlant = null;
				} else {
					setPlant = plantEntity;
				}
			}
			else if (plantEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
				if (generalPvZombieEntity.hasPassengers()) {
					for (Entity entity1 : generalPvZombieEntity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							setPlant = zpe;
						}
					}
				}
				else {
					setPlant = generalPvZombieEntity;
				}
			}
		}
		return setPlant;
	}

	public LivingEntity CollidesWithZombie(Float colliderOffset){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		LivingEntity setZombie = null;
		for (LivingEntity zombieEntity : list) {
			if (zombieEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.getHypno()) {
				setZombie = zombieEntity;
			}
			if (zombieEntity instanceof SuperFanImpEntity && this instanceof FootballEntity){
				setZombie = zombieEntity;
				break;
			}
		}
		return setZombie;
	}

	public TileEntity HasTile(BlockPos blockPos){
		List<TileEntity> list = getWorld().getNonSpectatingEntities(TileEntity.class, entityBox.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
		TileEntity setTile = null;
		for (TileEntity tileEntity : list) {
			setTile = tileEntity;
		}
		return setTile;
	}

	public PlayerEntity CollidesWithPlayer(Float colliderOffset){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PlayerEntity> list = getWorld().getNonSpectatingEntities(PlayerEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		if (!list.isEmpty()){
			return list.get(0);
		}
		else {
			return null;
		}
	}

	public ZombieObstacleEntity CollidesWithObstacle(Float colliderOffset){
		Vec3d vec3d = new Vec3d((double)colliderOffset + 0.66, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<ZombieObstacleEntity> list = getWorld().getNonSpectatingEntities(ZombieObstacleEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		ZombieObstacleEntity obstacleEntity = null;
		if (!list.isEmpty()) {
			for (ZombieObstacleEntity zombieObstacleEntity : list) {
				if (!zombieObstacleEntity.hasVehicle() && !zombieObstacleEntity.isDead()) {
					obstacleEntity = zombieObstacleEntity;
				}
			}
		}
		return obstacleEntity;
	}

	public void createScorchedTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			ScorchedTile tile = (ScorchedTile) PvZEntity.SCORCHEDTILE.create(getWorld());
			tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
			tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);

			Vec3d vec3d = Vec3d.ofCenter(blockPos).add(0, -0.5, 0);

			List<PlantEntity> list = getWorld().getNonSpectatingEntities(PlantEntity.class, entityBox.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));

			if (!list.isEmpty()){
				for (PlantEntity plantEntity : list) {
					if (!plantEntity.getFireImmune() && !PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
						damage(getWorld().getDamageSources().generic(), plantEntity.getMaxHealth() * 5);
					}
				}
			}
			tile.setHeadYaw(0);

			tile.setPersistent();
			serverWorld.spawnEntityAndPassengers(tile);
		}
	}

	public void createSnowTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			SnowTile tile = (SnowTile) PvZEntity.SNOWTILE.create(getWorld());
			tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
			tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);

			Vec3d vec3d = Vec3d.ofCenter(blockPos).add(0, -0.5, 0);

			List<PlantEntity> list = getWorld().getNonSpectatingEntities(PlantEntity.class, entityBox.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			List<TileEntity> tileCheck = getWorld().getNonSpectatingEntities(TileEntity.class, entityBox.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));

			if (tileCheck.isEmpty()) {
				if (!list.isEmpty()) {
					for (PlantEntity plantEntity : list) {
						if (!PLANT_TYPE.get(plantEntity.getType()).orElse("appease").equals("pepper") &&
								!PLANT_TYPE.get(plantEntity.getType()).orElse("appease").equals("winter") &&
								!PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
							damage(getWorld().getDamageSources().generic(), plantEntity.getMaxHealth() * 5);
						}
					}
				}
				tile.setHeadYaw(0);

				tile.setPersistent();
				serverWorld.spawnEntityAndPassengers(tile);
			}
		}
	}


	boolean pop = true;

	int stuckTimes;

	@Override
	protected void mobTick() {
		if (this.getRainbow()){
			if (this.hasStatusEffect(DISABLE)){
				this.setRainbowTag(Rainbow.FALSE);
				this.rainbowTicks = 5;
			}
			this.removeStatusEffect(ICE);
			this.removeStatusEffect(WARM);
			this.removeStatusEffect(WET);
			this.removeStatusEffect(BARK);
			this.removeStatusEffect(CHEESE);
			this.removeStatusEffect(GENERICSLOW);
			this.removeStatusEffect(SHADOW);
			this.removeStatusEffect(STUN);
			this.removeStatusEffect(FROZEN);
			this.removeStatusEffect(PVZPOISON);
			if (--rainbowTicks <= 0){
				this.setRainbowTag(Rainbow.FALSE);
				this.rainbowTicks = 5;
			}
			if (!(this instanceof ZombiePropEntity)) {
				for (Entity entity : this.getPassengerList()) {
					if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
						generalPvZombieEntity.setRainbowTag(Rainbow.TRUE);
						generalPvZombieEntity.rainbowTicks = this.rainbowTicks;
					}
				}
			}
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.getWorld().sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE) && !(this instanceof GargantuarEntity)){
			this.getWorld().sendEntityStatus(this, (byte) 71);
		}
		else if (!this.hasStatusEffect(ICE)) {
			this.getWorld().sendEntityStatus(this, (byte) 72);
		}
		if (this.hasStatusEffect(PVZPOISON)){
			this.getWorld().sendEntityStatus(this, (byte) 75);
		}
		else {
			this.getWorld().sendEntityStatus(this, (byte) 76);
		}
		if (this.hasStatusEffect(PvZCubed.STUN) || this.hasStatusEffect(PvZCubed.DISABLE)){
			this.getWorld().sendEntityStatus(this, (byte) 77);
		}
		else {
			this.getWorld().sendEntityStatus(this, (byte) 78);
		}
		super.mobTick();
	}

	@Override
	public Vec3d handleFrictionAndCalculateMovement(Vec3d movementInput, float slipperiness) {
		BlockPos blockPos = this.getVelocityAffectingPos();
		float p = this.getWorld().getBlockState(blockPos).getBlock().getSlipperiness();
		if (p > 0.6f){
			this.updateVelocity(this.getMovementSpeed(0.6f * p), movementInput);
		}
		else {
			this.updateVelocity(this.getMovementSpeed(0.6f), movementInput);
		}
		this.setVelocity(this.applyClimbingSpeed(this.getVelocity()));
		this.move(MovementType.SELF, this.getVelocity());
		Vec3d vec3d = this.getVelocity();
		if ((this.horizontalCollision || this.jumping)
				&& (this.isClimbing() || this.getBlockStateAtPos().isOf(Blocks.POWDER_SNOW) && PowderSnowBlock.canWalkOnPowderSnow(this))) {
			vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
		}

		return vec3d;
	}

	private Vec3d applyClimbingSpeed(Vec3d motion) {
		if (this.isClimbing()) {
			this.getNextAirOnLand(getAir());
			float f = 0.15F;
			double d = MathHelper.clamp(motion.x, -0.15F, 0.15F);
			double e = MathHelper.clamp(motion.z, -0.15F, 0.15F);
			double g = Math.max(motion.y, -0.15F);

			motion = new Vec3d(d, g, e);
		}

		return motion;
	}

	private float getMovementSpeed(float slipperiness) {
		return this.isOnGround() ? this.getMovementSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness)) : this.getAirSpeed();
	}

	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/


	public boolean damage(DamageSource source, float amount) {
		if ((!this.canHypno() || this.isCovered()) && source.isType(PvZDamageTypes.HYPNO_DAMAGE)) {
			return false;
		}
		else {
			return super.damage(source, amount * defenseMultiplier);
		}
	}

	public int playerGetTick;
	protected boolean frozenStart = true;
	protected float frzYaw;
	protected float frzPitch;
	protected float frzBodyYaw;
	protected float frzHeadYaw;


	protected Vec3d firstPos;

	protected int attackingTick;

	private boolean canJump;

	/**

	@Override
	public void setHeadYaw(float headYaw) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setHeadYaw(headYaw);
		}
	}

	@Override
	public void setBodyYaw(float bodyYaw) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setBodyYaw(bodyYaw);
		}
	}

	@Override
	public void setYaw(float yaw) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setYaw(yaw);
		}
	}

	@Override
	protected void setRotation(float yaw, float pitch) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setRotation(yaw, pitch);
		}
	}

	**/

	private int unstuckDelay;
	private int jumpDelay;
	private int chillTicks;
	public int oilTicks;
	public boolean canSlide = true;
	private int chillCDTicks;
	private int barkTicks;
	private int elecWetTicks;
	private float lastHealth = this.getHealth();

	public boolean dontWater = false;

	private boolean poisonSound = false;

	public int rainbowTicks = 5;

	public void tick() {
		if (this.getWorld().isRaining() && this.getWorld().isSkyVisible(this.getBlockPos())){
			this.addStatusEffect((new StatusEffectInstance(WET, 5, 1)));
		}
		if (this.lastHealth < this.getHealth()) {
			this.getWorld().sendEntityStatus(this, (byte) 69);
		}
		this.lastHealth = this.getHealth();
		if (!this.getWorld().isClient) {
			if (this.hasStatusEffect(SHADOW)) {
				this.removeStatusEffect(CHEESE);
				this.removeStatusEffect(GENERICSLOW);
				this.removeStatusEffect(BARK);
			}
			if (this.hasStatusEffect(BARK)) {
				this.removeStatusEffect(CHEESE);
				this.removeStatusEffect(GENERICSLOW);
				barkTicks = this.getStatusEffect(BARK).getDuration();
			}
			if (this.hasStatusEffect(ICE)) {
				if (this.hasStatusEffect(SHADOW)) {
					this.removeStatusEffect(SHADOW);
				}
				if (this.hasStatusEffect(BARK)) {
					this.removeStatusEffect(BARK);
				}
				if (this.hasStatusEffect(CHEESE)) {
					this.removeStatusEffect(CHEESE);
				}
				if (this.hasStatusEffect(GENERICSLOW)) {
					this.removeStatusEffect(GENERICSLOW);
				}
				--barkTicks;
				++chillTicks;
			} else {
				if (--chillTicks <= 0) {
					chillTicks = 0;
				}
			}
			if (chillTicks >= 80) {
				this.removeStatusEffect(ICE);
				this.chillCDTicks = 80;
			}
			if (--chillCDTicks > 0) {
				this.removeStatusEffect(ICE);
			}
			if (!this.hasStatusEffect(STUN)) {
				this.canSlide = oilTicks <= 0;
			}
			if (--oilTicks <= 0) {
				oilTicks = 0;
			}
			if (barkTicks > 0 && !this.hasStatusEffect(BARK)) {
				this.addStatusEffect((new StatusEffectInstance(BARK, barkTicks, 1)));
			} else if (barkTicks <= 0) {
				this.removeStatusEffect(BARK);
			}
		}
		if (this.getOwner() instanceof GraveEntity graveEntity && graveEntity.isChallengeGrave()) {
			this.setChallengeZombie(Challenge.TRUE);
		}
		this.setStepHeight(PVZCONFIG.nestedGeneralZombie.zombieStep());
		if (this.isOnFire() || this.hasStatusEffect(WARM)) {
			this.setStealthTag(Stealth.FALSE);
		}
		/**
		 if (canJump && !this.getWorld().isClient() && !this.isFlying() && !this.isInsideWaterOrBubbleColumn() && --jumpDelay <= 0 && this.age > 40) {
		 jumpOverGap();
		 jumpDelay = 20;
		 }**/
		/**
		 if (!(this instanceof ZombiePropEntity)) {
		 this.canJump = this.onGround;
		 if (!this.canJump && !this.isFlying() && !this.isInsideWaterOrBubbleColumn()) {
		 this.getNavigation().stop();
		 }
		 }**/
		if (this.getTarget() != null) {
			if (this.isAttacking() && this.squaredDistanceTo(this.getTarget()) < 1) {
				attackingTick = 40;
			} else {
				--attackingTick;
			}
		}
		Vec3d lastPos = this.getPos();
		if (this.firstPos != null && !this.isFlying() && !this.isHovering()) {
			if (lastPos.squaredDistanceTo(firstPos) < 0.0001 && this.CollidesWithPlant(0.1f, 0f) == null && !this.hasStatusEffect(PvZCubed.BOUNCED) && this.getTarget() != null && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.hasStatusEffect(PvZCubed.ICE) && this.age >= 30 && this.attackingTick <= 0 && --this.unstuckDelay <= 0 && !this.isInsideWaterOrBubbleColumn()) {
				this.setVelocity(0, 0, 0);
				this.addVelocity(0, 0.3, 0);
				++this.stuckTimes;
				this.unstuckDelay = 20;
			}
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)) {
			this.removeStatusEffect(STUN);
		}
		ZombiePropEntity zombiePropEntity = null;
		ZombiePropEntity zombiePropEntity2 = (ZombiePropEntity) getArmor2();
		for (Entity entity : this.getPassengerList()) {
			if (this.getArmor2() == entity) {
				zombiePropEntity2 = (ZombiePropEntity) entity;
			}
			if (entity instanceof ZombiePropEntity && zombiePropEntity == null && getArmor2() != entity) {
				zombiePropEntity = (ZombiePropEntity) entity;
			}
			if (entity instanceof ZombiePropEntity && entity != zombiePropEntity && zombiePropEntity2 == null && this.getArmor2() == null) {
				zombiePropEntity2 = (ZombiePropEntity) entity;
			}
		}
		if (zombiePropEntity != null) {
			var e = zombiePropEntity;
			if (this.getType().equals(PvZEntity.PYRAMIDHEAD)) {
				e.setHypno(IsHypno.FALSE);
			}
			if (e.hasStatusEffect(FROZEN)) {
				e.removeStatusEffect(STUN);
				this.removeStatusEffect(STUN);
			}
			if (e.isCovered()) {
				e.removeStatusEffect(STUN);
				this.removeStatusEffect(STUN);
			}
			if (e.isCovered()) {
				this.removeStatusEffect(PVZPOISON);
				this.removeStatusEffect(StatusEffects.POISON);
			}
		}
		if (zombiePropEntity2 != null) {
			var e = zombiePropEntity2;
			if (this.getType().equals(PvZEntity.PYRAMIDHEAD)) {
				e.setHypno(IsHypno.FALSE);
			}
			if (e.hasStatusEffect(FROZEN)) {
				e.removeStatusEffect(STUN);
				this.removeStatusEffect(STUN);
			}
			if (e.isCovered()) {
				e.removeStatusEffect(STUN);
				this.removeStatusEffect(STUN);
			}
			if (e.isCovered()) {
				this.removeStatusEffect(PVZPOISON);
				this.removeStatusEffect(StatusEffects.POISON);
			}
		}
		if (this.getAttacker() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) {
			this.setTarget(generalPvZombieEntity);
		}
		if (zombiePropEntity != null) {
			var e = zombiePropEntity;
			if (e.getAttacker() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) {
				this.setTarget(generalPvZombieEntity);
			}
		}
		if (zombiePropEntity2 != null) {
			var e = zombiePropEntity2;
			if (e.getAttacker() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) {
				this.setTarget(generalPvZombieEntity);
			}
		}
		if (this.getTarget() != null && this.getTarget() instanceof PlantEntity) {
			if ((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground") || (this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile())) && TARGET_GROUND.get(this.getType()).orElse(false).equals(false)) {
				this.setTarget(null);
			} else if (PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying") && TARGET_FLY.get(this.getType()).orElse(false).equals(false)) {
				this.setTarget(null);
			}
		}
		if (IS_MACHINE.get(this.getType()).orElse(false).equals(false) && !(this instanceof HoverGoatEntity)) {
			this.removeStatusEffect(DISABLE);
		} else if (IS_MACHINE.get(this.getType()).orElse(false).equals(true) && !(this instanceof HoverGoatEntity)) {
			this.removeStatusEffect(STUN);
		}
		if (this.isCovered()) {
			this.removeStatusEffect(STUN);
		}
		if (this.isCovered()) {
			this.removeStatusEffect(PVZPOISON);
			this.removeStatusEffect(StatusEffects.POISON);
		}
		if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("metallic")) &&
				!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("electronic")) &&
				!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("plant")) &&
				!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("cloth")) &&
				!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("paper")) && this.hasStatusEffect(ACID)) {
			this.removeStatusEffect(ACID);
		}
		if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) &&
				!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("plant")) &&
				this.hasStatusEffect(PVZPOISON)) {
			this.removeStatusEffect(PVZPOISON);
		}
		if (ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("stone") || ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("crystal") || ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("cloth")) {
			this.removeStatusEffect(FROZEN);
		}
		if (ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("cloth")) {
			this.removeStatusEffect(ICE);
		}
		LivingEntity target = this.getTarget();
		if (this.getHypno() && (target instanceof PlayerEntity || target instanceof PassiveEntity || target instanceof GolemEntity)) {
			this.setTarget(null);
		}
		if (this.isInsideWall()) {
			this.setPosition(this.getX(), this.getY() + 1, this.getZ());
		}
		if (!this.hasNoGravity() && !this.isFlying() && !this.isHovering()) {
			if (target != null) {
				if (target.squaredDistanceTo(this) < 2.25 && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
				}
			}
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && this.isInsideWaterOrBubbleColumn()) {
			this.kill();
		}
		if (this.hasStatusEffect(BOUNCED) && ZOMBIE_SIZE.get(this.getType()).orElse("normal").equals("small") && this.isAlive()) {
			this.addVelocity(0, 1, 0);
			this.kill();
		}

		if (this.getWorld().isClient) {
			if (zombiePropEntity != null && zombiePropEntity != getArmor2()) {
				var e = zombiePropEntity;
				this.geardmg = e.getHealth() < e.getMaxHealth() / 2;
			} else {
				this.geardmg = false;
			}
			if (zombiePropEntity2 != null && getArmor2() == null) {
				this.setArmor2(zombiePropEntity2.getId());
				zombiePropEntity2 = (ZombiePropEntity) getArmor2();
			}
			if (zombiePropEntity2 != null) {
				var e = zombiePropEntity2;
				this.geardmg2 = e.getHealth() < e.getMaxHealth() / 2;
			} else {
				this.geardmg2 = false;
			}

			if (zombiePropEntity == getArmor2()){
				zombiePropEntity = null;
			}

			if (zombiePropEntity2 != null && zombiePropEntity != null){
				this.gearless = false;
			}
			else {
				gear2less = getArmor2() == null;
				gear1less = zombiePropEntity == null;
				if (zombiePropEntity == null && zombiePropEntity2 == null) {
					this.gearless = true;
				}
			}

			this.armless = this.getHealth() < this.getMaxHealth() / 2;

		}
		if (this.getHealth() < this.getMaxHealth() / 2 && !(this instanceof ZombiePropEntity) &&
				!ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("gargantuar") && !ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("small") &&
				!(this instanceof ZombieKingEntity) && !(this instanceof ZombieVehicleEntity) && IS_MACHINE.get(this.getType()).orElse(false).equals(false)) {
			if (this.pop && !this.dead) {
				playSound(PvZSounds.POPLIMBEVENT, 1f, (float) (0.5F + Math.random()));
				pop = false;
			}
		}
		if (this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno() && this.getHypno()){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof FireTrailEntity){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof TileEntity){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof ZombieObstacleEntity zombieObstacleEntity && !zombieObstacleEntity.getHypno() && !this.getHypno()){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(this instanceof ZombieKingEntity)){
			var targetPropEntity = generalPvZombieEntity.getPassengerList()
					.stream()
					.filter(e -> e instanceof ZombiePropEntity)
					.map(e -> (ZombiePropEntity) e)
					.findFirst();
			if (targetPropEntity.isPresent()){
				var e = targetPropEntity.get();
				this.setTarget(e);
			}
		}
		if (this.submergedInWater){
			this.jump();
		}
		if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) && !(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("plant")) && (this.hasStatusEffect(PVZPOISON) || this.hasStatusEffect(StatusEffects.POISON) ) && !(this instanceof ZombiePropEntity)){
			this.removeStatusEffect(PVZPOISON);
			this.removeStatusEffect(StatusEffects.POISON);
		}
		if (ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("electronic") && (this.hasStatusEffect(WET) || this.isWet())){
			if (--elecWetTicks <= 0) {
				this.damage(getDamageSources().generic(), 4.0F);
				elecWetTicks = 20;
			}
		}
		else {
			elecWetTicks = 0;
		}
		if (this.hasStatusEffect(WARM) || this.isOnFire()){
			this.removeStatusEffect(FROZEN);
			this.removeStatusEffect(ICE);
		}
		if (!this.getHypno() && !(this instanceof ZombieKingEntity) && this.getTarget() == null && --this.playerGetTick <= 0) {
			this.setTarget(this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
			this.playerGetTick = 200;
		}
		if (frozenStart){
			this.frzYaw = this.getYaw();
			this.frzPitch = this.getPitch();
			this.frzHeadYaw = this.getHeadYaw();
			this.frzBodyYaw = this.bodyYaw;
		}
		if (this.hasStatusEffect(FROZEN) || this.hasStatusEffect(DISABLE) || this.hasStatusEffect(STUN) || this.isFrozen || this.isStunned) {
			this.setHeadYaw(frzHeadYaw);
			this.setBodyYaw(frzBodyYaw);
			frozenStart = false;
		}
		else if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN) && !this.isFrozen && !this.isStunned) {
			frozenStart = true;
		}
		super.tick();
		if (--damageMultiplierTicks <= 0){
			this.damageMultiplier = 1;
			this.damageMultiplierTicks = 20;
		}
		List<WaterTile> waterTiles = getWorld().getNonSpectatingEntities(WaterTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		for (WaterTile waterTile : waterTiles) {
			this.dontWater = true;
		}
		if (this.hasStatusEffect(FROZEN) || this.hasStatusEffect(DISABLE) || this.hasStatusEffect(STUN) || this.isFrozen || this.isStunned) {
			this.setHeadYaw(frzHeadYaw);
			this.setBodyYaw(frzBodyYaw);
			frozenStart = false;
		}
		else if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN) && !this.isFrozen && !this.isStunned) {
			frozenStart = true;
		}
		if (fireSplashTicks == 10){
			this.getWorld().sendEntityStatus(this, (byte) 80);
		}
		--fireSplashTicks;
		if (--damageCooldown <= 0){
			damageTaken = 0;
			damageCooldown = 30;
		}
		if (!this.getWorld().isClient) {
			if (this.hasStatusEffect(PVZPOISON) && !poisonSound) {
				this.playSound(POISONSPLASHEVENT, 1f, 1);
				poisonSound = true;
			} else if (!this.hasStatusEffect(PVZPOISON)) {
				poisonSound = false;
			}
		}
	}

	protected void jumpOverGap(){
		int airBlocks = 0;
		boolean hasBlockAtEnd = false;
		for (int x = 1; x < PVZCONFIG.nestedGeneralZombie.zombieBlockJump() + 2; ++x) {
			Vec3d vec3d2 = new Vec3d((double) x, -0.25, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			int l = MathHelper.floor(this.getPos().x + vec3d2.x);
			int m = MathHelper.floor(this.getPos().y + vec3d2.y);
			int n = MathHelper.floor(this.getPos().z + vec3d2.z);
			BlockPos blockPos2 = new BlockPos(l, m, n);
			if (!this.getWorld().getBlockState(blockPos2).isAir()) {
				hasBlockAtEnd = true;
				break;
			} else {
				++airBlocks;
			}
		}
		if (airBlocks > 0 && hasBlockAtEnd) {
			Vec3d vec3d2 = new Vec3d((double) airBlocks / (Math.pow(2, (float) -airBlocks / 2) * 10), 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			if (airBlocks == 1){
				vec3d2 = new Vec3d(0.2, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			}
			this.setVelocity(vec3d2.x, 0.5, vec3d2.z);
			this.canJump = false;
		}
	}

	public float damageTaken;
	private int damageCooldown;
	public boolean canTakeDmg;


	@Override
	protected void applyDamage(DamageSource source, float amount) {
		/**damageTaken += amount;
		canTakeDmg = true;
		if (damageTaken <= 50) {
			super.applyDamage(source, amount);
		}
		else if (amount >= 50){
			super.applyDamage(source, amount);
		}
		else if (source.getAttacker() instanceof PlantEntity plantEntity && plantEntity.isBurst){
			super.applyDamage(source, amount);
		}
		else {
			canTakeDmg = false;
			super.applyDamage(source, 0);
		}**/
		if (!this.getRainbow()) {
			super.applyDamage(source, amount);
		}
		else if (this.getRainbow() && (((source.getSource() instanceof SuperChomperEntity ||
				source.getSource() instanceof ChomperEntity ||
				source.getSource() instanceof ChesterEntity ||
				source.getSource() instanceof OlivePitEntity) && this.swallowed) ||
				source.getSource() instanceof GravebusterEntity ||
				source.getAttacker() == this ||
				source.isIndirect())){
			super.applyDamage(source, amount);
		}
	}

	@Override
	public boolean tryAttack(Entity target) {
		if (this.age > 1) {
			if (target.getVehicle() instanceof PlantEntity.VineEntity vineEntity){
				target = vineEntity;
			}
			if (this.getTarget() != null &&
					(((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground") || (this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile())) &&
							TARGET_GROUND.get(this.getType()).orElse(false).equals(true)) ||
							(PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying") &&
									TARGET_FLY.get(this.getType()).orElse(false).equals(true)) &&
									!(target instanceof PlantEntity plantEntity && plantEntity.getImmune()))) {
				if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					float sound = 0.75f;
					if (this.getHypno()) {
						sound = 0.33f;
					}
					target.playSound(PvZSounds.ZOMBIEBITEEVENT, sound, 1f);
					this.setStealthTag(Stealth.FALSE);
					if (!doesntBite && target instanceof HypnoshroomEntity hypnoshroomEntity && !hypnoshroomEntity.getIsAsleep() && !this.isCovered()){
						if (!ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("small")) {
							hypnoshroomEntity.damage(getDamageSources().mobAttack(this), hypnoshroomEntity.getMaxHealth() * 5);
						}
						this.damage(PvZDamageTypes.of(getWorld(),PvZDamageTypes.HYPNO_DAMAGE), 0);
					}
					float damage = 12;
					if (target instanceof EndurianEntity) {
						damage = 6;
					}
					if (!doesntBite && (target instanceof GravebusterEntity ||
							target instanceof EndurianEntity) &&
							!this.isCovered()){
						ZombiePropEntity zombiePropEntity2 = null;
						for (Entity entity1 : this.getPassengerList()) {
							if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null &&
									!(entity1 instanceof ZombieShieldEntity)) {
								zombiePropEntity2 = zpe;
							}
						}
						if (zombiePropEntity2 != null && damage > zombiePropEntity2.getHealth()) {
							float damage2 = damage - zombiePropEntity2.getHealth();
							zombiePropEntity2.damage(getDamageSources().mobProjectile(this,this), damage);
							this.damage(getDamageSources().mobProjectile(this,this), damage2);
						} else if (zombiePropEntity2 != null) {
							zombiePropEntity2.damage(getDamageSources().mobProjectile(this,this), damage);
						} else {
							this.damage(getDamageSources().mobProjectile(this,this), damage);
						}
						this.playSound(PEAHITEVENT, 0.3f, 1f);
					}
				}
				return super.tryAttack(target);
			} else if (this.getTarget() != null &&
					!((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground"))) &&
					!(this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile()) &&
					!((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying"))) &&
					!(target instanceof PlantEntity plantEntity2 && plantEntity2.getImmune())) {
				if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					float sound = 0.75f;
					if (this.getHypno()) {
						sound = 0.33f;
					}
					target.playSound(PvZSounds.ZOMBIEBITEEVENT, sound, 1f);
					this.setStealthTag(Stealth.FALSE);
					if (!doesntBite && target instanceof HypnoshroomEntity hypnoshroomEntity && !hypnoshroomEntity.getIsAsleep() && !this.isCovered()){
						if (!ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("small")) {
							hypnoshroomEntity.damage(getDamageSources().mobAttack(this), hypnoshroomEntity.getMaxHealth() * 5);
						}
						this.damage(PvZDamageTypes.of(getWorld(),PvZDamageTypes.HYPNO_DAMAGE), 0);
					}
					float damage = 12;
					if (target instanceof EndurianEntity) {
						damage = 6;
					}
					if (!doesntBite && (target instanceof GravebusterEntity ||
							target instanceof EndurianEntity) &&
							!this.isCovered()){
						ZombiePropEntity zombiePropEntity2 = null;
						for (Entity entity1 : this.getPassengerList()) {
							if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null &&
									!(entity1 instanceof ZombieShieldEntity)) {
								zombiePropEntity2 = zpe;
							}
						}
						if (zombiePropEntity2 != null && damage > zombiePropEntity2.getHealth()) {
							float damage2 = damage - zombiePropEntity2.getHealth();
							zombiePropEntity2.damage(getDamageSources().mobProjectile(this,this), damage);
							this.damage(getDamageSources().mobProjectile(this,this), damage2);
						} else if (zombiePropEntity2 != null) {
							zombiePropEntity2.damage(getDamageSources().mobProjectile(this,this), damage);
						} else {
							this.damage(getDamageSources().mobProjectile(this,this), damage);
						}
						this.playSound(PEAHITEVENT, 0.3f, 1f);
					}
				}
				return super.tryAttack(target);
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**@Override
	protected EntityNavigation createNavigation(World world) {
		return new GeneralPvZombieEntity.SwimNavigation(this, world);
	}

	static class PathNodeMaker extends AmphibiousPathNodeMaker {
		private final BlockPos.Mutable pos = new BlockPos.Mutable();

		public PathNodeMaker(boolean bl) {
			super(bl);
		}

		@Nullable
		@Override
		public PathNode getStart() {
			return this.m_etdbalqp(
					new BlockPos(
							MathHelper.floor(this.entity.getBoundingBox().minX),
							MathHelper.floor(this.entity.getBoundingBox().minY),
							MathHelper.floor(this.entity.getBoundingBox().minZ)
					)
			);
		}

		@Override
		public PathNodeType getNodeType(
				BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors
		) {
			BlockPos.Mutable mutable = new BlockPos.Mutable();

			for(int i = x; i < x + sizeX; ++i) {
				for(int j = y; j < y + sizeY; ++j) {
					for(int k = z; k < z + sizeZ; ++k) {
						FluidState fluidState = world.getFluidState(mutable.set(i, j, k));
						BlockState blockState = world.getBlockState(mutable.set(i, j, k));
						if (fluidState.isEmpty() && blockState.canPathfindThrough(world, mutable.down(), NavigationType.WATER) && blockState.isAir()) {
							return PathNodeType.BREACH;
						}

						if (!fluidState.isIn(FluidTags.WATER)) {
							return PathNodeType.BREACH;
						}
					}
				}
			}

			BlockState blockState2 = world.getBlockState(mutable);
			return blockState2.canPathfindThrough(world, mutable, NavigationType.WATER) ? PathNodeType.WATER : PathNodeType.OPEN;
		}
	}

	static class SwimNavigtaion extends AmphibiousNavigation {
		SwimNavigation(GeneralPvZombieEntity zombie, World world) {
			super(zombie, world);
		}

		@Override
		protected PathNodeNavigator createPathNodeNavigator(int range) {
			this.nodeMaker = new GeneralPvZombieEntity.PathNodeMaker(true);
			this.nodeMaker.setCanEnterOpenDoors(true);
			return new PathNodeNavigator(this.nodeMaker, range);
		}
	}**/
}
