/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.PathNavigateFlyer;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class MoCEntityAnimal extends AnimalEntity implements IMoCEntity {

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.createKey(MoCEntityAnimal.class, DataSerializers.STRING);
    protected boolean divePending;
    protected boolean jumpPending;
    protected int temper;
    protected boolean isEntityJumping;
    protected boolean riderIsDisconnecting; // used to prevent dupes when a player disconnects on animal from server
    protected String texture;
    protected boolean isTameable;
    protected PathNavigator navigatorWater;
    protected PathNavigator navigatorFlyer;
    private int huntingCounter;
    private double divingDepth;
    private boolean randomAttributesUpdated; //used to update divingDepth on world load

    protected MoCEntityAnimal(EntityType<? extends MoCEntityAnimal> type, World world) {
        super(type, world);
        this.riderIsDisconnecting = false;
        this.isTameable = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.moveController = new EntityAIMoverHelperMoC(this);
        this.navigatorFlyer = new PathNavigateFlyer(this, world);
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

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.MAX_HEALTH, 20.0D);
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

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
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

    public boolean isMale() {
        return (this.getTypeMoC() == 1);
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName() && (getPetName() != null && !getPetName().isEmpty() && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
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

    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    @Override
    public UUID getOwnerId() {
        return null;
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;
    }

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
    }

    /**
     * called in getCanSpawnHere to make sure the right type of creature spawns
     * in the right biome i.e. snakes, rays, bears, BigCats and later wolves,
     * etc.
     */
    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    protected LivingEntity getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (entitiesToIgnore(entity1)) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    public LivingEntity getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof LivingEntity) || (entity1 == entity) || (entity1 == entity.getRidingEntity()) || (entity1 instanceof PlayerEntity) || (entity1 instanceof MonsterEntity) || (this.getHeight() <= entity1.getHeight()) || (this.getWidth() <= entity1.getWidth())) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }
        return entityliving;
    }

    public boolean entitiesToIgnore(Entity entity) {
        return !(entity instanceof MobEntity) || entity instanceof MonsterEntity || entity instanceof MoCEntityKittyBed || entity instanceof MoCEntityLitterBox || this.getIsTamed() && entity instanceof IMoCEntity && ((IMoCEntity) entity).getIsTamed() || entity instanceof WolfEntity && !MoCreatures.proxy.attackWolves || entity instanceof MoCEntityHorse && !MoCreatures.proxy.attackHorses || entity.getWidth() >= this.getWidth() || entity.getHeight() >= this.getHeight() || entity instanceof MoCEntityEgg || entity instanceof IMoCEntity && !MoCreatures.proxy.enableHunters;
    }

    /**
     * Finds and entity described in entitiesToInclude at d distance
     */
    protected LivingEntity getBoogey(double d) {
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d, 4D, d));
        for (Entity entity : list) {
            if (entitiesToInclude(entity)) {
                entityliving = (LivingEntity) entity;
            }
        }
        return entityliving;
    }

    /**
     * Used in getBoogey to specify what kind of entity to look for
     */
    public boolean entitiesToInclude(Entity entity) {
        return ((entity.getClass() != this.getClass()) && (entity instanceof LivingEntity) && ((entity.getWidth() >= 0.5D) || (entity.getHeight() >= 0.5D)));
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {  // Server Side
            if (rideableEntity() && this.isBeingRidden()) {
                riding();
            }

            if (isMovementCeased()) {
                this.getNavigator().clearPath();
            }
            if (getAge() == 0) {
                setAge(getMaxAge() - 10); //fixes tiny creatures spawned by error
            }
            if (!getIsAdult() && (this.rand.nextInt(300) == 0) && getAge() <= getMaxAge()) {
                setAge(getAge() + 1);
                if (getAge() >= getMaxAge()) {
                    setAdult(true);
                }
            }

            if (MoCreatures.proxy.enableHunters && this.isReadyToHunt() && !this.getIsHunting() && this.rand.nextInt(500) == 0) {
                setIsHunting(true);
            }

            if (getIsHunting() && ++this.huntingCounter > 50) {
                setIsHunting(false);
            }

            this.getNavigator().tick();
        }

        if (this.isInWater() && this.isAmphibian() && (this.rand.nextInt(500) == 0 || !this.randomAttributesUpdated)) {
            this.setNewDivingDepth();
            this.randomAttributesUpdated = true;
        }

        if (this.canRidePlayer() && this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
        super.livingTick();
    }

    public int getMaxAge() {
        return 100;
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    public boolean swimmerEntity() {
        return false;
    }

    public boolean isSwimming() {
        return areEyesInFluid(FluidTags.WATER);
    }

    //used to drop armor, inventory, saddles, etc.
    public void dropMyStuff() {
    }

    /**
     * Used to heal the animal
     */
    protected boolean isMyHealFood(ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return isAmphibian();
    }

    public ItemEntity getClosestItem(Entity entity, double d, Ingredient... items) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (items.length > 0 && Arrays.stream(items).noneMatch(item -> item.test(entityitem1.getItem()))) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public ItemEntity getClosestEntityItem(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public void faceLocation(int i, int j, int k, float f) {
        double var4 = i + 0.5D - this.getPosX();
        double var8 = k + 0.5D - this.getPosZ();
        double var6 = j + 0.5D - this.getPosY();
        double var14 = MathHelper.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.rotationPitch = -this.updateRotation2(this.rotationPitch, var13, f);
        this.rotationYaw = this.updateRotation2(this.rotationYaw, var12, f);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation2(float par1, float par2, float par3) {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {
        }

        while (var4 >= 180.0F) {
            var4 -= 360.0F;
        }

        if (var4 > par3) {
            var4 = par3;
        }

        if (var4 < -par3) {
            var4 = -par3;
        }

        return par1 + var4;
    }

    public void setPathToEntity(Entity entity, float distance) {
        Path pathEntity = this.getNavigator().pathfind(entity, 0);
        if (pathEntity != null) {
            this.getNavigator().setPath(pathEntity, 1D);
        }
    }

    /**
     * Called to make ridden entities pass on collision to rider
     */
    public void riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(1.0D, 0.0D, 1.0D));
            for (Entity entity : list) {
                if (entity.removed) {
                    continue;
                }
                entity.onCollideWithPlayer(entityplayer);
                if (!(entity instanceof MonsterEntity)) {
                    continue;
                }
                float f = getDistance(entity);
                if (f < 2.0F && this.rand.nextInt(10) == 0) {
                    attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) entity), (float) ((MonsterEntity) entity).getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
            if (entityplayer.isSneaking()) {
                this.makeEntityDive();
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigator.getPathToPos(entity.getPosition(), 0);
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor(entity.getPosX()) - 2;
            int j = MathHelper.floor(entity.getPosZ()) - 2;
            int k = MathHelper.floor(entity.getBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, k, j);
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && this.world.getBlockState(pos.add(l, -1, i1)).isNormalCube(world, pos) && !this.world.getBlockState(pos.add(l, 0, i1)).isNormalCube(world, pos) && !this.world.getBlockState(pos.add(l, 1, i1)).isNormalCube(world, pos)) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }
            }
        } else {
            this.navigator.setPath(pathentity, 1D);
        }
    }

    public static boolean getCanSpawnHere(EntityType<MoCEntityAnimal> type, IWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        boolean willSpawn;
        boolean debug = MoCreatures.proxy.debug;
        BlockState iblockstate = world.getBlockState(pos.down());
        willSpawn = world.getLight(pos) > 8 && iblockstate.canEntitySpawn(world, pos, type);
        if (debug && willSpawn)
            MoCreatures.LOGGER.info("Animal: " + type.getName() + " at: " + pos + " State: " + world.getBlockState(pos) + " biome: " + MoCTools.biomeName(world, pos));
        return willSpawn;
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

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(Vector3d vector) { //float strafe, float vertical, float forward) {

        if (this.isBeingRidden()) {
            LivingEntity passenger = (LivingEntity) this.getControllingPassenger();
            if (passenger != null) this.moveWithRider(vector, passenger); //riding movement
            return;
        }
        if ((this.isAmphibian() && isInWater()) || (this.isFlyer() && getIsFlying())) { //amphibian in water movement
            this.moveRelative(0.1F, vector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().mul(0.8999999761581421D, 0.8999999761581421D, 0.8999999761581421D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().subtract( 0, 0.005D, 0));
            }
        } else // regular movement
        {
            super.travel(vector);
        }

    }

    /**
     * riding Code
     */
    public void moveWithRider(Vector3d vector, LivingEntity passenger) {
        if (passenger == null) {
            return;
        }
        if (this.isBeingRidden() && !getIsTamed()) {
            this.moveEntityWithRiderUntamed(vector, passenger);
            return;
        }
        boolean flySelfPropelled = selfPropelledFlyer() && isOnAir(); //like the black ostrich
        boolean flyingMount = isFlyer() && (this.isBeingRidden()) && getIsTamed() && !this.onGround && isOnAir();
        this.rotationYaw = passenger.rotationYaw;
        this.prevRotationYaw = this.rotationYaw;
        this.rotationPitch = passenger.rotationPitch * 0.5F;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.renderYawOffset = this.rotationYaw;
        this.rotationYawHead = this.renderYawOffset;
        if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir())) {
            vector = new Vector3d(passenger.moveStrafing * 0.5F * this.getCustomSpeed(), vector.getY() ,passenger.moveForward * this.getCustomSpeed());
        }

        if (this.jumpPending && (isFlyer())) {
            this.setMotion(this.getMotion().add(0.0D, flyerThrust(), 0.0D));
            this.jumpPending = false;

            if (flySelfPropelled) {
                float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
                float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

                this.setMotion(this.getMotion().add(-0.5F * velX, 0.0D, 0.5F * velZ));;
            }
        } else if (this.jumpPending && !getIsJumping()) {
            this.setMotion(this.getMotion().getX(), getCustomJump() * 2,this.getMotion().getZ());
            setIsJumping(true);
            this.jumpPending = false;
        }

        if (this.divePending) {
            this.divePending = false;
            this.setMotion(this.getMotion().subtract(0.0D, 0.3D, 0.0D));
        }
        if (flyingMount) {
            this.move(MoverType.SELF, this.getMotion());
            this.moveRelative(this.flyerFriction() / 10F, vector);
            this.setMotion(this.getMotion().mul(this.flyerFriction(), this.myFallSpeed(), this.flyerFriction()).subtract(0.0D, 0.055D, 0.0D));
        } else {
            this.setAIMoveSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            super.travel(vector);

        }
        if (this.onGround) {
            setIsJumping(false);
            this.divePending = false;
            this.jumpPending = false;
        }
    }

    public void moveEntityWithRiderUntamed(Vector3d vector, LivingEntity passenger) {
        if (!this.world.isRemote) {
            if (this.rand.nextInt(10) == 0) {
                this.setMotion(this.rand.nextGaussian() / 30D, this.getMotion().getY(), this.rand.nextGaussian() / 10D);
            }

            this.move(MoverType.SELF, this.getMotion());

            if (this.rand.nextInt(50) == 0) {
                passenger.dismount();
                this.jump();
            }

            if (this instanceof IMoCTameable && passenger instanceof PlayerEntity) {
                int chance = (this.getMaxTemper() - this.getTemper());
                if (chance <= 0) chance = 1;
                if (this.rand.nextInt(chance * 8) == 0)
                    MoCTools.tameWithName((PlayerEntity) passenger, (IMoCTameable) this);
            }
        }
    }

    /**
     * Maximum flyer height when moving autonomously
     */
    public int maxFlyingHeight() {
        return 5;
    }

    /**
     * Used for flyer mounts, to calculate fall speed
     */
    protected double myFallSpeed() {
        return 0.6D;
    }

    /**
     * flyer mounts Y thrust
     */
    protected double flyerThrust() {
        return 0.3D;
    }

    /**
     * flyer deceleration on Z and X axis
     */
    protected float flyerFriction() {
        return 0.91F;
    }

    /**
     * Alternative flyer mount movement, when true, the player only controls
     * frequency of wing flaps
     */
    protected boolean selfPropelledFlyer() {
        return false;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump() {
        this.jumpPending = true;
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
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

    /**
     * mount speed
     */
    public double getCustomSpeed() {
        return 0.6D;
    }

    /**
     * mount jumping power
     */
    public double getCustomJump() {
        return 0.4D;
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    protected SoundEvent getAngrySound() {
        return null;
    }

    /**
     * Is this a rideable entity?
     */
    public boolean rideableEntity() {
        return false;
    }

    @Override
    public int nameYOffset() {
        return -80;
    }


    /**
     * fixes bug with entities following a player carrying wheat
     */
    @SuppressWarnings("unused")
    protected Entity findPlayerToAttack() {
        return null;
    }

    @SuppressWarnings("unused")
    public boolean isFlyingAlone() {
        return false;
    }

    /**
     * Used to synchronize animations between server and client
     */
    @Override
    public void performAnimation(int attackType) {
    }

    /**
     * Used to follow the player carrying the item
     */
    @SuppressWarnings("unused")
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return false;
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    public boolean isOnAir() {
        return (this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() - 0.2D), MathHelper.floor(this.getPosZ()))) && this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() - 1.2D), MathHelper.floor(this.getPosZ()))));
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (!this.world.isRemote) {
            dropMyStuff();
        }

        super.onDeath(damagesource);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (isNotScared()) {
            LivingEntity tempEntity = this.getAttackTarget();
            boolean flag = super.attackEntityFrom(damagesource, i);
            setAttackTarget(tempEntity);
            return flag;
        }

        return super.attackEntityFrom(damagesource, i);
    }

    public boolean getIsRideable() {
        return false;
    }

    public void setRideable(boolean b) {
    }

    public int getArmorType() {
        return 0;
    }

    @Override
    public void setArmorType(int i) {
    }

    /**
     * Drops armor if the animal has one
     */
    public void dropArmor() {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    @Override
    public float yawRotationOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return this.getHeight() >= entity.getHeight() && this.getWidth() >= entity.getWidth();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public boolean isReadyToHunt() {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        if (!this.world.isRemote && !MoCTools.isThisPlayerAnOP(player) && this.getIsTamed() && !player.getUniqueID().equals(this.getOwnerId())) {
            return false;
        }
        return super.canBeLeashedTo(player);
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || this.isBeingRidden();
    }

    public boolean getIsHunting() {
        return this.huntingCounter != 0;
    }

    public void setIsHunting(boolean flag) {
        if (flag) {
            this.huntingCounter = this.rand.nextInt(30) + 1;
        } else {
            this.huntingCounter = 0;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public void onKillEntity(ServerWorld world, LivingEntity entityLivingIn) {
        if (!(entityLivingIn instanceof PlayerEntity)) {
            MoCTools.destroyDrops(this, 3D);
        }
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer() && getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean isDiving() {
        return false;
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
            this.divingDepth = (float) ((this.rand.nextDouble() * (maxDivingDepth() - minDivingDepth())) + minDivingDepth());
        }
    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.2D;
    }

    protected double maxDivingDepth() {
        return 1.0D;
    }

    @Override
    public void forceEntityJump() {
        this.jump();
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    /***
     * Used to select Animals that can 'ride' the player. Like mice, snakes, turtles, birds
     */
    public boolean canRidePlayer() {
        return false;
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

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.isPassenger() && super.isEntityInsideOpaqueBlock();
    }
}
