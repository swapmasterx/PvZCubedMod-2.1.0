package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlamingpeaEntityModel extends GeoModel<FlamingpeaEntity> {

    @Override
    public Identifier getModelResource(FlamingpeaEntity object)
    {
        return new Identifier("pvzmod", "geo/flamingpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlamingpeaEntity object)
    {
		return object.isWet()? new Identifier ("pvzmod", "textures/entity/peashooter/flamingpea_wet.png") :
				new Identifier ("pvzmod", "textures/entity/peashooter/flamingpea.png");
    }

    @Override
    public Identifier getAnimationResource(FlamingpeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
