package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.seapea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SeapeaEntityModel extends GeoModel<SeapeaEntity> {

    @Override
    public Identifier getModelResource(SeapeaEntity object)
    {
        return new Identifier("pvzmod", "geo/seapea.geo.json");
    }

	public Identifier getTextureResource(SeapeaEntity object) {
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/seapea.png");
	}

    @Override
    public Identifier getAnimationResource(SeapeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
