package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SuperChomperEntityModel extends GeoModel<SuperChomperEntity> {

    @Override
    public Identifier getModelResource(SuperChomperEntity object)
    {
        return new Identifier("pvzmod", "geo/superchomper.geo.json");
    }

	public Identifier getTextureResource(SuperChomperEntity object) {
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/chomper/superchomper.png");
	}

    @Override
    public Identifier getAnimationResource(SuperChomperEntity object)
    {
        return new Identifier ("pvzmod", "animations/chomper.json");
    }
}
