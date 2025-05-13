/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PathNavigateFlyer extends PathNavigator {

    public PathNavigateFlyer(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    protected PathFinder getPathFinder(int searchDepthIn) {
        this.nodeProcessor = new FlyNodeProcessor();
        return new PathFinder(this.nodeProcessor, searchDepthIn);
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canNavigate() {
        return true;
    }

    protected Vector3d getEntityPosition() {
        return new Vector3d(this.entity.getPosX(), this.entity.getPosY() + (double) this.entity.getHeight() * 0.5D, this.entity.getPosZ());
    }

    protected void pathFollow() {
        Vector3d vec3 = this.getEntityPosition();
        float f = this.entity.getWidth() * this.entity.getWidth();
        byte b0 = 6;

        if (vec3.squareDistanceTo(this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex())) < (double) f) {
            this.currentPath.incrementPathIndex();
        }

        for (int i = Math.min(this.currentPath.getCurrentPathIndex() + b0, this.currentPath.getCurrentPathLength() - 1); i > this.currentPath
                .getCurrentPathIndex(); --i) {
            Vector3d vec31 = this.currentPath.getVectorFromIndex(this.entity, i);

            if (vec31.squareDistanceTo(vec3) <= 36.0D && this.isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)) {
                this.currentPath.setCurrentPathIndex(i);
                break;
            }
        }

        this.checkForStuck(vec3);
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    protected void trimPath() {
        super.trimPath();
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    @Override
    protected boolean isDirectPathBetweenPoints(Vector3d posVec31, Vector3d posVec32, int sizeX, int sizeY, int sizeZ) {
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(posVec31, new Vector3d(posVec32.x, posVec32.y + (double) this.entity.getHeight() * 0.5D, posVec32.z), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.entity));
        return raytraceresult == null || raytraceresult.getType() == RayTraceResult.Type.MISS;
    }
}
