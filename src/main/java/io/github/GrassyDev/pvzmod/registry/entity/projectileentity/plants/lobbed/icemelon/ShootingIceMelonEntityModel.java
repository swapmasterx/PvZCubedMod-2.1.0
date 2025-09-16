package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.icemelon;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
public class ShootingIceMelonEntityModel extends GeoModel<ShootingIceMelonEntity> {

	@Override
	public Identifier getModelResource(ShootingIceMelonEntity object)
	{
		return Identifier.of("pvzmod", "geo/melon.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShootingIceMelonEntity object){
		return Identifier.of("pvzmod", "textures/entity/cabbagepult/wintermelon.png");
	}

	@Override
	public Identifier getAnimationResource(ShootingIceMelonEntity object)
	{
		return Identifier.of ("pvzmod", "animations/peashot.json");
	}
}
