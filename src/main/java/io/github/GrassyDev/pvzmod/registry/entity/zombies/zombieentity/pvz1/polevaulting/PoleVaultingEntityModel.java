package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.polevaulting;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PoleVaultingEntityModel extends GeoModel<PoleVaultingEntity> {

    @Override
    public Identifier getModelResource(PoleVaultingEntity object)
    {
        return Identifier.of("pvzmod", "geo/polevaulting.geo.json");
    }

    @Override
    public Identifier getTextureResource(PoleVaultingEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/polevaulting/polevaulting.png");
		if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/polevaulting/polevaulting_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PoleVaultingEntity object)
    {
        return Identifier.of ("pvzmod", "animations/polevaulting.json");
    }
}
