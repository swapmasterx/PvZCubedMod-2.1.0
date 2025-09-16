package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smackadamia;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SmackadamiaEntityModel extends GeoModel<SmackadamiaEntity> {

    @Override
    public Identifier getModelResource(SmackadamiaEntity object)
    {
        return Identifier.of("pvzmod", "geo/smackadamia.geo.json");
    }

	public Identifier getTextureResource(SmackadamiaEntity object) {
		return Identifier.of("pvzmod", "textures/entity/loquat/smackadamia.png");
	}

    @Override
    public Identifier getAnimationResource(SmackadamiaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/smackadamia.json");
    }
}
