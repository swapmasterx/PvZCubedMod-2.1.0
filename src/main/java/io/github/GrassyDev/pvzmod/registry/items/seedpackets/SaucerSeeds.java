package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer.SaucerEntity;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SaucerSeeds extends SeedItem implements FabricItem {
	public static int cooldown = (int) (PVZCONFIG.nestedSeeds.moreSeeds.saucerS() * 20);
    public SaucerSeeds(Settings settings) {
        super(settings);
    }

	@Override
	public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}


	public static final String COOL_KEY = "Cooldown";

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		if (entity instanceof PlayerEntity player){
			if (player.getItemCooldownManager().getCooldownProgress(this, 0) > 0.0f){
				nbtCompound.putFloat("Cooldown", player.getItemCooldownManager().getCooldownProgress(this, 0));
			}
			else if (nbtCompound.getFloat("Cooldown") > 0.1f && player.getItemCooldownManager().getCooldownProgress(this, 0) <= 0.0f){
				float progress = nbtCompound.getFloat("Cooldown");
				player.getItemCooldownManager().set(this, (int) Math.floor(cooldown * progress));
			}
			if (!player.getItemCooldownManager().isCoolingDown(this) && (nbtCompound.getFloat("Cooldown") != 0 || nbtCompound.get("Cooldown") == null)){
				nbtCompound.putFloat("Cooldown", 0);
			}
		}
	}

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.contain.family").setStyle(Style.EMPTY.withColor(10987175)));

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.fly.tooltip")
				.formatted(Formatting.UNDERLINE));

		tooltip.add(Text.translatable("item.pvzmod.saucer_seed_packet.flavour")
				.formatted(Formatting.DARK_GRAY));

		tooltip.add(Text.translatable("item.pvzmod.saucer_seed_packet.flavour2")
				.formatted(Formatting.DARK_GRAY));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				if (world instanceof ServerWorld serverWorld) {
					SaucerEntity aquaticEntity = this.createEntity(world, hitResult);
					aquaticEntity.setYaw(user.getYaw());
					if (!world.isSpaceEmpty(aquaticEntity, aquaticEntity.getBoundingBox())) {
						return TypedActionResult.fail(itemStack);
					} else {
						if (!getWorld().isClient) {
							List<PlantEntity> list = getWorld().getNonSpectatingEntities(PlantEntity.class, PvZEntity.SAUCER.getDimensions().getBoxAt(aquaticEntity.getPos()));
							List<TileEntity> list2 = getWorld().getNonSpectatingEntities(TileEntity.class, PvZEntity.SAUCER.getDimensions().getBoxAt(aquaticEntity.getPos()));
							if (list.isEmpty() && list2.isEmpty()){
								float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
								aquaticEntity.refreshPositionAndAngles(aquaticEntity.getX(), aquaticEntity.getY(), aquaticEntity.getZ(), f, 0.0F);
								aquaticEntity.initialize(serverWorld, getWorld().getLocalDifficulty(aquaticEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
								((ServerWorld) world).spawnEntityAndPassengers(aquaticEntity);
								RandomGenerator randomGenerator = aquaticEntity.getRandom();
								BlockState blockState = aquaticEntity.getLandingBlockState();
								for(int i = 0; i < 4; ++i) {
									double dg = aquaticEntity.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
									double eg = aquaticEntity.getY() + 0.3;
									double fg = aquaticEntity.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
									aquaticEntity.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), dg, eg, fg, 0.0, 0.0, 0.0);
								}
								world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
								FluidState fluidState = world.getFluidState(aquaticEntity.getBlockPos().add(0, -0.25, 0));
								if (fluidState.getFluid() == Fluids.WATER) {
									world.playSound((PlayerEntity) null, aquaticEntity.getX(), aquaticEntity.getY(), aquaticEntity.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.BLOCKS, 0.25f, 0.8F);
								} else {
									world.playSound((PlayerEntity) null, aquaticEntity.getX(), aquaticEntity.getY(), aquaticEntity.getZ(), PvZSounds.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);
								}
								if (!user.getAbilities().creativeMode) {
									if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
									if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
							user.getItemCooldownManager().set(this, cooldown);
						}
								}
							}
							else {
								return TypedActionResult.pass(itemStack);
							}
						}

						user.incrementStat(Stats.USED.getOrCreateStat(this));
						return TypedActionResult.success(itemStack, world.isClient());
					}
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
		return TypedActionResult.pass(itemStack);
	}

	private SaucerEntity createEntity(World world, HitResult hitResult) {
		return (SaucerEntity) (new SaucerEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z));
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		World world = user.getWorld();
		BlockPos blockPos = entity.getBlockPos();
		SoundEvent sound = null;
		PlantEntity plantEntity = null;
		List<PlantEntity> list = null;
		if (world instanceof ServerWorld serverWorld) {
			plantEntity = PvZEntity.SAUCER.create(serverWorld, stack.getNbt(), (Text) null, user, blockPos, SpawnReason.SPAWN_EGG, true, true);
			list = getWorld().getNonSpectatingEntities(PlantEntity.class, PvZEntity.SAUCER.getDimensions().getBoxAt(plantEntity.getPos()));
		}
		if (world instanceof ServerWorld serverWorld && entity instanceof TileEntity) {
			if (list.isEmpty()) {
				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), f, 0.0F);
			plantEntity.initialize(serverWorld, getWorld().getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), PvZSounds.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);

				if (!user.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
						stack.decrement(1);
					}
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						user.getItemCooldownManager().set(this, cooldown);
					}
				}
				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.FAIL;
			}
		} else if (world instanceof ServerWorld serverWorld && (entity instanceof LilyPadEntity ||
				entity instanceof BubblePadEntity ||
				entity instanceof PlantEntity.VineEntity))  {
			if (entity instanceof PlantEntity lilyPadEntity) {
				if (lilyPadEntity.onWater) {
					sound = SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
				} else {
					sound = PvZSounds.PLANTPLANTEDEVENT;
				}
				if (lilyPadEntity instanceof LilyPadEntity lilyPadEntity1) {
					lilyPadEntity1.setPuffshroomPermanency(LilyPadEntity.PuffPermanency.PERMANENT);
				}
			}
			if (plantEntity == null) {
				return ActionResult.FAIL;
			}

			float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
			plantEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), f, 0.0F);
			plantEntity.initialize(serverWorld, getWorld().getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
			((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
			plantEntity.rideLilyPad(entity);
			world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, 0.6f, 0.8F);
			if (!user.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					stack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					user.getItemCooldownManager().set(this, cooldown);
				}
			}
			return ActionResult.success(world.isClient);
		} else {
			return ActionResult.PASS;
		}
	}

}
