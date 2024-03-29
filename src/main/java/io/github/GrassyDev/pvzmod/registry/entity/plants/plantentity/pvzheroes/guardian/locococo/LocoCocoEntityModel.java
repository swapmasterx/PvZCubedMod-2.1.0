package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.locococo;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LocoCocoEntityModel extends AnimatedGeoModel<LocoCocoEntity> {

    @Override
    public Identifier getModelResource(LocoCocoEntity object)
    {
        return new Identifier("pvzmod", "geo/locococo.geo.json");
    }

    @Override
    public Identifier getTextureResource(LocoCocoEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/locococo/locococo.png");
    }

    @Override
    public Identifier getAnimationResource(LocoCocoEntity object)
    {
        return new Identifier ("pvzmod", "animations/locococo.json");
    }
}
