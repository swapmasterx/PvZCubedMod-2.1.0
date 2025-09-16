package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.electropea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ElectropeaEntityModel extends GeoModel<ElectropeaEntity> {

    @Override
    public Identifier getModelResource(ElectropeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/electropea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ElectropeaEntity object)
    {
		return Identifier.of ("pvzmod", "textures/entity/peashooter/electropea.png");

    }

    @Override
    public Identifier getAnimationResource(ElectropeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashooter.json");
    }
}
