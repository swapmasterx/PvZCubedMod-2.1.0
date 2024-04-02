package io.github.GrassyDev.pvzmod.registry.items.targets;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe.MissileToeTarget;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.SeedItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import java.util.List;

public class MissileToeTargetItem extends SeedItem implements FabricItem {
    public boolean used;

    public MissileToeTargetItem(Settings settings) {
        super(settings);
		targetID = 0;
    }

	@Override
	public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}


	public int targetID = 0;

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (targetID == 0){
			if (entity instanceof PlayerEntity player){
				player.getInventory().removeStack(slot);
			}
		}
	}

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.missiletoetarget")
				.formatted(Formatting.LIGHT_PURPLE));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				if (world instanceof ServerWorld serverWorld) {
					MissileToeTarget tileEntity = this.createEntity(world, hitResult);
					tileEntity.setYaw(user.getYaw());
					if (!world.isSpaceEmpty(tileEntity, tileEntity.getBoundingBox())) {
						return TypedActionResult.fail(itemStack);
					} else {
						if (!world.isClient) {
							float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
							tileEntity.refreshPositionAndAngles(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), f, 0.0F);
							if (targetID != 0) {
								tileEntity.setTargetID(targetID);
							}
							world.spawnEntity(tileEntity);
							int slot = user.getInventory().getSlotWithStack(itemStack);
							user.getInventory().removeStack(slot);
							targetID = 0;
						} else {
							return TypedActionResult.pass(itemStack);
						}
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.success(itemStack, world.isClient());
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
		return TypedActionResult.pass(itemStack);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		World world = user.getWorld();
		BlockPos blockPos = entity.getBlockPos();
		if (world instanceof ServerWorld serverWorld) {
			MissileToeTarget targetTile = PvZEntity.MISSILETOETARGET.spawnFromItemStack((ServerWorld)world, stack, user, blockPos, SpawnReason.SPAWN_EGG, true, true);
			float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
			targetTile.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), f, 0.0F);
			if (targetID != 0){
				targetTile.setTargetID(targetID);
			}
			world.spawnEntity(targetTile);
			int slot = user.getInventory().getSlotWithStack(stack);
			user.getInventory().removeStack(slot);
			targetID = 0;
		}
		return ActionResult.SUCCESS;
	}

	private MissileToeTarget createEntity(World world, HitResult hitResult) {
		return (MissileToeTarget) (new MissileToeTarget(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z));
	}
}
