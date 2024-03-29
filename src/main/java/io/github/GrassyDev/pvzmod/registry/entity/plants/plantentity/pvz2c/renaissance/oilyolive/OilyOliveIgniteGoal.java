package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.renaissance.oilyolive;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class OilyOliveIgniteGoal extends Goal {
    private final OilyOliveEntity oilyOliveEntity;
    private LivingEntity target;

    public OilyOliveIgniteGoal(OilyOliveEntity oilyOliveEntity) {
        this.oilyOliveEntity = oilyOliveEntity;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.oilyOliveEntity.getTarget();
        return this.oilyOliveEntity.getFuseSpeed() > 0 || livingEntity != null && this.oilyOliveEntity.squaredDistanceTo(livingEntity) < 9.0D;
    }

    public void start() {
        this.oilyOliveEntity.getNavigation().stop();
        this.target = this.oilyOliveEntity.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.oilyOliveEntity.setFuseSpeed(-1);
        } else if (this.oilyOliveEntity.squaredDistanceTo(this.target) > 9.0D || this.oilyOliveEntity.isInsideWaterOrBubbleColumn()) {
            this.oilyOliveEntity.setFuseSpeed(-1);
        } else {
            this.oilyOliveEntity.setFuseSpeed(1);
        }
    }
}
