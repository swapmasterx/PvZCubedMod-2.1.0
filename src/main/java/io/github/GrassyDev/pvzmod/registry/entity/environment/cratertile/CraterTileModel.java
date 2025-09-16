package io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CraterTileModel extends GeoModel<CraterTile> {

    @Override
    public Identifier getModelResource(CraterTile object)
    {
        return Identifier.of("pvzmod", "geo/cratertile.geo.json");
    }

    @Override
    public Identifier getTextureResource(CraterTile object)
    {
        return Identifier.of("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(CraterTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
