package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlagzombieEntityModel extends GeoModel<FlagzombieEntity> {

    @Override
    public Identifier getModelResource(FlagzombieEntity object)
    {
        return Identifier.of("pvzmod", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlagzombieEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(FlagZombieVariants.GAY) || object.getVariant().equals(FlagZombieVariants.GAYHYPNO)){
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g.png");
			if (object.armless){
				identifier = Identifier.of("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g_dmg1.png");
			}
		}
		else if (object.getVariant().equals(FlagZombieVariants.TRANS) || object.getVariant().equals(FlagZombieVariants.TRANSHYPNO)){
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t.png");
			if (object.armless){
				identifier = Identifier.of("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t_dmg1.png");
			}
		}
		else {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat.png");
			if (object.armless){
				identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(FlagzombieEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
