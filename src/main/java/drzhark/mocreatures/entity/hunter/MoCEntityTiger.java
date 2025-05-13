/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityTiger extends MoCEntityBigCat {

    public MoCEntityTiger(EntityType<? extends MoCEntityTiger> type, World world) {
        super(type, world);
        //setSize(1.25F, 1.275F);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityBigCat.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            if (this.rand.nextInt(20) == 0) {
                setTypeMoC(2);
            } else {
                setTypeMoC(1);
            }
        }
        super.selectType();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
    }

    @Override
    public ResourceLocation getTexture() {
        if (MoCreatures.proxy.legacyBigCatModels) {
            switch (getTypeMoC()) {
                case 2:
                case 3:
                    return MoCreatures.proxy.getModelTexture("big_cat_white_tiger_legacy.png");
                default:
                    return MoCreatures.proxy.getModelTexture("big_cat_tiger_legacy.png");
            }
        }
        switch (getTypeMoC()) {
            case 2: // White Tiger
            case 3: // Winged White Tiger
                return MoCreatures.proxy.getModelTexture("big_cat_white_tiger.png");
            default: // Orange Tiger
                return MoCreatures.proxy.getModelTexture("big_cat_tiger.png");
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getTypeMoC() == 3;
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getTypeMoC() == 2 && (stack.getItem() == MoCItems.essencelight)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setTypeMoC(3);
            return ActionResultType.SUCCESS;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.TIGER;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && mate.getTypeMoC() == 2) {
            return "Liger";
        }
        if (mate instanceof MoCEntityPanther && mate.getTypeMoC() == 1) {
            return "Panthger";
        }
        if (mate instanceof MoCEntityLeopard && mate.getTypeMoC() == 1) {
            return "Leoger";
        }
        return "Tiger";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && mate.getTypeMoC() == 2) {
            return 1; // Liger
        }
        if (mate instanceof MoCEntityLeopard && mate.getTypeMoC() == 1) {
            return 1; // Leoger
        }
        if (mate instanceof MoCEntityPanther && mate.getTypeMoC() == 1) {
            return 1; // Panthger
        }
        return this.getTypeMoC();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getTypeMoC() < 3)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getTypeMoC() == 2)
                || (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getTypeMoC() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getTypeMoC() == 1);
    }

    @Override
    public boolean readytoBreed() {
        return this.getTypeMoC() < 3 && super.readytoBreed();
    }

    public double calculateMaxHealth() {
        // White Tiger
        if (this.getTypeMoC() == 2 || this.getTypeMoC() == 3) {
            return 40.0D;
        }
        // Orange Tiger
        else {
            return 35.0D;
        }
    }

    public double calculateAttackDmg() {
        // White Tiger
        if (this.getTypeMoC() == 2 || this.getTypeMoC() == 3) {
            return 7.5D;
        }
        // Orange Tiger
        return 7.0D;
    }

    @Override
    public int getMaxAge() {
        // White Tiger
        if (this.getTypeMoC() == 2 || this.getTypeMoC() == 3) {
            return 130;
        }
        // Orange Tiger
        return 120;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        if (!this.getIsAdult() && (this.getAge() < this.getMaxAge() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityTiger) {
            return false;
        }
        return entity.getHeight() < 2F && entity.getWidth() < 2F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.92F;
    }
}
