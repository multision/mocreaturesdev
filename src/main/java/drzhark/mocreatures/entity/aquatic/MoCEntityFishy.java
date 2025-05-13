/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityFishy extends MoCEntityTameableAquatic {

    public static final String[] fishNames = {"Blue", "Orange", "Light Blue", "Lime", "Green", "Purple", "Yellow", "Cyan", "Striped", "Red"};
    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.createKey(MoCEntityFishy.class, DataSerializers.BOOLEAN);
    public int gestationtime;

    public MoCEntityFishy(EntityType<? extends MoCEntityFishy> type, World world) {
        super(type, world);
        //setSize(0.5f, 0.3f);
        setAdult(true);
        //setAge(50 + this.rand.nextInt(50));
        setAge(100);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.3D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromEntityMoC(this, entity -> (entity.getHeight() > 0.3F || entity.getWidth() > 0.3F), 2.0F, 0.6D, 1.5D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAquatic.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 3.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D);
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
            case 2:
                return MoCreatures.proxy.getModelTexture("fishy_orange.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("fishy_light_blue.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("fishy_lime.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("fishy_green.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("fishy_purple.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("fishy_yellow.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("fishy_cyan.png");
            case 9:
                return MoCreatures.proxy.getModelTexture("fishy_striped.png");
            case 10:
                return MoCreatures.proxy.getModelTexture("fishy_red.png");
            default:
                return MoCreatures.proxy.getModelTexture("fishy_blue.png");
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_EATEN, Boolean.FALSE);
    }

    public boolean getHasEaten() {
        return this.dataManager.get(HAS_EATEN);
    }

    public void setHasEaten(boolean flag) {
        this.dataManager.set(HAS_EATEN, flag);
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        int i = this.rand.nextInt(100);
        if (i < 70) {
            //entityDropItem(new ItemStack(Items.FISH, 1, 0), 0.0F);
        } else {
            int j = this.rand.nextInt(2);
            for (int k = 0; k < j; k++) {
                //entityDropItem(new ItemStack(MoCItems.mocegg[getTypeMoC()], 1), 0.0F); //TODO TheidenHD
            }

        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FISHY;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.areEyesInFluid(FluidTags.WATER)) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }

        if (!this.world.isRemote) {

            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }

            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4D, 3D, 4D));
            for (Entity entity : list) {
                if (entity instanceof MoCEntityFishy) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4D, 2D, 4D));
            for (int k = 0; k < list.size(); k++) {
                Entity entity1 = list1.get(k);
                if (!(entity1 instanceof MoCEntityFishy) || (entity1 == this)) {
                    continue;
                }
                MoCEntityFishy entityfishy = (MoCEntityFishy) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entityfishy) || (this.getTypeMoC() != entityfishy.getTypeMoC())) {
                    continue;
                }
                if (this.rand.nextInt(100) == 0) {
                    this.gestationtime++;
                }
                if (this.gestationtime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageHeart(this.getEntityId()));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                int l = this.rand.nextInt(3) + 1;
                for (int i1 = 0; i1 < l; i1++) {
                    MoCEntityFishy entityfishy1 = MoCEntities.FISHY.create(this.world);
                    entityfishy1.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                    this.world.addEntity(entityfishy1);
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    setHasEaten(false);
                    entityfishy.setHasEaten(false);
                    this.gestationtime = 0;
                    entityfishy.gestationtime = 0;

                    PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                    if (entityplayer != null) {
                        MoCTools.tameWithName(entityplayer, entityfishy1);
                    }

                    entityfishy1.setAge(20);
                    entityfishy1.setAdult(false);
                    entityfishy1.setTypeInt(getTypeMoC());
                }

                break;
            }
        }

    }

    public boolean ReadyforParenting(MoCEntityFishy entityfishy) {
        return false; //TOOD pending overhaul of breeding
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public float rollRotationOffset() {
        if (!this.areEyesInFluid(FluidTags.WATER)) {
            return -90F;
        }
        return 0F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
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
    public float getSizeFactor() {
        return getAge() * 0.006F;
    }

    @Override
    public float getAdjustedXOffset() {
        if (!isInWater()) {
            return -0.1F;
        }
        return 0F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.areEyesInFluid(FluidTags.WATER)) {
            return 0.2F;
        }
        return -0.5F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.65F;
    }
}
