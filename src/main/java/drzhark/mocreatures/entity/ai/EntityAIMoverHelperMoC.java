/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityAIMoverHelperMoC extends MovementController {

    protected MovementController.Action action = MovementController.Action.WAIT;
    CreatureEntity theCreature;

    public EntityAIMoverHelperMoC(MobEntity entityliving) {
        super(entityliving);
        this.theCreature = (CreatureEntity) entityliving;
    }

    public boolean isUpdating() {
        return this.action == MovementController.Action.MOVE_TO;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setMoveTo(double x, double y, double z, double speedIn) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speedIn;
        this.action = MovementController.Action.MOVE_TO;
    }

    /*public void read(MovementController that)
    {
        this.action = that.action;
        this.getX() = that.getX();
        this.getPosY() = that.getPosY();
        this.getPosZ() = that.getPosZ();
        this.speed = Math.max(that.speed, 1.0D);
        this.moveForward = that.moveForward;
        this.moveStrafe = that.moveStrafe;
    }*/

    public void strafe(float forward, float strafe) {
        this.action = MovementController.Action.STRAFE;
        this.moveForward = forward;
        this.moveStrafe = strafe;
        this.speed = 0.25D;
    }

    public void onUpdateMoveOnGround() {
        if (this.action == MovementController.Action.STRAFE) {
            float f = (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float f1 = (float) this.speed * f;
            float f2 = this.moveForward;
            float f3 = this.moveStrafe;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

            if (f4 < 1.0F) {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.mob.rotationYaw * 0.017453292F);
            float f6 = MathHelper.cos(this.mob.rotationYaw * 0.017453292F);
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            PathNavigator pathnavigate = this.mob.getNavigator();

            if (pathnavigate != null) {
                NodeProcessor nodeprocessor = pathnavigate.getNodeProcessor();

                if (nodeprocessor != null && nodeprocessor.getFloorNodeType(this.mob.world, MathHelper.floor(this.mob.getPosX() + (double) f7), MathHelper.floor(this.mob.getPosY()), MathHelper.floor(this.mob.getPosZ() + (double) f8)) != PathNodeType.WALKABLE) {
                    this.moveForward = 1.0F;
                    this.moveStrafe = 0.0F;
                    f1 = f;
                }
            }

            this.mob.setAIMoveSpeed(f1);
            this.mob.setMoveForward(this.moveForward);
            this.mob.setMoveStrafing(this.moveStrafe);
            this.action = MovementController.Action.WAIT;
        } else if (this.action == MovementController.Action.MOVE_TO) {
            this.action = MovementController.Action.WAIT;
            double d0 = this.getX() - this.mob.getPosX();
            double d1 = this.getZ() - this.mob.getPosZ();
            double d2 = this.getY() - this.mob.getPosY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 < 2.500000277905201E-7D) {
                this.mob.setMoveForward(0.0F);
                return;
            }

            float f9 = (float) (MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, f9, 20.0F);
            this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));

            if (d2 > (double) this.mob.stepHeight && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getWidth())) {
                this.mob.getJumpController().setJumping();
            }
        } else {
            this.mob.setMoveForward(0.0F);
        }
    }

    /**
     * Limits the given angle to a upper and lower limit.
     */
    protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_) {
        float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);

        if (f > p_75639_3_) {
            f = p_75639_3_;
        }

        if (f < -p_75639_3_) {
            f = -p_75639_3_;
        }

        float f1 = p_75639_1_ + f;

        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }

    @Override
    public void tick() {
        boolean isFlyer = ((IMoCEntity) theCreature).isFlyer();
        boolean isSwimmer = this.theCreature.isInWater();
        float fLimitAngle = 90F;
        if (!isFlyer && !isSwimmer) {
            onUpdateMoveOnGround();
            return;
        }

        /*
         * Flying specific movement code
         */
        if (isFlyer && !theCreature.isBeingRidden()) {
            this.flyingMovementUpdate();
        }

        /*
         * Water movement code
         */
        if (isSwimmer) {
            this.swimmerMovementUpdate();
            fLimitAngle = 30F;
        }
        if (this.action == MovementController.Action.MOVE_TO && !this.theCreature.getNavigator().noPath()) {
            double d0 = this.getX() - this.theCreature.getPosX();
            double d1 = this.getX() - this.theCreature.getPosY();
            double d2 = this.getZ() - this.theCreature.getPosZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);
            if (d3 < 0.5) {
                this.mob.setMoveForward(0.0F);
                this.theCreature.getNavigator().clearPath();
                return;
            }
            //System.out.println("distance to objective = " + d3 + "objective: X = " + this.getX() + ", Y = " + this.getPosY() + ", Z = " + this.getPosZ());
            d1 /= d3;
            float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.theCreature.rotationYaw = this.limitAngle(this.theCreature.rotationYaw, f, fLimitAngle);
            this.theCreature.renderYawOffset = this.theCreature.rotationYaw;
            float f1 = (float) (this.speed * this.theCreature.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.theCreature.setAIMoveSpeed(this.theCreature.getAIMoveSpeed() + (f1 - this.theCreature.getAIMoveSpeed()) * 0.125F);
            double d4 = Math.sin((double) (this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.01D;
            double d5 = Math.cos(this.theCreature.rotationYaw * (float) Math.PI / 180.0F);
            double d6 = Math.sin(this.theCreature.rotationYaw * (float) Math.PI / 180.0F);

            double targetDepth = MoCTools.waterSurfaceAtGivenEntity(this.theCreature) - this.theCreature.getPosY();
            double yMotion = 0.0D;

            if (targetDepth > ((IMoCEntity) this.theCreature).getDivingDepth()) {
                yMotion = 0.01D; // gently ascend
            } else if (targetDepth < ((IMoCEntity) this.theCreature).getDivingDepth() - 0.2D) {
                yMotion = -0.005D; // descend slightly
            }

            this.theCreature.setMotion(this.theCreature.getMotion().add(d4 * d5, yMotion, d4 * d5));

            //this.theCreature.setMotion(this.theCreature.getMotion().add(d4 * d5, (d4 * (d6 + d5) * 0.25D) + ((double) this.theCreature.getAIMoveSpeed() * d1 * 1.5D), d4 * d5));
        }
    }

    /**
     * Makes flying creatures reach the proper flying height
     */
    private void flyingMovementUpdate() {

        //Flying alone
        if (((IMoCEntity) theCreature).getIsFlying()) {
            int distY = MoCTools.distanceToFloor(this.theCreature);
            if (distY <= ((IMoCEntity) theCreature).minFlyingHeight()
                    && (this.theCreature.collidedHorizontally || this.theCreature.world.rand.nextInt(100) == 0)) {
                this.theCreature.setMotion(this.theCreature.getMotion().add(0.0F, 0.02D, 0.0F));
            }
            if (distY > ((IMoCEntity) theCreature).maxFlyingHeight() || this.theCreature.world.rand.nextInt(150) == 0) {
                this.theCreature.setMotion(this.theCreature.getMotion().subtract(0.0F, 0.02D, 0.0F));
            }

        } else {
            if (this.theCreature.getMotion().getY() < 0) {
                this.theCreature.setMotion(this.theCreature.getMotion().mul(1.0F, 0.6D, 1.0F));
            }
        }

    }

    /**
     * Makes creatures in the water float to the right depth
     */
    private void swimmerMovementUpdate() {
        if (theCreature.isBeingRidden()) {
            return;
        }

        double distToSurface = (MoCTools.waterSurfaceAtGivenEntity(theCreature) - theCreature.getPosY());
        if (distToSurface > ((IMoCEntity) theCreature).getDivingDepth()) {
            if (theCreature.getMotion().getY() < 0) {
                this.theCreature.setMotion(this.theCreature.getMotion().getX(), 0, this.theCreature.getMotion().getZ());
            }
            this.theCreature.setMotion(this.theCreature.getMotion().add(0.0F, 0.001D + (distToSurface * 0.01), 0.0F));
        }

        if (!theCreature.getNavigator().noPath() && theCreature.collidedHorizontally) {
            if (theCreature instanceof MoCEntityAquatic) {
                this.theCreature.setMotion(this.theCreature.getMotion().getX(), 0.05D, this.theCreature.getMotion().getZ());
            } else {
                ((IMoCEntity) theCreature).forceEntityJump();
            }
        }

        if ((this.theCreature.getAttackTarget() != null && ((this.theCreature.getAttackTarget().getPosY() < (this.posX - 0.5D)) && this.theCreature
                .getDistance(this.theCreature.getAttackTarget()) < 10F))) {
            if (this.theCreature.getMotion().getY() < -0.1) {
                this.theCreature.setMotion(this.theCreature.getMotion().getX(), -0.1, this.theCreature.getMotion().getZ());
            }
        }
    }

    public enum Action {
        WAIT,
        MOVE_TO,
        STRAFE
    }
}
