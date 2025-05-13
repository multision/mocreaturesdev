/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityPolarBear extends MoCEntityBear {

    public MoCEntityPolarBear(EntityType<? extends MoCEntityPolarBear> type, World world) {
        super(type, world);
        //setSize(1.5F, 1.834F);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityBear.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 45.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.5D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("bear_polar.png");
    }

    @Override
    public float getBearSize() {
        return 1.4F;
    }

    @Override
    public int getMaxAge() {
        return 140;
    }

    public double getAttackRange() {
        int factor = 1;
        if (this.world.getDifficulty().getId() > 1) {
            factor = 2;
        }
        return 8D * factor;
    }

    /*public int getAttackStrength() {
        int factor = (this.world.getDifficulty().getId());
        return 4 * factor;
    }*/

    @Override
    public boolean shouldAttackPlayers() {
        return super.shouldAttackPlayers();
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && this.getAge() < 80 && MoCTools.isItemEdibleforCarnivores(stack.getItem())) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);

            if (!getIsTamed() && !this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (!this.world.isRemote && !getIsAdult() && (getAge() < 100)) {
                setAge(getAge() + 1);
            }

            return ActionResultType.SUCCESS;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.whip)) {
            if (getBearState() == 0) {
                setBearState(2);
            } else {
                setBearState(0);
            }
            return ActionResultType.SUCCESS;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setBearState(0);
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.POLAR_BEAR;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "WildPolarBear";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityPolarBear;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.76F;
    }
}
