package io.github.GrassyDev.pvzmod.items.spawneggs;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.flagzombie.fairytale.FlagPokerEntity;
import io.github.GrassyDev.pvzmod.items.seedpackets.SeedItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class FlagPokerEgg extends SeedItem {
    public FlagPokerEgg(Settings settings) {
        super(settings);
    }

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.creative")
				.formatted(Formatting.UNDERLINE));
	}

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        ItemStack itemStack = context.getStack();
        Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
        Box box = PvZEntity.FLAGPOKER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
		if (world.isSpaceEmpty((Entity)null, box) && world instanceof ServerWorld serverWorld) {
				 double random = Math.random();
                    FlagPokerEntity flagzombieEntity = (FlagPokerEntity) PvZEntity.FLAGPOKER.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
                    if (flagzombieEntity == null) {
                        return ActionResult.FAIL;
                    }

                    float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    flagzombieEntity.refreshPositionAndAngles(flagzombieEntity.getX(), flagzombieEntity.getY(), flagzombieEntity.getZ(), f, 0.0F);
                    ((ServerWorld) world).spawnEntityAndPassengers(flagzombieEntity);
					flagzombieEntity.setPersistent();
                    world.playSound((PlayerEntity) null, flagzombieEntity.getX(), flagzombieEntity.getY(), flagzombieEntity.getZ(), PvZSounds.ENTITYRISINGEVENT, SoundCategory.BLOCKS, 0.75F, 0.8F);


                if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
                return ActionResult.success(world.isClient);
            }
			 else {
				 return ActionResult.PASS;
			 }
	}
}
