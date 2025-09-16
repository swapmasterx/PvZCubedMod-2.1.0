package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BeetEntityModel extends GeoModel<BeetEntity> {

    @Override
    public Identifier getModelResource(BeetEntity object)
    {
        return Identifier.of("pvzmod", "geo/beet.geo.json");
    }

	public Identifier getTextureResource(BeetEntity object) {
		return Identifier.of("pvzmod", "textures/entity/beet/beet.png");
	}

    @Override
    public Identifier getAnimationResource(BeetEntity object)
    {
        return Identifier.of ("pvzmod", "animations/beet.json");
    }
}
