package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GravebusterEntityModel extends GeoModel<GravebusterEntity> {

    @Override
    public Identifier getModelResource(GravebusterEntity object)
    {
        return Identifier.of("pvzmod", "geo/gravebuster.geo.json");
    }

    @Override
    public Identifier getTextureResource(GravebusterEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/gravebuster/gravebuster.png");
    }

    @Override
    public Identifier getAnimationResource(GravebusterEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gravebuster.json");
    }
}
