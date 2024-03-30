package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.bass;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BassZombieEntityModel extends GeoModel<BassZombieEntity> {

	@Override
	public Identifier getModelResource(BassZombieEntity object)
	{
		return new Identifier("pvzmod", "geo/bass.geo.json");
	}

	@Override
	public Identifier getTextureResource(BassZombieEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bass/bass.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bass/bass_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(BassZombieEntity object)
	{
		return new Identifier ("pvzmod", "animations/bass.json");
	}
}
