package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GloomVineEntityModel extends AnimatedGeoModel<GloomVineEntity> {

    @Override
    public Identifier getModelResource(GloomVineEntity object)
    {
        return new Identifier("pvzmod", "geo/gloomvine.geo.json");
    }

	public Identifier getTextureResource(GloomVineEntity object) {
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/vines/gloomvine.png");
	}

    @Override
    public Identifier getAnimationResource(GloomVineEntity object)
    {
        return new Identifier ("pvzmod", "animations/gloomvine.json");
    }
}
