/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import com.google.common.collect.Sets;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

// Courtesy of Daveyx0, permission given
public class MoCEntityFilchLizard extends MoCEntityAnimal {

    protected ItemStack[] stealItems;

    public MoCEntityFilchLizard(EntityType<? extends MoCEntityFilchLizard> type, World worldIn) {
        super(type, worldIn);
        this.inventoryHandsDropChances[0] = 0f;
        this.inventoryHandsDropChances[1] = 0f;
        //this.setSize(0.6f, 0.5f);
        this.experienceValue = 3;
    }

    @Override
    protected void registerGoals() {
        stealItems = getCustomLootItems(this, this.getStealLootTable(), new ItemStack(Items.IRON_INGOT));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new EntityAIGrabItemFromFloor(this, 1.2D, Sets.newHashSet(stealItems), true));
        this.goalSelector.addGoal(3, new EntityAIStealFromPlayer(this, 0.8D, Sets.newHashSet(stealItems), true));
        this.goalSelector.addGoal(4, new MoCEntityFilchLizard.AIAvoidWhenNasty(this, PlayerEntity.class, 16.0F, 1.0D, 1.33D));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("lizard_filch_sand.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("lizard_filch_sand_red.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("lizard_filch_sand_silver.png");
            default:
                return MoCreatures.proxy.getModelTexture("lizard_filch.png");
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        RegistryKey<Biome> biome = MoCTools.biomeKind(this.world, pos);
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
            setTypeMoC(2);
        } else if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA)) {
            setTypeMoC(3);
        } else if (this.world.getDimensionKey() == MoCreatures.proxy.wyvernDimension) {
            setTypeMoC(4);
        } else {
            setTypeMoC(1);
        }
        return true;
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        if (!this.getHeldItemMainhand().isEmpty()) {
            return super.getSize(poseIn).scale(1.0F, 2.5F);
        } else {
            return super.getSize(poseIn);
        }
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Nullable
    protected ResourceLocation getSpawnLootTable() {
        return MoCLootTables.FILCH_LIZARD_SPAWN;
    }

    @Nullable
    protected ResourceLocation getStealLootTable() {
        return MoCLootTables.FILCH_LIZARD_STEAL;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAnimal.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.ARMOR, 2.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (rand.nextInt(100 / MoCreatures.proxy.filchLizardSpawnItemChance) == 0) {
            while (this.getHeldItemMainhand().isEmpty() && !getEntityWorld().isRemote) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, getCustomLootItem(this, this.getSpawnLootTable(), new ItemStack(Items.IRON_INGOT)));
            }
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void dropItemStack(ItemStack itemIn, float offsetY) {
        this.entityDropItem(itemIn, offsetY);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (par1DamageSource.getTrueSource() != null) {
            this.setLastAttackedEntity(par1DamageSource.getTrueSource());
        }
        ItemStack stack = this.getHeldItemMainhand();
        if (!stack.isEmpty() && !getEntityWorld().isRemote) {
            ItemStack newStack = new ItemStack(stack.getItem(), 1);
            this.dropItemStack(newStack, 1);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Nullable
    public ItemStack getCustomLootItem(Entity entityIn, ResourceLocation resourceLootTable, ItemStack defaultItem) {
        if (resourceLootTable != null) {
            LootTable loottable = entityIn.world.getServer().getLootTableManager().getLootTableFromLocation(resourceLootTable);
            LootContext.Builder lootContextBuilder = (new LootContext.Builder((ServerWorld) entityIn.world)).withParameter(LootParameters.ORIGIN, entityIn.getPositionVec()).withParameter(LootParameters.THIS_ENTITY, entityIn);
            for (ItemStack itemstack : loottable.generate(lootContextBuilder.build(LootParameterSets.SELECTOR))) {
                return itemstack;
            }
        }
        return defaultItem;
    }

    @Nullable
    public ItemStack[] getCustomLootItems(Entity entityIn, ResourceLocation resourceLootTable, ItemStack defaultItem) {
        ItemStack[] arrayOfItems = null;
        if (resourceLootTable != null) {
            LootTable loottable = entityIn.world.getServer().getLootTableManager().getLootTableFromLocation(resourceLootTable);
            LootContext.Builder lootContextBuilder = (new LootContext.Builder((ServerWorld) entityIn.world)).withParameter(LootParameters.ORIGIN, entityIn.getPositionVec()).withParameter(LootParameters.THIS_ENTITY, entityIn);
            List<ItemStack> listOfItems = loottable.generate(lootContextBuilder.build(LootParameterSets.SELECTOR));
            arrayOfItems = new ItemStack[listOfItems.size()];
            int i = 0;
            for (ItemStack itemstack : listOfItems) {
                arrayOfItems[i] = itemstack;
                i += 1;
            }
        }
        if (arrayOfItems == null) {
            arrayOfItems = new ItemStack[]{defaultItem};
        }
        return arrayOfItems;
    }

    // Sneaky...
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.FILCH_LIZARD;
    }

    static class AIAvoidWhenNasty extends AvoidEntityGoal {
        public AIAvoidWhenNasty(CreatureEntity theEntityIn, Class classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
            super(theEntityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean shouldExecute() {
            return !entity.getHeldItemMainhand().isEmpty() && super.shouldExecute();
        }
    }

    static class EntityAIGrabItemFromFloor extends Goal {
        /**
         * The entity using this AI that is tempted by the player.
         */
        private final CreatureEntity temptedEntity;
        private final double speed;
        private final Set<ItemStack> temptItem;
        private final boolean canGetScared;
        /**
         * X position of player tempting this mob
         */
        private double targetX;
        /**
         * Y position of player tempting this mob
         */
        private double targetY;
        /**
         * Z position of player tempting this mob
         */
        private double targetZ;
        /**
         * Tempting player's pitch
         */
        private double pitch;
        /**
         * Tempting player's yaw
         */
        private double yaw;
        /**
         * The player that is tempting the entity that is using this AI.
         */
        private ItemEntity temptingItem;
        private boolean isRunning;
        private int stealDelay = 0;

        public EntityAIGrabItemFromFloor(CreatureEntity temptedEntityIn, double speedIn, Set<ItemStack> temptItemIn, boolean canGetScared) {
            this.temptedEntity = temptedEntityIn;
            this.speed = speedIn;
            this.temptItem = temptItemIn;
            this.canGetScared = canGetScared;
            if (!(temptedEntityIn.getNavigator() instanceof GroundPathNavigator)) {
                throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
            }
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean shouldExecute() {
            if (temptedEntity.getLastAttackedEntity() != null && canGetScared && stealDelay <= 0) {
                this.resetTask();
                return false;
            }
            if (!this.temptedEntity.getHeldItemMainhand().isEmpty()) {
                return false;
            }
            List<Entity> list = this.temptedEntity.getEntityWorld().getEntitiesWithinAABBExcludingEntity(temptedEntity, temptedEntity.getBoundingBox().expand(6D, 4D, 6D));
            if (this.stealDelay > 0) {
                --this.stealDelay;
                if (stealDelay == 0) {
                    temptedEntity.setLastAttackedEntity(null);
                }
                return false;
            } else if (!list.isEmpty()) {
                for (Entity entity : list) {
                    if (entity instanceof ItemEntity) {
                        ItemEntity item = (ItemEntity) entity;
                        ItemStack stack = item.getItem();
                        if (!stack.isEmpty() && this.isTempting(stack)) {
                            this.temptingItem = item;
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        protected boolean isTempting(ItemStack stack) {
            if (!stack.isEmpty()) {
                for (ItemStack item : temptItem) {
                    if (item != null && item.getItem() == stack.getItem()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.targetX = this.temptingItem.getPosX();
            this.targetY = this.temptingItem.getPosY();
            this.targetZ = this.temptingItem.getPosZ();
            this.isRunning = true;
        }

        /**
         * Resets the task
         */
        public void resetTask() {
            this.temptingItem = null;
            this.temptedEntity.getNavigator().clearPath();
            this.isRunning = false;
            if (canGetScared) {
                this.stealDelay = 50;
            }
        }

        /**
         * Updates the task
         */
        public void tick() {
            this.temptedEntity.getLookController().setLookPositionWithEntity(this.temptingItem, (float) (this.temptedEntity.getHorizontalFaceSpeed() + 20), (float) this.temptedEntity.getVerticalFaceSpeed());
            if (this.temptedEntity.getDistanceSq(this.temptingItem) < 1.0D) {
                this.temptedEntity.getNavigator().clearPath();
                ItemStack loot = temptingItem.getItem().copy();
                temptingItem.remove();
                this.temptedEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, loot);
            } else {
                this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingItem, this.speed);
            }
        }

        /**
         * @see #isRunning
         */
        public boolean isRunning() {
            return this.isRunning;
        }
    }

    public class EntityAIStealFromPlayer extends Goal {
        /**
         * The entity using this AI that is tempted by the player.
         */
        private final CreatureEntity temptedEntity;
        private final double speed;
        private final Set<ItemStack> temptItem;
        private final boolean canGetScared;
        /**
         * X position of player tempting this mob
         */
        private double targetX;
        /**
         * Y position of player tempting this mob
         */
        private double targetY;
        /**
         * Z position of player tempting this mob
         */
        private double targetZ;
        /**
         * Tempting player's pitch
         */
        private double pitch;
        /**
         * Tempting player's yaw
         */
        private double yaw;
        /**
         * The player that is tempting the entity that is using this AI.
         */
        private PlayerEntity temptingPlayer;
        private boolean isRunning;
        private int stealDelay = 0;

        public EntityAIStealFromPlayer(CreatureEntity temptedEntityIn, double speedIn, Set<ItemStack> temptItemIn, boolean canGetScared) {
            this.temptedEntity = temptedEntityIn;
            this.speed = speedIn;
            this.temptItem = temptItemIn;
            this.canGetScared = canGetScared;
            if (!(temptedEntityIn.getNavigator() instanceof GroundPathNavigator)) {
                throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
            }
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean shouldExecute() {
            if (temptedEntity.getLastAttackedEntity() != null && canGetScared && stealDelay <= 0) {
                this.resetTask();
                return false;
            }
            if (!this.temptedEntity.getHeldItemMainhand().isEmpty()) {
                return false;
            }
            this.temptingPlayer = this.temptedEntity.getEntityWorld().getClosestPlayer(this.temptedEntity, 10.0D);
            if (this.stealDelay > 0) {
                --this.stealDelay;
                if (stealDelay == 0) {
                    temptedEntity.setLastAttackedEntity(null);
                }
                return false;
            } else if (temptingPlayer != null) {
                for (int i = 0; i < this.temptingPlayer.inventory.getSizeInventory(); i++) {
                    ItemStack item = this.temptingPlayer.inventory.getStackInSlot(i);
                    if (!item.isEmpty() && this.isTempting(item)) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected boolean isTempting(ItemStack stack) {
            if (!stack.isEmpty()) {
                for (ItemStack item : temptItem) {
                    if (item != null && item.getItem() == stack.getItem()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.targetX = this.temptingPlayer.getPosX();
            this.targetY = this.temptingPlayer.getPosY();
            this.targetZ = this.temptingPlayer.getPosZ();
            this.isRunning = true;
        }

        /**
         * Resets the task
         */
        public void resetTask() {
            this.temptingPlayer = null;
            this.temptedEntity.getNavigator().clearPath();
            this.isRunning = false;
            if (canGetScared) {
                this.stealDelay = 100;
            }
        }

        /**
         * Updates the task
         */
        public void tick() {
            this.temptedEntity.getLookController().setLookPositionWithEntity(this.temptingPlayer, (float) (this.temptedEntity.getHorizontalFaceSpeed() + 20), (float) this.temptedEntity.getVerticalFaceSpeed());
            if (temptingPlayer.abilities.isCreativeMode) return;
            if (this.temptedEntity.getDistanceSq(this.temptingPlayer) < 3.25D) {
                this.temptedEntity.getNavigator().clearPath();
                for (int i = 0; i < this.temptingPlayer.inventory.getSizeInventory(); i++) {
                    ItemStack item = this.temptingPlayer.inventory.getStackInSlot(i);
                    if (!item.isEmpty()) {
                        for (ItemStack itemstack : temptItem) {
                            if (itemstack != null && !itemstack.isEmpty() && itemstack.getItem() == item.getItem()) {
                                MoCTools.playCustomSound(this.temptedEntity, SoundEvents.ENTITY_ITEM_PICKUP);
                                ItemStack loot = item.copy();
                                this.temptedEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, loot);
                                item.shrink(1);
                                return;
                            }
                        }
                    }
                }
            } else {
                this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
            }
        }

        /**
         * @see #isRunning
         */
        public boolean isRunning() {
            return this.isRunning;
        }
    }
}
