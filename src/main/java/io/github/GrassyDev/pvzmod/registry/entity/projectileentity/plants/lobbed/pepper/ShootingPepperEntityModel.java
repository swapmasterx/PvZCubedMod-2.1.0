package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pepper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPepperEntityModel extends GeoModel<ShootingPepperEntity> {

    @Override
    public Identifier getModelResource(ShootingPepperEntity object)
    {
        return new Identifier("pvzmod", "geo/pepperproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPepperEntity object){
			return new Identifier("pvzmod", "textures/entity/projectiles/pepperproj.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingPepperEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
