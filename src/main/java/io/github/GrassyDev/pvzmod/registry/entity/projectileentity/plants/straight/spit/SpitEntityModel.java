package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spit;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpitEntityModel extends GeoModel<SpitEntity> {

    @Override
    public Identifier getModelResource(SpitEntity object)
    {
        return new Identifier("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpitEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/spit.png");
    }

    @Override
    public Identifier getAnimationResource(SpitEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
