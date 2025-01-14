package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.darkages;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagPeasantEntityModel extends GeoModel<FlagPeasantEntity> {

	@Override
	public Identifier getModelResource(FlagPeasantEntity object)
	{
		return new Identifier("pvzmod", "geo/flagpeasant.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagPeasantEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagPeasantEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
