/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.Random;

public class RandomPositionGeneratorMoCFlyer {
    /**
     * used to store a direction when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
     * MULTIPLE findTowards and findAway calls, will share this var
     */
    private static Vector3d staticVector = Vector3d.ZERO;

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks
     */
    @Nullable
    public static Vector3d findRandomTarget(CreatureEntity entitycreatureIn, int xz, int y) {
        /*
          searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
          of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
         */
        return findRandomTargetBlock(entitycreatureIn, xz, y, null);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
     */
    @Nullable
    public static Vector3d findRandomTargetBlockTowards(CreatureEntity entitycreatureIn, int xz, int y, Vector3d targetVec3) {
        staticVector = targetVec3.subtract(entitycreatureIn.getPosX(), entitycreatureIn.getPosY(), entitycreatureIn.getPosZ());
        /*
          searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
          of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
         */
        return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    @Nullable
    public static Vector3d findRandomTargetBlockAwayFrom(CreatureEntity entitycreatureIn, int xz, int y, Vector3d targetVec3) {
        staticVector = (new Vector3d(entitycreatureIn.getPosX(), entitycreatureIn.getPosY(), entitycreatureIn.getPosZ())).subtract(targetVec3);
        /*
          searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
          of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
         */
        return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
    }

    /**
     * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction of
     * par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
     */
    @Nullable
    private static Vector3d findRandomTargetBlock(CreatureEntity entitycreatureIn, int xz, int y, @Nullable Vector3d targetVec3) {
        PathNavigator pathnavigate = entitycreatureIn.getNavigator();
        Random random = entitycreatureIn.getRNG();
        boolean flag = false;
        int i = 0;
        int j = 0;
        int k = 0;
        float f = -99999.0F;
        boolean flag1;

        if (entitycreatureIn.detachHome()) {
            double d0 = entitycreatureIn.getHomePosition().distanceSq(MathHelper.floor(entitycreatureIn.getPosX()), MathHelper.floor(entitycreatureIn.getPosY()), MathHelper.floor(entitycreatureIn.getPosZ()), false) + 4.0D;
            double d1 = entitycreatureIn.getMaximumHomeDistance() + (float) xz;
            flag1 = d0 < d1 * d1;
        } else {
            flag1 = false;
        }

        for (int j1 = 0; j1 < 10; ++j1) {
            int l = random.nextInt(2 * xz + 1) - xz;
            int k1 = random.nextInt(2 * y + 1) - y;
            int i1 = random.nextInt(2 * xz + 1) - xz;

            if (targetVec3 == null || (double) l * targetVec3.x + (double) i1 * targetVec3.z >= 0.0D) {
                if (entitycreatureIn.detachHome() && xz > 1) {
                    BlockPos blockpos = entitycreatureIn.getHomePosition();

                    if (entitycreatureIn.getPosX() > (double) blockpos.getX()) {
                        l -= random.nextInt(xz / 2);
                    } else {
                        l += random.nextInt(xz / 2);
                    }

                    if (entitycreatureIn.getPosZ() > (double) blockpos.getZ()) {
                        i1 -= random.nextInt(xz / 2);
                    } else {
                        i1 += random.nextInt(xz / 2);
                    }
                }

                BlockPos blockpos1 = new BlockPos((double) l + entitycreatureIn.getPosX(), (double) k1 + entitycreatureIn.getPosY(), (double) i1 + entitycreatureIn.getPosZ());

                if ((!flag1 || entitycreatureIn.isWithinHomeDistanceFromPosition(blockpos1)))// && pathnavigate.canEntityStandOnPos(blockpos1))
                {
                    float f1 = entitycreatureIn.getBlockPathWeight(blockpos1);

                    if (f1 > f) {
                        f = f1;
                        i = l;
                        j = k1;
                        k = i1;
                        flag = true;
                    }
                }
            }
        }

        if (flag) {
            return new Vector3d((double) i + entitycreatureIn.getPosX(), (double) j + entitycreatureIn.getPosY(), (double) k + entitycreatureIn.getPosZ());
        } else {
            return null;
        }
    }
}