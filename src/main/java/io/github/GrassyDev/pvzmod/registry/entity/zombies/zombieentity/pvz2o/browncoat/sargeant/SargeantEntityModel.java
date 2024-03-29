package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.browncoat.sargeant;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SargeantEntityModel extends AnimatedGeoModel<SargeantEntity> {

    @Override
    public Identifier getModelResource(SargeantEntity object)
    {
		return SargeantEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(SargeantEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant.png");
		if (object.getType().equals(PvZEntity.BOOKBURNER) || object.getType().equals(PvZEntity.BOOKBURNERHYPNO)){
			if (object.getFireStage()) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant.png");
				if (object.armless && object.gear1less) {
					identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_gearless_dmg1.png");
				} else if (object.gear1less) {
					identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_gearless.png");
				} else if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_dmg1.png");
				}
			} else {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_extinguished.png");
				if (object.armless && object.gear1less) {
					identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_gearless_dmg1.png");
				} else if (object.gear1less) {
					identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_gearless.png");
				} else if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_dmg1_extinguished.png");
				}
			}
		}
		else {
			if (object.armless && object.geardmg2 && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_dmg1_shielddmg1_hatless.png");
			} else if (object.armless && object.geardmg && object.gear2less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_dmg1_helmetdmg1_shieldless.png");
			} else if (object.armless && object.geardmg2 && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_dmg1_geardmg1.png");
			} else if (object.armless && object.geardmg && object.getArmor2() != null) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_dmg1_helmetdmg1.png");
			} else if (object.armless && object.geardmg2 && object.getArmor2() != null) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_dmg1_shielddmg1.png");
			} else if (object.armless && object.gearless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_gearless_dmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_dmg1_hatless.png");
			} else if (object.armless && object.gear2less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_dmg1_shieldless.png");
			} else if (object.geardmg2 && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_shielddmg1_hatless.png");
			} else if (object.geardmg && object.gear2less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_helmetdmg1_shieldless.png");
			} else if (object.geardmg2 && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_geardmg1.png");
			} else if (object.geardmg && object.getArmor2() != null) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_helmetdmg1.png");
			} else if (object.geardmg2 && object.getArmor2() != null) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_shielddmg1.png");
			} else if (object.gearless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_gearless.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_hatless.png");
			} else if (object.gear2less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/shield/sargeant_shieldless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/sargeant/sargeant_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(SargeantEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
