package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.armorbubble;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ArmorBubbleEntityModel extends GeoModel<ArmorBubbleEntity> {

    @Override
    public Identifier getModelResource(ArmorBubbleEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArmorBubbleEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/armorbubble.png");
    }

    @Override
    public Identifier getAnimationResource(ArmorBubbleEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
