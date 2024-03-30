package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingBeeSpikeEntityModel extends GeoModel<ShootingBeeSpikeEntity> {

    @Override
    public Identifier getModelResource(ShootingBeeSpikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingBeeSpikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/beespike.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingBeeSpikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
