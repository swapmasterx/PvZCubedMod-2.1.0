package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class JalapenoEntityModel extends GeoModel<JalapenoEntity> {

    @Override
    public Identifier getModelResource(JalapenoEntity object)
    {
        return Identifier.of("pvzmod", "geo/jalapeno.geo.json");
    }

    @Override
    public Identifier getTextureResource(JalapenoEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/cherrybomb/jalapeno.png");
    }

    @Override
    public Identifier getAnimationResource(JalapenoEntity object)
    {
        return Identifier.of ("pvzmod", "animations/jalapeno.json");
    }
}
