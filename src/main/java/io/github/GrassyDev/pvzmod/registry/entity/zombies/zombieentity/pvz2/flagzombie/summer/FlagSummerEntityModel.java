package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.summer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlagSummerEntityModel extends AnimatedGeoModel<FlagSummerEntity> {

	@Override
	public Identifier getModelResource(FlagSummerEntity object)
	{
		return new Identifier("pvzmod", "geo/flagsummer.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagSummerEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
		if (object.armless){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagSummerEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
