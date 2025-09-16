 package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.melonslice;


import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
public class ShootingMelonSliceEntityModel extends GeoModel<ShootingMelonSliceEntity> {

	@Override
	public Identifier getModelResource(ShootingMelonSliceEntity object)
	{
		return Identifier.of("pvzmod", "geo/slice.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShootingMelonSliceEntity object){
		return Identifier.of("pvzmod", "textures/entity/cabbagepult/melonslice.png");
	}

	@Override
	public Identifier getAnimationResource(ShootingMelonSliceEntity object)
	{
		return Identifier.of ("pvzmod", "animations/peashot.json");
	}
}
