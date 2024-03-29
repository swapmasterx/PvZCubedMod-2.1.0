package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;

public abstract class SmallAnimalEntity extends PvZombieEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";

    public SmallAnimalEntity(EntityType<? extends SmallAnimalEntity> entityType, World world) {
        super(entityType, world);
    }

	static {

	}

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				if (this.isOnGround() || this.isInsideWaterOrBubbleColumn()){
					this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
					this.setTarget(CollidesWithPlant(0.1f, 0f));
				}
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()){
				this.setTarget(CollidesWithPlayer(1.5f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
		List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, PvZEntity.IMP.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()).expand(0.25));
		List<PlantEntity> list1 = new ArrayList<>();
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof PlantEntity plantEntity && (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("tall") || PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"))){
				list1.add(plantEntity);
			}
		}
		if (!list1.isEmpty() && !this.hasStatusEffect(PvZCubed.BOUNCED) && !this.onGround && !this.isInsideWaterOrBubbleColumn()){
			this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
			this.setTarget(list1.get(0));
		}
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence)).multiply((double)speed);
		this.setVelocity(vec3d);
		double d = vec3d.horizontalLength();
		this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
		this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
		this.prevYaw = this.getYaw();
		this.prevPitch = this.getPitch();
	}
}
