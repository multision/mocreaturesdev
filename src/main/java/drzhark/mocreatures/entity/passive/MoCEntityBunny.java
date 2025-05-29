/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.*;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityBunny extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.createKey(MoCEntityBunny.class, DataSerializers.BOOLEAN);
    private int bunnyReproduceTickerA;
    private int bunnyReproduceTickerB;
    private int jumpTimer;

    public MoCEntityBunny(EntityType<? extends MoCEntityBunny> type, World world) {
        super(type, world);
        setAdult(true);
        setTamed(false);
        setAge(50 + this.rand.nextInt(15));
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);
        }
        //setSize(0.5F, 0.5F);
        this.bunnyReproduceTickerA = this.rand.nextInt(64);
        this.bunnyReproduceTickerB = 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.ARMOR, 1.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_EATEN, Boolean.FALSE);
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

    public boolean getHasEaten() {
        return this.dataManager.get(HAS_EATEN);
    }

    public void setHasEaten(boolean flag) {
        this.dataManager.set(HAS_EATEN, flag);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(5) + 1);
        }

    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);

        RegistryKey<Biome> currentbiome = MoCTools.biomeKind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setTypeMoC(3); //snow-white bunnies!
                return true;
            }
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_beige.png") : MoCreatures.proxy.getModelTexture("bunny_beige_detailed.png");
            case 3:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_white.png") : MoCreatures.proxy.getModelTexture("bunny_white_detailed.png");
            case 4:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_black.png") : MoCreatures.proxy.getModelTexture("bunny_black_detailed.png");
            case 5:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_spotted.png") : MoCreatures.proxy.getModelTexture("bunny_spotted_detailed.png");
            default:
                return MoCreatures.proxy.legacyBunnyTextures ? MoCreatures.proxy.getModelTexture("bunny_golden.png") : MoCreatures.proxy.getModelTexture("bunny_golden_detailed.png");
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
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
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.BUNNY;
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == Items.GOLDEN_CARROT) && !getHasEaten()) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            setHasEaten(true);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING.get());
            return ActionResultType.SUCCESS;
        }
        if (this.getRidingEntity() == null) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
                if (!getIsTamed() && !this.world.isRemote) {
                    MoCTools.tameWithName(player, this);
                }
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getRidingEntity() != null) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }
        if (!this.world.isRemote) {

            if (--this.jumpTimer <= 0 && this.onGround && ((this.getMotion().getX() > 0.05D) || (this.getMotion().getZ() > 0.05D) || (this.getMotion().getX() < -0.05D) || (this.getMotion().getZ() < -0.05D))) {
                this.setMotion(this.getMotion().getX(), 0.3D, this.getMotion().getZ());
                this.jumpTimer = 15;
            }

            if (!getIsTamed() || !getIsAdult() || !getHasEaten() || (this.getRidingEntity() != null)) {
                return;
            }
            if (this.bunnyReproduceTickerA < 1023) {
                this.bunnyReproduceTickerA++;
            } else if (this.bunnyReproduceTickerB < 127) {
                this.bunnyReproduceTickerB++;
            } else {
                List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4.0D));
                for (Entity entity1 : list1) {
                    if (!(entity1 instanceof MoCEntityBunny) || (entity1 == this)) {
                        continue;
                    }
                    MoCEntityBunny entitybunny = (MoCEntityBunny) entity1;
                    if ((entitybunny.getRidingEntity() != null) || (entitybunny.bunnyReproduceTickerA < 1023) || !entitybunny.getIsAdult() || !entitybunny.getHasEaten()) {
                        continue;
                    }
                    MoCEntityBunny entitybunny1 = MoCEntities.BUNNY.create(this.world);
                    entitybunny1.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                    entitybunny1.setAdult(false);
                    int babytype = this.getTypeMoC();
                    if (this.rand.nextInt(2) == 0) {
                        babytype = entitybunny.getTypeMoC();
                    }
                    entitybunny1.setTypeMoC(babytype);
                    this.world.addEntity(entitybunny1);
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    proceed();
                    entitybunny.proceed();
                    break;
                }
            }
        }
    }

    public void proceed() {
        setHasEaten(false);
        this.bunnyReproduceTickerB = 0;
        this.bunnyReproduceTickerA = this.rand.nextInt(64);
    }

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() { return !this.isMovementCeased(); }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.CARROT;
    }

    /**
     * So bunny-hats don't suffer damage
     */
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (this.getRidingEntity() != null) {
            return false;
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity) {
            return this.getRidingEntity().isSneaking() ? 0.25 : 0.5F;
        }

        return super.getYOffset();
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }

    @Override
    public boolean canRidePlayer() {
        return true;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.675F;
    }
}
