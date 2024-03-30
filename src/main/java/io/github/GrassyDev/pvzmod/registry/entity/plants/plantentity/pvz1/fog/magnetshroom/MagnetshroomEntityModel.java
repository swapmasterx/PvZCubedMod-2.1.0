package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.magnetshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MagnetshroomEntityModel extends GeoModel<MagnetshroomEntity> {

    @Override
    public Identifier getModelResource(MagnetshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/magnetshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagnetshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/magnetshroom/magnetshroom.png");
    }

    @Override
    public Identifier getAnimationResource(MagnetshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/magnetshroom.json");
    }
}
