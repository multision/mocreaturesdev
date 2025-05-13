/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class MoCEntityAquatic extends WaterMobEntity implements IMoCEntity {

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.createKey(MoCEntityAquatic.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.createKey(MoCEntityAquatic.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.createKey(MoCEntityAquatic.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.createKey(MoCEntityAquatic.class, DataSerializers.STRING);
    protected boolean fishHooked;
    protected boolean divePending;
    protected boolean jumpPending;
    protected boolean isEntityJumping;
    protected int outOfWater;
    protected boolean riderIsDisconnecting;
    protected float moveSpeed;
    protected String texture;
    protected PathNavigator navigatorWater;
    protected int temper;
    private boolean diving;
    private int divingCount;
    private int mountCount;
    private boolean updateDivingDepth = false;
    private double divingDepth;

    protected MoCEntityAquatic(EntityType<? extends MoCEntityAquatic> type, World world) {
        super(type, world);
        this.outOfWater = 0;
        setTemper(50);
        this.setNewDivingDepth();
        this.riderIsDisconnecting = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.moveController = new EntityAIMoverHelperMoC(this);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ITextComponent getName() {
        String entityString = this.getType().getTranslationKey();
        if (!MoCreatures.proxy.verboseEntityNames || entityString == null) return super.getName();
        String translationKey = "entity." + entityString + ".verbose.name";
        String translatedString = I18n.format(translationKey);
        return !translatedString.equals(translationKey) ? new TranslationTextComponent(translationKey) : super.getName();
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.7F).createMutableAttribute(Attributes.MAX_HEALTH, 6.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture(this.texture);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        selectType();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void selectType() {
        setTypeMoC(1);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ADULT, false);
        this.dataManager.register(TYPE, 0);
        this.dataManager.register(AGE, 45);
        this.dataManager.register(NAME_STR, "");
    }

    @Override
    public int getTypeMoC() {
        return this.dataManager.get(TYPE);
    }

    @Override
    public void setTypeMoC(int i) {
        this.dataManager.set(TYPE, i);
    }

    @Override
    public int getOwnerPetId() {
        return -1;
    }

    @Override
    public void setOwnerPetId(int i) {
    }

    @Nullable
    public UUID getOwnerId() {
        return null;
    }

    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public boolean getIsAdult() {
        return this.dataManager.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
        this.dataManager.set(ADULT, flag);
    }

    @Override
    public String getPetName() {
        return this.dataManager.get(NAME_STR);
    }

    @Override
    public void setPetName(String name) {
        this.dataManager.set(NAME_STR, name);
    }

    @Override
    public int getAge() {
        return this.dataManager.get(AGE);
    }

    @Override
    public void setAge(int i) {
        this.dataManager.set(AGE, i);
        if (getAge() >= getMaxAge()) {
            setAdult(true);
        }
    }

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int i) {
        this.temper = i;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    public int getMaxTemper() {
        return 100;
    }

    public float b(float f, float f1, float f2) {
        float f3;
        for (f3 = f1 - f; f3 < -180F; f3 += 360F) {
        }
        for (; f3 >= 180F; f3 -= 360F) {
        }
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    public void faceItem(int i, int j, int k, float f) {
        double d = i - this.getPosX();
        double d1 = k - this.getPosZ();
        double d2 = j - this.getPosY();
        double d3 = MathHelper.sqrt((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        this.rotationPitch = -b(this.rotationPitch, f2, f);
        this.rotationYaw = b(this.rotationYaw, f1, f);
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState par4) {
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    public ItemEntity getClosestFish(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) continue;
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (!ItemTags.FISHES.contains(entityitem1.getItem().getItem()) || !entityitem1.isInWater()) continue;
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }
        return entityitem;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    /**
     * mount jumping power
     */
    public double getCustomJump() {
        return 0.4D;
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;
    }

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump() {
        this.jumpPending = true;
    }

    protected void moveToNextEntity(Entity entity) {
        if (entity != null) {
            int i = MathHelper.floor(entity.getPosX());
            int j = MathHelper.floor(entity.getPosY());
            int k = MathHelper.floor(entity.getPosZ());
            faceItem(i, j, k, 30F);
            if (this.getPosX() < i) {
                double d = entity.getPosX() - this.getPosX();
                if (d > 0.5D) {
                    this.setMotion(this.getMotion().add(0.050000000000000003D, 0.0F, 0.0F));
                }
            } else {
                double d1 = this.getPosX() - entity.getPosX();
                if (d1 > 0.5D) {
                    this.setMotion(this.getMotion().subtract(0.050000000000000003D, 0.0F, 0.0F));
                }
            }
            if (this.getPosZ() < k) {
                double d2 = entity.getPosZ() - this.getPosZ();
                if (d2 > 0.5D) {
                    this.setMotion(this.getMotion().add(0.0F, 0.0F, 0.050000000000000003D));
                }
            } else {
                double d3 = this.getPosZ() - entity.getPosZ();
                if (d3 > 0.5D) {
                    this.setMotion(this.getMotion().subtract(0.0F, 0.0F, 0.050000000000000003D));
                }
            }
        }
    }

    /**
     * Speed used to move the mob around
     */
    public double getCustomSpeed() {
        return 1.5D;
    }

    @Override
    public boolean isDiving() {
        return this.diving;
    }

    @Override
    protected void jump() {

    }

    // used to pick up objects while riding an entity
    public void riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(1.0D, 0.0D, 1.0D));
            for (Entity entity : list) {
                if (entity.removed) continue;
                entity.onCollideWithPlayer(entityplayer);
                if (!(entity instanceof MonsterEntity)) continue;
                float f = getDistance(entity);
                if (f < 2.0F && this.rand.nextInt(10) == 0) {
                    attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) entity), (float) ((MonsterEntity) entity).getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
        }
    }

    @Override
    public boolean isMovementCeased() {
        return ((!isSwimming() && !this.isBeingRidden()) || this.isBeingRidden() || this.getIsSitting());
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            if (this.isBeingRidden()) {
                riding();
                this.mountCount = 1;
            }

            if (this.mountCount > 0 && ++this.mountCount > 50) {
                this.mountCount = 0;
            }
            if (getAge() == 0) setAge(getMaxAge() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.rand.nextInt(300) == 0)) {
                setAge(getAge() + 1);
                if (getAge() >= getMaxAge()) {
                    setAdult(true);
                }
            }

            this.getNavigator().tick();

            //updates diving depth after finishing movement
            if (!this.getNavigator().noPath())// && !updateDivingDepth)
            {
                if (!this.updateDivingDepth) {
                    float targetDepth = (MoCTools.distanceToSurface(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), this.world));
                    setNewDivingDepth(targetDepth);
                    this.updateDivingDepth = true;
                }
            } else {
                this.updateDivingDepth = false;
            }

            if (isMovementCeased() || rand.nextInt(200) == 0) {
                this.getNavigator().clearPath();
            }

            if (isFisheable() && !this.fishHooked && this.rand.nextInt(30) == 0) {
                getFished();
            }

            if (this.fishHooked && this.rand.nextInt(200) == 0) {
                this.fishHooked = false;

                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(2));
                for (Entity entity1 : list) {
                    if (entity1 instanceof FishingBobberEntity && ((FishingBobberEntity) entity1).func_234607_k_() == this) {
                        // TODO: PRIVATE ACCESS
                        //((FishingBobberEntity) entity1).onEntityHit(new EntityRayTraceResult(null));
                    }
                }
            }
        }

        this.moveSpeed = 0.7F;

        if (isSwimming()) {
            this.outOfWater = 0;
            this.setAir(800);
        } else {
            this.outOfWater++;
            this.setMotion(this.getMotion().subtract(0.0F, 0.1F, 0.0F));
            if (this.outOfWater > 20) {
                this.getNavigator().clearPath();
            }
            if (this.outOfWater > 300 && (this.outOfWater % 40) == 0) {
                this.setMotion((Math.random() * 0.2D - 0.1D), this.getMotion().getY() + 0.3F, (Math.random() * 0.2D - 0.1D));
                attackEntityFrom(DamageSource.DROWN, 1);
            }
        }
        if (!this.diving) {
            if (!this.isBeingRidden() && getAttackTarget() == null && !this.navigator.noPath() && this.rand.nextInt(500) == 0) {
                this.diving = true;
            }
        } else {
            this.divingCount++;
            if (this.divingCount > 100 || this.isBeingRidden()) {
                this.diving = false;
                this.divingCount = 0;
            }
        }
        super.livingTick();
    }

    public boolean isSwimming() {
        return areEyesInFluid(FluidTags.WATER);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Adult", getIsAdult());
        nbttagcompound.putInt("Edad", getAge());
        nbttagcompound.putString("Name", getPetName());
        nbttagcompound.putInt("TypeInt", getTypeMoC());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setAge(nbttagcompound.getInt("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setTypeMoC(nbttagcompound.getInt("TypeInt"));
    }

    public void setTypeInt(int i) {
        setTypeMoC(i);
        selectType();
    }

    /**
     * Used to synchronize the attack animation between server and client
     */
    @Override
    public void performAnimation(int attackType) {
    }

    /**
     * Makes the entity despawn if requirements are reached changed to the
     * entities now last longer
     */
    @Override
    public void checkDespawn() {
        PlayerEntity var1 = this.world.getClosestPlayer(this, -1.0D);
        if (var1 != null) {
            double d0 = var1.getDistanceSq(this);
            int i = this.getType().getClassification().getInstantDespawnDistance();
            int j = i * i;
            if (d0 > (double)j && this.canDespawn(d0)) {
                this.remove();
            }
            int k = this.getType().getClassification().getRandomDespawnDistance();
            int l = k * k;
            //changed from 600
            if (this.idleTime > 1800 && this.rand.nextInt(800) == 0 && d0 > (double) l && this.canDespawn(d0)) {
                this.remove();
            } else if (d0 < (double) l) {
                this.idleTime = 0;
            }
        }
    }

    @Override
    public int nameYOffset() {
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName() && (getPetName() != null && !getPetName().isEmpty() && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    public static boolean getCanSpawnHere(EntityType<MoCEntityAquatic> type, IWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        boolean willSpawn = pos.getY() >= world.getSeaLevel() - 12;
        boolean debug = MoCreatures.proxy.debug;
        if (debug && willSpawn)
            MoCreatures.LOGGER.info("Aquatic: " + type.getName() + " at: " + pos + " State: " + world.getBlockState(pos) + " biome: " + MoCTools.biomeName(world, pos));
        return willSpawn;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (usesNewAI()) {
            return super.attackEntityFrom(damagesource, i);
        }

        if (isNotScared()) {
            LivingEntity tempEntity = this.getAttackTarget();
            setAttackTarget(tempEntity);
            return super.attackEntityFrom(damagesource, i);
        }

        return super.attackEntityFrom(damagesource, i);
    }

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
    }

    protected void dropMyStuff() {
    }

    /**
     * Used to heal the animal
     */
    protected boolean isMyHealFood(ItemStack itemstack) {
        return false;
    }

    @Override
    public void setArmorType(int i) {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    /**
     * The act of getting Hooked into a fish Hook.
     */
    private void getFished() {
        PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 18D);
        if (entityplayer1 != null) {
            FishingBobberEntity fishHook = entityplayer1.fishingBobber;
            if (fishHook != null && fishHook.func_234607_k_() == null) {
                float f = fishHook.getDistance(this);
                if (f > 1) {
                    MoCTools.setPathToEntity(this, fishHook, f);
                } else {
                    // TODO: PRIVATE ACCESS
                    //fishHook.onEntityHit(new EntityRayTraceResult(this));
                    this.fishHooked = true;
                }
            }
        }
    }

    /**
     * Is this aquatic entity prone to be fished with a fish Hook?
     */
    protected boolean isFisheable() {
        return false;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.world.getDifficulty() != Difficulty.PEACEFUL;
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(Vector3d travelVector) {
        if (this.isInWater()) {
            if (this.isBeingRidden()) {
                LivingEntity passenger = (LivingEntity) this.getControllingPassenger();
                if (passenger != null) this.moveWithRider(passenger, travelVector); //riding movement
                return;
            }
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().mul(0.8999999761581421D, 0.8999999761581421D, 0.8999999761581421D));

            if (this.getAttackTarget() == null && this.navigator.noPath()) {
                this.setMotion(this.getMotion().subtract(0.0F, 0.005D, 0.0F));
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d2 = this.getPosX() - this.prevPosX;
            double d3 = this.getPosZ() - this.prevPosZ;
            float f7 = MathHelper.sqrt(d2 * d2 + d3 * d3) * 4.0F;

            if (f7 > 1.0F) {
                f7 = 1.0F;
            }

            this.limbSwingAmount += (f7 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else {
            super.travel(travelVector);
        }
    }

    /**
     * * riding Code
     */
    public void moveWithRider(LivingEntity passenger, Vector3d travelVector) {
        if (passenger == null) {
            return;
        }
        //Buckles rider if out of water
        if (this.isBeingRidden() && !getIsTamed() && !isSwimming()) {
            this.removePassengers();
            return;
        }

        if (this.isBeingRidden() && !getIsTamed()) {
            this.moveWithRiderUntamed(passenger, travelVector);
            return;
        }

        if (this.isBeingRidden() && getIsTamed()) {
            this.prevRotationYaw = this.rotationYaw = passenger.rotationYaw;
            this.rotationPitch = passenger.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            travelVector = new Vector3d(passenger.moveStrafing * 0.35F, travelVector.getY(),passenger.moveForward * (float) (this.getCustomSpeed() / 5D));
            if (this.jumpPending) {
                if (this.isSwimming()) {
                    this.setMotion(this.getMotion().add(0.0F, getCustomJump(), 0.0F));
                }
                this.jumpPending = false;
            }
            //So it doesn't sink on its own
            if (this.getMotion().getY() < 0D && isSwimming()) {
                this.setMotion(this.getMotion().getX(), 0D, this.getMotion().getZ());
            }
            if (this.divePending) {
                this.divePending = false;
                this.setMotion(this.getMotion().subtract(0.0F, 0.3D, 0.0F));
            }
            this.setAIMoveSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            super.travel(travelVector);
            this.moveRelative(0.1F, travelVector);
        }
    }

    public void moveWithRiderUntamed(LivingEntity passenger, Vector3d travelVector) {
        if ((this.isBeingRidden()) && !getIsTamed()) {
            if ((this.rand.nextInt(5) == 0) && !getIsJumping() && this.jumpPending) {
                this.setMotion(this.getMotion().add(0.0F, getCustomJump(), 0.0F));
                setIsJumping(true);
                this.jumpPending = false;
            }
            if (this.rand.nextInt(10) == 0) {
                this.setMotion(this.getMotion().add(this.rand.nextDouble() / 30D, 0.0F, this.rand.nextDouble() / 10D));
            }
            move(MoverType.SELF, this.getMotion());
            if (!this.world.isRemote && this.rand.nextInt(100) == 0) {
                this.setMotion(this.getMotion().add(0.0F, 0.9D, -0.3D));
                passenger.dismount();
            }
            if (this.onGround) {
                setIsJumping(false);
            }
            if (!this.world.isRemote && this instanceof IMoCTameable && passenger instanceof PlayerEntity) {
                int chance = (getMaxTemper() - getTemper());
                if (chance <= 0) {
                    chance = 1;
                }
                if (this.rand.nextInt(chance * 8) == 0) {
                    MoCTools.tameWithName((PlayerEntity) passenger, (IMoCTameable) this);
                }
            }
        }
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    @Override
    public int getTalkInterval() {
        return 300;
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void updateAir(int air) {
        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -30) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
                this.setMotion(this.getMotion().add(this.rand.nextDouble() / 10D, 0.0F, this.rand.nextDouble() / 10D));
            }
        } else {
            this.setAir(300);
        }
    }

    protected boolean usesNewAI() {
        return false;
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isInWater()) {
            return this.navigatorWater;
        }
        return this.navigator;
    }

    /**
     * The distance the entity will float under the surface. 0F = surface 1.0F = 1 block under
     */
    @Override
    public double getDivingDepth() {
        return (float) this.divingDepth;
    }

    /**
     * Sets diving depth. if setDepth given = 0.0D, will then choose a random value within proper range
     */
    protected void setNewDivingDepth(double setDepth) {
        if (setDepth != 0.0D) {
            if (setDepth > maxDivingDepth()) {
                setDepth = maxDivingDepth();
            }
            if (setDepth < minDivingDepth()) {
                setDepth = minDivingDepth();
            }
            this.divingDepth = setDepth;
        } else {
            this.divingDepth = (float) (this.rand.nextDouble() * (maxDivingDepth() - minDivingDepth()) + minDivingDepth());
        }

    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.20D;
    }

    protected double maxDivingDepth() {
        return 3.0D;
    }

    @Override
    public void forceEntityJump() {
        this.jump();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        double d4 = 0F;
        if ((this.getMotion().getX() != 0D) || (this.getMotion().getZ() != 0D)) {
            d4 = Math.sin(this.ticksExisted * 0.5D) * 8D;
        }
        return (float) (d4);
    }

    public int getMaxAge() {
        return 100;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!entityIn.isInWater()) {
            return false;
        }
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Override
    public int maxFlyingHeight() {
        return 1;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }

    @Override
    public void setLeashHolder(Entity entityIn, boolean sendAttachNotification) {
        if (this.getIsTamed() && entityIn instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityIn;
            if (MoCreatures.proxy.enableOwnership && this.getOwnerId() != null && !entityplayer.getUniqueID().equals(this.getOwnerId()) && !MoCTools.isThisPlayerAnOP((entityplayer))) {
                return;
            }
        }
        super.setLeashHolder(entityIn, sendAttachNotification);
    }
}
