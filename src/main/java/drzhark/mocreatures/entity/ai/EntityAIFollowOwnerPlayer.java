/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.UUID;

public class EntityAIFollowOwnerPlayer extends Goal {

    private final MobEntity thePet;
    private final double speed;
    private final PathNavigator petPathfinder;
    World world;
    float maxDist;
    float minDist;
    private PlayerEntity theOwner;
    private int delayCounter;

    public EntityAIFollowOwnerPlayer(MobEntity thePetIn, double speedIn, float minDistIn, float maxDistIn) {
        this.thePet = thePetIn;
        this.world = thePetIn.world;
        this.speed = speedIn;
        this.petPathfinder = thePetIn.getNavigator();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));

        //if (!(thePetIn.getNavigator() instanceof PathNavigateGround)) {
        //System.out.println("exiting due to first illegal argument");
        //    throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        //}
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (((IMoCEntity) this.thePet).getIsSitting()) {
            return false;
        }

        UUID ownerUniqueId = ((IMoCTameable) this.thePet).getOwnerId();
        if (ownerUniqueId == null) {
            return false;
        }

        PlayerEntity entityplayer = EntityAITools.getIMoCTameableOwner((IMoCTameable) this.thePet);

        if (entityplayer == null) {
            return false;
        } else if (this.thePet.getDistanceSq(entityplayer) < this.minDist * this.minDist
                || this.thePet.getDistanceSq(entityplayer) > this.maxDist * this.maxDist) {
            return false;
        } else {
            this.theOwner = entityplayer;
            return true;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.petPathfinder.noPath() && this.thePet.getDistanceSq(this.theOwner) > this.maxDist * this.maxDist
                && !((IMoCEntity) this.thePet).getIsSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.delayCounter = 0;
        //this.flag = ((PathNavigateGround) this.thePet.getNavigator()).getAvoidsWater();
        //((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPath();
        //((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(true); //TODO
    }

    private boolean isEmptyBlock(BlockPos pos) {
        BlockState iblockstate = this.world.getBlockState(pos);
        return iblockstate.getMaterial() == Material.AIR || !iblockstate.hasOpaqueCollisionShape(this.world, pos);
    }

    public void tick() {
        this.thePet.getLookController().setLookPositionWithEntity(this.theOwner, 10.0F, (float) this.thePet.getVerticalFaceSpeed());

        if (!((IMoCEntity) this.thePet).getIsSitting()) {
            if (--this.delayCounter <= 0) {
                this.delayCounter = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.speed)) {
                    if (!this.thePet.getLeashed()) {
                        if (this.thePet.getDistanceSq(this.theOwner) >= 144.0D) {
                            int i = MathHelper.floor(this.theOwner.getPosX()) - 2;
                            int j = MathHelper.floor(this.theOwner.getPosZ()) - 2;
                            int k = MathHelper.floor(this.theOwner.getBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l) {
                                for (int i1 = 0; i1 <= 4; ++i1) {
                                    final BlockPos pos = new BlockPos(i + l, k - 1, j + i1);
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.world.getBlockState(pos).isSolidSide(world, pos, Direction.DOWN) && this.isEmptyBlock(new BlockPos(i + l, k, j + i1)) && this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1))) {
                                        this.thePet.setLocationAndAngles((float) (i + l) + 0.5F, k, (float) (j + i1) + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
                                        this.petPathfinder.clearPath();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
