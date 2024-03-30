package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPowerIcespikeEntityModel extends GeoModel<ShootingPowerIcespikeEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerIcespikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerIcespikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/powerspikeice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerIcespikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
