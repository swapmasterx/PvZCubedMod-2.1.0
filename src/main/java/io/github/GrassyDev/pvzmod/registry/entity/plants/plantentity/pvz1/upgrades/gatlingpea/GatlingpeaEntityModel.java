package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GatlingpeaEntityModel extends GeoModel<GatlingpeaEntity> {

    @Override
    public Identifier getModelResource(GatlingpeaEntity object)
    {
        return new Identifier("pvzmod", "geo/gatlingpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(GatlingpeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/gatlingpea.png");
    }

    @Override
    public Identifier getAnimationResource(GatlingpeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
