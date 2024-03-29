package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.GargantuarVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GargantuarEntityModel extends AnimatedGeoModel<GargantuarEntity> {

    @Override
    public Identifier getModelResource(GargantuarEntity object)
    {
		return GargantuarEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(GargantuarEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargantuar.png");
		if (object.getVariant().equals(GargantuarVariants.UNICORNGARGANTUAR) || object.getVariant().equals(GargantuarVariants.UNICORNGARGANTUARHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/unicorngargantuar.png");
		}
		else if (object.getVariant().equals(GargantuarVariants.GARGOLITH) || object.getVariant().equals(GargantuarVariants.GARGOLITHHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargolith.png");
		}
		else if (object.getVariant().equals(GargantuarVariants.MUMMY) || object.getVariant().equals(GargantuarVariants.MUMMYHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/mummygargantuar.png");
		}
		else if (object.getVariant().equals(GargantuarVariants.DEFENSIVEEND) || object.getVariant().equals(GargantuarVariants.DEFENSIVEENDHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend.png");
			if (object.gear1less){
				identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_gearless.png");
			}
		}
		else if (object.getVariant().equals(GargantuarVariants.DEFENSIVEEND_NEWYEAR) || object.getVariant().equals(GargantuarVariants.DEFENSIVEEND_NEWYEARHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_newyear.png");
			if (object.gear1less){
				identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_newyear_gearless.png");
			}
		}
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(GargantuarEntity object)
    {
        return new Identifier ("pvzmod", "animations/gargantuar.json");
    }
}
