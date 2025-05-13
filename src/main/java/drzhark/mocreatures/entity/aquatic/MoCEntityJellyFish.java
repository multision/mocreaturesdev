/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityJellyFish extends MoCEntityTameableAquatic {

    private static final DataParameter<Boolean> GLOWS = EntityDataManager.createKey(MoCEntityJellyFish.class, DataSerializers.BOOLEAN);
    private int poisoncounter;

    public MoCEntityJellyFish(EntityType<? extends MoCEntityJellyFish> type, World world) {
        super(type, world);
        //setSize(0.45F, 0.575F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(50 + (this.rand.nextInt(50)));
        setAge(100);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 0.5D, 120));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAquatic.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 6.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(5) + 1);
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GLOWS, Boolean.FALSE);
    }

    public boolean isGlowing() {
        return (this.dataManager.get(GLOWS));
    }

    public void setGlowing(boolean flag) {
        this.dataManager.set(GLOWS, flag);
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.02F;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("jellyfish_purple_gray.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("jellyfish_blue_dark.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("jellyfish_green.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("jellyfish_orange_red.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("jellyfish_orange_yellow.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("jellyfish_blue_speckled.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("jellyfish_white.png");
            case 9:
                return MoCreatures.proxy.getModelTexture("jellyfish_purple.png");
            case 10:
                return MoCreatures.proxy.getModelTexture("jellyfish_orange_light.png");
            case 11:
                return MoCreatures.proxy.getModelTexture("jellyfish_red.png");
            case 12:
                return MoCreatures.proxy.getModelTexture("jellyfish_blue_light.png");
            default:
                return MoCreatures.proxy.getModelTexture("jellyfish_orange_dark.png");
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {

            if (this.rand.nextInt(200) == 0) {
                setGlowing(!this.world.isDaytime());
            }

            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.shouldAttackPlayers()) && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    this.poisoncounter = 0;
                }
            }
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_ATTACK;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_ATTACK;
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.JELLYFISH;
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return (int) (getAge() * -1 / 2.3);
    }

    @Override
    public float getSizeFactor() {
        float myMoveSpeed = MoCTools.getMyMovementSpeed(this);
        float pulseSpeed = 0.08F;
        if (myMoveSpeed > 0F)
            pulseSpeed = 0.5F;
        float pulseSize = MathHelper.cos(this.ticksExisted * pulseSpeed) * 0.2F;
        return getAge() * 0.01F + (pulseSize / 5);
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.85F;
    }
}
