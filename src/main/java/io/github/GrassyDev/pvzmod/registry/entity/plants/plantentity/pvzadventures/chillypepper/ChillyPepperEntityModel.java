package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ChillyPepperEntityModel extends GeoModel<ChillyPepperEntity> {

    @Override
    public Identifier getModelResource(ChillyPepperEntity object)
    {
        return Identifier.of("pvzmod", "geo/chillypepper.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChillyPepperEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/cherrybomb/chillypepper.png");
    }

    @Override
    public Identifier getAnimationResource(ChillyPepperEntity object)
    {
        return Identifier.of ("pvzmod", "animations/jalapeno.json");
    }
}
