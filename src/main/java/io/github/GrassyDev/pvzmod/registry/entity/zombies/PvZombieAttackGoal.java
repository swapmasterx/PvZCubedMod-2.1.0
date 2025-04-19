package io.github.GrassyDev.pvzmod.registry.entity.zombies;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Hand;

public class PvZombieAttackGoal extends MeleeAttackGoal {

   private final GeneralPvZombieEntity pvzombie;

   private int ticks;


   public PvZombieAttackGoal(GeneralPvZombieEntity pvzombie, double speed, boolean pauseWhenMobIdle) {
      super(pvzombie, speed, pauseWhenMobIdle);

      this.pvzombie = pvzombie;

   }

//	protected double getSquaredMaxAttackDistance(LivingEntity entity) {
//		float f = (float) pvzombie.getBaseValue(ReachEntityAttributes.ATTACK_RANGE);
//		return f * f;
//	}
	@Override
	protected void attack(LivingEntity entity) {
		float f = (float) pvzombie.getBaseValue(ReachEntityAttributes.ATTACK_RANGE);
		if (this.pvzombie.squaredDistanceTo(entity) <= (double)((entity.getWidth() * f + 0.5F) * (entity.getWidth() * f + 0.5F))) {
			if (this.isCooledDown()){
//			if (this.method_53715(entity)) {
				this.pvzombie.tryAttack(entity);
				this.resetCooldown();
			}
		}
	}
   public void start() {
      super.start();
      this.ticks = 0;

   }

   public void stop() {
      super.stop();
      this.pvzombie.setAttacking(false);
   }

   public void tick() {
      super.tick();
      ++this.ticks;
      if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
         this.pvzombie.setAttacking(true);
      } else {
         this.pvzombie.setAttacking(false);
      }
   }
}
