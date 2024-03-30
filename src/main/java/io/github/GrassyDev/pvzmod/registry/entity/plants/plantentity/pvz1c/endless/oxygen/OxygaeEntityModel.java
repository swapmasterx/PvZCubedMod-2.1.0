package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OxygaeEntityModel extends GeoModel<OxygaeEntity> {

    @Override
    public Identifier getModelResource(OxygaeEntity object)
    {
        return new Identifier("pvzmod", "geo/oxygae.geo.json");
    }

    @Override
    public Identifier getTextureResource(OxygaeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/oxygae/oxygae.png");
    }

    @Override
    public Identifier getAnimationResource(OxygaeEntity object)
    {
        return new Identifier ("pvzmod", "animations/oxygae.json");
    }
}
