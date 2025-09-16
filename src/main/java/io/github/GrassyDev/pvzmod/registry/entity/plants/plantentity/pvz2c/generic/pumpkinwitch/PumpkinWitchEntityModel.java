package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.pumpkinwitch;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PumpkinWitchEntityModel extends GeoModel<PumpkinWitchEntity> {

    @Override
    public Identifier getModelResource(PumpkinWitchEntity object)
    {
        return Identifier.of("pvzmod", "geo/pumpkinwitch.geo.json");
    }

    @Override
    public Identifier getTextureResource(PumpkinWitchEntity object)
    {
		if (!object.hasProj()){
			return Identifier.of("pvzmod", "textures/entity/pumpkinwitch/pumpkinwitch_projless.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/pumpkinwitch/pumpkinwitch.png");
		}
    }

    @Override
    public Identifier getAnimationResource(PumpkinWitchEntity object)
    {
        return Identifier.of ("pvzmod", "animations/pumpkinwitch.json");
    }
}
