package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PeashooterEntityModel extends GeoModel<PeashooterEntity> {

    @Override
    public Identifier getModelResource(PeashooterEntity object)
    {
        return Identifier.of("pvzmod", "geo/peashooter.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeashooterEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(PeashooterEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashooter.json");
    }
}
