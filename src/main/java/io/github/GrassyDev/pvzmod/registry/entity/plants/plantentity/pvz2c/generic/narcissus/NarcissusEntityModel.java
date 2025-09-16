package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.narcissus;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NarcissusEntityModel extends GeoModel<NarcissusEntity> {

    @Override
    public Identifier getModelResource(NarcissusEntity object)
    {
        return Identifier.of("pvzmod", "geo/narcissus.geo.json");
    }

    @Override
    public Identifier getTextureResource(NarcissusEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/narcissus/narcissus.png");
    }

    @Override
    public Identifier getAnimationResource(NarcissusEntity object)
    {
        return Identifier.of ("pvzmod", "animations/narcissus.json");
    }
}
