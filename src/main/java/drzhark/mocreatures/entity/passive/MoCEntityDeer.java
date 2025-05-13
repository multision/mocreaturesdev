/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityDeer extends MoCEntityTameableAnimal {

    private int readyToJumpTimer;

    public MoCEntityDeer(EntityType<? extends MoCEntityDeer> type, World world) {
        super(type, world);
        setAge(75);
        //setSize(0.9F, 1.425F);
        setAdult(true);
        setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EntityAIFleeFromEntityMoC(this, entity -> !(entity instanceof MoCEntityDeer) && (entity.getHeight() > 0.8F || entity.getWidth() > 0.8F), 6.0F, this.getMyAISpeed(), this.getMyAISpeed() * 1.2D));
        this.goalSelector.addGoal(2, new PanicGoal(this, this.getMyAISpeed() * 1.2D));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, getMyAISpeed()));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, getMyAISpeed()));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 20) {
                setTypeMoC(1);
            } else if (i <= 70) {
                setTypeMoC(2);
            } else {
                setAdult(false);
                setTypeMoC(3);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("deer_doe.png");
            case 3:
                setAdult(false);
                return MoCreatures.proxy.getModelTexture("deer_fawn.png");
            default:
                return MoCreatures.proxy.getModelTexture("deer_stag.png");
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return !(entity instanceof MoCEntityDeer) && super.entitiesToInclude(entity);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DEER_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DEER_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!getIsAdult()) {
            return MoCSoundEvents.ENTITY_DEER_AMBIENT_BABY.get();
        } else {
            return MoCSoundEvents.ENTITY_DEER_AMBIENT.get();
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.DEER;
    }

    public double getMyAISpeed() {
        /*if (getTypeMoC() == 1) {
            return 1.1D;
        } else if (getTypeMoC() == 2) {
            return 1.3D;
        }*/
        return 1.1D;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxAge() {
        return 130;
    }

    @Override
    public void setAdult(boolean flag) {
        if (!this.world.isRemote) {
            setTypeMoC(this.rand.nextInt(1));
        }
        super.setAdult(flag);
    }

    @Override
    public boolean getIsAdult() {
        return this.getTypeMoC() != 3 && super.getIsAdult();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote) {

            if (this.onGround && --this.readyToJumpTimer <= 0) {
                if (MoCTools.getMyMovementSpeed(this) > 0.17F) {
                    float velX = (float) (0.5F * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
                    float velZ = (float) (0.5F * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
                    this.setMotion(new Vector3d(this.getMotion().getX(), 0.5D, this.getMotion().getZ()).subtract(velX, 0.0D, velZ));
                    this.readyToJumpTimer = this.rand.nextInt(10) + 20;
                }
            }
        }
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.onGround && MoCTools.getMyMovementSpeed(this) > 0.08F) {
            if (this.getMotion().getY() > 0.5D) {
                return 25F;
            }
            if (this.getMotion().getY() < -0.5D) {
                return -25F;
            }
            return (float) (this.getMotion().getY() * 70D);
        }
        return 0F;
    }

    @Override
    public float getSizeFactor() {
        if (getTypeMoC() == 1) {
            return 1.6F;
        }
        if (getTypeMoC() == 2) {
            return 1.3F;
        }
        return getAge() * 0.01F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.945F;
    }
}
