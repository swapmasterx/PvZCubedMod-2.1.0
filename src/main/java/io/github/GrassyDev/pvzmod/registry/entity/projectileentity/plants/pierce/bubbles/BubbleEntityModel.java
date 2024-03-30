package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bubbles;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BubbleEntityModel extends GeoModel<BubbleEntity> {

    @Override
    public Identifier getModelResource(BubbleEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(BubbleEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(BubbleEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
