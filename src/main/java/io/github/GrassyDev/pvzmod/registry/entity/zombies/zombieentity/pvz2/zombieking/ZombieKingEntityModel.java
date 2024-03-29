package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.zombieking;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ZombieKingVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZombieKingEntityModel extends AnimatedGeoModel<ZombieKingEntity> {

    @Override
    public Identifier getModelResource(ZombieKingEntity object)
    {
		if (object.getColor().equals(ZombieKingVariants.RED)) {
			return new Identifier("pvzmod", "geo/redzombieking.geo.json");
		}
		else if (object.getColor().equals(ZombieKingVariants.BLACK)) {
			return new Identifier("pvzmod", "geo/blackzombieking.geo.json");
		}
		else {
			return new Identifier("pvzmod", "geo/zombieking.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(ZombieKingEntity object)
    {
		if (object.getColor().equals(ZombieKingVariants.RED)) {
			if (object.gear1less) {
				return new Identifier("pvzmod", "textures/entity/zombieking/zombieking_red_gearless.png");
			}
			else {
				return new Identifier("pvzmod", "textures/entity/zombieking/zombieking_red.png");
			}
		}
		else if (object.getColor().equals(ZombieKingVariants.BLACK)) {
			if (object.gear1less) {
				return new Identifier("pvzmod", "textures/entity/zombieking/zombieking_black_gearless.png");
			}
			else {
				return new Identifier("pvzmod", "textures/entity/zombieking/zombieking_black.png");
			}
		}
		else {
			return new Identifier("pvzmod", "textures/entity/zombieking/zombieking.png");
		}
    }

    @Override
    public Identifier getAnimationResource(ZombieKingEntity object)
    {
        return new Identifier ("pvzmod", "animations/zombieking.json");
    }
}
