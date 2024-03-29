package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpringProjEntityModel extends AnimatedGeoModel<SpringProjEntity> {

    @Override
    public Identifier getModelResource(SpringProjEntity object)
    {
        return new Identifier("pvzmod", "geo/springtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpringProjEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/springprincess/springprincess.png");
    }

    @Override
    public Identifier getAnimationResource(SpringProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
