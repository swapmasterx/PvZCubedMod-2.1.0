package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bark;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BarkModel extends GeoModel<BarkEntity> {

    @Override
    public Identifier getModelResource(BarkEntity object)
    {
        return new Identifier("pvzmod", "geo/bark.geo.json");
    }

    @Override
    public Identifier getTextureResource(BarkEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/bark.png");
    }

    @Override
    public Identifier getAnimationResource(BarkEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
