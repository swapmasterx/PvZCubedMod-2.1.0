package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingCardEntityModel extends GeoModel<ShootingCardEntity> {

    @Override
    public Identifier getModelResource(ShootingCardEntity object)
    {
		if (object.getGolden()){
			return Identifier.of("pvzmod", "geo/goldencard.geo.json");
		}
		else{
			return Identifier.of("pvzmod", "geo/card.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(ShootingCardEntity object){
		return Identifier.of("pvzmod", "textures/entity/projectiles/card.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingCardEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
