/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityThrowableRock extends Entity {

    private static final DataParameter<Integer> ROCK_STATE = EntityDataManager.createKey(MoCEntityThrowableRock.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MASTERS_ID = EntityDataManager.createKey(MoCEntityThrowableRock.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BEHAVIOUR_TYPE = EntityDataManager.createKey(MoCEntityThrowableRock.class, DataSerializers.VARINT);
    public int acceleration = 100;
    private int rockTimer;
    private double oPosX;
    private double oPosY;
    private double oPosZ;

    public MoCEntityThrowableRock(EntityType<? extends MoCEntityThrowableRock> type, World par1World) {
        super(type, par1World);
        this.preventEntitySpawning = true;
        //this.yOffset = this.getHeight() / 2.0F; //TODO TheidenHD
    }

    public static  MoCEntityThrowableRock build(World par1World, Entity entitythrower, double par2, double par4, double par6) {
        MoCEntityThrowableRock rock = new MoCEntityThrowableRock(MoCEntities.TROCK, par1World);
        rock.setPosition(par2, par4, par6);
        rock.rockTimer = 250;
        rock.prevPosX = rock.oPosX = par2;
        rock.prevPosY = rock.oPosY = par4;
        rock.prevPosZ = rock.oPosZ = par6;
        rock.setMasterID(entitythrower.getEntityId());
        return rock;
    }

    public BlockState getState() {
        return Block.getStateById(this.dataManager.get(ROCK_STATE) & 65535);
    }

    public void setState(BlockState state) {
        this.dataManager.set(ROCK_STATE, (Block.getStateId(state) & 65535));
    }

    public int getMasterID() {
        return this.dataManager.get(MASTERS_ID);
    }

    public void setMasterID(int i) {
        this.dataManager.set(MASTERS_ID, i);
    }

    public int getBehavior() {
        return this.dataManager.get(BEHAVIOUR_TYPE);
    }

    public void setBehavior(int i) {
        this.dataManager.set(BEHAVIOUR_TYPE, i);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(BEHAVIOUR_TYPE, 0);
        this.dataManager.register(ROCK_STATE, 0);
        this.dataManager.register(MASTERS_ID, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        BlockState iblockstate = this.getState();
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putInt("Behavior", getBehavior());
        nbttagcompound.putInt("MasterID", getMasterID());
        nbttagcompound.putShort("BlockID", (short) (Block.getStateId(iblockstate) & 65535));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setBehavior(nbttagcompound.getInt("Behavior"));
        setMasterID(nbttagcompound.getInt("MasterID"));
        BlockState iblockstate;
        iblockstate = Block.getStateById(nbttagcompound.getShort("BlockID") & 65535);
        this.setState(iblockstate);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public void baseTick() {
        Entity master = getMaster();
        if (this.rockTimer-- <= -50 && getBehavior() == 0 || master == null) transformToItem();

        // held TRocks don't need to adjust its position
        if (getBehavior() == 1) return;

        // rock damage code (for all rock behaviors)
        if (!this.onGround) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().contract(this.getMotion().getX(), this.getMotion().getY(), this.getMotion().getZ()).expand(1.0D, 1.0D, 1.0D));
            for (Entity entity : list) {
                if (master != null && entity.getEntityId() == master.getEntityId()) continue;
                if (entity instanceof MoCEntityGolem) continue;
                if (entity != null && !(entity instanceof LivingEntity)) continue;
                if (master != null) entity.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) master), 4);
                else if (entity != null) entity.attackEntityFrom(DamageSource.GENERIC, 4);
            }
        }

        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();

        if (getBehavior() == 2) {
            if (master == null) {
                setBehavior(0);
                this.rockTimer = -50;
                return;
            }

            // moves towards the master entity the bigger the number, the slower
            --this.acceleration;
            if (this.acceleration < 10) {
                this.acceleration = 10;
            }

            float tX = (float) this.getPosX() - (float) master.getPosX();
            float tZ = (float) this.getPosZ() - (float) master.getPosZ();
            float distXZToMaster = tX * tX + tZ * tZ;

            if (distXZToMaster < 1.5F && master instanceof MoCEntityGolem) {
                ((MoCEntityGolem) master).receiveRock(this.getState());
                this.setBehavior(0);
                this.remove();
            }

            double summonedSpeed = this.acceleration;
            this.setMotion((master.getPosX() - this.getPosX()) / summonedSpeed, (master.getPosY() - this.getPosY()) / 20D + 0.15D, (master.getPosZ() - this.getPosZ()) / summonedSpeed);
            if (!this.world.isRemote)
                this.move(MoverType.SELF, this.getMotion());
            return;
        }

        // imploding / exploding rock
        if (getBehavior() == 4) {
            if (master == null) {
                if (!this.world.isRemote) setBehavior(5);
                return;
            }

            // moves towards the master entity
            // the bigger the number, the slower
            this.acceleration = 10;

            float tX = (float) this.getPosX() - (float) master.getPosX();
            float tZ = (float) this.getPosZ() - (float) master.getPosZ();
            float distXZToMaster = tX * tX + tZ * tZ;

            double summonedSpeed = this.acceleration;
            this.setMotion((master.getPosX() - this.getPosX()) / summonedSpeed, (master.getPosY() - this.getPosY()) / 20D + 0.15D, (master.getPosZ() - this.getPosZ()) / summonedSpeed);

            if (distXZToMaster < 2.5F && master instanceof MoCEntityGolem) {
                this.setMotion(0.0D, 0.0D, 0.0D);
            }

            if (!this.world.isRemote) {
                this.move(MoverType.SELF, this.getMotion());
            }

            return;
        }

        // exploding rock
        if (getBehavior() == 5) {
            this.acceleration = 5;
            double summonedSpeed = this.acceleration;
            this.setMotion((this.oPosX - this.getPosX()) / summonedSpeed, (this.oPosY - this.getPosY()) / 20D + 0.15D, (this.oPosZ - this.getPosZ()) / summonedSpeed);
            if (!this.world.isRemote)
                this.move(MoverType.SELF, this.getMotion());
            setBehavior(0);
            return;
        }

        this.setMotion(this.getMotion().subtract(0.0D, 0.04D, 0.0D));
        if (!this.world.isRemote)
            this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().mul(0.98D, 0.98D, 0.98D));

        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.699D, 0.699D, -0.5D));
        }
    }

    public void transformToItem() {
        // don't drop rocks if mob griefing is set to false, prevents duping
        if (!this.world.isRemote && MoCTools.mobGriefing(this.world) && MoCreatures.proxy.golemDestroyBlocks) {
            ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(Item.getItemFromBlock(this.getState().getBlock())));
            entityitem.setDefaultPickupDelay();
            entityitem.lifespan = 1200;
            this.world.addEntity(entityitem);
        }
        this.remove();
    }

    private Entity getMaster() {
        Entity entity = this.world.getEntityByID(getMasterID());
        if (entity != null) {
            return entity;
        }
        return null;
    }
}
