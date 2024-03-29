package io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


public class SnowTile extends TileEntity {

	public SnowTile(EntityType<? extends SnowTile> entityType, World world) {
		super(entityType, world);

		this.setInvulnerable(true);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 2400){
			this.discard();
		}
	}
}
