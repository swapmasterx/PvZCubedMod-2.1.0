package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingSnowPeaEntityModel extends GeoModel<ShootingSnowPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingSnowPeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSnowPeaEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/peaice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSnowPeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
