/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;

public class EntityAIFollowAdult extends Goal {

    /**
     * The child that is following its parent.
     */
    MobEntity childAnimal;
    MobEntity parentAnimal;
    double moveSpeed;
    private int delayCounter;

    public EntityAIFollowAdult(MobEntity animal, double speed) {
        this.childAnimal = animal;
        this.moveSpeed = speed;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if ((!(this.childAnimal instanceof IMoCEntity)) || ((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else {
            List<MobEntity> list =
                    this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(),
                            this.childAnimal.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
            MobEntity entityliving = null;
            double d0 = Double.MAX_VALUE;

            for (MobEntity entityliving1 : list) {
                if (((IMoCEntity) entityliving1).getIsAdult()) {
                    double d1 = this.childAnimal.getDistanceSq(entityliving1);

                    if (d1 <= d0) {
                        d0 = d1;
                        entityliving = entityliving1;
                    }
                }
            }

            if (entityliving == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parentAnimal = entityliving;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        if (((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else if (!this.parentAnimal.isAlive()) {
            return false;
        } else {
            double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.delayCounter = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
}
