package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dancingzombie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DancingZombieEntityModel extends GeoModel<DancingZombieEntity> {

    @Override
    public Identifier getModelResource(DancingZombieEntity object)
    {
        return Identifier.of("pvzmod", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(DancingZombieEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/dancingzombie.png");
		if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/dancingzombie_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(DancingZombieEntity object)
    {
        return Identifier.of ("pvzmod", "animations/dancingzombie.json");
    }
}
