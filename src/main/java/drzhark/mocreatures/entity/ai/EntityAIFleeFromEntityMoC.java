/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class EntityAIFleeFromEntityMoC extends Goal {

    private final double farSpeed;
    private final double nearSpeed;
    private final float avoidDistance;
    private final Predicate<Entity> avoidTargetSelector;
    /**
     * The entity we are attached to
     */
    protected CreatureEntity entity;
    public final Predicate<Entity> canBeSeenSelector = new Predicate<Entity>() {

        public boolean isApplicable(Entity entityIn) {
            return entityIn.isAlive() && EntityAIFleeFromEntityMoC.this.entity.getEntitySenses().canSee(entityIn);
        }

        @Override
        public boolean test(Entity p_apply_1_) {
            return this.isApplicable(p_apply_1_);
        }
    };
    protected Entity closestLivingEntity;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromEntityMoC(CreatureEntity creature, Predicate<Entity> targetSelector, float searchDistance, double farSpeedIn, double nearSpeedIn) {
        this.entity = creature;
        this.avoidTargetSelector = targetSelector;
        this.avoidDistance = searchDistance;
        this.farSpeed = farSpeedIn;
        this.nearSpeed = nearSpeedIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean shouldExecute() {
        if (this.entity instanceof IMoCEntity && ((IMoCEntity) this.entity).isNotScared()) {
            return false;
        }

        if (this.entity instanceof MoCEntityAquatic && !this.entity.isInWater()) {
            return false;
        }

        List<Entity> list =
                this.entity.world.getEntitiesInAABBexcluding(this.entity,
                        this.entity.getBoundingBox().grow(this.avoidDistance, 3.0D, this.avoidDistance),
                        (EntityPredicates.NOT_SPECTATING.and(this.canBeSeenSelector).and(this.avoidTargetSelector)));

        if (list.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list.get(0);
            Vector3d vec3 =
                    RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vector3d(this.closestLivingEntity.getPosX(),
                            this.closestLivingEntity.getPosY(), this.closestLivingEntity.getPosZ()));

            if (vec3 == null) {
                return false;
            } else if (this.closestLivingEntity.getDistanceSq(vec3.x, vec3.y, vec3.z) < this.closestLivingEntity
                    .getDistanceSq(this.entity)) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.nearSpeed);
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (this.entity.getDistanceSq(this.closestLivingEntity) < 8.0D) {
            this.entity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
