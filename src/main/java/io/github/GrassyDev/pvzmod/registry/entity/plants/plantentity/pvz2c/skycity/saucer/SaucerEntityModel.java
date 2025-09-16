package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SaucerEntityModel extends GeoModel<SaucerEntity> {

    @Override
    public Identifier getModelResource(SaucerEntity object)
    {
        return Identifier.of("pvzmod", "geo/saucer.geo.json");
    }

	public Identifier getTextureResource(SaucerEntity object) {
		return Identifier.of("pvzmod", "textures/entity/loquat/saucer.png");
	}

    @Override
    public Identifier getAnimationResource(SaucerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/loquat.json");
    }
}
