/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageTwoBytes;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoCEntityGolem extends MoCEntityMob implements IEntityAdditionalSpawnData {

    private static final DataParameter<Integer> GOLEM_STATE = EntityDataManager.createKey(MoCEntityGolem.class, DataSerializers.VARINT);
    public int tCounter;
    private byte[] golemCubes;
    private int dCounter = 0;
    private int sCounter;

    public MoCEntityGolem(EntityType<? extends MoCEntityGolem> type, World world) {
        super(type, world);
        this.texture = "golem.png";
        //this.setSize(1.8F, 4.3F);
        experienceValue = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MoCEntityGolem.AIGolemAttack(this));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MoCEntityGolem.AIGolemTarget<>(this, PlayerEntity.class));
        this.targetSelector.addGoal(3, new MoCEntityGolem.AIGolemTarget<>(this, IronGolemEntity.class));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 50.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public void writeSpawnData(PacketBuffer data) {
        for (int i = 0; i < 23; i++) data.writeByte(this.golemCubes[i]);
    }

    @Override
    public void readSpawnData(PacketBuffer data) {
        for (int i = 0; i < 23; i++) this.golemCubes[i] = data.readByte();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.initGolemCubes();
        this.dataManager.register(GOLEM_STATE, 0); // 0: spawned / 1: summoning rocks / 2: has enemy / 3: half life (harder) / 4: dying
    }

    public int getGolemState() {
        return this.dataManager.get(GOLEM_STATE);
    }

    public void setGolemState(int i) {
        this.dataManager.set(GOLEM_STATE, i);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (getGolemState() == 0) // just spawned
            {
                PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 8D);
                if (entityplayer1 != null) setGolemState(1); // activated
            }

            if (getGolemState() == 1 && !isMissingCubes()) setGolemState(2); // is complete

            if (getGolemState() > 2 && getGolemState() != 4 && this.getAttackTarget() == null) setGolemState(1);

            if (getGolemState() > 1 && this.getAttackTarget() != null && this.rand.nextInt(20) == 0) {
                if (getHealth() >= 30) setGolemState(2);
                if (getHealth() < 30 && getHealth() >= 10) setGolemState(3); // more dangerous
                if (getHealth() < 10) setGolemState(4); // dying
            }

            if (getGolemState() != 0 && getGolemState() != 4 && isMissingCubes()) {
                int freq = 42 - (getGolemState() * this.world.getDifficulty().getId());
                if (getGolemState() == 1) freq = 20;
                if (this.rand.nextInt(freq) == 0) acquireRock(2);
            }

            if (getGolemState() == 4) {
                this.getNavigator().clearPath();
                this.dCounter++;

                if (this.dCounter < 80 && this.rand.nextInt(3) == 0) acquireRock(4);

                if (this.dCounter == 120) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_DYING.get(), 3F);
                    MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 1));
                }

                if (this.dCounter > 140) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_EXPLODE.get(), 3F);
                    destroyGolem();
                }
            }
        }

        if (this.tCounter == 0 && this.getAttackTarget() != null && this.canShoot()) {
            float distanceToTarget = this.getDistance(this.getAttackTarget());
            if (distanceToTarget > 6F) {
                this.tCounter = 1;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 0));
            }

        }
        if (this.tCounter != 0) {
            if (this.tCounter++ == 70 && this.getAttackTarget() != null && this.canShoot() && !this.getAttackTarget().removed && this.canEntityBeSeen(this.getAttackTarget())) {
                shootBlock(this.getAttackTarget());
            } else if (this.tCounter > 90) this.tCounter = 0;
        }

        if (MoCreatures.proxy.getParticleFX() > 0 && getGolemState() == 4 && this.sCounter > 0) {
            for (int i = 0; i < 10; i++) {
                this.world.addParticle(ParticleTypes.POOF, this.getPosX(), this.getPosY(), this.getPosZ(), this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian());
            }
        }
    }

    private void destroyGolem() {
        List<Integer> usedBlocks = usedCubes();
        if (!usedBlocks.isEmpty() && MoCTools.mobGriefing(this.world) && MoCreatures.proxy.golemDestroyBlocks) {
            for (Integer usedBlock : usedBlocks) {
                Block block = Block.getStateById(generateBlock(this.golemCubes[usedBlock])).getBlock();
                ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(block, 1));
                entityitem.setDefaultPickupDelay();
                this.world.addEntity(entityitem);
            }
        }
        this.remove();
    }

    @Override
    public boolean isMovementCeased() {
        return getGolemState() == 4;
    }

    /*
     * Finds a missing rock spot in its body, looks for a random rock around it
     */
    protected void acquireRock(int type) {
        if (this.world.isRemote) return;

        BlockPos blockPos = MoCTools.getRandomSurfaceBlockPos(this, 12);
        BlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        boolean canDestroyBlock = MoCTools.mobGriefing(this.world) && MoCreatures.proxy.golemDestroyBlocks;
        if (block instanceof AirBlock || blockState.getBlockHardness(this.world, blockPos) < 0 || blockState.getBlockHardness(this.world, blockPos) > 50)
            canDestroyBlock = false; // skip air and unbreakable rocks
        if (canDestroyBlock) {
            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(this.world, blockPos, blockState, FakePlayerFactory.get((ServerWorld) this.world, MoCreatures.MOCFAKEPLAYER));
            if (!event.isCanceled()) this.world.destroyBlock(blockPos, false); // destroys the original rock
        } else blockState = returnRandomCheapBlock(); // get cheap rocks

        MoCEntityThrowableRock tRock = MoCEntityThrowableRock.build(this.world, this, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
        tRock.setState(blockState);
        tRock.setBehavior(type); // 2: rock follows the golem / 3: rock gets around the golem
        this.world.addEntity(tRock); // spawns the new TRock
    }

    /**
     * Returns a random block when the golem is unable to break blocks
     */
    private BlockState returnRandomCheapBlock() {
        int i = this.rand.nextInt(4);
        switch (i) {
            case 1:
                return Blocks.COBBLESTONE.getDefaultState();
            case 2:
                return Blocks.OAK_PLANKS.getDefaultState();
            case 3:
                return Blocks.ICE.getDefaultState();
            default:
                return Blocks.DIRT.getDefaultState();
        }
    }

    /**
     * When the golem receives the rock, called from within EntityTRock
     */
    public void receiveRock(BlockState state) {
        if (!this.world.isRemote) {
            byte myBlock = translateOre(Block.getStateId(state));
            byte slot = (byte) getRandomCubeAdj();
            if (slot != -1 && slot < 23 && myBlock != -1 && getGolemState() != 4) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_ATTACH.get(), 3F);
                int h = this.world.getDifficulty().getId();
                this.setHealth(getHealth() + h);
                if (getHealth() > getMaxHealth()) this.setHealth(getMaxHealth());
                saveGolemCube(slot, myBlock);
            } else {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT.get(), 2F);
                if ((MoCTools.mobGriefing(this.world)) && (MoCreatures.proxy.golemDestroyBlocks)) {
                    ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(state.getBlock(), 1));
                    entityitem.setDefaultPickupDelay();
                    entityitem.lifespan = 1200;
                }
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) this.tCounter = 1; // rock throwing animation
        else if (animationType == 1) this.sCounter = 1; // smoking animation
    }

    /**
     * Shoots one block to the target
     */
    private void shootBlock(Entity entity) {
        if (entity == null) return;

        List<Integer> armBlocks = new ArrayList<>();
        for (int i = 9; i < 15; i++) {
            if (this.golemCubes[i] != 30) armBlocks.add(i);
        }
        if (armBlocks.isEmpty()) return;

        int j = this.rand.nextInt(armBlocks.size());
        int i = armBlocks.get(j);
        int x = i;

        if (i == 9 || i == 12) {
            if (this.golemCubes[i + 2] != 30) x = i + 2;
            else if (this.golemCubes[i + 1] != 30) x = i + 1;
        }

        if (this.golemCubes[i + 1] != 30 && (i == 10 || i == 13)) x = i + 1;
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_SHOOT.get(), 3F);
        MoCTools.throwStone(this, entity, Block.getStateById(generateBlock(this.golemCubes[x])), 10D, 0.4D);
        saveGolemCube((byte) x, (byte) 30);
        this.tCounter = 0;
    }

    private boolean canShoot() {
        int x = 0;
        for (byte i = 9; i < 15; i++) {
            if (this.golemCubes[i] != 30) x++;
        }
        return (x != 0) && getGolemState() != 4 && getGolemState() != 1;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (getGolemState() == 4) return false;

        List<Integer> missingChestBlocks = missingChestCubes();
        boolean uncoveredChest = (missingChestBlocks.size() == 4);
        if (!openChest() && !uncoveredChest && getGolemState() != 1) {
            int j = this.world.getDifficulty().getId();
            if (!this.world.isRemote && this.rand.nextInt(j) == 0) destroyRandomGolemCube();
            else MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT.get(), 2F);

            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity) entity);
                return true;
            } else return false;
        }
        if (i > 5) i = 5; //so you can't hit a Golem too hard
        if (getGolemState() != 1 && super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity) entity);
                return true;
            } else return false;
        }
        if (getGolemState() == 1) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity) entity);
                return true;
            } else return false;
        } else return false;
    }

    /**
     * Destroys a random cube, with the proper check for extremities and spawns
     * a block in world
     */
    private void destroyRandomGolemCube() {
        int i = getRandomUsedCube();
        if (i == 4) return; // do not destroy the valuable back cube

        int x = i;
        if (i == 10 || i == 13 || i == 16 || i == 19) {
            if (this.golemCubes[i + 1] != 30) x = i + 1;
        }

        if (i == 9 || i == 12 || i == 15 || i == 18) {
            if (this.golemCubes[i + 2] != 30) x = i + 2;
            else if (this.golemCubes[i + 1] != 30) x = i + 1;
        }

        if (x != -1 && this.golemCubes[x] != 30) {
            Block block = Block.getStateById(generateBlock(this.golemCubes[x])).getBlock();
            saveGolemCube((byte) x, (byte) 30);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_HURT.get(), 3F);
            if ((MoCTools.mobGriefing(this.world)) && (MoCreatures.proxy.golemDestroyBlocks)) {
                ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(block, 1));
                entityitem.setDefaultPickupDelay();
                this.world.addEntity(entityitem);
            }
        }
    }

    @Override
    public float getAdjustedYOffset() {
        if (this.golemCubes[17] != 30 || this.golemCubes[20] != 30) return 0F; // has feet
        if (this.golemCubes[16] != 30 || this.golemCubes[19] != 30) return 0.4F; // has knees but not feet
        if (this.golemCubes[15] != 30 || this.golemCubes[18] != 30) return 0.7F; // has thighs but not knees or feet
        if (this.golemCubes[1] != 30 || this.golemCubes[3] != 30) return 0.8F; // has lower chest
        return 1.45F; //missing everything
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.8F;
    }

    /**
     * @param i = slot
     * @return the block type stored in that slot. 30 = empty
     */
    public byte getBlockText(int i) {
        return this.golemCubes[i];
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putInt("golemState", getGolemState());
        ListNBT cubeLists = new ListNBT();

        for (int i = 0; i < 23; i++) {
            CompoundNBT nbttag = new CompoundNBT();
            nbttag.putByte("Slot", this.golemCubes[i]);
            cubeLists.add(nbttag);
        }
        nbttagcompound.put("GolemBlocks", cubeLists);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setGolemState(nbttagcompound.getInt("golemState"));
        ListNBT nbttaglist = nbttagcompound.getList("GolemBlocks", 10);
        for (int i = 0; i < 23; i++) {
            CompoundNBT var4 = nbttaglist.getCompound(i);
            this.golemCubes[i] = var4.getByte("Slot");
        }
    }

    /**
     * Initializes the goleCubes array
     */
    private void initGolemCubes() {
        this.golemCubes = new byte[23];

        for (int i = 0; i < 23; i++) this.golemCubes[i] = 30;

        int j = this.rand.nextInt(4);
        switch (j) {
            case 0:
                j = 7;
                break;
            case 1:
                j = 11;
                break;
            case 2:
                j = 15;
                break;
            case 3:
                j = 21;
                break;
        }
        saveGolemCube((byte) 4, (byte) j);
    }

    /**
     * Saves the type of Cube(value) on the given 'slot' if server, then sends a
     * packet to the clients
     */
    public void saveGolemCube(byte slot, byte value) {
        this.golemCubes[slot] = value;
        if (!this.world.isRemote && MoCreatures.proxy.worldInitDone) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageTwoBytes(this.getEntityId(), slot, value));
        }
    }

    /**
     * returns a list of the empty blocks
     */
    private List<Integer> missingCubes() {
        List<Integer> emptyBlocks = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] == 30) emptyBlocks.add(i);
        }
        return emptyBlocks;
    }

    /**
     * Returns true if is 'missing' any cube, false if it's full
     */
    public boolean isMissingCubes() {
        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] == 30) return true;
        }
        return false;
    }

    private List<Integer> missingChestCubes() {
        List<Integer> emptyChestBlocks = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (this.golemCubes[i] == 30) emptyChestBlocks.add(i);
        }
        return emptyChestBlocks;
    }

    /**
     * returns a list of the used block spots
     */
    private List<Integer> usedCubes() {
        List<Integer> usedBlocks = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] != 30) usedBlocks.add(i);
        }
        return usedBlocks;
    }

    /**
     * Returns a random used cube position if the golem is empty, returns -1
     */
    private int getRandomUsedCube() {
        List<Integer> usedBlocks = usedCubes();
        if (usedBlocks.isEmpty()) return -1;
        int randomEmptyBlock = this.rand.nextInt(usedBlocks.size());
        return usedBlocks.get(randomEmptyBlock);
    }

    /**
     * Returns a random empty cube position if the golem is full, returns -1
     */
    private int getRandomMissingCube() {
        //first it makes sure it has the four chest cubes
        List<Integer> emptyChestBlocks = missingChestCubes();
        if (!emptyChestBlocks.isEmpty()) {
            int randomEmptyBlock = this.rand.nextInt(emptyChestBlocks.size());
            return emptyChestBlocks.get(randomEmptyBlock);
        }

        //otherwise returns any other cube
        List<Integer> emptyBlocks = missingCubes();
        if (emptyBlocks.isEmpty()) {
            return -1;
        }
        int randomEmptyBlock = this.rand.nextInt(emptyBlocks.size());
        return emptyBlocks.get(randomEmptyBlock);
    }

    /**
     * returns the position of the cube to be added, contains logic for the
     * extremities
     */
    private int getRandomCubeAdj() {
        int i = getRandomMissingCube();

        if (i == 10 || i == 13 || i == 16 || i == 19) {
            if (this.golemCubes[i - 1] != 30) saveGolemCube((byte) i, this.golemCubes[i - 1]);
            return i - 1;
        }

        if (i == 11 || i == 14 || i == 17 || i == 20) {
            if (this.golemCubes[i - 2] == 30 && this.golemCubes[i - 1] == 30) return i - 2;
            if (this.golemCubes[i - 1] != 30) saveGolemCube((byte) i, this.golemCubes[i - 1]);
            saveGolemCube((byte) (i - 1), this.golemCubes[i - 2]);
            return i - 2;
        }
        return i;
    }

    @Override
    public float rollRotationOffset() {
        int leftLeg = 0;
        int rightLeg = 0;
        if (this.golemCubes[15] != 30) leftLeg++;
        if (this.golemCubes[16] != 30) leftLeg++;
        if (this.golemCubes[17] != 30) leftLeg++;
        if (this.golemCubes[18] != 30) rightLeg++;
        if (this.golemCubes[19] != 30) rightLeg++;
        if (this.golemCubes[20] != 30) rightLeg++;
        return (leftLeg - rightLeg) * 10F;
    }

    /**
     * The chest opens when the Golem is missing cubes and the summoned blocks
     * are close
     */
    public boolean openChest() {
        if (isMissingCubes()) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(2D));
            for (Entity entity1 : list) {
                if (entity1 instanceof MoCEntityThrowableRock) {
                    if (MoCreatures.proxy.getParticleFX() > 0) MoCreatures.proxy.VacuumFX(this);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converts the world block into the golem block texture if not found,
     * returns -1
     */
    private byte translateOre(int blockType) {
        switch (blockType) {
            case 0:
            case 1:
                return 0;
            case 18:
                return 10; //leaves
            case 2:
            case 3:
                return 1; //dirt, grass
            case 4:
            case 48:
                return 2; //cobblestones
            case 5:
                return 3;
            case 12:
                return 4;
            case 13:
                return 5;
            case 16:
            case 21:
            case 56:
            case 74:
            case 73:
                return 24; //all ores are transformed into diamond ore
            case 14:
            case 41:
                return 7; //ore gold and block gold = block gold
            case 15:
            case 42:
                return 11;//iron ore and blocks = block iron
            case 57:
                return 15; //block diamond
            case 17:
                return 6; //wood
            case 20:
                return 8;
            case 22:
            case 35: //lapis and cloths
                return 9;
            case 45:
                return 12; //brick
            case 49:
                return 14; //obsidian
            case 58:
                return 16; //workbench
            case 61:
            case 62:
                return 17; //stonebench
            case 78:
            case 79:
                return 18; //ice
            case 81:
                return 19; //cactus
            case 82:
                return 20; //clay
            case 86:
            case 91:
            case 103:
                return 22; //pumpkin pumpkin lantern melon
            case 87:
                return 23; //netherrack
            case 89:
                return 25; //glowstone
            case 98:
                return 26; //stonebrick
            case 112:
                return 27; //netherbrick
            case 129:
            case 133:
                return 21; //emeralds
            default:
                return -1;
        }
    }

    /**
     * Provides the blockID originated from the golem's block
     */
    private int generateBlock(int golemBlock) {
        switch (golemBlock) {
            case 0:
                return 1;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 12;
            case 5:
                return 13;
            case 6:
                return 17;
            case 7:
                return 41;
            case 8:
                return 20;
            case 9:
                return 35;
            case 10:
                return 18;
            case 11:
                return 42;
            case 12:
                return 45;
            case 13: //unused
                return 2;
            case 14:
                return 49;
            case 15:
                return 57;
            case 16:
                return 58;
            case 17:
                return 51;
            case 18:
                return 79;
            case 19:
                return 81;
            case 20:
                return 82;
            case 21:
                return 133;
            case 22:
                return 86;
            case 23:
                return 87;
            case 24:
                return 56;
            case 25:
                return 89;
            case 26:
                return 98;
            case 27:
                return 112;
            default:
                return 2;
        }
    }

    private int countLegBlocks() {
        int x = 0;
        for (byte i = 15; i < 21; i++) {
            if (this.golemCubes[i] != 30) x++;
        }
        return x;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F * (countLegBlocks() / 6F);
    }

    /**
     * Used for the power texture used on the golem
     */
    public ResourceLocation getEffectTexture() {
        switch (getGolemState()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("golem_effect_red.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("golem_effect_yellow.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("golem_effect_orange.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("golem_effect_blue.png");
            default:
                return null;
        }
    }

    /**
     * Used for the particle FX
     */
    public float colorFX(int i) {
        switch (getGolemState()) {
            case 1:
                if (i == 1) return 65F / 255F;
                if (i == 2) return 157F / 255F;
                if (i == 3) return 254F / 255F;
            case 2:
                if (i == 1) return 244F / 255F;
                if (i == 2) return 248F / 255F;
                if (i == 3) return 36F / 255F;
            case 3:
                if (i == 1) return 1F;
                if (i == 2) return 154F / 255F;
                if (i == 3) return 21F / 255F;
            case 4:
                if (i == 1) return 248F / 255F;
                if (i == 2) return 10F / 255F;
                if (i == 3) return 10F / 255F;
            default:
                return 0;
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(MoCSoundEvents.ENTITY_GOLEM_WALK.get(), 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOLEM_AMBIENT.get();
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.BIG_GOLEM;
    }

    public static boolean getCanSpawnHere(EntityType<? extends MoCEntityMob> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        return (MoCEntityMob.getCanSpawnHere(type, world, reason, pos, randomIn) && world.canBlockSeeSky(new BlockPos(MathHelper.floor(pos.getX()), MathHelper.floor(pos.getY()), MathHelper.floor(pos.getZ()))) && (pos.getY() > 50D));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.935F;
    }

    static class AIGolemAttack extends MeleeAttackGoal {
        public AIGolemAttack(MoCEntityGolem golem) {
            super(golem, 1.0D, true);
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

    static class AIGolemTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public AIGolemTarget(MoCEntityGolem golem, Class<T> classTarget) {
            super(golem, classTarget, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.goalOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
