package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GloomVineEntityModel extends GeoModel<GloomVineEntity> {

    @Override
    public Identifier getModelResource(GloomVineEntity object)
    {
        return Identifier.of("pvzmod", "geo/gloomvine.geo.json");
    }

	public Identifier getTextureResource(GloomVineEntity object) {
		return Identifier.of(PvZCubed.MOD_ID, "textures/entity/vines/gloomvine.png");
	}

    @Override
    public Identifier getAnimationResource(GloomVineEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gloomvine.json");
    }
}
