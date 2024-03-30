package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.lightningreed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LightningReedEntityModel extends GeoModel<LightningReedEntity> {

    @Override
    public Identifier getModelResource(LightningReedEntity object)
    {
        return new Identifier("pvzmod", "geo/lightningreed.geo.json");
    }

	public Identifier getTextureResource(LightningReedEntity object) {
		return new Identifier("pvzmod", "textures/entity/lightningreed/lightningreed.png");
	}

    @Override
    public Identifier getAnimationResource(LightningReedEntity object)
    {
        return new Identifier ("pvzmod", "animations/lightningreed.json");
    }
}
