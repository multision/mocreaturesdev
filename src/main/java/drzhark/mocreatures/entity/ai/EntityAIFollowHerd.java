/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;

public class EntityAIFollowHerd extends Goal {

    /**
     * The child that is following its parent.
     */
    MobEntity theAnimal;
    MobEntity herdAnimal;
    double moveSpeed;
    double maxRange;
    double minRange;
    private int delayCounter;
    private int executionChance;

    public EntityAIFollowHerd(MobEntity animal, double speed, double minRangeIn, double maxRangeIn, int chance) {
        this.theAnimal = animal;
        this.moveSpeed = speed;
        this.minRange = minRangeIn; //4D;
        this.maxRange = maxRangeIn; //20D;
        this.executionChance = chance;
    }

    public EntityAIFollowHerd(MobEntity animal, double speed) {
        this(animal, speed, 4D, 20D, 120);
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean shouldExecute() {

        if (this.theAnimal.getRNG().nextInt(this.executionChance) != 0) {
            return false;
        }

        List<MobEntity> list =
                this.theAnimal.world.getEntitiesWithinAABB(this.theAnimal.getClass(),
                        this.theAnimal.getBoundingBox().grow(this.maxRange, 4.0D, this.maxRange));
        MobEntity entityliving = null;
        double d0 = Double.MAX_VALUE;

        for (MobEntity entityliving1 : list) {
            double d1 = this.theAnimal.getDistanceSq(entityliving1);
            if (d1 >= this.minRange && this.theAnimal != entityliving1) {
                d0 = d1;
                entityliving = entityliving1;
            }

        }

        if (entityliving == null) {
            //System.out.println("didn't find any herd");
            return false;

        } else if (d0 < this.maxRange) {
            //System.out.println("herd is too close: " + d0);

            return false;
        } else {
            this.herdAnimal = entityliving;
            //System.out.println("found herd " + entityliving);
            return true;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        if (this.theAnimal instanceof IMoCEntity && ((IMoCEntity) this.theAnimal).isMovementCeased()) { //System.out.println("returning, movement ceased");
            return false;
        } else if (!this.herdAnimal.isAlive()) {
            return false;
        } else {
            double d0 = this.theAnimal.getDistanceSq(this.herdAnimal);
            return d0 >= this.minRange && d0 <= this.maxRange;
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
        this.herdAnimal = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 20;
            //System.out.println("moving " + this + " to " + this.herdAnimal);
            this.theAnimal.getNavigator().tryMoveToEntityLiving(this.herdAnimal, this.moveSpeed);
        }
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}
