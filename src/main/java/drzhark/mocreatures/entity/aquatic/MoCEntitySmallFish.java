/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCEntitySmallFish extends MoCEntityTameableAquatic {

    public static final String[] fishNames = {"Anchovy", "Angelfish", "Angler", "Clownfish", "Goldfish", "Hippo Tang", "Mandarinfish"};

    public MoCEntitySmallFish(EntityType<? extends MoCEntitySmallFish> type, World world) {
        super(type, world);
        //setSize(0.5f, 0.3f);
        // TODO: Make hitboxes adjust depending on size
        //setAge(70 + this.rand.nextInt(30));
        setAge(100);
    }

    public static MoCEntitySmallFish createEntity(World world, int type) {
        if (type == 1) {
            return MoCEntities.ANCHOVY.create(world);
        }
        if (type == 2) {
            return MoCEntities.ANGELFISH.create(world);
        }
        if (type == 3) {
            return MoCEntities.ANGLER.create(world);
        }
        if (type == 4) {
            return MoCEntities.CLOWNFISH.create(world);
        }
        if (type == 5) {
            return MoCEntities.GOLDFISH.create(world);
        }
        if (type == 6) {
            return MoCEntities.HIPPOTANG.create(world);
        }
        if (type == 7) {
            return MoCEntities.MANDERIN.create(world);
        }
        return MoCEntities.CLOWNFISH.create(world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityAIPanicMoC(this, 1.3D));
        this.goalSelector.addGoal(2, new EntityAIFleeFromEntityMoC(this, entity -> (entity.getHeight() > 0.3F || entity.getWidth() > 0.3F), 2.0F, 0.6D, 1.5D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntitySmallFish.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(fishNames.length) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {

        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("smallfish_anchovy.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("smallfish_angelfish.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("smallfish_angler.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("smallfish_goldfish.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("smallfish_hippotang.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("smallfish_manderin.png");
            default:
                return MoCreatures.proxy.getModelTexture("smallfish_clownfish.png");
        }
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {

            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isInWater()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }
    }

    @Override
    public float getSizeFactor() {
        return getAge() * 0.01F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 0.5F;
        }
        return 0.3F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 90F + super.yawRotationOffset();
    }

    @Override
    public float rollRotationOffset() {
        if (!this.isInWater()) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.10F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double maxDivingDepth() {
        return 2.0D;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public float getAdjustedZOffset() {
        if (!isInWater()) {
            return 0.1F;
        }
        return 0F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.45F;
    }
}
