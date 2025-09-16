package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OxygaeEntityModel extends GeoModel<OxygaeEntity> {

    @Override
    public Identifier getModelResource(OxygaeEntity object)
    {
        return Identifier.of("pvzmod", "geo/oxygae.geo.json");
    }

    @Override
    public Identifier getTextureResource(OxygaeEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/oxygae/oxygae.png");
    }

    @Override
    public Identifier getAnimationResource(OxygaeEntity object)
    {
        return Identifier.of ("pvzmod", "animations/oxygae.json");
    }
}
