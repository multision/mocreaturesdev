/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MoCEntityKittyBed extends MobEntity {

    private static final DataParameter<Boolean> HAS_MILK = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_FOOD = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SHEET_COLOR = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.VARINT);
    public float milkLevel;

    public MoCEntityKittyBed(EntityType<? extends MoCEntityKittyBed> type, World world) {
        super(type, world);
        setNoAI(true);
        this.milkLevel = 0.0F;
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("kitty_bed.png");
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_MILK, Boolean.FALSE);
        this.dataManager.register(HAS_FOOD, Boolean.FALSE);
        this.dataManager.register(PICKED_UP, Boolean.FALSE);
        this.dataManager.register(SHEET_COLOR, 0);
    }

    public boolean getHasFood() {
        return this.dataManager.get(HAS_FOOD);
    }

    public void setHasFood(boolean flag) {
        this.dataManager.set(HAS_FOOD, flag);
    }

    public boolean getHasMilk() {
        return this.dataManager.get(HAS_MILK);
    }

    public void setHasMilk(boolean flag) {
        this.dataManager.set(HAS_MILK, flag);
    }

    public boolean getPickedUp() {
        return this.dataManager.get(PICKED_UP);
    }

    public void setPickedUp(boolean flag) {
        this.dataManager.set(PICKED_UP, flag);
    }

    public int getSheetColor() {
        return this.dataManager.get(SHEET_COLOR);
    }

    public void setSheetColor(int i) {
        this.dataManager.set(SHEET_COLOR, i);
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
    public boolean canEntityBeSeen(Entity entity) {
        return this.world.rayTraceBlocks(new RayTraceContext(new Vector3d(this.getPosX(), this.getPosY() + getEyeHeight(), this.getPosZ()), new Vector3d(entity.getPosX(), entity.getPosY() + entity.getEyeHeight(), entity.getPosZ()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
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
        if (!stack.isEmpty() && !getHasFood() && !getHasMilk()) {
            if (stack.getItem() == MoCItems.petfood) {
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTYBED_POURINGFOOD.get());
                setHasMilk(false);
                setHasFood(true);
            } else if (stack.getItem() == Items.MILK_BUCKET) {
                player.setHeldItem(hand, new ItemStack(Items.BUCKET, 1));
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTYBED_POURINGMILK.get());
                setHasMilk(true);
                setHasFood(false);
            }
            return ActionResultType.SUCCESS;
        }
        if (this.getRidingEntity() == null) {
            if (player.isSneaking()) {
                final int color = getSheetColor();
                player.inventory.addItemStackToInventory(new ItemStack(MoCItems.kittybed[color], 1));
                if (getHasFood()) player.inventory.addItemStackToInventory(new ItemStack(MoCItems.petfood, 1));
                else if (getHasMilk()) player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET, 1));
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
        if (!this.world.isRemote && (this.getRidingEntity() != null || !this.onGround || !MoCreatures.proxy.staticBed)) {
            super.move(type, pos);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (!this.world.isRemote && (getHasMilk() || getHasFood()) && this.isBeingRidden() && getPassengers().get(0) instanceof MoCEntityKitty) {
            MoCEntityKitty kitty = (MoCEntityKitty) getPassengers().get(0);
            if (kitty.getKittyState() != 12) {
                this.milkLevel += 0.003F;
                if (this.milkLevel > 2.0F) {
                    this.milkLevel = 0.0F;
                    setHasMilk(false);
                    setHasFood(false);
                }
            }
        }
        if (this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        setHasMilk(compound.getBoolean("HasMilk"));
        setSheetColor(compound.getInt("SheetColour"));
        setHasFood(compound.getBoolean("HasFood"));
        this.milkLevel = compound.getFloat("MilkLevel");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("HasMilk", getHasMilk());
        compound.putInt("SheetColour", getSheetColor());
        compound.putBoolean("HasFood", getHasFood());
        compound.putFloat("MilkLevel", this.milkLevel);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
