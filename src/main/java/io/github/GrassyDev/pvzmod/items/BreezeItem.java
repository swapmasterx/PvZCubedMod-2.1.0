package io.github.GrassyDev.pvzmod.items;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.breeze.BreezeEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreezeItem extends Item {
    public BreezeItem(Settings settings) {
        super(settings);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent

        if (!world.isClient) {
            BreezeEntity proj = new BreezeEntity(PvZEntity.BREEZE, world);
            proj.setPos(user.getX(), user.getY() + 1f, user.getZ());
            proj.setOwner(user);
            proj.setProperties(user, user.getPitch(), user.getYaw(), 0, 1.00F, 0);
            world.spawnEntity(proj);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
        }
		user.getItemCooldownManager().set(this, 80);
        return TypedActionResult.success(itemStack, world.isClient());
    }

}
