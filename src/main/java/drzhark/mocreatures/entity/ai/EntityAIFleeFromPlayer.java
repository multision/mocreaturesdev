/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class EntityAIFleeFromPlayer extends Goal {

    private final CreatureEntity entityCreature;
    protected double speed;
    protected double distance;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromPlayer(CreatureEntity creature, double speedIn, double distanceToCheck) {
        this.entityCreature = creature;
        this.distance = distanceToCheck;
        this.speed = speedIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean shouldExecute() {

        if (this.entityCreature instanceof IMoCEntity) {
            if (((IMoCEntity) this.entityCreature).isNotScared()) {
                return false;
            }
        }

        if (!this.IsNearPlayer(this.distance)) {
            return false;
        } else {
            Vector3d vec3 = RandomPositionGenerator.findRandomTarget(this.entityCreature, 5, 4);

            if (vec3 == null) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }
        }
    }

    protected boolean IsNearPlayer(double d) {
        PlayerEntity entityplayer1 = this.entityCreature.world.getClosestPlayer(this.entityCreature, d);
        return entityplayer1 != null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.entityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entityCreature.getNavigator().noPath();
    }
}
