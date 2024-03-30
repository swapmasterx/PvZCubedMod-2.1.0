package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.future;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagFutureEntityModel extends GeoModel<FlagFutureEntity> {

	@Override
	public Identifier getModelResource(FlagFutureEntity object)
	{
		return new Identifier("pvzmod", "geo/flagfuture.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagFutureEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/future/future.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/future/future_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagFutureEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
