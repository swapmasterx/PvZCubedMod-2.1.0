package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.future;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagFutureEntityModel extends GeoModel<FlagFutureEntity> {

	@Override
	public Identifier getModelResource(FlagFutureEntity object)
	{
		return Identifier.of("pvzmod", "geo/flagfuture.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagFutureEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future.png");
		if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagFutureEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
