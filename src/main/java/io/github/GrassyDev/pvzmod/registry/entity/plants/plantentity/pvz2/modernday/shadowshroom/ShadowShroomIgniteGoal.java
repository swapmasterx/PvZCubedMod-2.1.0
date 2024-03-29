package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.modernday.shadowshroom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ShadowShroomIgniteGoal extends Goal {
    private final ShadowShroomEntity shadowShroomEntity;
    private LivingEntity target;

    public ShadowShroomIgniteGoal(ShadowShroomEntity shadowShroomEntity) {
        this.shadowShroomEntity = shadowShroomEntity;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.shadowShroomEntity.getTarget();
        return this.shadowShroomEntity.getFuseSpeed() > 0 || livingEntity != null && this.shadowShroomEntity.squaredDistanceTo(livingEntity) < 2.25D;
    }

    public void start() {
        this.shadowShroomEntity.getNavigation().stop();
        this.target = this.shadowShroomEntity.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.shadowShroomEntity.setFuseSpeed(-1);
        } else if (this.shadowShroomEntity.squaredDistanceTo(this.target) > 2.25D || this.shadowShroomEntity.isInsideWaterOrBubbleColumn()) {
            this.shadowShroomEntity.setFuseSpeed(-1);
        } else {
            this.shadowShroomEntity.setFuseSpeed(1);
        }
    }
}
