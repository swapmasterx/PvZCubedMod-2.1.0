package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChesterEntityModel extends AnimatedGeoModel<ChesterEntity> {

    @Override
    public Identifier getModelResource(ChesterEntity object)
    {
        return new Identifier("pvzmod", "geo/chester.geo.json");
    }

	public Identifier getTextureResource(ChesterEntity object) {
		return new  Identifier(PvZCubed.MOD_ID, "textures/entity/chomper/chester.png");
	}

    @Override
    public Identifier getAnimationResource(ChesterEntity object)
    {
        return new Identifier ("pvzmod", "animations/chomper.json");
    }
}
