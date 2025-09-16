 package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.melon;

 import net.minecraft.util.Identifier;
 import software.bernie.geckolib.model.GeoModel;


 public class ShootingMelonEntityModel extends GeoModel<ShootingMelonEntity> {

	 @Override
	 public Identifier getModelResource(ShootingMelonEntity object)
	 {
		 return Identifier.of("pvzmod", "geo/melon.geo.json");
	 }

	 @Override
	 public Identifier getTextureResource(ShootingMelonEntity object){
		 return Identifier.of("pvzmod", "textures/entity/cabbagepult/melonpult.png");
	 }

	 @Override
	 public Identifier getAnimationResource(ShootingMelonEntity object)
	 {
		 return Identifier.of ("pvzmod", "animations/peashot.json");
	 }
 }
