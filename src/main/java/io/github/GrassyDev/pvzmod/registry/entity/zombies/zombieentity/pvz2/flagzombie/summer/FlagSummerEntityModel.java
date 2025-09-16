package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.summer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagSummerEntityModel extends GeoModel<FlagSummerEntity> {

	@Override
	public Identifier getModelResource(FlagSummerEntity object)
	{
		return Identifier.of("pvzmod", "geo/flagsummer.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagSummerEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat.png");
		if (object.armless){
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagSummerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
