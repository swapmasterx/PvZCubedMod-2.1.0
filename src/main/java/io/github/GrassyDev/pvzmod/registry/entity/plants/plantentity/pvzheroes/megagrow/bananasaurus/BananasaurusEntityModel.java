package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.megagrow.bananasaurus;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BananasaurusEntityModel extends GeoModel<BananasaurusEntity> {

    @Override
    public Identifier getModelResource(BananasaurusEntity object)
    {
        return Identifier.of("pvzmod", "geo/bananasaurus.geo.json");
    }

	public Identifier getTextureResource(BananasaurusEntity object) {
		return Identifier.of("pvzmod", "textures/entity/bananasaurus/bananasaurus.png");
	}

    @Override
    public Identifier getAnimationResource(BananasaurusEntity object)
    {
        return Identifier.of ("pvzmod", "animations/bananasaurus.json");
    }
}
