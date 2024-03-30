package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.ghostpepper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GhostpepperEntityModel extends GeoModel<GhostpepperEntity> {

    @Override
    public Identifier getModelResource(GhostpepperEntity object)
    {
		if (object.getMoonPowered()){
			return new Identifier("pvzmod", "geo/ghostpepper_moon.geo.json");
		}
		else if (object.getShadowPowered()){
			return new Identifier("pvzmod", "geo/ghostpepper_shadow.geo.json");
		}
		else {
			return new Identifier("pvzmod", "geo/ghostpepper.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(GhostpepperEntity object)
    {
		if (object.getMoonPowered() || object.getShadowPowered()) {
			return new Identifier("pvzmod", "textures/entity/ghostpepper/ghostpepper_shadow.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/ghostpepper/ghostpepper.png");
		}
    }

    @Override
    public Identifier getAnimationResource(GhostpepperEntity object)
    {
        return new Identifier ("pvzmod", "animations/ghostpepper.json");
    }
}
