package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercespore;

import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShadowSporeVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PierceSporeEntityModel extends GeoModel<PierceSporeEntity> {

    @Override
    public Identifier getModelResource(PierceSporeEntity object)
    {
        return Identifier.of("pvzmod", "geo/piercespore.geo.json");
    }

    @Override
    public Identifier getTextureResource(PierceSporeEntity object)
    {
		if (object.getVariant().equals(ShadowSporeVariants.SHADOW)) {
			return Identifier.of("pvzmod", "textures/entity/projectiles/shadowfume.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/projectiles/fume.png");
		}
    }

    @Override
    public Identifier getAnimationResource(PierceSporeEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
