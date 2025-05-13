/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityOgre;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityLitterBox extends MobEntity {

    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.createKey(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> USED_LITTER = EntityDataManager.createKey(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);
    public int litterTime;

    public MoCEntityLitterBox(EntityType<? extends MoCEntityLitterBox> type, World world) {
        super(type, world);
        setNoAI(true);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("litter_box.png");
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PICKED_UP, Boolean.FALSE);
        this.dataManager.register(USED_LITTER, Boolean.FALSE);
    }

    public boolean getPickedUp() {
        return this.dataManager.get(PICKED_UP);
    }

    public void setPickedUp(boolean flag) {
        this.dataManager.set(PICKED_UP, flag);
    }

    public boolean getUsedLitter() {
        return this.dataManager.get(USED_LITTER);
    }

    public void setUsedLitter(boolean flag) {
        this.dataManager.set(USED_LITTER, flag);
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return !this.removed;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getMountedYOffset() {
        return 0.0D;
    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.SAND)) {
            MoCTools.playCustomSound(this, SoundEvents.BLOCK_SAND_PLACE);
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            setUsedLitter(false);
            this.litterTime = 0;
            return ActionResultType.SUCCESS;
        }
        if (this.getRidingEntity() == null) {
            if (player.isSneaking()) {
                player.inventory.addItemStackToInventory(new ItemStack(MoCItems.litterbox));
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_ITEM_PICKUP, 0.2F);
                remove();
            } else {
                setRotationYawHead((float) MoCTools.roundToNearest90Degrees(this.rotationYawHead) + 90.0F);
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_ITEM_FRAME_ROTATE_ITEM);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void move(MoverType type, Vector3d pos) {
        if (!this.world.isRemote && (getRidingEntity() != null || !this.onGround || !MoCreatures.proxy.staticLitter)) {
            super.move(type, pos);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (getUsedLitter()) {
            if (!this.world.isRemote) {
                this.litterTime++;
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(12D, 4D, 12D));
                for (Entity entity : list) {
                    if (!(entity instanceof MonsterEntity)) {
                        continue;
                    }
                    MonsterEntity entityMob = (MonsterEntity) entity;
                    entityMob.setAttackTarget(this);
                    if (entityMob instanceof CreeperEntity) {
                        ((CreeperEntity) entityMob).setCreeperState(-1);
                    }
                    if (entityMob instanceof MoCEntityOgre) {
                        ((MoCEntityOgre) entityMob).smashCounter = 0;
                    }
                }
            } else {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }
        if (this.litterTime > 5000 && !this.world.isRemote) {
            setUsedLitter(false);
            this.litterTime = 0;
        }
        if (this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound = MoCTools.getEntityData(this);
        compound.putBoolean("UsedLitter", getUsedLitter());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        compound = MoCTools.getEntityData(this);
        setUsedLitter(compound.getBoolean("UsedLitter"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
