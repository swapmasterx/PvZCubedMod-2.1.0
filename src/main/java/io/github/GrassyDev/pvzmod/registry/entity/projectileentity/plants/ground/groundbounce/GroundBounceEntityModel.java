package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.groundbounce;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GroundBounceEntityModel extends GeoModel<GroundBounceEntity> {

    @Override
    public Identifier getModelResource(GroundBounceEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(GroundBounceEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(GroundBounceEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
