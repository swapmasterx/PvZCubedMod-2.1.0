package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BubblePadEntityModel extends AnimatedGeoModel<BubblePadEntity> {

    @Override
    public Identifier getModelResource(BubblePadEntity object)
    {
        return new Identifier("pvzmod", "geo/bubble.geo.json");
    }

    @Override
    public Identifier getTextureResource(BubblePadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/oxygae/bubble.png");
    }

    @Override
    public Identifier getAnimationResource(BubblePadEntity object)
    {
        return new Identifier ("pvzmod", "animations/oxygae.json");
    }
}
