package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class VampireFlowerEntityModel extends GeoModel<VampireFlowerEntity> {

    @Override
    public Identifier getModelResource(VampireFlowerEntity object)
    {
        return Identifier.of("pvzmod", "geo/vampireflower.geo.json");
    }

	public Identifier getTextureResource(VampireFlowerEntity object) {
		return Identifier.of(PvZCubed.MOD_ID, "textures/entity/sunflower/vampiresunflower.png");
	}

    @Override
    public Identifier getAnimationResource(VampireFlowerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/sunflower.json");
    }
}
