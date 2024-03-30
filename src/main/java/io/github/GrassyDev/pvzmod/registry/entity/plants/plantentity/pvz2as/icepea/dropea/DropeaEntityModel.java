package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.icepea.dropea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DropeaEntityModel extends GeoModel<DropeaEntity> {

    @Override
    public Identifier getModelResource(DropeaEntity object)
    {
        return new Identifier("pvzmod", "geo/dropea.geo.json");
    }

	public Identifier getTextureResource(DropeaEntity object) {
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/dropea.png");
	}

    @Override
    public Identifier getAnimationResource(DropeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
