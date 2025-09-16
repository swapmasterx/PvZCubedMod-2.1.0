package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RocketEntityModel extends GeoModel<RocketEntity> {

    @Override
    public Identifier getModelResource(RocketEntity object)
    {
        return Identifier.of("pvzmod", "geo/rocketproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(RocketEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/bully/80sactionhero.png");
    }

    @Override
    public Identifier getAnimationResource(RocketEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
