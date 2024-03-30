package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowqueenpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingSnowqueenPeaEntityModel extends GeoModel<ShootingSnowqueenPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingSnowqueenPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSnowqueenPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/spikeice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSnowqueenPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
