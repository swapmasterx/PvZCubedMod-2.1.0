package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LaserModel extends GeoModel<LaserEntity> {

    @Override
    public Identifier getModelResource(LaserEntity object)
    {
        return new Identifier("pvzmod", "geo/laser.geo.json");
    }

    @Override
    public Identifier getTextureResource(LaserEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/laser.png");
    }

    @Override
    public Identifier getAnimationResource(LaserEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
