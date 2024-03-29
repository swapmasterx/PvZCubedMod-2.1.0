package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VampireFlowerEntityModel extends AnimatedGeoModel<VampireFlowerEntity> {

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
