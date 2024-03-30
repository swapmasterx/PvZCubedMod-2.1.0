package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.flamingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingFlamingPeaEntityModel extends GeoModel<ShootingFlamingPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingFlamingPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingFlamingPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/firepea.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingFlamingPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
