package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPowerSpikeEntityModel extends GeoModel<ShootingPowerSpikeEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerSpikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerSpikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/elecspike.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerSpikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
