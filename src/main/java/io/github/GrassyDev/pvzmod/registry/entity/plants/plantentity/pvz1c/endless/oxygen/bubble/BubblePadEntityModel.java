package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BubblePadEntityModel extends GeoModel<BubblePadEntity> {

    @Override
    public Identifier getModelResource(BubblePadEntity object)
    {
        return Identifier.of("pvzmod", "geo/bubble.geo.json");
    }

    @Override
    public Identifier getTextureResource(BubblePadEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/oxygae/bubble.png");
    }

    @Override
    public Identifier getAnimationResource(BubblePadEntity object)
    {
        return Identifier.of ("pvzmod", "animations/oxygae.json");
    }
}
