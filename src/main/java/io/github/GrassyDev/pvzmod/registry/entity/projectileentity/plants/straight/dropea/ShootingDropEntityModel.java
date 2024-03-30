package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dropea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingDropEntityModel extends GeoModel<ShootingDropEntity> {

    @Override
    public Identifier getModelResource(ShootingDropEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingDropEntity object){
		return new Identifier("pvzmod", "textures/entity/projectiles/drop.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingDropEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
