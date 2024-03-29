package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.megagrow.bananasaurus;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BananasaurusEntityModel extends AnimatedGeoModel<BananasaurusEntity> {

    @Override
    public Identifier getModelResource(BananasaurusEntity object)
    {
        return new Identifier("pvzmod", "geo/bananasaurus.geo.json");
    }

	public Identifier getTextureResource(BananasaurusEntity object) {
		return new Identifier("pvzmod", "textures/entity/bananasaurus/bananasaurus.png");
	}

    @Override
    public Identifier getAnimationResource(BananasaurusEntity object)
    {
        return new Identifier ("pvzmod", "animations/bananasaurus.json");
    }
}
