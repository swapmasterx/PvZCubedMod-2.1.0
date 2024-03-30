package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BeeshooterEntityModel extends GeoModel<BeeshooterEntity> {

    @Override
    public Identifier getModelResource(BeeshooterEntity object)
    {
        return new Identifier("pvzmod", "geo/beeshooter.geo.json");
    }

    @Override
    public Identifier getTextureResource(BeeshooterEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/beeshooter.png");
    }

    @Override
    public Identifier getAnimationResource(BeeshooterEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
