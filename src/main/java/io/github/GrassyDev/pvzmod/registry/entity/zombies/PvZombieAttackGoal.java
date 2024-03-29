package io.github.GrassyDev.pvzmod.registry.entity.zombies;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class PvZombieAttackGoal extends MeleeAttackGoal {

   private final GeneralPvZombieEntity pvzombie;
   private int ticks;

   public PvZombieAttackGoal(GeneralPvZombieEntity pvzombie, double speed, boolean pauseWhenMobIdle) {
      super(pvzombie, speed, pauseWhenMobIdle);
      this.pvzombie = pvzombie;
   }

   @Override
	protected double getSquaredMaxAttackDistance(LivingEntity entity) {
	   float f = (float) pvzombie.getAttributeBaseValue(ReachEntityAttributes.ATTACK_RANGE);
	   return f * f;
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
