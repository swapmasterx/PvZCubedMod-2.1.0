package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.heian.dripphylleia;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DripphylleiaEntityModel extends AnimatedGeoModel<DripphylleiaEntity> {

    @Override
    public Identifier getModelResource(DripphylleiaEntity object)
    {
        return new Identifier("pvzmod", "geo/dripphylleia.geo.json");
    }

    @Override
    public Identifier getTextureResource(DripphylleiaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/drip/dripphylleia.png");
    }

    @Override
    public Identifier getAnimationResource(DripphylleiaEntity object)
    {
        return new Identifier ("pvzmod", "animations/drip.json");
    }
}
