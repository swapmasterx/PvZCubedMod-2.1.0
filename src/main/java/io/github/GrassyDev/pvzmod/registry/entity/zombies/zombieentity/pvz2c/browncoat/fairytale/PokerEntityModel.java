package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PokerVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PokerEntityModel extends AnimatedGeoModel<PokerEntity> {

    @Override
    public Identifier getModelResource(PokerEntity object)
    {
		return PokerEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(PokerEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker.png");
		if (object.getPoker().equals(PokerVariants.HEART)) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker.png");
			if (object.armless && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker_gearless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/poker_dmg1.png");
			}
		}
		if (object.getPoker().equals(PokerVariants.DIAMOND)) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/diamond/pokerdia.png");
			if (object.armless && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/diamond/pokerdia_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/diamond/pokerdia_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/diamond/pokerdia_gearless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/diamond/pokerdia_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/diamond/pokerdia_dmg1.png");
			}
		}
		if (object.getPoker().equals(PokerVariants.CLUB)) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/club/pokercl.png");
			if (object.armless && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/club/pokercl_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/club/pokercl_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/club/pokercl_gearless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/club/pokercl_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/club/pokercl_dmg1.png");
			}
		}
		if (object.getPoker().equals(PokerVariants.SPADE)) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/spade/pokersp.png");
			if (object.armless && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/spade/pokersp_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/spade/pokersp_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/spade/pokersp_gearless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/spade/pokersp_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/poker/spade/pokersp_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PokerEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
