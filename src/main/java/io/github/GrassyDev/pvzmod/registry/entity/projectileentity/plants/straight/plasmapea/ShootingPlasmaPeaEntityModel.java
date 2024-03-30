package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.plasmapea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPlasmaPeaEntityModel extends GeoModel<ShootingPlasmaPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingPlasmaPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPlasmaPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/plasmapea.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPlasmaPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
