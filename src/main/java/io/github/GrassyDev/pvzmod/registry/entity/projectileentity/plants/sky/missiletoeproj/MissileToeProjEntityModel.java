package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.sky.missiletoeproj;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MissileToeProjEntityModel extends AnimatedGeoModel<MissileToeProjEntity> {

    @Override
    public Identifier getModelResource(MissileToeProjEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(MissileToeProjEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/missiletoeproj.png");
    }

    @Override
    public Identifier getAnimationResource(MissileToeProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
