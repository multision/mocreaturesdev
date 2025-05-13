/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class EntityAIMateMoC extends Goal {
    private final MoCEntityTameableAnimal animal;
    private final Class<? extends MoCEntityTameableAnimal> mateClass;
    World world;
    /**
     * Delay preventing a baby from spawning immediately when two mate-able animals find each other.
     */
    int spawnBabyDelay;
    /**
     * The speed the creature moves at during mating behavior.
     */
    double moveSpeed;
    private MoCEntityTameableAnimal targetMate;

    public EntityAIMateMoC(MoCEntityTameableAnimal animal, double speedIn) {
        this(animal, speedIn, animal.getClass());
    }

    public EntityAIMateMoC(MoCEntityTameableAnimal p_i47306_1_, double p_i47306_2_, Class<? extends MoCEntityTameableAnimal> p_i47306_4_) {
        this.animal = p_i47306_1_;
        this.world = p_i47306_1_.world;
        this.mateClass = p_i47306_4_;
        this.moveSpeed = p_i47306_2_;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.targetMate = this.getNearbyMate();
            return this.targetMate != null;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.animal.getLookController().setLookPositionWithEntity(this.targetMate, 10.0F, (float) this.animal.getVerticalFaceSpeed());
        this.animal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;

        if (this.spawnBabyDelay >= 60 && this.animal.getDistanceSq(this.targetMate) < 9.0D) {
            this.spawnBaby();
        }
    }

    /**
     * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
     * valid mate found.
     */
    private MoCEntityTameableAnimal getNearbyMate() {
        List<MoCEntityTameableAnimal> list = this.world.getEntitiesWithinAABB(mateClass, this.animal.getBoundingBox().grow(8.0D));
        double d0 = Double.MAX_VALUE;
        MoCEntityTameableAnimal entityanimal = null;

        for (MoCEntityTameableAnimal entityanimal1 : list) {
            if (this.animal.canMateWith(entityanimal1) && this.animal.getDistanceSq(entityanimal1) < d0) {
                entityanimal = entityanimal1;
                d0 = this.animal.getDistanceSq(entityanimal1);
            }
        }

        return entityanimal;
    }

    /**
     * Spawns a baby animal of the same type.
     */
    private void spawnBaby() {
        AgeableEntity entityageable = this.animal.createChild((ServerWorld)this.world, this.targetMate);

        if (entityageable != null) {
            ServerPlayerEntity entityplayermp = this.animal.getLoveCause();

            if (entityplayermp == null && this.targetMate.getLoveCause() != null) {
                entityplayermp = this.targetMate.getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.addStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this.animal, this.targetMate, entityageable);
            }

            // Exclude Males from the reset.
            if (this.animal.getTypeMoC() != 1) {
                this.animal.setGrowingAge(6000);
                this.animal.resetInLove();
            }

            // Exclude Males from the reset.
            if (this.targetMate.getTypeMoC() != 1) {
                this.targetMate.setGrowingAge(6000);
                this.targetMate.resetInLove();
            }

            entityageable.setGrowingAge(-24000);
            entityageable.setLocationAndAngles(this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), 0.0F, 0.0F);
            if (entityageable instanceof MoCEntityTurkey) {
                // Randomly select sex of spawn.
                ((MoCEntityTurkey) entityageable).selectType();
            }

            this.world.addEntity(entityageable);
            Random random = this.animal.getRNG();

            for (int i = 0; i < 7; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextDouble() * (double) this.animal.getWidth() * 2.0D - (double) this.animal.getWidth();
                double d4 = 0.5D + random.nextDouble() * (double) this.animal.getHeight();
                double d5 = random.nextDouble() * (double) this.animal.getWidth() * 2.0D - (double) this.animal.getWidth();
                this.world.addParticle(ParticleTypes.HEART, this.animal.getPosX() + d3, this.animal.getPosY() + d4, this.animal.getPosZ() + d5, d0, d1, d2);
            }

            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), random.nextInt(7) + 1));
            }
        }
    }
}