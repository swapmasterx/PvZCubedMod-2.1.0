package io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CheeseTileModel extends GeoModel<CheeseTile> {

    @Override
    public Identifier getModelResource(CheeseTile object)
    {
        return new Identifier("pvzmod", "geo/cheesetile.geo.json");
    }

    @Override
    public Identifier getTextureResource(CheeseTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(CheeseTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
