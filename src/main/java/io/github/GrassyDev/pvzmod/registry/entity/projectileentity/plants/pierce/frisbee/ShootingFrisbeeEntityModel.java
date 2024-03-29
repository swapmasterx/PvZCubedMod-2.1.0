package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.frisbee;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingFrisbeeEntityModel extends AnimatedGeoModel<ShootingFrisbeeEntity> {

    @Override
    public Identifier getModelResource(ShootingFrisbeeEntity object)
    {
        return new Identifier("pvzmod", "geo/frisbee.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingFrisbeeEntity object){
		return new Identifier("pvzmod", "textures/entity/bloomerang/bloomerang.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingFrisbeeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
