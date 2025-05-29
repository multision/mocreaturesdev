/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityBird extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> PRE_TAMED = EntityDataManager.createKey(MoCEntityBird.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.createKey(MoCEntityBird.class, DataSerializers.BOOLEAN);
    public float wingb;
    public float wingc;
    public float wingd;
    public float winge;
    public float wingh;
    public boolean textureSet;
    protected EntityAIWanderMoC2 wander;
    private boolean fleeing;
    private int jumpTimer;

    public MoCEntityBird(EntityType<? extends MoCEntityBird> type, World world) {
        super(type, world);
        //setSize(0.5F, 0.9F);
        this.collidedVertically = true;
        this.wingb = 0.0F;
        this.wingc = 0.0F;
        this.wingh = 1.0F;
        this.fleeing = false;
        this.textureSet = false;
        setTamed(false);
        this.stepHeight = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIFleeFromEntityMoC(this, entity -> !(entity instanceof MoCEntityBird) && (entity.getHeight() > 0.4F || entity.getWidth() > 0.4F), 6.0F, 1.D, 1.3D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 6.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(6) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("bird_white.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("bird_black.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("bird_green.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("bird_yellow.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("bird_red.png");
            default:
                return MoCreatures.proxy.getModelTexture("bird_blue.png");
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        RegistryKey<Biome> currentbiome = MoCTools.biomeKind(this.world, pos);

        try {
            if (BiomeDictionary.hasType(currentbiome, BiomeDictionary.Type.MESA)) {
                setTypeMoC(2); // only black birds
            }
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PRE_TAMED, Boolean.FALSE);
        this.dataManager.register(IS_FLYING, Boolean.FALSE);
    }

    public boolean getPreTamed() {
        return this.dataManager.get(PRE_TAMED);
    }

    public void setPreTamed(boolean flag) {
        this.dataManager.set(PRE_TAMED, flag);
    }

    public boolean getIsFlying() {
        return this.dataManager.get(IS_FLYING);
    }

    public void setIsFlying(boolean flag) {
        this.dataManager.set(IS_FLYING, flag);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    private int[] FindTreeTop(int i, int j, int k) {
        int l = i - 5;
        int i1 = k - 5;
        int j1 = i + 5;
        int k1 = j + 7;
        int l1 = k + 5;
        for (int i2 = l; i2 < j1; i2++) {
            label0:
            for (int j2 = i1; j2 < l1; j2++) {
                BlockPos pos = new BlockPos(i2, j, j2);
                BlockState blockstate = this.world.getBlockState(pos);
                if (blockstate.getBlock().isAir(blockstate, this.world, pos) || (blockstate.getMaterial() != Material.WOOD)) {
                    continue;
                }
                int l2 = j;
                do {
                    if (l2 >= k1) {
                        continue label0;
                    }
                    BlockPos pos1 = new BlockPos(i2, l2, j2);
                    BlockState blockstate1 = this.world.getBlockState(pos1);
                    if (blockstate1.getBlock().isAir(blockstate1, this.world, pos1)) {
                        return (new int[]{i2, l2 + 2, j2});
                    }
                    l2++;
                } while (true);
            }

        }

        return (new int[]{0, 0, 0});
    }

    private void FlyToNextEntity(Entity entity) {
        if (entity != null) {
            int i = MathHelper.floor(entity.getPosX());
            int j = MathHelper.floor(entity.getPosY());
            int k = MathHelper.floor(entity.getPosZ());
            faceLocation(i, j, k, 30F);
            if (MathHelper.floor(this.getPosY()) < j) {
                this.setMotion(this.getMotion().add(0.0D, 0.14999999999999999D, 0.0D));
            }
            if (this.getPosX() < entity.getPosX()) {
                double d = entity.getPosX() - this.getPosX();
                if (d > 0.5D) {
                    this.setMotion(this.getMotion().add(0.050000000000000003D, 0.0D, 0.0D));
                }
            } else {
                double d1 = this.getPosX() - entity.getPosX();
                if (d1 > 0.5D) {
                    this.setMotion(this.getMotion().subtract(0.050000000000000003D, 0.0D, 0.0D));
                }
            }
            if (this.getPosZ() < entity.getPosZ()) {
                double d2 = entity.getPosZ() - this.getPosZ();
                if (d2 > 0.5D) {
                    this.setMotion(this.getMotion().add(0.0D, 0.0D,0.050000000000000003D));
                }
            } else {
                double d3 = this.getPosZ() - entity.getPosZ();
                if (d3 > 0.5D) {
                    this.setMotion(this.getMotion().subtract(0.0D, 0.0D, 0.050000000000000003D));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private boolean FlyToNextTree() {
        int[] ai = ReturnNearestMaterialCoord(this, Material.LEAVES, 20D);
        int[] ai1 = FindTreeTop(ai[0], ai[1], ai[2]);
        if (ai1[1] != 0) {
            int i = ai1[0];
            int j = ai1[1];
            int k = ai1[2];
            faceLocation(i, j, k, 30F);
            if ((j - MathHelper.floor(this.getPosY())) > 2) {
                this.setMotion(this.getMotion().add(0.0D, 0.14999999999999999D, 0.0D));
            }
            int l;
            int i1;
            if (this.getPosX() < i) {
                l = i - MathHelper.floor(this.getPosX());
                this.setMotion(this.getMotion().add(0.050000000000000003D, 0.0D, 0.0D));
            } else {
                l = MathHelper.floor(this.getPosX()) - i;
                this.setMotion(this.getMotion().subtract(0.050000000000000003D, 0.0D, 0.0D));
            }
            if (this.getPosZ() < k) {
                i1 = k - MathHelper.floor(this.getPosZ());
                this.setMotion(this.getMotion().add(0.0D, 0.0D,0.050000000000000003D));
            } else {
                i1 = MathHelper.floor(this.getPosX()) - k;
                this.setMotion(this.getMotion().subtract(0.0D, 0.0D, 0.050000000000000003D));
            }
            double d = l + i1;
            return d < 3D;
        }
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BIRD_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_BIRD_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getTypeMoC() == 1) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_WHITE.get();
        }
        if (getTypeMoC() == 2) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_BLACK.get();
        }
        if (getTypeMoC() == 3) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_GREEN.get();
        }
        if (getTypeMoC() == 4) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_BLUE.get();
        }
        if (getTypeMoC() == 5) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_YELLOW.get();
        } else {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_RED.get();
        }
    }

    // TODO: Add unique sound event
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.BIRD;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity) {
            return this.getRidingEntity().isSneaking() ? 0.2 : 0.45F;
        }

        return super.getYOffset();
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getPreTamed() && !getIsTamed() && stack.getItem() == Items.WHEAT_SEEDS) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            return ActionResultType.SUCCESS;
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

    // TODO: Add updated flap ai based on vanilla's parrot
    @Override
    public void livingTick() {
        super.livingTick();

        this.winge = this.wingb;
        this.wingd = this.wingc;
        this.wingc = (float) (this.wingc + ((this.onGround ? -1 : 4) * 0.3D));
        if (this.wingc < 0.0F) {
            this.wingc = 0.0F;
        }
        if (this.wingc > 1.0F) {
            this.wingc = 1.0F;
        }
        if (!this.onGround && (this.wingh < 1.0F)) {
            this.wingh = 1.0F;
        }
        this.wingh = (float) (this.wingh * 0.9D);
        if (!this.onGround && (this.getMotion().getY() < 0.0D)) {
            this.setMotion(this.getMotion().mul(1.0D, 0.8D, 1.0D));
        }
        this.wingb += this.wingh * 2.0F;

        //check added to avoid duplicating behavior on client / server
        if (!this.world.isRemote) {

            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && rand.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (!getIsFlying() && !getIsTamed() && this.rand.nextInt(10) == 0) {
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4D));
                for (Entity entity1 : list) {
                    if (!(entity1 instanceof LivingEntity) || entity1 instanceof MoCEntityBird) {
                        continue;
                    }
                    if (entity1.getWidth() >= 0.4F && entity1.getHeight() >= 0.4F && canEntityBeSeen(entity1)) {
                        setIsFlying(true);
                        this.fleeing = true;
                        this.wander.makeUpdate();
                    }
                }
            }

            if (!isMovementCeased() && !getIsFlying() && this.rand.nextInt(getIsTamed() ? 1000 : 400) == 0) {
                setIsFlying(true);
                this.wander.makeUpdate();
            }

            if (getIsFlying() && rand.nextInt(200) == 0) {
                setIsFlying(false);
            }

            if (this.fleeing && rand.nextInt(50) == 0) {
                this.fleeing = false;
            }

            //TODO move to new AI
            if (!this.fleeing) {
                ItemEntity entityitem = getClosestItem(this, 12D, Ingredient.fromItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS));
                if (entityitem != null) {
                    FlyToNextEntity(entityitem);
                    ItemEntity entityitem1 = getClosestItem(this, 1.0D, Ingredient.fromItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS));
                    if ((this.rand.nextInt(50) == 0) && (entityitem1 != null)) {
                        entityitem1.remove();
                        setPreTamed(true);
                    }
                }
            }
            if (this.rand.nextInt(10) == 0 && areEyesInFluid(FluidTags.WATER)) {
                WingFlap();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getRidingEntity() != null) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }

        if ((this.getRidingEntity() != null) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            this.rotationYaw = entityplayer.rotationYaw;
            entityplayer.fallDistance = 0.0F;
            if (entityplayer.getMotion().getY() < -0.1D)
                entityplayer.setMotion(entityplayer.getMotion().mul(1.0D, 0.6D, 1.0D));
        }

        if (--this.jumpTimer <= 0 && this.onGround
                && ((this.getMotion().getX() > 0.05D) || (this.getMotion().getZ() > 0.05D) || (this.getMotion().getX() < -0.05D) || (this.getMotion().getZ() < -0.05D))) {
            this.setMotion(this.getMotion().getX(), 0.25D, this.getMotion().getZ());
            float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
            float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

            this.setMotion(this.getMotion().add((-0.2F * velX), 0.0D, (0.2F * velZ)));
            this.jumpTimer = 15;
        }
    }

    public int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(double1);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = this.world.getBlockState(pos);
                    blockstate.getBlock();
                    if (!blockstate.getBlock().isAir(blockstate, this.world, pos) && blockstate.getMaterial() == material) {
                        return (new int[]{k1, l1, i2});
                    }
                }

            }

        }

        return (new int[]{-1, 0, 0});
    }

    @Override
    public void remove(boolean keepData) {
        if (!this.world.isRemote && getIsTamed() && (this.getHealth() > 0)) {
        } else {
            super.remove(keepData);
        }
    }

    private void WingFlap() {
        this.setMotion(this.getMotion().add(0.0D, 0.05D, 0.0D));
        if (this.rand.nextInt(30) == 0) {
            this.setMotion(this.getMotion().add(0.2D, 0.0D, 0.0D));
        }
        if (this.rand.nextInt(30) == 0) {
            this.setMotion(this.getMotion().subtract(0.2D, 0.0D, 0.0D));
        }
        if (this.rand.nextInt(30) == 0) {
            this.setMotion(this.getMotion().add(0.0D, 0.0D, 0.2D));
        }
        if (this.rand.nextInt(30) == 0) {
            this.setMotion(this.getMotion().subtract(0.0D, 0.0D, 0.2D));
        }
    }

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() { return !this.isMovementCeased(); }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.WHEAT_SEEDS || stack.getItem() == Items.MELON_SEEDS);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed())
            return 4;
        return 6;
    }

    @Override
    public int minFlyingHeight() {
        return 2;
    }

    @Override
    public boolean canRidePlayer() {
        return true;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.75F;
    }
}
