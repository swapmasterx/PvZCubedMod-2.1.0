package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.loquat;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LoquatEntityModel extends GeoModel<LoquatEntity> {

    @Override
    public Identifier getModelResource(LoquatEntity object)
    {
        return new Identifier("pvzmod", "geo/loquat.geo.json");
    }

	public Identifier getTextureResource(LoquatEntity object) {
		return new Identifier("pvzmod", "textures/entity/loquat/loquat.png");
	}

    @Override
    public Identifier getAnimationResource(LoquatEntity object)
    {
        return new Identifier ("pvzmod", "animations/loquat.json");
    }
}
