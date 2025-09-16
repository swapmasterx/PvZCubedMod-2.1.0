package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.heian.dripphylleia;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DripphylleiaEntityModel extends GeoModel<DripphylleiaEntity> {

    @Override
    public Identifier getModelResource(DripphylleiaEntity object)
    {
        return Identifier.of("pvzmod", "geo/dripphylleia.geo.json");
    }

    @Override
    public Identifier getTextureResource(DripphylleiaEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/drip/dripphylleia.png");
    }

    @Override
    public Identifier getAnimationResource(DripphylleiaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/drip.json");
    }
}
