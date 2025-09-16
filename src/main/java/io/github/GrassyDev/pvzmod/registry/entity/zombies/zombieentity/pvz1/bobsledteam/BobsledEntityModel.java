package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.bobsledteam;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BobsledEntityModel extends GeoModel<BobsledRiderEntity> {

	@Override
	public Identifier getModelResource(BobsledRiderEntity object)
	{
		return Identifier.of("pvzmod", "geo/bobsledrider.geo.json");
	}

	@Override
	public Identifier getTextureResource(BobsledRiderEntity object)
	{
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/bobsled/bobsledrider.png");
		if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/bobsled/bobsledrider_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(BobsledRiderEntity object)
	{
		return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
	}
}
