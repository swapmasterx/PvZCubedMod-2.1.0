package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.zapricot;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ZapricotEntityModel extends GeoModel<ZapricotEntity> {

    @Override
    public Identifier getModelResource(ZapricotEntity object)
    {
        return Identifier.of("pvzmod", "geo/zapricot.geo.json");
    }

	public Identifier getTextureResource(ZapricotEntity object) {
		return Identifier.of("pvzmod", "textures/entity/lightningreed/zapricot.png");
	}

    @Override
    public Identifier getAnimationResource(ZapricotEntity object)
    {
        return Identifier.of ("pvzmod", "animations/zapricot.json");
    }
}
