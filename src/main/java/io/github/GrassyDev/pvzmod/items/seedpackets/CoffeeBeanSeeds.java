package io.github.GrassyDev.pvzmod.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.coffeebean.CoffeeBeanEntity;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class CoffeeBeanSeeds extends SeedItem implements FabricItem {
	public boolean used;
	public static int cooldown = (int) (PVZCONFIG.nestedSeeds.moreSeeds.coffeeBeanS() * 20);

	public CoffeeBeanSeeds(Settings settings) {
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
		if (entity instanceof PlayerEntity player) {
			if (player.getItemCooldownManager().getCooldownProgress(this, 0) > 0.0f) {
				nbtCompound.putFloat("Cooldown", player.getItemCooldownManager().getCooldownProgress(this, 0));
			} else if (nbtCompound.getFloat("Cooldown") > 0.1f && player.getItemCooldownManager().getCooldownProgress(this, 0) <= 0.0f) {
				float progress = nbtCompound.getFloat("Cooldown");
				player.getItemCooldownManager().set(this, (int) Math.floor(cooldown * progress));
			}
			if (!player.getItemCooldownManager().isCoolingDown(this) && (nbtCompound.getFloat("Cooldown") != 0 || nbtCompound.get("Cooldown") == null)) {
				nbtCompound.putFloat("Cooldown", 0);
			}
		}
	}

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.enchant.family").setStyle(Style.EMPTY.withColor(16399550)));

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.instant.tooltip")
				.formatted(Formatting.UNDERLINE));

		tooltip.add(Text.translatable("item.pvzmod.coffeebean_seed_packet.flavour")
				.formatted(Formatting.DARK_GRAY));
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		World world = user.getWorld();
		BlockPos blockPos = entity.getBlockPos();
		SoundEvent sound = null;
		List<CoffeeBeanEntity> list = null;
		if (world instanceof ServerWorld serverWorld && entity instanceof PlantEntity plantEntity1 && plantEntity1.nocturnal && !plantEntity1.getCofee()) {
			CoffeeBeanEntity plantEntity = PvZEntity.COFFEEBEAN.spawnFromItemStack((ServerWorld)world, stack, user, blockPos, SpawnReason.SPAWN_EGG, true, true);
			list = world.getNonSpectatingEntities(CoffeeBeanEntity.class, PvZEntity.COFFEEBEAN.getDimensions().getBoxAt(plantEntity.getPos()));
			if (list.isEmpty()) {
				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), f, 0.0F);
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), PvZSounds.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);

				if (!user.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
						stack.decrement(1);
					}
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBooleanValue(PvZCubed.INSTANT_RECHARGE)) {
						user.getItemCooldownManager().set(this, cooldown);
					}
				}
				return ActionResult.success(world.isClient);
			}else {
				return ActionResult.PASS;
			}
		}else {
			return ActionResult.PASS;
		}
	}
}
