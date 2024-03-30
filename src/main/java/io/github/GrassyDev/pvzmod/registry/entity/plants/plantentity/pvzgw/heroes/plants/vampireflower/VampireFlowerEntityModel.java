package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class VampireFlowerEntityModel extends GeoModel<VampireFlowerEntity> {

    @Override
    public Identifier getModelResource(VampireFlowerEntity object)
    {
        return new Identifier("pvzmod", "geo/vampireflower.geo.json");
    }

	public Identifier getTextureResource(VampireFlowerEntity object) {
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/vampiresunflower.png");
	}

    @Override
    public Identifier getAnimationResource(VampireFlowerEntity object)
    {
        return new Identifier ("pvzmod", "animations/sunflower.json");
    }
}
