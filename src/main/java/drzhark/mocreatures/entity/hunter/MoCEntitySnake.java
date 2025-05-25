/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.item.MoCItemEgg;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

/**
 * Biome - specific Forest Desert plains Swamp Jungle Tundra Taiga Extreme Hills
 * Ocean
 * <p>
 * swamp: python, bright green, #1 plains: coral, cobra #1, #2, #3, #4 desert:
 * rattlesnake , #2 jungle: all except rattlesnake hills: all except python,
 * bright green, bright orange tundra-taiga: none ocean: leave alone
 */

public class MoCEntitySnake extends MoCEntityTameableAnimal {

    public float bodyswing;
    private float fTongue;
    private float fMouth;
    private boolean isBiting;
    private float fRattle;
    private boolean isPissed;
    private int hissCounter;
    private int movInt;
    private boolean isNearPlayer;

    public MoCEntitySnake(EntityType<? extends MoCEntitySnake> type, World world) {
        super(type, world);
        //setSize(1.4F, 0.5F);
        this.bodyswing = 2F;
        this.movInt = this.rand.nextInt(10);
        setAge(50 + this.rand.nextInt(50));
        experienceValue = 3;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 0.8D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 0.8D, 4D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 0.8D, 30));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        //this.targetSelector.addGoal(1, new EntityAIHunt<>(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(2, new EntityAIHunt<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (this.world.getDimensionKey() == MoCreatures.proxy.wyvernDimension) this.enablePersistence();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return this.world.getDimensionKey() != MoCreatures.proxy.wyvernDimension;
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
        // snake types:
        // 1 small blackish/dark snake (passive)
        // 2 dark green /brown snake (passive)
        // 3 bright orangy snake aggressive venomous swamp, jungle, forest
        // 4 bright green snake aggressive venomous swamp, jungle, forest
        // 5 coral (aggressive - venomous) small / plains, forest
        // 6 cobra (aggressive - venomous - spitting) plains, forest
        // 7 rattlesnake (aggressive - venomous) desert
        // 8 python (aggressive - non-venomous) big - swamp
        // 9 sea snake (aggressive - venomous)
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(8) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("snake_wolf.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("snake_orange.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("snake_green_bright.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("snake_coral.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("snake_cobra.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("snake_rattle.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("snake_python.png");
            default:
                return MoCreatures.proxy.getModelTexture("snake_green_dark.png");
        }
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    @Override
    // snakes can't jump
    protected void jump() {
        if (this.isInWater()) {
            super.jump();
        }
    }

    public boolean pickedUp() {
        return (this.getRidingEntity() != null);
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (!getIsTamed()) {
            return ActionResultType.FAIL;
        }

        if (this.getRidingEntity() == null) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Override
    public boolean isNotScared() {
        return getTypeMoC() > 2 && getAge() > 50;
    }

    /**
     * returns true when is climbing up
     */
    public boolean isClimbing() {
        return isOnLadder() && this.getMotion().getY() > 0.01F;
    }

    public boolean isResting() {
        return (!getNearPlayer() && this.onGround && (this.getMotion().getX() < 0.01D && this.getMotion().getX() > -0.01D) && (this.getMotion().getZ() < 0.01D && this.getMotion().getZ() > -0.01D));
    }

    public boolean getNearPlayer() {
        return (this.isNearPlayer || this.isBiting());
    }

    public void setNearPlayer(boolean flag) {
        this.isNearPlayer = flag;
    }

    public int getMovInt() {
        return this.movInt;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity) {
            return 0.1F;
        }

        return super.getYOffset();
    }

    public float getSizeF() {
        float factor = 1.0F;
        if (getTypeMoC() == 1 || getTypeMoC() == 2)// small shy snakes
        {
            factor = 0.8F;
        } else if (getTypeMoC() == 5)// coral
        {
            factor = 0.6F;
        }
        if (getTypeMoC() == 6)// cobra 1.1
        {
            factor = 1.1F;
        }
        if (getTypeMoC() == 7)// rattlesnake
        {
            factor = 0.9F;
        }
        if (getTypeMoC() == 8)// python
        {
            factor = 1.5F;
        }
        return this.getAge() * 0.01F * factor;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.isRemote) {
            if (getfTongue() != 0.0F) {
                setfTongue(getfTongue() + 0.2F);
                if (getfTongue() > 8.0F) {
                    setfTongue(0.0F);
                }
            }

            if (getfMouth() != 0.0F && this.hissCounter == 0) //biting
            {
                setfMouth(getfMouth() + 0.1F);
                if (getfMouth() > 0.5F) {
                    setfMouth(0.0F);
                }
            }

            if (getTypeMoC() == 7 && getfRattle() != 0.0F) // rattling
            {
                setfRattle(getfRattle() + 0.2F);
                if (getfRattle() == 1.0F) {
                    // TODO synchronize
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SNAKE_RATTLE.get());
                }
                if (getfRattle() > 8.0F) {
                    setfRattle(0.0F);
                }
            }

            /*
             * stick tongue
             */
            if (this.rand.nextInt(50) == 0 && getfTongue() == 0.0F) {
                setfTongue(0.1F);
            }

            /*
             * Open mouth
             */
            if (this.rand.nextInt(100) == 0 && getfMouth() == 0.0F) {
                setfMouth(0.1F);
            }
            if (getTypeMoC() == 7) {
                int chance;
                if (getNearPlayer()) {
                    chance = 30;
                } else {
                    chance = 100;
                }

                if (this.rand.nextInt(chance) == 0) {
                    setfRattle(0.1F);
                }
            }
            /*
             * change in movement pattern
             */
            if (!isResting() && !pickedUp() && this.rand.nextInt(50) == 0) {
                this.movInt = this.rand.nextInt(10);
            }

            /*
             * Biting animation
             */
            if (isBiting()) {
                this.bodyswing -= 0.5F;
                setfMouth(0.3F);

                if (this.bodyswing < 0F) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SNAKE_SNAP.get());
                    this.bodyswing = 2.5F;
                    setfMouth(0.0F);
                    setBiting(false);
                }
            }

        }
        if (pickedUp()) {
            this.movInt = 0;
        }

        if (isResting()) {

            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;

        }

        if (!this.onGround && (this.getRidingEntity() != null)) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;// -90F;
        }

        if (this.world.getDifficulty().getId() > 0 && getNearPlayer() && !getIsTamed() && isNotScared()) {

            this.hissCounter++;

            // TODO synchronize and get sound
            // hiss
            if (this.hissCounter % 25 == 0) {
                setfMouth(0.3F);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SNAKE_ANGRY.get());
            }
            if (this.hissCounter % 35 == 0) {
                setfMouth(0.0F);
            }

            if (this.hissCounter > 100 && this.rand.nextInt(50) == 0) {
                // then randomly get pissed
                setPissed(true);
                this.hissCounter = 0;
            }
        }
        if (this.hissCounter > 500) {
            this.hissCounter = 0;
        }

    }

    /**
     * from 0.0 to 4.0F 0.0 = inside mouth 2.0 = completely stuck out 3.0 =
     * returning 4.0 = in.
     */
    public float getfTongue() {
        return this.fTongue;
    }

    public void setfTongue(float fTongue) {
        this.fTongue = fTongue;
    }

    public float getfMouth() {
        return this.fMouth;
    }

    public void setfMouth(float fMouth) {
        this.fMouth = fMouth;
    }

    public float getfRattle() {
        return this.fRattle;
    }

    public void setfRattle(float fRattle) {
        this.fRattle = fRattle;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        /*
         * this stops chasing the target randomly
         */
        if (getAttackTarget() != null && this.rand.nextInt(300) == 0) {
            setAttackTarget(null);
        }

        PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 12D);
        if (entityplayer1 != null) {
            double distP = MoCTools.getSqDistanceTo(entityplayer1, this.getPosX(), this.getPosY(), this.getPosZ());
            if (isNotScared()) {
                setNearPlayer(distP < 5D);

                /*if (entityplayer1.isBeingRidden()
                        && (entityplayer1.riddenByEntity instanceof MoCEntityMouse || entityplayer1.riddenByEntity instanceof MoCEntityBird)) {
                    PathEntity pathentity = this.navigator.pathfind(entityplayer1);
                    this.navigator.setPath(pathentity, 1D);
                    setPissed(false);
                    this.hissCounter = 0;
                }*/
            } else {
                setNearPlayer(false);
            }

        } else {
            setNearPlayer(false);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if ((getTypeMoC() < 3 || getIsTamed()) && entityIn instanceof PlayerEntity) {
            return false;
        }

        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        setBiting(true);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void performAnimation(int i) {
        setBiting(true);
    }

    public boolean isBiting() {
        return this.isBiting;
    }

    public void setBiting(boolean flag) {
        if (flag && !this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 0));
        }
        this.isBiting = flag;
    }

    public boolean isPissed() {
        return this.isPissed;
    }

    public void setPissed(boolean isPissed) {
        this.isPissed = isPissed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {

        if (getTypeMoC() < 3) {
            return super.attackEntityFrom(damagesource, i);
        }

        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if ((entity != this) && entity instanceof LivingEntity && (super.shouldAttackPlayers())) {
                setPissed(true);
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        if (getAge() > 60) {
            int j = this.rand.nextInt(3);
            for (int l = 0; l < j; l++) {

                int snakeEggType = getTypeMoC() + 20;
                ItemStack snakeEgg = new ItemStack(MoCItems.mocegg, 1);

                snakeEgg.getOrCreateTag().putInt("EggType", snakeEggType);

                entityDropItem(snakeEgg, 0.0F);
            }
        }
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntitySnake) && entity.getHeight() < 0.5D && entity.getWidth() < 0.5D;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState par4) {
        if (areEyesInFluid(FluidTags.WATER)) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SNAKE_SWIM.get());
        }
        // TODO - add sound for slither
        /*
         * else { world.playSoundAtEntity(this, "snakeslither", 1.0F, 1.0F);
         * }
         */
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SNAKE_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SNAKE_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_SNAKE_AMBIENT.get();
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        /*
         * swamp: python, bright green, #1 (done) plains: coral, cobra #1, #2,
         * #3, #4 (everyone but 7) desert: rattlesnake , #2 jungle: all except
         * rattlesnake forest: all except rattlesnake hills: all except python,
         * bright green, bright orange, rattlesnake tundra-taiga: none ocean:
         * leave alone
         */

        /*
         * Biome lists: Ocean Plains Desert Extreme Hills Forest Taiga Swampland
         * River Frozen Ocean Frozen River Ice Plains Ice Mountains Mushroom
         * Island Mushroom Island Shore Beach DesertHills ForestHills TaigaHills
         * Extreme Hills Edge Jungle JungleHills
         *
         */
        try {
            RegistryKey<Biome> currentbiome = MoCTools.biomeKind(this.world, pos);
            int l = this.rand.nextInt(10);

            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                return false;
            }

            // Trying to make sense of the override order
            if (BiomeDictionary.hasType(currentbiome, Type.SANDY) || BiomeDictionary.hasType(currentbiome, Type.MESA)) {
                // rattlesnake or spotted
                if (l < 5) {
                    setTypeMoC(7);
                } else {
                    setTypeMoC(2);
                }
            }

            if (BiomeDictionary.hasType(currentbiome, Type.PLAINS) || BiomeDictionary.hasType(currentbiome, Type.FOREST)) {
                // dark green or coral or spotted
                if (l < 3) {
                    setTypeMoC(1);
                } else if (l < 5) {
                    setTypeMoC(5);
                } else {
                    setTypeMoC(2);
                }
            }

            if (BiomeDictionary.hasType(currentbiome, Type.SWAMP)) {
                // python or cobra or dark green
                if (l < 5) {
                    setTypeMoC(8);
                } else if (l < 7) {
                    setTypeMoC(6);
                } else {
                    setTypeMoC(1);
                }
            }

            if (BiomeDictionary.hasType(currentbiome, Type.JUNGLE)) {
                // bright green or bright orange or cobra or dark green
                if (l < 4) {
                    setTypeMoC(4);
                } else if (l < 6) {
                    setTypeMoC(3);
                } else if (l < 8) {
                    setTypeMoC(6);
                } else {
                    setTypeMoC(1);
                }
            }

            if (BiomeDictionary.hasType(currentbiome, MoCEntities.WYVERN_LAIR)) {
                // bright green or bright orange or spotted or dark green
                if (l < 3) {
                    setTypeMoC(4);
                } else if (l < 5) {
                    setTypeMoC(3);
                } else if (l < 7) {
                    setTypeMoC(2);
                } else {
                    setTypeMoC(1);
                }
            }

            if (getTypeMoC() == 7 && !(BiomeDictionary.hasType(currentbiome, Type.SANDY))) {
                setTypeMoC(2);
            }
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == MoCItems.ratRaw);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (isVenomous()) {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 150, 2));
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    private boolean isVenomous() {
        return getTypeMoC() == 3 || getTypeMoC() == 4 || getTypeMoC() == 5 || getTypeMoC() == 6 || getTypeMoC() == 7 || getTypeMoC() == 9;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.isPissed() && super.shouldAttackPlayers();
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
    public boolean canRidePlayer() {
        return true;
    }

    @Override
    protected double maxDivingDepth() {
        return (this.getAge() / 100D);
    }
}
