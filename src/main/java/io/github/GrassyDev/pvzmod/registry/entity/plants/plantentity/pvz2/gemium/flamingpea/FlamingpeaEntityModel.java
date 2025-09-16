package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlamingpeaEntityModel extends GeoModel<FlamingpeaEntity> {

    @Override
    public Identifier getModelResource(FlamingpeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/flamingpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlamingpeaEntity object)
    {
		return object.isWet()? Identifier.of ("pvzmod", "textures/entity/peashooter/flamingpea_wet.png") :
				Identifier.of ("pvzmod", "textures/entity/peashooter/flamingpea.png");
    }

    @Override
    public Identifier getAnimationResource(FlamingpeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashooter.json");
    }
}
