package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.flagzombie.sargeant;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagSargeantEntityModel extends GeoModel<FlagSargeantEntity> {

	@Override
	public Identifier getModelResource(FlagSargeantEntity object)
	{
		return new Identifier("pvzmod", "geo/flagsargeant.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagSargeantEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagSargeantEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
