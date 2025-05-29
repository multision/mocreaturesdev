/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCrocodile extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> IS_RESTING = EntityDataManager.createKey(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EATING_PREY = EntityDataManager.createKey(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_BITING = EntityDataManager.createKey(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    public float biteProgress;
    public float spin;
    public int spinInt;
    private boolean waterbound;

    public MoCEntityCrocodile(EntityType<? extends MoCEntityCrocodile> type, World world) {
        super(type, world);
        this.texture = "crocodile.png";
        //setSize(0.9F, 0.5F);
        setAdult(true);
        // TODO: Make hitboxes adjust depending on size
        //setAge(50 + this.rand.nextInt(50));
        setAge(80);
        setTamed(false);
        experienceValue = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 0.8D, 4D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new EntityAIWanderMoC2(this, 0.9D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        //this.targetSelector.addGoal(1, new EntityAIHunt<>(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(3, new EntityAIHunt<>(this, PlayerEntity.class, false));

    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).createMutableAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.ARMOR, 6.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_RESTING, Boolean.FALSE);
        this.dataManager.register(EATING_PREY, Boolean.FALSE);
        this.dataManager.register(IS_BITING, Boolean.FALSE);
    }

    public boolean getIsBiting() {
        return this.dataManager.get(IS_BITING);
    }

    public boolean getIsSitting() {
        return this.dataManager.get(IS_RESTING);
    }

    public void setIsSitting(boolean flag) {
        this.dataManager.set(IS_RESTING, flag);
    }

    public boolean getHasCaughtPrey() {
        return this.dataManager.get(EATING_PREY);
    }

    public void setHasCaughtPrey(boolean flag) {
        this.dataManager.set(EATING_PREY, flag);
    }

    public void setBiting(boolean flag) {
        this.dataManager.set(IS_BITING, flag);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Override
    protected void jump() {

        if (isSwimming()) {
            if (getHasCaughtPrey()) {
                return;
            }

            this.setMotion(this.getMotion().getX(), 0.3D, this.getMotion().getZ());
            this.isAirBorne = true;

        } else if (this.getAttackTarget() != null || getHasCaughtPrey()) {
            super.jump();
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting();
    }

    @Override
    public void livingTick() {
        if (getIsSitting()) {
            this.rotationPitch = -5F;
            if (!isSwimming() && this.biteProgress < 0.3F && this.rand.nextInt(5) == 0) {
                this.biteProgress += 0.005F;
            }
            if (this.getAttackTarget() != null) {
                setIsSitting(false);
            }
            if (!this.world.isRemote && this.getAttackTarget() != null || isSwimming() || getHasCaughtPrey() || this.rand.nextInt(500) == 0)// areEyesInFluid(FluidTags.WATER)
            {
                setIsSitting(false);
                this.biteProgress = 0;
            }

        } else {
            if (!this.world.isRemote && (this.rand.nextInt(500) == 0) && this.getAttackTarget() == null && !getHasCaughtPrey() && !isSwimming()) {
                setIsSitting(true);
                this.getNavigator().clearPath();
            }

        }

        if (this.rand.nextInt(500) == 0 && !getHasCaughtPrey() && !getIsSitting()) {
            crocBite();
        }

        //TODO replace with move to water AI
        if (!this.world.isRemote && this.rand.nextInt(500) == 0 && !this.waterbound && !getIsSitting() && !isSwimming()) {
            MoCTools.moveToWater(this);
        }

        if (this.waterbound) {
            if (!areEyesInFluid(FluidTags.WATER)) {
                MoCTools.moveToWater(this);
            } else {
                this.waterbound = false;
            }
        }

        if (getHasCaughtPrey()) {
            if (this.isBeingRidden()) {
                setAttackTarget(null);

                this.biteProgress = 0.4F;
                setIsSitting(false);

                if (!areEyesInFluid(FluidTags.WATER)) {
                    this.waterbound = true;
                    if (this.getRidingEntity() instanceof MobEntity && ((LivingEntity) this.getRidingEntity()).getHealth() > 0) {
                        ((LivingEntity) this.getRidingEntity()).deathTime = 0;
                    }

                    if (!this.world.isRemote && this.rand.nextInt(50) == 0) {
                        this.getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 2);
                    }
                }
            } else {
                setHasCaughtPrey(false);
                this.biteProgress = 0F;
                this.waterbound = false;
            }

            if (isSpinning()) {
                this.spinInt += 3;
                if ((this.spinInt % 20) == 0) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CROCODILE_ROLL.get());
                }
                if (this.spinInt > 80) {
                    this.spinInt = 0;
                    this.getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 4); //TODO ADJUST
                }

                //TODO 4FIX
                //the following if to be removed from SMP
                //if (!this.world.isRemote && this.isBeingRidden() && this.getRidingEntity() instanceof PlayerEntity) {
                //MoCreatures.mc.gameSettings.thirdPersonView = 1;
                //}
            }
        }

        super.livingTick();
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public void crocBite() {
        if (!getIsBiting()) {
            setBiting(true);
            this.biteProgress = 0.0F;
        }
    }

    @Override
    public void tick() {
        if (getIsBiting() && !getHasCaughtPrey())// && biteProgress <0.3)
        {
            this.biteProgress += 0.1F;
            if (this.biteProgress == 0.4F) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CROCODILE_JAWSNAP.get());
            }
            if (this.biteProgress > 0.6F) {

                setBiting(false);
                this.biteProgress = 0.0F;
            }
        }

        super.tick();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (getHasCaughtPrey()) {
            return false;
        }

        //TODO FIX!!!!
        /*if (!this.world.isRemote && entityIn.getRidingEntity() == null && this.rand.nextInt(3) == 0) {
            entityIn.mountEntity(this);
            this.setHasCaughtPrey(true);
            return false;
        } */

        crocBite();
        setHasCaughtPrey(false);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (this.isBeingRidden()) {

            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.getRidingEntity() == entity) {
                if (this.rand.nextInt(2) != 0) {
                    return false;
                } else {
                    unMount();
                }
            }

        }
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (this.isBeingRidden() && this.getRidingEntity() == entity) {
                if ((entity != this) && entity instanceof LivingEntity && super.shouldAttackPlayers()) {
                    setAttackTarget((LivingEntity) entity);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityCrocodile);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (!this.isBeingRidden()) {
            return;
        }
        int direction;

        double dist = getAge() * 0.01F + passenger.getWidth() - 0.4D;
        double newPosX = this.getPosX() - (dist * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);

        if (this.spinInt > 40) {
            direction = -1;
        } else {
            direction = 1;
        }

        ((LivingEntity) passenger).renderYawOffset = this.rotationYaw * direction;
        ((LivingEntity) passenger).prevRenderYawOffset = this.rotationYaw * direction;
    }

    @Override
    public double getMountedYOffset() {
        return this.getHeight() * 0.35D;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_CROCODILE_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_CROCODILE_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsSitting()) {
            return MoCSoundEvents.ENTITY_CROCODILE_RESTING.get();
        }
        return MoCSoundEvents.ENTITY_CROCODILE_AMBIENT.get();
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.CROCODILE;
    }

    public boolean isSpinning() {
        return getHasCaughtPrey() && (this.isBeingRidden()) && (this.isSwimming());
    }

    @Override
    public void onDeath(DamageSource damagesource) {

        unMount();
        MoCTools.checkForTwistedEntities(this.world);
        super.onDeath(damagesource);
    }

    public void unMount() {

        if (this.isBeingRidden()) {
            if (this.getRidingEntity() instanceof MobEntity && ((LivingEntity) this.getRidingEntity()).getHealth() > 0) {
                ((LivingEntity) this.getRidingEntity()).deathTime = 0;
            }

            this.dismount();
            setHasCaughtPrey(false);
        }
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public boolean isSwimming() {
        return this.isInWater();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isBeingRidden() && !this.getHasCaughtPrey();
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return !this.isMovementCeased() ? this.getHeight() * 0.7F : this.getHeight() * 0.39F;
    }
}
