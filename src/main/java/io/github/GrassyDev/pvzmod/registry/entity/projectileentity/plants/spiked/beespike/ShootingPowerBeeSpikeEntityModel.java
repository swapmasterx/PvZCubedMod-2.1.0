package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPowerBeeSpikeEntityModel extends GeoModel<ShootingPowerBeeSpikeEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerBeeSpikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerBeeSpikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/powerbeespike.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerBeeSpikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
