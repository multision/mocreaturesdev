/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class MoCEntityWerewolf extends MoCEntityMob {

    private static final DataParameter<Boolean> IS_HUMAN = EntityDataManager.createKey(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HUNCHED = EntityDataManager.createKey(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    private boolean transforming;
    private int tcounter;
    private int textCounter;
    private boolean isImmuneToFire;

    public MoCEntityWerewolf(EntityType<? extends MoCEntityWerewolf> type, World world) {
        super(type, world);
        // TODO: Change hitbox depending on form
        //setSize(0.7F, 2.0F);
        this.transforming = false;
        this.tcounter = 0;
        setHumanForm(true);
        experienceValue = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 40.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.5D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_HUMAN, Boolean.FALSE);
        this.dataManager.register(IS_HUNCHED, Boolean.FALSE);
    }

    @Override
    public void setHealth(float par1) {
        if (this.getIsHumanForm() && par1 > 15F) {
            par1 = 15F;
        }
        super.setHealth(par1);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            int k = this.rand.nextInt(100);
            if (k <= 28) {
                setTypeMoC(1);
            } else if (k <= 56) {
                setTypeMoC(2);
            } else if (k <= 85) {
                setTypeMoC(3);
            } else {
                setTypeMoC(4);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.getIsHumanForm()) {
            return MoCreatures.proxy.getModelTexture("wereblank.png");
        }

        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("werewolf_black.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("werewolf_white.png");
            case 4:
                if (!MoCreatures.proxy.getAnimateTextures()) {
                    return MoCreatures.proxy.getModelTexture("werewolf_fire1.png");
                }
                this.textCounter++;
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > 39) {
                    this.textCounter = 10;
                }
                String NTA = "werewolf_fire";
                String NTB = String.valueOf(this.textCounter);
                NTB = NTB.substring(0, 1);
                String NTC = ".png";

                return MoCreatures.proxy.getModelTexture(NTA + NTB + NTC);
            default:
                return MoCreatures.proxy.getModelTexture("werewolf_brown.png");
        }
    }

    public boolean getIsHumanForm() {
        return this.dataManager.get(IS_HUMAN);
    }

    public void setHumanForm(boolean flag) {
        this.dataManager.set(IS_HUMAN, flag);
    }

    public boolean getIsHunched() {
        return this.dataManager.get(IS_HUNCHED);
    }

    public void setHunched(boolean flag) {
        this.dataManager.set(IS_HUNCHED, flag);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (getIsHumanForm()) {
            setAttackTarget(null);
            return false;
        }
        if (this.getTypeMoC() == 4 && entityIn instanceof LivingEntity) {
            entityIn.setFire(10);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if (!getIsHumanForm() && (entity instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) entity;
            ItemStack stack = entityplayer.getHeldItemMainhand();
            if (!stack.isEmpty()) {
                if (stack.getItem() == MoCItems.silversword) {
                    i = 10F;
                } else if (stack.getItem() instanceof SwordItem) {
                    String swordMaterial = ((SwordItem) stack.getItem()).getTier().toString();
                    String swordName = stack.getItem().getTranslationKey();
                    if (swordMaterial.toLowerCase().contains("silver") || swordName.toLowerCase().contains("silver")) {
                        i = ((SwordItem) stack.getItem()).getAttackDamage() * 3F;
                    } else {
                        i = ((SwordItem) stack.getItem()).getAttackDamage() * 0.5F;
                    }
                } else if (stack.getItem() instanceof ToolItem) {
                    String swordMaterial = ((ToolItem) stack.getItem()).getTier().toString();
                    String swordName = stack.getItem().getTranslationKey();
                    if (swordMaterial.toLowerCase().contains("silver") || swordName.toLowerCase().contains("silver")) {
                        i = ((ToolItem) stack.getItem()).getAttackDamage() * 3F;
                    } else {
                        i = ((ToolItem) stack.getItem()).getAttackDamage() * 0.5F;
                    }
                } else if (stack.getItem().getTranslationKey().toLowerCase().contains("silver")) {
                    i = 6F;
                } else {
                    i = Math.min(i * 0.5F, 4F);
                }
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsHumanForm() && super.shouldAttackPlayers();
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (getIsHumanForm()) {
            return MoCreatures.proxy.legacyWerehumanSounds ? MoCSoundEvents.ENTITY_WEREWOLF_DEATH_HUMAN.get() : SoundEvents.ENTITY_GENERIC_HURT;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_DEATH.get();
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (getIsHumanForm()) {
            if (!this.transforming)
                return MoCreatures.proxy.legacyWerehumanSounds ? MoCSoundEvents.ENTITY_WEREWOLF_HURT_HUMAN.get() : SoundEvents.ENTITY_GENERIC_HURT;
            return null;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_HURT.get();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsHumanForm()) {
            return null;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_AMBIENT.get();
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {        if (getIsHumanForm()) {
            return MoCLootTables.WEREHUMAN;
        }

        return MoCLootTables.WEREWOLF;
    }

    public boolean IsNight() {
        return !this.world.isDaytime();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (((IsNight() && getIsHumanForm()) || (!IsNight() && !getIsHumanForm())) && (this.rand.nextInt(250) == 0)) {
                this.transforming = true;
            }
            if (getIsHumanForm() && (this.getAttackTarget() != null)) {
                setAttackTarget(null);
            }
            if (this.getAttackTarget() != null && !getIsHumanForm()) {
                boolean hunch = (this.getDistanceSq(this.getAttackTarget()) > 12D);
                setHunched(hunch);
            }

            if (this.transforming && (this.rand.nextInt(3) == 0)) {
                this.tcounter++;
                if ((this.tcounter % 2) == 0) {
                    this.setPosition(this.getPosX() + 0.3D, this.getPosY() + (double) this.tcounter / 30, this.getPosZ());
                    attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                }
                if ((this.tcounter % 2) != 0) {
                    this.setPosition(this.getPosX() - 0.3D, this.getPosY(), this.getPosZ());
                }
                if (this.tcounter == 10) {
                    MoCTools.playCustomSound(this, MoCreatures.proxy.legacyWerehumanSounds ? MoCSoundEvents.ENTITY_WEREWOLF_TRANSFORM_HUMAN.get() : MoCSoundEvents.ENTITY_WEREWOLF_TRANSFORM.get());
                }
                if (this.tcounter > 30) {
                    Transform();
                    this.tcounter = 0;
                    this.transforming = false;
                }
            }
            //so entity doesn't despawn that often
            if (this.rand.nextInt(300) == 0) {
                this.idleTime -= 100 * this.world.getDifficulty().getId();
                if (this.idleTime < 0) {
                    this.idleTime = 0;
                }
            }
        }
    }

    public static boolean getCanSpawnHere(EntityType<? extends MoCEntityMob> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        return MoCEntityMob.getCanSpawnHere(type, world, reason, pos, randomIn) && world.canSeeSky(new BlockPos(pos));
    }

    // PATCHED Transform method:
    private void Transform() {
        if (this.deathTime > 0) return;

        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY) + 1;
        int k = MathHelper.floor(this.getPosZ());
        float f = 0.1F;
        for (int l = 0; l < 30; l++) {
            double d = i + this.world.rand.nextFloat();
            double d1 = j + this.world.rand.nextFloat();
            double d2 = k + this.world.rand.nextFloat();
            double d3 = d - i;
            double d4 = d1 - j;
            double d5 = d2 - k;
            double d6 = MathHelper.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
            d3 /= d6;
            d4 /= d6;
            d5 /= d6;
            double d7 = 0.5D / ((d6 / f) + 0.1D);
            d7 *= (this.world.rand.nextFloat() * this.world.rand.nextFloat()) + 0.3F;
            d3 *= d7;
            d4 *= d7;
            d5 *= d7;
            this.world.addParticle(ParticleTypes.POOF, (d + (i * 1.0D)) / 2D, (d1 + (j * 1.0D)) / 2D, (d2 + (k * 1.0D)) / 2D, d3, d4, d5);
        }

        if (getIsHumanForm()) {
            setHumanForm(false);
            this.setHealth(40);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        } else {
            setHumanForm(true);
            this.setHealth(15);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        }

        this.recalculateSize(); // ✅ forces size/hitbox update
        this.stepHeight = getIsHumanForm() ? 0.6F : 1.0F; // ✅ smooth terrain stepping
        this.transforming = false;
    }
    /*private void Transform() {
        if (this.deathTime > 0) {
            return;
        }
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY) + 1;
        int k = MathHelper.floor(this.getPosZ());
        float f = 0.1F;
        for (int l = 0; l < 30; l++) {
            double d = i + this.world.rand.nextFloat();
            double d1 = j + this.world.rand.nextFloat();
            double d2 = k + this.world.rand.nextFloat();
            double d3 = d - i;
            double d4 = d1 - j;
            double d5 = d2 - k;
            double d6 = MathHelper.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
            d3 /= d6;
            d4 /= d6;
            d5 /= d6;
            double d7 = 0.5D / ((d6 / f) + 0.1D);
            d7 *= (this.world.rand.nextFloat() * this.world.rand.nextFloat()) + 0.3F;
            d3 *= d7;
            d4 *= d7;
            d5 *= d7;
            this.world.addParticle(ParticleTypes.POOF, (d + (i * 1.0D)) / 2D, (d1 + (j * 1.0D)) / 2D, (d2 + (k * 1.0D)) / 2D, d3, d4, d5);
        }

        if (getIsHumanForm()) {
            setHumanForm(false);
            this.setHealth(40);
            //setSize(0.6F, 2.125F);//TODO TheidenHD
            this.transforming = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        } else {
            setHumanForm(true);
            this.setHealth(15);
            //setSize(0.6F, 2.125F);//TODO TheidenHD
            this.transforming = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        }
    }*/

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setHumanForm(nbttagcompound.getBoolean("HumanForm"));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("HumanForm", getIsHumanForm());
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsHumanForm()) {
            return 0.1F;
        }
        if (getIsHunched()) {
            return 0.35F;
        }
        return 0.2F;
    }


    /*
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return getIsHumanForm() ? this.getHeight() * 0.885F : this.getHeight();
    }*/
    @Override
    public EntitySize getSize(Pose poseIn) {
        if (getIsHumanForm()) {
            return EntitySize.fixed(0.6F, 1.8F); // player size
        } else {
            return EntitySize.fixed(1.2F, 2.4F); // beast form
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return getIsHumanForm() ? 1.62F : 2.0F; // match model scale
    }

    @Override
    public void setTypeMoC(int i) {
        this.isImmuneToFire = i == 4;
        super.setTypeMoC(i);
    }

    @Override
    public boolean isImmuneToFire() {
        return this.isImmuneToFire ? true : super.isImmuneToFire();
    }
}
