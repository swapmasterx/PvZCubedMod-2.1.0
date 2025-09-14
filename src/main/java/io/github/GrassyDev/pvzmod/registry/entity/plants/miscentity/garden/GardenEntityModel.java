package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GardenEntityModel extends GeoModel<GardenEntity> {

    @Override
    public Identifier getModelResource(GardenEntity object)
    {
        return Identifier.of("pvzmod", "geo/garden.geo.json");
    }

    @Override
    public Identifier getTextureResource(GardenEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/misc/garden.png");
    }

    @Override
    public Identifier getAnimationResource(GardenEntity object)
    {
        return Identifier.of ("pvzmod", "animations/garden.json");
    }
}
