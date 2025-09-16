package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.boomerang;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingBoomerangEntityModel extends GeoModel<ShootingBoomerangEntity> {

    @Override
    public Identifier getModelResource(ShootingBoomerangEntity object)
    {
        return Identifier.of("pvzmod", "geo/boomerang.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingBoomerangEntity object){
		return Identifier.of("pvzmod", "textures/entity/bloomerang/bloomerang.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingBoomerangEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
