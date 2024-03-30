package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OlivePitEntityModel extends GeoModel<OlivePitEntity> {

    @Override
    public Identifier getModelResource(OlivePitEntity object)
    {
        return new Identifier("pvzmod", "geo/olivepit.geo.json");
    }

    @Override
    public Identifier getTextureResource(OlivePitEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/oilyolive/olivepit.png");
    }

    @Override
    public Identifier getAnimationResource(OlivePitEntity object)
    {
        return new Identifier ("pvzmod", "animations/olivepit.json");
    }
}
