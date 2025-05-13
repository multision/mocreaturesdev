/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntitySnail extends MoCEntityAmbient {

    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.createKey(MoCEntitySnail.class, DataSerializers.BOOLEAN);

    public MoCEntitySnail(EntityType<? extends MoCEntitySnail> type, World world) {
        super(type, world);
        //setSize(0.4F, 0.3F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityAIWanderMoC2(this, 0.8D));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_HIDING, Boolean.FALSE);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAmbient.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.ARMOR, 2.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.10D);
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsHiding());
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(6) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("snail_green.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("snail_yellow.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("snail_red.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("slug_golden.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("slug_black.png");
            default:
                return MoCreatures.proxy.getModelTexture("snail_brown.png");
        }
    }

    public boolean getIsHiding() {
        return this.dataManager.get(IS_HIDING);
    }

    public void setIsHiding(boolean flag) {
        this.dataManager.set(IS_HIDING, flag);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            LivingEntity entityliving = getBoogey(3D);
            if ((entityliving != null) && entityliving.getHeight() > 0.5F && entityliving.getWidth() > 0.5F && canEntityBeSeen(entityliving)) {
                if (!getIsHiding()) {
                    setIsHiding(true);
                }
                this.getNavigator().clearPath();
            } else {
                setIsHiding(false);
            }
            // Slugs won't hide
            if (getIsHiding() && this.getTypeMoC() > 4) {
                setIsHiding(false);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (getIsHiding()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SILVERFISH_STEP;
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.SNAIL;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }
}
