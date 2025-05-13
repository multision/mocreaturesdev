/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityGoat extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> IS_CHARGING = EntityDataManager.createKey(MoCEntityGoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_UPSET = EntityDataManager.createKey(MoCEntityGoat.class, DataSerializers.BOOLEAN);
    public int movecount;
    private boolean hungry;
    private boolean swingLeg;
    private boolean swingEar;
    private boolean swingTail;
    private boolean bleat;
    private boolean eating;
    private int bleatcount;
    private int attacking;
    private int chargecount;
    private int tailcount; // 90 to -45
    private int earcount; // 20 to 40 default = 30
    private int eatcount;

    public MoCEntityGoat(EntityType<? extends MoCEntityGoat> type, World world) {
        super(type, world);
        // TODO: Separate hitbox for female goats
        //setSize(0.8F, 0.9F);
        setAdult(true);
        setAge(70);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.ARMOR, 1.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.5D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_CHARGING, Boolean.FALSE);
        this.dataManager.register(IS_UPSET, Boolean.FALSE);
    }

    public boolean getUpset() {
        return this.dataManager.get(IS_UPSET);
    }

    public void setUpset(boolean flag) {
        this.dataManager.set(IS_UPSET, flag);
    }

    public boolean getCharging() {
        return this.dataManager.get(IS_CHARGING);
    }

    public void setCharging(boolean flag) {
        this.dataManager.set(IS_CHARGING, flag);
    }

    @Override
    public void selectType() {
        /*
         * type 1 = baby type 2 = female type 3 = female 2 type 4 = female 3
         * type 5 = male 1 type 6 = male 2 type 7 = male 3
         */
        if (getTypeMoC() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 15) {
                setTypeMoC(1);
                setAge(50);
            } else if (i <= 30) {
                setTypeMoC(2);
                setAge(70);
            } else if (i <= 45) {
                setTypeMoC(3);
                setAge(70);
            } else if (i <= 60) {
                setTypeMoC(4);
                setAge(70);
            } else if (i <= 75) {
                setTypeMoC(5);
                setAge(90);
            } else if (i <= 90) {
                setTypeMoC(6);
                setAge(90);
            } else {
                setTypeMoC(7);
                setAge(90);
            }
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("goat_brown_light.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("goat_brown_spotted.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("goat_gray_spotted.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("goat_gray.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("goat_brown.png");
            default:
                return MoCreatures.proxy.getModelTexture("goat_white.png");
        }
    }

    public void calm() {
        setAttackTarget(null);
        setUpset(false);
        setCharging(false);
        this.attacking = 0;
        this.chargecount = 0;
    }

    @Override
    protected void jump() {
        if (getTypeMoC() == 1) {
            this.setMotion(this.getMotion().getX(), 0.41D, this.getMotion().getZ());
        } else if (getTypeMoC() < 5) {
            this.setMotion(this.getMotion().getX(), 0.45D, this.getMotion().getZ());
        } else {
            this.setMotion(this.getMotion().getX(), 0.5D, this.getMotion().getZ());
        }

        if (isPotionActive(Effects.JUMP_BOOST)) {
            this.setMotion(this.getMotion().add(0.0D, (getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.1F, 0.0D));
        }
        if (isSprinting()) {
            float f = this.rotationYaw * 0.01745329F;
            this.setMotion(this.getMotion().add(MathHelper.sin(f) * -0.2F, 0.0D, MathHelper.sin(f) * 0.2F));
        }
        this.isAirBorne = true;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            if (this.rand.nextInt(100) == 0) {
                setSwingEar(true);
            }

            if (this.rand.nextInt(80) == 0) {
                setSwingTail(true);
            }

            if (this.rand.nextInt(50) == 0) {
                setEating(true);
            }
        }
        if (getBleating()) {
            this.bleatcount++;
            if (this.bleatcount > 15) {
                this.bleatcount = 0;
                setBleating(false);
            }

        }

        if ((this.hungry) && (this.rand.nextInt(20) == 0)) {
            this.hungry = false;
        }

        if (!this.world.isRemote && (getAge() < 90 || getTypeMoC() > 4 && getAge() < 100) && this.rand.nextInt(500) == 0) {
            setAge(getAge() + 1);
            if (getTypeMoC() == 1 && getAge() > 70) {
                int i = this.rand.nextInt(6) + 2;
                setTypeMoC(i);

            }
        }

        if (getUpset()) {
            this.attacking += (this.rand.nextInt(4)) + 2;
            if (this.attacking > 75) {
                this.attacking = 75;
            }

            if (this.rand.nextInt(200) == 0 || getAttackTarget() == null) {
                calm();
            }

            if (!getCharging() && this.rand.nextInt(35) == 0) {
                swingLeg();
            }

            if (!getCharging()) {
                this.getNavigator().clearPath();
            }

            if (getAttackTarget() != null)// && rand.nextInt(100)==0)
            {
                faceEntity(getAttackTarget(), 10F, 10F);
                if (this.rand.nextInt(80) == 0) {
                    setCharging(true);
                }
            }
        }

        if (getCharging()) {
            this.chargecount++;
            if (this.chargecount > 120) {
                this.chargecount = 0;
            }
            if (getAttackTarget() == null) {
                calm();
            }
        }

        if (!getUpset() && !getCharging()) {
            PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 24D);
            if (entityplayer1 != null) {// Behaviour that happens only close to player :)

                // is there food around? only check with player near
                ItemEntity entityitem = getClosestEntityItem(this, 10D);
                if (entityitem != null) {
                    float f = entityitem.getDistance(this);
                    if (f > 2.0F) {
                        int i = MathHelper.floor(entityitem.getPosX());
                        int j = MathHelper.floor(entityitem.getPosY());
                        int k = MathHelper.floor(entityitem.getPosZ());
                        faceLocation(i, j, k, 30F);

                        setPathToEntity(entityitem, f);
                        return;
                    }
                    if (f < 2.0F && this.deathTime == 0 && this.rand.nextInt(50) == 0) {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EATING.get());
                        setEating(true);

                        entityitem.remove();
                        return;
                    }
                }

                // find other goat to play!
                if (getTypeMoC() > 4 && this.rand.nextInt(200) == 0) {
                    MoCEntityGoat entitytarget = (MoCEntityGoat) getClosestEntityLiving(this, 14D);
                    if (entitytarget != null) {
                        setUpset(true);
                        setAttackTarget(entitytarget);
                        entitytarget.setUpset(true);
                        entitytarget.setAttackTarget(this);
                    }
                }

            }// end of close to player behavior
        }// end of !upset !charging
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem());
    }

    @Override
    public int getTalkInterval() {
        if (this.hungry) {
            return 80;
        }

        return 200;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof MoCEntityGoat)) || ((((MoCEntityGoat) entity).getTypeMoC() < 5)));
    }

    @Override
    public boolean isMovementCeased() {
        return getUpset() && !getCharging();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attacking = 30;
        if (entityIn instanceof MoCEntityGoat) {
            MoCTools.bigSmack(this, entityIn, 0.4F);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_SMACK.get());
            if (this.rand.nextInt(3) == 0) {
                calm();
                ((MoCEntityGoat) entityIn).calm();
            }
            return false;
        }
        MoCTools.bigSmack(this, entityIn, 0.8F);
        if (this.rand.nextInt(3) == 0) {
            calm();
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean isNotScared() {
        return getTypeMoC() > 4;
    }

    private void swingLeg() {
        if (!getSwingLeg()) {
            setSwingLeg(true);
            this.movecount = 0;
        }
    }

    public boolean getSwingLeg() {
        return this.swingLeg;
    }

    public void setSwingLeg(boolean flag) {
        this.swingLeg = flag;
    }

    public boolean getSwingEar() {
        return this.swingEar;
    }

    public void setSwingEar(boolean flag) {
        this.swingEar = flag;
    }

    public boolean getSwingTail() {
        return this.swingTail;
    }

    public void setSwingTail(boolean flag) {
        this.swingTail = flag;
    }

    public boolean getEating() {
        return this.eating;
    }

    public void setEating(boolean flag) {
        this.eating = flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (entity != this && entity instanceof LivingEntity && super.shouldAttackPlayers() && getTypeMoC() > 4) {
                setAttackTarget((LivingEntity) entity);
                setUpset(true);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {

        if (getSwingLeg()) {
            this.movecount += 5;
            if (this.movecount == 30) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_DIGG.get());
            }

            if (this.movecount > 100) {
                setSwingLeg(false);
                this.movecount = 0;
            }
        }

        if (getSwingEar()) {
            this.earcount += 5;
            if (this.earcount > 40) {
                setSwingEar(false);
                this.earcount = 0;
            }
        }

        if (getSwingTail()) {
            this.tailcount += 15;
            if (this.tailcount > 135) {
                setSwingTail(false);
                this.tailcount = 0;
            }
        }

        if (getEating()) {
            this.eatcount += 1;
            if (this.eatcount == 2) {
                PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 3D);
                if (entityplayer1 != null) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EATING.get());
                }
            }
            if (this.eatcount > 25) {
                setEating(false);
                this.eatcount = 0;
            }
        }

        super.tick();
    }

    public int legMovement() {
        if (!getSwingLeg()) {
            return 0;
        }

        if (this.movecount < 21) {
            return this.movecount * -1;
        }
        if (this.movecount < 70) {
            return this.movecount - 40;
        }
        return -this.movecount + 100;
    }

    public int earMovement() {
        // 20 to 40 default = 30
        if (!getSwingEar()) {
            return 0;
        }
        if (this.earcount < 11) {
            return this.earcount + 30;
        }
        if (this.earcount < 31) {
            return -this.earcount + 50;
        }
        return this.earcount - 10;
    }

    public int tailMovement() {
        // 90 to -45
        if (!getSwingTail()) {
            return 90;
        }

        return this.tailcount - 45;
    }

    public int mouthMovement() {
        if (!getEating()) {
            return 0;
        }
        if (this.eatcount < 6) {
            return this.eatcount;
        }
        if (this.eatcount < 16) {
            return -this.eatcount + 10;
        }
        return this.eatcount - 20;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem() == Items.BUCKET) {
            if (getTypeMoC() > 4) {
                setUpset(true);
                setAttackTarget(player);
                return ActionResultType.FAIL;
            }
            if (getTypeMoC() == 1) {
                return ActionResultType.FAIL;
            }

            if (!player.abilities.isCreativeMode) stack.shrink(1);
            player.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET));
            return ActionResultType.SUCCESS;
        }

        if (getIsTamed() && !stack.isEmpty() && (MoCTools.isItemEdible(stack.getItem()))) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            this.setHealth(getMaxHealth());
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EATING.get());
            return ActionResultType.SUCCESS;
        }

        if (!getIsTamed() && !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem())) {
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);

    }

    public boolean getBleating() {
        return this.bleat && (getAttacking() == 0);
    }

    public void setBleating(boolean flag) {
        this.bleat = flag;
    }

    public int getAttacking() {
        return this.attacking;
    }

    public void setAttacking(int flag) {
        this.attacking = flag;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOAT_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        setBleating(true);
        if (getTypeMoC() == 1) {
            return MoCSoundEvents.ENTITY_GOAT_AMBIENT_BABY.get();
        }
        if (getTypeMoC() > 2 && getTypeMoC() < 5) {
            return MoCSoundEvents.ENTITY_GOAT_AMBIENT_FEMALE.get();
        }

        return MoCSoundEvents.ENTITY_GOAT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GOAT_DEATH.get();
    }

    // TODO: Add unique step sound
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.GOAT;
    }

    @Override
    public int getMaxAge() {
        return 50; //so the update is not handled on MoCEntityAnimal
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.945F;
    }
}
