/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.inventory.MoCAnimalChest;
import drzhark.mocreatures.entity.inventory.MoCContainer;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class MoCEntityElephant extends MoCEntityTameableAnimal {

    private static final DataParameter<Integer> TUSK_TYPE = EntityDataManager.createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STORAGE_TYPE = EntityDataManager.createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HARNESS_TYPE = EntityDataManager.createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    public int sprintCounter;
    public int sitCounter;
    public MoCAnimalChest localelephantchest;
    public MoCAnimalChest localelephantchest2;
    public MoCAnimalChest localelephantchest3;
    public MoCAnimalChest localelephantchest4;
    public ItemStack localstack;
    public int tailCounter;
    public int trunkCounter;
    public int earCounter;
    boolean hasPlatform;
    private byte tuskUses;
    private byte temper;


    public MoCEntityElephant(EntityType<? extends MoCEntityElephant> type, World world) {
        super(type, world);
        setAdult(true);
        setTamed(false);
        setAge(50);
        // TODO: Different hitboxes for each elephant type
        //setSize(1.1F, 3F);
        this.stepHeight = 1.0F;

        if (!this.world.isRemote) {
            setAdult(this.rand.nextInt(4) != 0);
        }

        experienceValue = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).createMutableAttribute(Attributes.MAX_HEALTH, 30.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(2) + 1);
        }
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TUSK_TYPE, 0);// tusks: 0 nothing, 1 wood, 2 iron, 3 diamond
        this.dataManager.register(STORAGE_TYPE, 0);// storage: 0 nothing, 1 chest, 2 chests....
        this.dataManager.register(HARNESS_TYPE, 0);// harness: 0 nothing, 1 harness, 2 cabin
    }

    public int getTusks() {
        return this.dataManager.get(TUSK_TYPE);
    }

    public void setTusks(int i) {
        this.dataManager.set(TUSK_TYPE, i);
    }

    @Override
    public int getArmorType() {
        return this.dataManager.get(HARNESS_TYPE);
    }

    @Override
    public void setArmorType(int i) {
        this.dataManager.set(HARNESS_TYPE, i);
    }

    public int getStorage() {
        return this.dataManager.get(STORAGE_TYPE);
    }

    public void setStorage(int i) {
        this.dataManager.set(STORAGE_TYPE, i);
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("elephant_asian.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("mammoth_woolly.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("mammoth_songhua.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("elephant_asian_decorated.png");
            default:
                return MoCreatures.proxy.getModelTexture("elephant_african.png");
        }
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    public float calculateMaxHealth() {
        switch (getTypeMoC()) {
            case 1:
            case 5:
                return 40;
            case 3:
                return 50;
            case 4:
                return 60;
            default:
                return 30;
        }
    }

    @Override
    public double getCustomSpeed() {
        if (this.sitCounter != 0) {
            return 0D;
        }
        double tSpeed = 0.5D;
        if (getTypeMoC() == 1) {
            tSpeed = 0.55D;
        } else if (getTypeMoC() == 2) {
            tSpeed = 0.6D;
        } else if (getTypeMoC() == 4) {
            tSpeed = 0.55D;
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 150) {
            tSpeed *= 1.5D;
        }
        if (this.sprintCounter > 150) {
            tSpeed *= 0.5D;
        }
        return tSpeed;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isBeingRidden()) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobsNotPlayers(this, 3D, this.world);
            }

            if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }

            if (getIsTamed() && (!this.isBeingRidden()) && getArmorType() >= 1 && this.rand.nextInt(20) == 0) {
                PlayerEntity ep = this.world.getClosestPlayer(this, 3D);
                if (ep != null && (!MoCreatures.proxy.enableOwnership || ep.getUniqueID().equals(this.getOwnerId())) && ep.isSneaking()) {
                    sit();
                }
            }

            if (MoCreatures.proxy.elephantBulldozer && getIsTamed() && (this.isBeingRidden()) && (getTusks() > 0)) {
                int height = 2;
                if (getTypeMoC() == 3) {
                    height = 3;
                }
                if (getTypeMoC() == 4) {
                    height = 3;
                }
                int dmg = MoCTools.destroyBlocksInFront(this, 2D, this.getTusks(), height);
                checkTusks(dmg);

            }

        } else //client only animation counters
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 8) {
                this.tailCounter = 0;
            }

            if (this.rand.nextInt(200) == 0) {
                this.tailCounter = 1;
            }

            if (this.trunkCounter > 0 && ++this.trunkCounter > 38) {
                this.trunkCounter = 0;
            }

            if (this.trunkCounter == 0 && this.rand.nextInt(200) == 0) {
                this.trunkCounter = rand.nextInt(10) + 1;
            }

            if (this.earCounter > 0 && ++this.earCounter > 30) {
                this.earCounter = 0;
            }

            if (this.rand.nextInt(200) == 0) {
                this.earCounter = rand.nextInt(20) + 1;
            }

        }

        if (this.sitCounter != 0) {
            if (++this.sitCounter > 100) {
                this.sitCounter = 0;
            }
        }
    }

    /**
     * Checks if the tusks sets need to break or not (wood = 59, stone = 131,
     * iron = 250, diamond = 1561, gold = 32)
     */
    private void checkTusks(int dmg) {
        this.tuskUses += (byte) dmg;
        if ((this.getTusks() == 1 && this.tuskUses > 59) || (this.getTusks() == 2 && this.tuskUses > 250)
                || (this.getTusks() == 3 && this.tuskUses > 1000)) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT.get());
            setTusks((byte) 0);
        }
    }

    private void sit() {
        this.sitCounter = 1;
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 0));
        }
        this.getNavigator().clearPath();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPath();
        }
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && !getIsTamed() && !getIsAdult() && stack.getItem() == Items.CAKE) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING.get());
            this.temper += 2;
            this.setHealth(getMaxHealth());
            if (!this.world.isRemote && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                MoCTools.tameWithName(player, this);
            }
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && !getIsTamed() && !getIsAdult() && stack.getItem() == MoCItems.sugarlump) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING.get());
            this.temper += 1;
            this.setHealth(getMaxHealth());
            if (!this.world.isRemote && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                setTamed(true);
                MoCTools.tameWithName(player, this);
            }
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 0 && stack.getItem() == MoCItems.elephantHarness) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            setArmorType((byte) 1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 0
                && stack.getItem() == MoCItems.elephantChest) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setStorage((byte) 1);
            return ActionResultType.SUCCESS;
        }
        // second storage unit
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 1
                && stack.getItem() == MoCItems.elephantChest) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            setStorage((byte) 2);
            return ActionResultType.SUCCESS;
        }
        // third storage unit for small mammoths
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && (getTypeMoC() == 3) && getArmorType() >= 1 && getStorage() == 2
                && stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            setStorage((byte) 3);
            return ActionResultType.SUCCESS;
        }
        // fourth storage unit for small mammoths
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && (getTypeMoC() == 3) && getArmorType() >= 1 && getStorage() == 3
                && stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            setStorage((byte) 4);
            return ActionResultType.SUCCESS;
        }

        //giving a garment to an indian elephant with a harness will make it pretty
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 1 && getTypeMoC() == 2
                && stack.getItem() == MoCItems.elephantGarment) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            setArmorType((byte) 2);
            setTypeMoC(5);
            return ActionResultType.SUCCESS;
        }

        //giving a howdah to a pretty indian elephant with a garment will attach the howdah
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 2 && getTypeMoC() == 5
                && stack.getItem() == MoCItems.elephantHowdah) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING.get());
            setArmorType((byte) 3);
            return ActionResultType.SUCCESS;
        }

        //giving a platform to a ? mammoth with harness will attach the platform
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 1 && getTypeMoC() == 4
                && stack.getItem() == MoCItems.mammothPlatform) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
            setArmorType((byte) 3);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && stack.getItem() == MoCItems.tusksWood) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
            dropTusks();
            this.tuskUses = (byte) stack.getDamage();
            setTusks((byte) 1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && stack.getItem() == MoCItems.tusksIron) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
            dropTusks();
            this.tuskUses = (byte) stack.getDamage();
            setTusks((byte) 2);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && stack.getItem() == MoCItems.tusksDiamond) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
            dropTusks();
            this.tuskUses = (byte) stack.getDamage();
            setTusks((byte) 3);
            return ActionResultType.SUCCESS;
        }

        if (player.isSneaking()) {
            initChest();
            if (getStorage() == 1) {
                if (!this.world.isRemote) {
                    player.openContainer(this.localelephantchest);
                }
                return ActionResultType.SUCCESS;
            }
            if (getStorage() == 2) {
                DoubleSidedInventory doubleChest = new DoubleSidedInventory(this.localelephantchest, this.localelephantchest2);
                if (!this.world.isRemote) {
                    player.openContainer(new MoCContainer("ElephantChest", MoCAnimalChest.Size.large, doubleChest));
                }
                return ActionResultType.SUCCESS;
            }
            if (getStorage() == 3) {
                DoubleSidedInventory doubleChest = new DoubleSidedInventory(this.localelephantchest, this.localelephantchest2);
                DoubleSidedInventory tripleChest = new DoubleSidedInventory(doubleChest, this.localelephantchest3);
                if (!this.world.isRemote) {
                    player.openContainer(new MoCContainer("ElephantChest", MoCAnimalChest.Size.huge, tripleChest));
                }
                return ActionResultType.SUCCESS;
            }
            if (getStorage() == 4) {
                DoubleSidedInventory doubleChest = new DoubleSidedInventory(this.localelephantchest, this.localelephantchest2);
                DoubleSidedInventory doubleChestb = new DoubleSidedInventory(this.localelephantchest3, this.localelephantchest4);
                DoubleSidedInventory fourChest = new DoubleSidedInventory(doubleChest, doubleChestb);
                if (!this.world.isRemote) {
                    player.openContainer(new MoCContainer("ElephantChest", MoCAnimalChest.Size.gigantic, fourChest));
                }
                return ActionResultType.SUCCESS;
            }

        }
        if (!stack.isEmpty() && getTusks() > 0 && stack.getItem() instanceof PickaxeItem) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
            dropTusks();
            return ActionResultType.SUCCESS;
        }

        if (getIsTamed() && getIsAdult() && getArmorType() >= 1 && this.sitCounter != 0) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                this.sitCounter = 0;
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    private void initChest() {
        if (getStorage() > 0 && this.localelephantchest == null) {
            this.localelephantchest = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.small);
        }

        if (getStorage() > 1 && this.localelephantchest2 == null) {
            this.localelephantchest2 = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.small);
        }

        if (getStorage() > 2 && this.localelephantchest3 == null) {
            this.localelephantchest3 = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.tiny);
        }

        if (getStorage() > 3 && this.localelephantchest4 == null) {
            this.localelephantchest4 = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.tiny);
        }
    }

    /**
     * Drops tusks, makes sound
     */
    private void dropTusks() {
        if (this.world.isRemote) {
            return;
        }
        int i = getTusks();

        if (i == 1) {
            ItemEntity entityitem =
                    new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.tusksWood, 1));
            entityitem.getItem().setDamage(this.tuskUses);
            entityitem.setDefaultPickupDelay();
            this.world.addEntity(entityitem);
        }
        if (i == 2) {
            ItemEntity entityitem =
                    new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.tusksIron, 1));
            entityitem.getItem().setDamage(this.tuskUses);
            entityitem.setDefaultPickupDelay();
            this.world.addEntity(entityitem);
        }
        if (i == 3) {
            ItemEntity entityitem =
                    new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.tusksDiamond, 1));
            entityitem.getItem().setDamage(this.tuskUses);
            entityitem.setDefaultPickupDelay();
            this.world.addEntity(entityitem);
        }
        setTusks((byte) 0);
        this.tuskUses = 0;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }
    */
    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        RegistryKey<Biome> currentbiome = MoCTools.biomeKind(this.world, pos);

        // African
        if (BiomeDictionary.hasType(currentbiome, Type.SANDY) || BiomeDictionary.hasType(currentbiome, Type.SAVANNA)) {
            setTypeMoC(1);
            return true;
        }
        // Indian
        if (BiomeDictionary.hasType(currentbiome, Type.JUNGLE)) {
            setTypeMoC(2);
            return true;
        }
        // Mammoth
        if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
            setTypeMoC(3 + this.rand.nextInt(2));
            return true;
        }

        return false;
    }

    @Override
    public float getSizeFactor() {
        float sizeF = 1.25F;

        switch (getTypeMoC()) {
            case 4:
                sizeF *= 1.2F;
                break;
            case 2:
            case 5:
                sizeF *= 0.80F;
                break;
        }

        if (!getIsAdult()) {
            sizeF = sizeF * (getAge() * 0.01F);
        }
        return sizeF;
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setTusks(nbttagcompound.getInt("Tusks"));
        setArmorType(nbttagcompound.getInt("Harness"));
        setStorage(nbttagcompound.getInt("Storage"));
        this.tuskUses = nbttagcompound.getByte("TuskUses");
        if (getStorage() > 0) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localelephantchest = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.small);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localelephantchest.getSizeInventory()) {
                    this.localelephantchest.setInventorySlotContents(j, ItemStack.read(nbttagcompound1));
                }
            }
        }
        if (getStorage() >= 2) {
            ListNBT nbttaglist = nbttagcompound.getList("Items2", 10);
            this.localelephantchest2 = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.small);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localelephantchest2.getSizeInventory()) {
                    this.localelephantchest2.setInventorySlotContents(j, ItemStack.read(nbttagcompound1));
                }
            }
        }

        if (getStorage() >= 3) {
            ListNBT nbttaglist = nbttagcompound.getList("Items3", 10);
            this.localelephantchest3 = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.tiny);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localelephantchest3.getSizeInventory()) {
                    this.localelephantchest3.setInventorySlotContents(j, ItemStack.read(nbttagcompound1));
                }
            }
        }
        if (getStorage() >= 4) {
            ListNBT nbttaglist = nbttagcompound.getList("Items4", 10);
            this.localelephantchest4 = new MoCAnimalChest("ElephantChest", MoCAnimalChest.Size.tiny);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localelephantchest4.getSizeInventory()) {
                    this.localelephantchest4.setInventorySlotContents(j, ItemStack.read(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putInt("Tusks", getTusks());
        nbttagcompound.putInt("Harness", getArmorType());
        nbttagcompound.putInt("Storage", getStorage());
        nbttagcompound.putByte("TuskUses", this.tuskUses);

        if (getStorage() > 0 && this.localelephantchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }

        if (getStorage() >= 2 && this.localelephantchest2 != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest2.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest2.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items2", nbttaglist);
        }

        if (getStorage() >= 3 && this.localelephantchest3 != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest3.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest3.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items3", nbttaglist);
        }

        if (getStorage() >= 4 && this.localelephantchest4 != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest4.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest4.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items4", nbttaglist);
        }
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() == Items.BAKED_POTATO || stack.getItem() == Items.BREAD || stack.getItem() == MoCItems.haystack);
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || this.sitCounter != 0;
    }

    @Override
    public void riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(1.0D, 0.0D, 1.0D));
            for (Entity entity : list) {
                if (entity.removed) {
                    continue;
                }
                entity.onCollideWithPlayer(entityplayer);
            }

            if (entityplayer.isSneaking()) {
                if (!this.world.isRemote) {
                    if (this.sitCounter == 0) {
                        sit();
                    }
                    if (this.sitCounter >= 50) {
                        entityplayer.dismount();
                    }

                }
            }
        }
    }

    @Override
    public boolean canBePushed() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void updatePassenger(Entity passenger) {

        double dist = (1.0D);
        switch (getTypeMoC()) {
            case 1:
            case 3:
                dist = 0.8D;
                break;
            case 2:
            case 5:
                dist = 0.1D;
                break;
            case 4:
                dist = 1.2D;
                break;
        }

        double newPosX = this.getPosX() - (dist * Math.cos((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.sin((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public double getMountedYOffset() {
        double yOff = 0F;
        boolean sit = (this.sitCounter != 0);

        switch (getTypeMoC()) {
            case 1:
            case 3:
                yOff = 0.55D;
                if (sit) {
                    yOff = -0.05D;
                }
                break;
            case 2:
            case 5:
                yOff = -0.17D;
                if (sit) {
                    yOff = -0.5D;
                }
                break;
            case 4:
                yOff = 1.2D;
                if (sit) {
                    yOff = 0.45D;
                }
                break;
        }
        return yOff + (this.getHeight() * 0.75D);
    }

    /**
     * Had to set to false to avoid damage due to the collision boxes
     */
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ELEPHANT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_ELEPHANT_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!getIsAdult() && getAge() < 80) {
            return MoCSoundEvents.ENTITY_ELEPHANT_AMBIENT_BABY.get();
        }
        return MoCSoundEvents.ENTITY_ELEPHANT_AMBIENT.get();
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        int i = this.rand.nextInt(3);

        if (looting > 0)
        {
            i += this.rand.nextInt(looting + 1);
        }
        this.entityDropItem(new ItemStack(MoCItems.animalHide, i));
    }

    @Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropTusks();
            //dropSaddle(this, world);
            if (getStorage() > 0) {
                if (getStorage() > 0) {
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.elephantChest, 1));
                    if (this.localelephantchest != null) {
                        MoCTools.dropInventory(this, this.localelephantchest);
                    }

                }
                if (getStorage() >= 2) {
                    if (this.localelephantchest2 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest2);
                    }
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.elephantChest, 1));
                }
                if (getStorage() >= 3) {
                    if (this.localelephantchest3 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest3);
                    }
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                }
                if (getStorage() >= 4) {
                    if (this.localelephantchest4 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest4);
                    }
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                }
                setStorage((byte) 0);
            }
            dropArmor();
        }
    }

    @Override
    public void dropArmor() {
        if (this.world.isRemote) {
            return;
        }
        if (getArmorType() >= 1) {
            MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.elephantHarness, 1));
        }
        if (getTypeMoC() == 5 && getArmorType() >= 2) {

            MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.elephantGarment, 1));
            if (getArmorType() == 3) {
                MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.elephantHowdah, 1));
            }
            setTypeMoC(2);
        }
        if (getTypeMoC() == 4 && getArmorType() == 3) {
            MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.mammothPlatform, 1));
        }
        setArmorType((byte) 0);

    }

    @Override
    public int nameYOffset() {
        int yOff = (int) ((100 / getAge()) * (getSizeFactor() * -110));
        if (getIsAdult()) {
            yOff = (int) (getSizeFactor() * -110);
        }
        if (sitCounter != 0)
            yOff += 25;
        return yOff;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != null && getIsTamed() && entity instanceof PlayerEntity) || !(entity instanceof LivingEntity)) {
                return false;
            }
            if (this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && super.shouldAttackPlayers()) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        int i = (int) Math.ceil(distance - 3F);
        if ((i > 0)) {
            i /= 2;
            if (i > 0) {
                attackEntityFrom(DamageSource.FALL, i);
            }
            if ((this.isBeingRidden()) && (i > 0)) {
                for (Entity entity : this.getRecursivePassengers()) {
                    entity.attackEntityFrom(DamageSource.FALL, i);
                }
            }
            BlockState iblockstate = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double) this.prevRotationYaw, this.getPosZ()));
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() != Material.AIR && !this.isSilent()) {
                SoundType soundtype = block.getSoundType(iblockstate, world, new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double) this.prevRotationYaw, this.getPosZ()), this);
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
            return  true;
        }
        return false;
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || getAge() > 80 || getIsTamed();
    }
}
