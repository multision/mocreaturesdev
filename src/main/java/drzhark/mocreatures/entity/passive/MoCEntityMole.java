/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class MoCEntityMole extends MoCEntityTameableAnimal {

    private static final DataParameter<Integer> MOLE_STATE = EntityDataManager.createKey(MoCEntityMole.class, DataSerializers.VARINT);

    public MoCEntityMole(EntityType<? extends MoCEntityMole> type, World world) {
        super(type, world);
        //setSize(1F, 0.5F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 6.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("mole.png");
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOLE_STATE, 0); // state - 0 outside / 1 digging / 2 underground / 3 pick-a-boo

    }

    public boolean isOnDirt() {
        Block block =
                this.world.getBlockState(
                        new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getBoundingBox().minY - 0.5D), MathHelper
                                .floor(this.getPosZ()))).getBlock();
        return isDiggableBlock(block);//(j == 2 | j == 3 | j == 12);
    }

    private boolean isDiggableBlock(Block i) {
        return Tags.Blocks.DIRT.contains(i) || Tags.Blocks.SAND.contains(i) || Tags.Blocks.GRAVEL.contains(i);
    }

    /**
     * Moves entity forward underground
     */
    @SuppressWarnings("unused")
    private void digForward() {
        double coordY = this.getPosY();
        double coordZ = this.getPosZ();
        double coordX = this.getPosX();
        int x = 1;
        double newPosY = coordY - Math.cos((this.rotationPitch - 90F) / 57.29578F) * x;
        double newPosX =
                coordX + Math.cos((MoCTools.realAngle(this.rotationYaw - 90F) / 57.29578F)) * (Math.sin((this.rotationPitch - 90F) / 57.29578F) * x);
        double newPosZ =
                coordZ + Math.sin((MoCTools.realAngle(this.rotationYaw - 90F) / 57.29578F)) * (Math.sin((this.rotationPitch - 90F) / 57.29578F) * x);
        Block block =
                this.world.getBlockState(
                                new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ)))
                        .getBlock();
        if (isDiggableBlock(block)) {
            this.setPosition(newPosX, newPosY, newPosZ);
        }
    }

    /**
     * obtains State
     *
     * @return 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public int getState() {
        return this.dataManager.get(MOLE_STATE);
    }

    /**
     * Changes the state
     * 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public void setState(int i) {
        this.dataManager.set(MOLE_STATE, i);
    }

    @Override
    public float pitchRotationOffset() {

        int i = getState();
        switch (i) {
            case 1:
                return -45F;
            case 3:
                return 60F;
            default:
                return 0F;
        }
    }

    @Override
    public float getAdjustedYOffset() {
        int i = getState();
        switch (i) {
            case 1:
                return 0.3F;
            case 2:
                return 1F;
            case 3:
                return 0.1F;
            default:
                return 0F;
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (this.rand.nextInt(10) == 0 && getState() == 1) {
                setState(2);
            }

            if (getState() != 2 && getState() != 1 && isOnDirt()) {
                LivingEntity entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving)) {
                    setState(1);
                    this.getNavigator().clearPath();
                }
            }

            //if underground and no enemies: pick a boo
            if (this.rand.nextInt(20) == 0 && getState() == 2 && (getBoogey(4D) == null)) {
                setState(3);
                this.getNavigator().clearPath();
            }

            //if not on dirt, get out!
            if (getState() != 0 && !isOnDirt()) {
                setState(0);
            }

            if (this.rand.nextInt(30) == 0 && getState() == 3) {
                setState(2);
            }

            /*
             * if (getState() == 2) { if (rand.nextInt(50) == 0) digForward(); }
             */

            //digging fx
            setSprinting(getState() == 1 || getState() == 2);
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getState() == 1 || getState() == 3;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (getState() != 2) {
            return super.attackEntityFrom(damagesource, i);
        }
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return (getState() != 2);
    }

    @Override
    public boolean canBePushed() {
        return (getState() != 2);
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
        if (getState() != 2) {
            super.collideWithEntity(par1Entity);
            //            par1Entity.applyEntityCollision(this);
        }
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (getState() == 2) {
            return false;
        }
        return super.isEntityInsideOpaqueBlock();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (getState() == 2) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_RABBIT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_RABBIT_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.MOLE;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.525F;
    }
}
