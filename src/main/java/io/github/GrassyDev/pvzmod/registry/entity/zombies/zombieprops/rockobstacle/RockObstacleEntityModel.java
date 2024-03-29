package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RockObstacleEntityModel extends AnimatedGeoModel<RockObstacleEntity> {

    @Override
    public Identifier getModelResource(RockObstacleEntity object)
    {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "geo/egypttombstone.geo.json");
		if (object.getType().equals(PvZEntity.GARGOLITHOBSTACLE)) {
			identifier = new Identifier("pvzmod", "geo/gargolith.geo.json");
		}
		else if (object.getType().equals(PvZEntity.IMPTABLETOBSTACLE)) {
			identifier = new Identifier("pvzmod", "geo/imptablet.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(RockObstacleEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/tombstone/egypttombstone.png");
		if (object.getType().equals(PvZEntity.GARGOLITHOBSTACLE)) {
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargolith.png");
		}
		else if (object.getType().equals(PvZEntity.IMPTABLETOBSTACLE)) {
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargolith.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(RockObstacleEntity object)
    {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "animations/gravestone.json");
		if (object.getType().equals(PvZEntity.GARGOLITHOBSTACLE)) {
			identifier = new Identifier("pvzmod", "animations/gargantuar.json");
		}
		return identifier;
    }
}
