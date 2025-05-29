/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class MoCEntitySilverSkeleton extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;

    public MoCEntitySilverSkeleton(EntityType<? extends MoCEntitySilverSkeleton> type, World world) {
        super(type, world);
        this.texture = "silver_skeleton.png";
        //setSize(0.6F, 2.125F);
        experienceValue = 5 + this.world.rand.nextInt(4);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MoCEntitySilverSkeleton.AISkeletonAttack(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MoCEntitySilverSkeleton.AISkeletonTarget<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new MoCEntitySilverSkeleton.AISkeletonTarget<>(this, IronGolemEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.ARMOR, 11.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            setSprinting(this.getAttackTarget() != null);
        }

        if (this.attackCounterLeft > 0 && ++this.attackCounterLeft > 10) {
            this.attackCounterLeft = 0;
        }

        if (this.attackCounterRight > 0 && ++this.attackCounterRight > 10) {
            this.attackCounterRight = 0;
        }

        super.livingTick();
    }

    @Override
    public void performAnimation(int animationType) {

        if (animationType == 1) //left arm
        {
            this.attackCounterLeft = 1;
        }
        if (animationType == 2) //right arm
        {
            this.attackCounterRight = 1;
        }
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startAttackAnimation() {
        if (!this.world.isRemote) {
            boolean leftArmW = this.rand.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounterLeft = 1;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 1));
            } else {
                this.attackCounterRight = 1;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 2));
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startAttackAnimation();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public float getAIMoveSpeed() {
        if (isSprinting()) {
            return 0.35F;
        }
        return 0.2F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    // TODO: Add unique step sound
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.SILVER_SKELETON;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.905F;
    }

    static class AISkeletonAttack extends MeleeAttackGoal {
        public AISkeletonAttack(MoCEntitySilverSkeleton skeleton, double speed, boolean useLongMemory) {
            super(skeleton, speed, useLongMemory);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getWidth();
        }
    }

    static class AISkeletonTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public AISkeletonTarget(MoCEntitySilverSkeleton skeleton, Class<T> classTarget, boolean checkSight) {
            super(skeleton, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.goalOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
