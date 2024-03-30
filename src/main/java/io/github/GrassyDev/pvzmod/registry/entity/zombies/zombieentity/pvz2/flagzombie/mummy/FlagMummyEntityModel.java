package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.mummy;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagMummyEntityModel extends GeoModel<FlagMummyEntity> {

	@Override
	public Identifier getModelResource(FlagMummyEntity object)
	{
		return new Identifier("pvzmod", "geo/flagmummy.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagMummyEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagMummyEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
