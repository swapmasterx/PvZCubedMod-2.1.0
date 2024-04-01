package io.github.GrassyDev.pvzmod.registry.items.spawneggs;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.SeedItem;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

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

public class GardenChallengeSpawn extends SeedItem {
    public GardenChallengeSpawn(Settings settings) {
        super(settings);
    }

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.gardenchallenge_spawn.flavour")
				.formatted(Formatting.LIGHT_PURPLE));

		tooltip.add(Text.translatable("item.pvzmod.gardenchallenge_spawn.flavour2")
				.formatted(Formatting.LIGHT_PURPLE));

		tooltip.add(Text.translatable("item.pvzmod.gardenchallenge_spawn.flavour3")
				.formatted(Formatting.LIGHT_PURPLE));

		tooltip.add(Text.translatable("item.pvzmod.gardenchallenge_spawn.flavour4")
				.formatted(Formatting.LIGHT_PURPLE));
	}

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        ItemStack itemStack = context.getStack();
        Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
		Box box = PvZEntity.GARDENCHALLENGE.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
		if (world.isSpaceEmpty((Entity)null, box) && world instanceof ServerWorld serverWorld) {
			GardenChallengeEntity gardenEntity = (GardenChallengeEntity) PvZEntity.GARDENCHALLENGE.create(serverWorld, itemStack.getNbt(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
			List<PlantEntity> list = getWorld().getNonSpectatingEntities(PlantEntity.class, PvZEntity.GARDENCHALLENGE.getDimensions().getBoxAt(gardenEntity.getPos()));
			if (list.isEmpty()) {
				List<GardenChallengeEntity> list2 = getWorld().getNonSpectatingEntities(GardenChallengeEntity.class, PvZEntity.GARDENCHALLENGE.getDimensions().getBoxAt(gardenEntity.getPos()).expand(50, 20, 50));
				if (list2.isEmpty()) {

					gardenEntity.refreshPositionAndAngles(gardenEntity.getX(), gardenEntity.getY(), gardenEntity.getZ(), 0, 0.0F);
					gardenEntity.setBodyYaw(0);
					gardenEntity.setHeadYaw(0);
					gardenEntity.setYaw(0);
					((ServerWorld) world).spawnEntityAndPassengers(gardenEntity);
					gardenEntity.setPersistent();
					world.playSound((PlayerEntity) null, gardenEntity.getX(), gardenEntity.getY(), gardenEntity.getZ(), PvZSounds.ENTITYRISINGEVENT, SoundCategory.BLOCKS, 0.75F, 0.8F);


					PlayerEntity user = context.getPlayer();
					if (!user.getAbilities().creativeMode) {
						if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
							itemStack.decrement(1);
						}
					}
					return ActionResult.success(world.isClient);
				} else {
					return ActionResult.FAIL;
				}
			}
			else {
				return ActionResult.PASS;
			}
		} else {
			return ActionResult.PASS;
		}
	}
}
