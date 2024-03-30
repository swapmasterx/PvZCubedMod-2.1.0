package io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OilTileModel extends GeoModel<OilTile> {

    @Override
    public Identifier getModelResource(OilTile object)
    {
        return new Identifier("pvzmod", "geo/oiltile.geo.json");
    }

    @Override
    public Identifier getTextureResource(OilTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(OilTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
