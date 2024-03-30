package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.bobsledteam;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BobsledEntityModel extends GeoModel<BobsledRiderEntity> {

	@Override
	public Identifier getModelResource(BobsledRiderEntity object)
	{
		return new Identifier("pvzmod", "geo/bobsledrider.geo.json");
	}

	@Override
	public Identifier getTextureResource(BobsledRiderEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bobsled/bobsledrider.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bobsled/bobsledrider_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(BobsledRiderEntity object)
	{
		return new Identifier ("pvzmod", "animations/newbrowncoat.json");
	}
}
