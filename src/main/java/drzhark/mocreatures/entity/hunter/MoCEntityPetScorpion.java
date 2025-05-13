/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SaddleItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class MoCEntityPetScorpion extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_BABIES = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_SITTING = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);
    public int mouthCounter;
    public int armCounter;
    public int transformType;
    private boolean isPoisoning;
    private int poisontimer;
    private int transformCounter;

    public MoCEntityPetScorpion(EntityType<? extends MoCEntityPetScorpion> type, World world) {
        super(type, world);
        this.poisontimer = 0;
        setAdult(false);
        setAge(20);
        setHasBabies(false);
        this.stepHeight = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MoCEntityPetScorpion.AIPetScorpionAttack(this));
        this.goalSelector.addGoal(4, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.goalSelector.addGoal(6, new EntityAIFollowOwnerPlayer(this, 1.0D, 2F, 10F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        //this.targetSelector.addGoal(1, new EntityAIHunt<>(this, AnimalEntity.class, true));
    }

    // TODO: Varied stats depending on type
    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        boolean saddle = getIsRideable();

        // Textures based on type for transforming
        if (this.transformCounter != 0 && this.transformType != 0) {
            String newText;
            switch (this.transformType) {
                case 3: // Fire Scorpion
                    newText = saddle ? "scorpion_fire_saddled.png" : "scorpion_fire.png";
                    break;
                case 5: // Undead Scorpion
                    newText = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                    break;
                default:
                    newText = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                    break;
            }

            // Textures flashing during transformation
            if (this.transformCounter > 60 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
        }

        switch (getTypeMoC()) {
            case 1:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_dirt.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_dirt_saddled.png");
            case 2:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_cave.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_cave_saddled.png");
            case 3:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_fire.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_fire_saddled.png");
            case 4:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_frost.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_frost_saddled.png");
            case 5:
                if (!saddle) {
                    return MoCreatures.proxy.getModelTexture("scorpion_undead.png");
                }
                return MoCreatures.proxy.getModelTexture("scorpion_undead_saddled.png");
            default:
                return MoCreatures.proxy.getModelTexture("scorpion_dirt.png");
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CLIMBING, Boolean.FALSE);
        this.dataManager.register(HAS_BABIES, Boolean.FALSE);
        this.dataManager.register(IS_SITTING, Boolean.FALSE);
        this.dataManager.register(RIDEABLE, Boolean.FALSE);
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, flag);
    }

    @Override
    public boolean getIsRideable() {
        return this.dataManager.get(RIDEABLE);
    }

    public boolean getHasBabies() {
        return getIsAdult() && this.dataManager.get(HAS_BABIES);
    }

    public void setHasBabies(boolean flag) {
        this.dataManager.set(HAS_BABIES, flag);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    @Override
    public boolean getIsSitting() {
        return this.dataManager.get(IS_SITTING);
    }

    public void setSitting(boolean flag) {
        this.dataManager.set(IS_SITTING, flag);
    }

    public void setPoisoning(boolean flag) {
        if (flag && !this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 0));
        }
        this.isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) // Tail Animation
        {
            setPoisoning(true);
        } else if (animationType == 1) // Attack Animation (Claws)
        {
            this.armCounter = 1;
            swingArm();
        } else if (animationType == 3) // Mouth Movement Animation
        {
            this.mouthCounter = 1;
        } else if (animationType == 5) // Type Transformation (e.g. Undead)
        {
            this.transformCounter = 1;
        }
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return this.dataManager.get(CLIMBING);
    }

    public void setBesideClimbableBlock(boolean climbing) {
        this.dataManager.set(CLIMBING, climbing);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        // Claw Attack Sound
        if (this.poisontimer != 1) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_CLAW.get());
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    public void livingTick() {

        if (!this.onGround && (this.getRidingEntity() != null)) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_STING.get());
            }

            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }

        if (this.transformCounter > 0) {
            // Sound plays after this amount of time has passed during transformation
            if (this.transformCounter == 60) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM.get());
            }

            // Transformation completed
            if (++this.transformCounter > 100) {
                this.transformCounter = 0;

                if (this.transformType != 0) {
                    setTypeMoC(this.transformType);
                }
            }
        }

        super.livingTick();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (!(entity instanceof LivingEntity) || entity instanceof PlayerEntity && getIsTamed()) {
                return false;
            }
            if (entity != this && super.shouldAttackPlayers() && getIsAdult()) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            if (getTypeMoC() <= 1) // Dirt Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 15 * 20, 1)); // 15 seconds
            } else if (getTypeMoC() == 2) // Cave Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.NAUSEA, 15 * 20, 0)); // 15 seconds
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 15 * 20, 0));
            } else if (getTypeMoC() == 3) // Fire Scorpion
            {
                entityIn.setFire(15);
            } else if (getTypeMoC() == 4) // Frost Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 25 * 20, 0)); // 25 seconds
            } else if (getTypeMoC() == 5) // Undead Scorpion
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 15 * 20, 0)); // 15 seconds
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WITHER, 15 * 20, 0));
            }
        } else {
            swingArm();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    public void swingArm() {
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 1));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SCORPION_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SCORPION_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Mouth Movement Animation
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 3));
        }
        return MoCSoundEvents.ENTITY_SCORPION_AMBIENT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.5F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        switch (getTypeMoC()) {
            case 1:
                return MoCLootTables.DIRT_SCORPION;
            case 2:
                return MoCLootTables.CAVE_SCORPION;
            case 3:
                return MoCLootTables.FIRE_SCORPION;
            case 4:
                return MoCLootTables.FROST_SCORPION;
            case 5:
                return MoCLootTables.UNDEAD_SCORPION;
            default:
                return MoCLootTables.DIRT_SCORPION;
        }
    }

    // TODO: Make it not give items in creative
    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsAdult() && !getIsRideable()
                && (stack.getItem() instanceof SaddleItem || stack.getItem() == MoCItems.horsesaddle)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            setRideable(true);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());
            return ActionResultType.SUCCESS;
        }

        // Transformations
        if (!stack.isEmpty() && this.getIsTamed() && !this.isBeingRidden() && !this.isPassenger() && this.transformCounter < 1) {

            // Fire Scorpion (Essence of Fire)
            if (stack.getItem() == MoCItems.essencefire && this.getTypeMoC() != 3) {
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                }

                transform(3);
                return ActionResultType.SUCCESS;
            }

            // Undead Scorpion (Essence of Undead)
            if (!stack.isEmpty() && this.getIsTamed() && !this.isBeingRidden() && !this.isPassenger() && this.transformCounter < 1 && stack.getItem() == MoCItems.essenceundead && this.getTypeMoC() != 5) {
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                }

                transform(5);
                return ActionResultType.SUCCESS;
            }
        }

        if (!stack.isEmpty() && this.getIsTamed() && !this.isBeingRidden() && !this.isPassenger() && stack.getItem() == MoCItems.essencedarkness) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            this.setHealth(getMaxHealth());
            if (!this.world.isRemote) {
                int i = getTypeMoC() + 40;
                MoCEntityEgg entityegg = MoCEntities.EGG.create(this.world);
                entityegg.setEggType(i);
                entityegg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                player.world.addEntity(entityegg);
                entityegg.setMotion(entityegg.getMotion().add((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F, this.world.rand.nextFloat() * 0.05F, (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F));
            }
            return ActionResultType.SUCCESS;
        }

        if (this.getRidingEntity() == null && this.getAge() < 60 && !getIsAdult()) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
                if (!this.world.isRemote && !getIsTamed()) {
                    MoCTools.tameWithName(player, this);
                }
            }

            return ActionResultType.SUCCESS;
        } else if (this.getRidingEntity() != null) {
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            this.dismount();
            this.setMotion(player.getMotion().getX() * 5D, (player.getMotion().getY() / 2D) + 0.5D, player.getMotion().getZ() * 5D);
            return ActionResultType.SUCCESS;
        }

        if (getIsRideable() && getIsTamed() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            if (!this.world.isRemote) {
                player.startRiding(this);
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
        setRideable(nbttagcompound.getBoolean("Saddled"));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Babies", getHasBabies());
        nbttagcompound.putBoolean("Saddled", getIsRideable());
    }

    @Override
    public int nameYOffset() {
        int n = (int) (1 - (getAge() * 0.8));
        if (n < -60) {
            n = -60;
        }
        if (getIsAdult()) {
            n = -60;
        }
        if (getIsSitting()) {
            n = (int) (n * 0.8);
        }
        return n;
    }

    // TODO: Overhaul acceptable food
    @Override
    protected boolean isMyHealFood(ItemStack itemstack) {
        return (itemstack.getItem() == MoCItems.ratRaw || itemstack.getItem() == MoCItems.ratCooked);
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || getIsSitting();
    }

    @Override
    public void dropMyStuff() {
        MoCTools.dropSaddle(this, this.world);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public double getMountedYOffset() {
        return (this.getHeight() * 0.75D) - 0.15D;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && this.world.isRemote) {
            return 0.1F;
        }

        if ((this.getRidingEntity() instanceof PlayerEntity) && this.world.isRemote) {
            return (super.getYOffset() + 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.2D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    public void transform(int tType) {
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), tType));
        }

        // Set what type to transform to based on type integer
        this.transformType = tType;
        if (this.transformType != 0) {
            this.transformCounter = 1;
        }
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityFox) && entity.getHeight() <= 1D && entity.getWidth() <= 1D;
    }

    static class AIPetScorpionAttack extends MeleeAttackGoal {
        public AIPetScorpionAttack(MoCEntityPetScorpion scorpion) {
            super(scorpion, 1.0D, true);
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
}
