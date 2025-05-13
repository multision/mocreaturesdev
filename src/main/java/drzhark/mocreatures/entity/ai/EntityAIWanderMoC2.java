/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class EntityAIWanderMoC2 extends Goal {

    private final CreatureEntity entity;
    private final double speed;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private int executionChance;
    private boolean mustUpdate;

    public EntityAIWanderMoC2(CreatureEntity creatureIn, double speedIn) {
        this(creatureIn, speedIn, 120);
    }

    public EntityAIWanderMoC2(CreatureEntity creatureIn, double speedIn, int chance) {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.entity instanceof IMoCEntity && ((IMoCEntity) this.entity).isMovementCeased()) {
            return false;
        }
        if (this.entity.isBeingRidden() && !(this.entity instanceof MoCEntityAnt || this.entity instanceof MoCEntityMob)) {
            return false;
        }

        if (!this.mustUpdate) {
            if (this.entity.getIdleTime() >= 100) {
                //System.out.println("exiting path finder !mustUpdate + Age > 100" + this.entity);
                return false;
            }

            if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
                //System.out.println(this.entity + "exiting due executionChance, age = " + this.entity.getIdleTime() + ", executionChance = " + this.executionChance );
                return false;
            }
        }

        Vector3d vec3 = RandomPositionGeneratorMoCFlyer.findRandomTarget(this.entity, 10, 12);

        if (vec3 != null && this.entity instanceof IMoCEntity && this.entity.getNavigator() instanceof PathNavigateFlyer) {
            int distToFloor = MoCTools.distanceToFloor(this.entity);
            int finalYHeight = distToFloor + MathHelper.floor(vec3.y - this.entity.getPosY());
            if ((finalYHeight < ((IMoCEntity) this.entity).minFlyingHeight())) {
                //System.out.println("vector height " + finalYHeight + " smaller than min flying height " + ((IMoCEntity) this.entity).minFlyingHeight());
                return false;
            }
            if ((finalYHeight > ((IMoCEntity) this.entity).maxFlyingHeight())) {
                //System.out.println("vector height " + finalYHeight + " bigger than max flying height " + ((IMoCEntity) this.entity).maxFlyingHeight());
                return false;
            }

        }

        if (vec3 == null) {
            //System.out.println("exiting path finder null Vec3");
            return false;
        } else {
            //System.out.println("found vector " + vec3.x + ", " +  vec3.y + ", " + vec3.z);
            this.xPosition = vec3.x;
            this.yPosition = vec3.y;
            this.zPosition = vec3.z;
            this.mustUpdate = false;
            return true;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entity.getNavigator().noPath() && !entity.isBeingRidden();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        //System.out.println(this.entity + "moving to " + this.xPosition + ", " + this.yPosition + ", " + this.zPosition);
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate() {
        //System.out.println(entity + " has forced update");
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}
