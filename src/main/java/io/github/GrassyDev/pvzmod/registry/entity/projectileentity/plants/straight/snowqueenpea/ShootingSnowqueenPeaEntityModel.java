package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowqueenpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingSnowqueenPeaEntityModel extends GeoModel<ShootingSnowqueenPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingSnowqueenPeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSnowqueenPeaEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/spikeice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSnowqueenPeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
