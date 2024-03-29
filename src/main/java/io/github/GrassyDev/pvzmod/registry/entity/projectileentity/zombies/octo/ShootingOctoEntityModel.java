package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.octo;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingOctoEntityModel extends AnimatedGeoModel<ShootingOctoEntity> {

    @Override
    public Identifier getModelResource(ShootingOctoEntity object)
    {
        return new Identifier("pvzmod", "geo/octoproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingOctoEntity object){
			return new Identifier("pvzmod", "textures/entity/bully/octo.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingOctoEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
