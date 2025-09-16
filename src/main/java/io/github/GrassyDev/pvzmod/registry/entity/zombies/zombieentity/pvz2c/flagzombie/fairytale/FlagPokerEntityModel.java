package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.flagzombie.fairytale;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagPokerEntityModel extends GeoModel<FlagPokerEntity> {

	@Override
	public Identifier getModelResource(FlagPokerEntity object)
	{
		return Identifier.of("pvzmod", "geo/flagpoker.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagPokerEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/browncoat/poker/flagpoker.png");
		if (object.armless){
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/poker/flagpoker_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagPokerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
