/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.inventory.MoCAnimalChest;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import drzhark.mocreatures.network.message.MoCMessageVanish;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class MoCEntityHorse extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.createKey(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.createKey(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BRED = EntityDataManager.createKey(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMOR_TYPE = EntityDataManager.createKey(MoCEntityHorse.class, DataSerializers.VARINT);
    public int shuffleCounter;
    public int wingFlapCounter;
    public MoCAnimalChest localChest;
    public boolean eatenPumpkin;
    public ItemStack localStack;
    public int mouthCounter;
    public int standCounter;
    public int tailCounter;
    public int vanishCounter;
    public int sprintCounter;
    public int transformType;
    public int transformCounter;
    protected EntityAIWanderMoC2 wander;
    private int gestationTime;
    private int countEating;
    private int textCounter;
    private int fCounter;
    private float transFloat = 0.2F;
    private boolean hasReproduced;
    private int nightmareInt;
    private boolean isImmuneToFire;

    public MoCEntityHorse(EntityType<? extends MoCEntityHorse> type, World world) {
        super(type, world);
        //setSize(1.3964844F, 1.6F);
        this.gestationTime = 0;
        this.eatenPumpkin = false;
        this.nightmareInt = 0;
        this.isImmuneToFire = false;
        setAge(50);
        setIsChested(false);
        this.stepHeight = 1.0F;

        if (!this.world.isRemote) {
            setAdult(this.rand.nextInt(5) != 0);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 30.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RIDEABLE, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.dataManager.register(SITTING, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.dataManager.register(CHESTED, Boolean.FALSE);
        this.dataManager.register(BRED, Boolean.FALSE);
        this.dataManager.register(ARMOR_TYPE, 0);
    }

    @Override
    public int getArmorType() {
        return this.dataManager.get(ARMOR_TYPE);
    }

    @Override
    public void setArmorType(int i) {
        this.dataManager.set(ARMOR_TYPE, i);
    }

    public boolean getIsChested() {
        return this.dataManager.get(CHESTED);
    }

    public void setIsChested(boolean flag) {
        this.dataManager.set(CHESTED, flag);
    }

    @Override
    public boolean getIsSitting() {
        return this.dataManager.get(SITTING);
    }

    public boolean getHasBred() {
        return this.dataManager.get(BRED);
    }

    public void setBred(boolean flag) {
        this.dataManager.set(BRED, flag);
    }

    @Override
    public boolean getIsRideable() {
        return this.dataManager.get(RIDEABLE);
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, flag);
    }

    public void setSitting(boolean flag) {
        this.dataManager.set(SITTING, flag);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if ((this.isBeingRidden()) && (entity == this.getRidingEntity())) return false;
        if (entity instanceof WolfEntity) {
            CreatureEntity entitycreature = (CreatureEntity) entity;
            entitycreature.setAttackTarget(null);
            return false;
        } else {
            i = i - (getArmorType() + 2);
            if (i < 0F) i = 0F;
            return super.attackEntityFrom(damagesource, i);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        RegistryKey<Biome> currentbiome = MoCTools.biomeKind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SAVANNA)) setTypeMoC(60); // zebra
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * returns one of the RGB color codes
     *
     * @param sColor  : 1 will return the Red component, 2 will return the Green
     *                and 3 the blue
     * @param typeInt : which set of colors to inquiry about, corresponds with
     *                the horse types.
     */
    public float colorFX(int sColor, int typeInt) {
        switch (typeInt) {
            case 23: // green for undeads
            case 24: // green for undeads
            case 25: // green for undeads
                if (sColor == 1) {
                    return (float) 60 / 256;
                }
                if (sColor == 2) {
                    return (float) 179 / 256;
                }
                return (float) 112 / 256;
            case 40: // dark red for black pegasus
                if (sColor == 1) {
                    return (float) 139 / 256;
                }
                if (sColor == 2) {
                    return 0F;
                }
                return 0F;
            case 48: // yellow
                if (sColor == 1) {
                    return (float) 179 / 256;
                } else if (sColor == 2) {
                    return (float) 160 / 256;
                }
                return (float) 22 / 256;
            case 49: // purple
                if (sColor == 1) {
                    return (float) 147 / 256;
                } else if (sColor == 2) {
                    return (float) 90 / 256;
                }
                return (float) 195 / 256;
            case 51: // blue
                if (sColor == 1) {
                    return (float) 30 / 256;
                } else if (sColor == 2) {
                    return (float) 144 / 256;
                }
                return (float) 255 / 256;
            case 52: // pink
                if (sColor == 1) {
                    return (float) 255 / 256;
                }
                if (sColor == 2) {
                    return (float) 105 / 256;
                }
                return (float) 180 / 256;
            case 53: // lightgreen
                if (sColor == 1) {
                    return (float) 188 / 256;
                }
                if (sColor == 2) {
                    return (float) 238 / 256;
                }
                return (float) 104 / 256;
            case 54: // black fairy
                if (sColor == 1) {
                    return (float) 110 / 256;
                }
                if (sColor == 2) {
                    return (float) 123 / 256;
                }
                return (float) 139 / 256;
            case 55: // red fairy
                if (sColor == 1) {
                    return (float) 194 / 256;
                }
                if (sColor == 2) {
                    return (float) 29 / 256;
                }
                return (float) 34 / 256;
            case 56: // dark blue fairy
                if (sColor == 1) {
                    return (float) 63 / 256;
                }
                if (sColor == 2) {
                    return (float) 45 / 256;
                }
                return (float) 255 / 256;
            case 57: // cyan
                if (sColor == 1) {
                    return (float) 69 / 256;
                }
                if (sColor == 2) {
                    return (float) 146 / 256;
                }
                return (float) 145 / 256;
            case 58: // green
                if (sColor == 1) {
                    return (float) 90 / 256;
                }
                if (sColor == 2) {
                    return (float) 136 / 256;
                }
                return (float) 43 / 256;
            case 59: // orange
                if (sColor == 1) {
                    return (float) 218 / 256;
                }
                if (sColor == 2) {
                    return (float) 40 / 256;
                }
                return (float) 0 / 256;
            default: // by default will return clear gold
                if (sColor == 1) {
                    return (float) 255 / 256;
                } else if (sColor == 2) {
                    return (float) 236 / 256;
                } else {
                    return (float) 139 / 256;
                }
        }
    }

    /**
     * Called to vanish a Horse without FX
     */
    public void dissapearHorse() {
        this.removed = true;
    }

    private void drinkingHorse() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING.get());
    }

    /**
     * Drops the current armor if the horse has one
     */
    @Override
    public void dropArmor() {
        if (this.world.isRemote) return;
        int armorType = this.getArmorType();
        if (armorType != 0) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF.get());
        ItemStack armorStack;
        switch (armorType) {
            case 1:
                armorStack = new ItemStack(Items.IRON_HORSE_ARMOR, 1);
                break;
            case 2:
                armorStack = new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1);
                break;
            case 3:
                armorStack = new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1);
                break;
            case 4:
                armorStack = new ItemStack(MoCItems.horsearmorcrystal, 1);
                break;
            default:
                return; // No armor to drop
        }
        ItemEntity entityItem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), armorStack);
        entityItem.setDefaultPickupDelay();
        this.world.addEntity(entityItem);
        setArmorType((byte) 0);
    }

    /**
     * Drops a chest block if the horse is bagged
     */
    public void dropBags() {
        if (!isBagger() || !getIsChested() || this.world.isRemote) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(Blocks.CHEST, 1));
        double f3 = 0.05D;
        entityitem.setMotion(this.world.rand.nextGaussian() * f3, (this.world.rand.nextGaussian() * f3) + 0.2D, this.world.rand.nextGaussian() * f3);
        this.world.addEntity(entityitem);
        setIsChested(false);
    }

    private void eatingHorse() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING.get());
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        if (isFlyer() || isFloater()) return false;
        float i = (float) (Math.ceil(distance - 3F) / 2F);
        if (!this.world.isRemote && (i > 0)) {
            if (getTypeMoC() >= 10) i /= 2;
            if (i > 1F) attackEntityFrom(DamageSource.FALL, i);
            if ((this.isBeingRidden()) && (i > 1F)) {
                for (Entity entity : this.getRecursivePassengers()) entity.attackEntityFrom(DamageSource.FALL, i);
            }
            BlockState iblockstate = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double) this.prevRotationYaw, this.getPosZ()));
            Block block = iblockstate.getBlock();
            if (iblockstate.getMaterial() != Material.AIR && !this.isSilent()) {
                SoundType soundtype = block.getSoundType(iblockstate, world, new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double) this.prevRotationYaw, this.getPosZ()), this);
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
            return true;
        }
        return false;
    }

    public MoCAnimalChest.Size getInventorySize() {
        if (getTypeMoC() == 40) return MoCAnimalChest.Size.small;
        if (getTypeMoC() > 64) return MoCAnimalChest.Size.medium;
        return MoCAnimalChest.Size.tiny;
    }

    @Override
    public double getCustomJump() {
        double horseJump = 0.35D;
        if (getTypeMoC() < 6) // tier 1
        {
            horseJump = 0.35;
        } else if (getTypeMoC() > 5 && getTypeMoC() < 11) // tier 2
        {
            horseJump = 0.40D;
        } else if (getTypeMoC() > 10 && getTypeMoC() < 16) // tier 3
        {
            horseJump = 0.45D;
        } else if (getTypeMoC() > 15 && getTypeMoC() < 21) // tier 4
        {
            horseJump = 0.50D;
        } else if (getTypeMoC() > 20 && getTypeMoC() < 26) // ghost and undead
        {
            horseJump = 0.45D;
        } else if (getTypeMoC() > 25 && getTypeMoC() < 30) // skelly
        {
            horseJump = 0.5D;
        } else if (getTypeMoC() >= 30 && getTypeMoC() < 40) // magics
        {
            horseJump = 0.55D;
        } else if (getTypeMoC() >= 40 && getTypeMoC() < 60) // black pegasus and fairies
        {
            horseJump = 0.6D;
        } else if (getTypeMoC() >= 60) // donkeys - zebras and the like
        {
            horseJump = 0.4D;
        }
        return horseJump;
    }

    @Override
    public double getCustomSpeed() {
        double horseSpeed = 0.8D;
        if (getTypeMoC() < 6) // tier 1
        {
            horseSpeed = 0.9;
        } else if (getTypeMoC() > 5 && getTypeMoC() < 11) // tier 2
        {
            horseSpeed = 1.0D;
        } else if (getTypeMoC() > 10 && getTypeMoC() < 16) // tier 3
        {
            horseSpeed = 1.1D;
        } else if (getTypeMoC() > 15 && getTypeMoC() < 21) // tier 4
        {
            horseSpeed = 1.2D;
        } else if (getTypeMoC() > 20 && getTypeMoC() < 26) // ghost and undead
        {
            horseSpeed = 0.8D;
        } else if (getTypeMoC() > 25 && getTypeMoC() < 30) // skelly
        {
            horseSpeed = 1.0D;
        } else if (getTypeMoC() > 30 && getTypeMoC() < 40) // magics
        {
            horseSpeed = 1.2D;
        } else if (getTypeMoC() >= 40 && getTypeMoC() < 60) // black pegasus and fairies
        {
            horseSpeed = 1.3D;
        } else if (getTypeMoC() == 60 || getTypeMoC() == 61) // zebras and zorse
        {
            horseSpeed = 1.1D;
        } else if (getTypeMoC() == 65) // donkeys
        {
            horseSpeed = 0.7D;
        } else if (getTypeMoC() > 65) // mule and zorky
        {
            horseSpeed = 0.9D;
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 150) {
            horseSpeed *= 1.5D;
        }
        if (this.sprintCounter > 150) {
            horseSpeed *= 0.5D;
        }
        return horseSpeed;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        if (this.isUndead()) return MoCSoundEvents.ENTITY_HORSE_DEATH_UNDEAD.get();
        if (this.getIsGhost()) return MoCSoundEvents.ENTITY_HORSE_DEATH_GHOST.get();
        if (this.getTypeMoC() == 60 || this.getTypeMoC() == 61) return MoCSoundEvents.ENTITY_HORSE_HURT_ZEBRA.get();
        if (this.getTypeMoC() > 64 && this.getTypeMoC() < 68) return MoCSoundEvents.ENTITY_HORSE_DEATH_DONKEY.get();
        return MoCSoundEvents.ENTITY_HORSE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!blockIn.getMaterial().isLiquid()) {
            SoundType soundtype = blockIn.getSoundType();

            if (this.world.getBlockState(pos.up()).getBlock() == Blocks.SNOW) {
                soundtype = Blocks.SNOW.getSoundType(blockIn);
            }

            /*if (this.isBeingRidden() && this.canGallop) {
                ++this.gallopTime;

                if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                    this.playGallopSound(soundtype);
                }
                else if (this.gallopTime <= 5) {
                    this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
                }
            }*/
            else if (soundtype == SoundType.WOOD) {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
            } else {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
            }
        }
    }

    @Override
    public boolean renderName() {
        if (getIsGhost() && getAge() < 10) return false;
        return super.renderName();
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);

        Item drop = Items.LEATHER;

        if (flag && (this.getTypeMoC() == 36 || (this.getTypeMoC() > 49 && this.getTypeMoC() < 60))) // unicorn
        {
            drop = MoCItems.unicornhorn;
        }
        if (this.getTypeMoC() == 39 || this.getTypeMoC() == 40) // (dark) pegasus
        {
            drop = Items.FEATHER;
        }
        if (this.getTypeMoC() == 38 && flag && this.world.getDimensionType().isUltrawarm()) // nightmare
        {
            drop = MoCItems.heartfire;
        }
        if (this.getTypeMoC() == 32 && flag) // bat horse
        {
            drop = MoCItems.heartdarkness;
        }
        if (this.getTypeMoC() == 26)// skelly
        {
            drop = Items.BONE;
        }
        if ((this.getTypeMoC() == 23 || this.getTypeMoC() == 24 || this.getTypeMoC() == 25)) {
            if (flag) {
                drop = MoCItems.heartundead;
            } else {
                drop = Items.ROTTEN_FLESH;
            }
        }
        if (this.getTypeMoC() == 21 || this.getTypeMoC() == 22) {
            drop = Items.GHAST_TEAR;
        }

        int i = this.rand.nextInt(3);

        if (looting > 0)
        {
            i += this.rand.nextInt(looting + 1);
        }

        this.entityDropItem(new ItemStack(drop, i));
    }

    public boolean getHasReproduced() {
        return this.hasReproduced;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        if (isFlyer() && !this.isBeingRidden()) wingFlap();
        else if (this.rand.nextInt(3) == 0) stand();

        if (this.isUndead()) return MoCSoundEvents.ENTITY_HORSE_HURT_UNDEAD.get();
        if (this.getIsGhost()) return MoCSoundEvents.ENTITY_HORSE_HURT_GHOST.get();
        if (this.getTypeMoC() == 60 || this.getTypeMoC() == 61) return MoCSoundEvents.ENTITY_HORSE_HURT_ZEBRA.get();
        if (this.getTypeMoC() > 64 && this.getTypeMoC() < 68) return MoCSoundEvents.ENTITY_HORSE_HURT_DONKEY.get();

        return MoCSoundEvents.ENTITY_HORSE_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (this.rand.nextInt(10) == 0 && !isMovementCeased()) stand();

        if (this.isUndead()) return MoCSoundEvents.ENTITY_HORSE_AMBIENT_UNDEAD.get();
        if (this.getIsGhost()) return MoCSoundEvents.ENTITY_HORSE_AMBIENT_GHOST.get();
        if (this.getTypeMoC() == 60 || this.getTypeMoC() == 61) return MoCSoundEvents.ENTITY_HORSE_AMBIENT_ZEBRA.get();
        if (this.getTypeMoC() > 64 && this.getTypeMoC() < 68) return MoCSoundEvents.ENTITY_HORSE_HURT_DONKEY.get();

        return MoCSoundEvents.ENTITY_HORSE_AMBIENT.get();
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    @Override
    protected SoundEvent getAngrySound() {
        openMouth();
        stand();
        if (this.isUndead()) return MoCSoundEvents.ENTITY_HORSE_ANGRY_UNDEAD.get();
        if (this.getIsGhost()) return MoCSoundEvents.ENTITY_HORSE_ANGRY_GHOST.get();
        if (this.getTypeMoC() == 60 || this.getTypeMoC() == 61) return MoCSoundEvents.ENTITY_HORSE_HURT_ZEBRA.get();
        if (this.getTypeMoC() > 64 && this.getTypeMoC() < 68) return MoCSoundEvents.ENTITY_HORSE_HURT_DONKEY.get();
        return MoCSoundEvents.ENTITY_HORSE_MAD.get();
    }

    public float calculateMaxHealth() {
        int maximumHealth = 30;
        if (getTypeMoC() < 6) // tier 1
        {
            maximumHealth = 25;
        } else if (getTypeMoC() > 5 && getTypeMoC() < 11) // tier 2
        {
            maximumHealth = 30;
        } else if (getTypeMoC() > 10 && getTypeMoC() < 16) // tier 3
        {
            maximumHealth = 35;
        } else if (getTypeMoC() > 15 && getTypeMoC() < 21) // tier 4
        {
            maximumHealth = 40;
        } else if (getTypeMoC() > 20 && getTypeMoC() < 26) // ghost and undead
        {
            maximumHealth = 35;
        } else if (getTypeMoC() > 25 && getTypeMoC() < 30) // skelly
        {
            maximumHealth = 35;
        } else if (getTypeMoC() >= 30 && getTypeMoC() < 40) // magics
        {
            maximumHealth = 50;
        } else if (getTypeMoC() == 40) // black pegasus
        {
            maximumHealth = 50;
        } else if (getTypeMoC() > 40 && getTypeMoC() < 60) // fairies
        {
            maximumHealth = 40;
        } else if (getTypeMoC() >= 60) // donkeys - zebras and the like
        {
            maximumHealth = 30;
        }

        return maximumHealth;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    @Override
    public int getMaxTemper() {
        if (getTypeMoC() == 60) return 200; // zebras are harder to tame
        return 100;
    }

    public int getNightmareInt() {
        return this.nightmareInt;
    }

    public void setNightmareInt(int i) {
        this.nightmareInt = i;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    /**
     * Overridden for the dynamic nightmare texture.
     */
    @Override
    public ResourceLocation getTexture() {
        String tempTexture;

        switch (getTypeMoC()) {
            case 1:
                tempTexture = "horsewhite.png";
                break;
            case 2:
                tempTexture = "horsecreamy.png";
                break;
            case 3:
                tempTexture = "horsebrown.png";
                break;
            case 4:
                tempTexture = "horsedarkbrown.png";
                break;
            case 5:
                tempTexture = "horseblack.png";
                break;
            case 6:
                tempTexture = "horsebrightcreamy.png";
                break;
            case 7:
                tempTexture = "horsespeckled.png";
                break;
            case 8:
                tempTexture = "horsepalebrown.png";
                break;
            case 9:
                tempTexture = "horsegrey.png";
                break;
            case 11:
                tempTexture = "horsepinto.png";
                break;
            case 12:
                tempTexture = "horsebrightpinto.png";
                break;
            case 13:
                tempTexture = "horsepalespeckles.png";
                break;
            case 16:
                tempTexture = "horsespotted.png";
                break;
            case 17:
                tempTexture = "horsecow.png";
                break;
            case 21:
                tempTexture = "horseghost.png";
                break;
            case 22:
                tempTexture = "horseghostb.png";
                break;
            case 23:
                tempTexture = "horseundead.png";
                break;
            case 24:
                tempTexture = "horseundeadunicorn.png";
                break;
            case 25:
                tempTexture = "horseundeadpegasus.png";
                break;
            case 26:
                tempTexture = "horseskeleton.png";
                break;
            case 27:
                tempTexture = "horseunicornskeleton.png";
                break;
            case 28:
                tempTexture = "horsepegasusskeleton.png";
                break;
            case 32:
                tempTexture = "horsebat.png";
                break;
            case 36:
                tempTexture = "horseunicorn.png";
                break;
            case 38:
                //this.isImmuneToFire = true;
                tempTexture = "horsenightmare.png";
                break;
            case 39:
                tempTexture = "horsepegasus.png";
                break;
            case 40:
                //this.isImmuneToFire = true;
                tempTexture = "horsedarkpegasus.png";
                break;
            /*
             * case 44: tempTexture = "horsefairydarkblue.png"; break; case 45:
             * tempTexture = "horsefairydarkblue.png"; break; case 46:
             * tempTexture = "horsefairydarkblue.png"; break; case 47:
             * tempTexture = "horsefairydarkblue.png"; break;
             */
            case 48:
                tempTexture = "horsefairyyellow.png";
                break;
            case 49:
                tempTexture = "horsefairypurple.png";
                break;
            case 50:
                tempTexture = "horsefairywhite.png";
                break;
            case 51:
                tempTexture = "horsefairyblue.png";
                break;
            case 52:
                tempTexture = "horsefairypink.png";
                break;
            case 53:
                tempTexture = "horsefairylightgreen.png";
                break;
            case 54:
                tempTexture = "horsefairyblack.png";
                break;
            case 55:
                tempTexture = "horsefairyred.png";
                break;
            case 56:
                tempTexture = "horsefairydarkblue.png";
                break;
            case 57:
                tempTexture = "horsefairycyan.png";
                break;
            case 58:
                tempTexture = "horsefairygreen.png";
                break;
            case 59:
                tempTexture = "horsefairyorange.png";
                break;
            case 60:
                tempTexture = "horsezebra.png";
                break;
            case 61:
                tempTexture = "horsezorse.png";
                break;
            case 65:
                tempTexture = "horsedonkey.png";
                break;
            case 66:
                tempTexture = "horsemule.png";
                break;
            case 67:
                tempTexture = "horsezonky.png";
                break;
            default:
                tempTexture = "horsebug.png";
        }

        if ((isArmored() || isMagicHorse()) && getArmorType() > 0) {
            String armorTex = "";
            if (getArmorType() == 1) armorTex = "metal.png";
            if (getArmorType() == 2) armorTex = "gold.png";
            if (getArmorType() == 3) armorTex = "diamond.png";
            if (getArmorType() == 4) armorTex = "crystaline.png";
            return MoCreatures.proxy.getModelTexture(tempTexture.replace(".png", armorTex));
        }

        if (this.isUndead() && this.getTypeMoC() < 26) {
            String baseTex = "horseundead";
            int max = 79;
            if (this.getTypeMoC() == 25) // undead pegasus
            {
                baseTex = "horseundeadpegasus";
                // max = 79; //undead pegasus have an extra animation

            }
            if (this.getTypeMoC() == 24)// undead unicorn
            {
                baseTex = "horseundeadunicorn";
                max = 69; // undead unicorn have an animation less
            }

            String iteratorTex = "1";
            if (MoCreatures.proxy.getAnimateTextures()) {
                if (this.rand.nextInt(3) == 0) this.textCounter++;
                if (this.textCounter < 10) this.textCounter = 10;
                if (this.textCounter > max) this.textCounter = 10;
                iteratorTex = String.valueOf(this.textCounter);
                iteratorTex = iteratorTex.substring(0, 1);
            }

            String decayTex = String.valueOf(getAge() / 100);
            decayTex = decayTex.substring(0, 1);
            return MoCreatures.proxy.getModelTexture(baseTex + decayTex + iteratorTex + ".png");
        }

        // if animate textures is off, return plain textures
        if (!MoCreatures.proxy.getAnimateTextures()) {
            return MoCreatures.proxy.getModelTexture(tempTexture);
        }

        if (this.isNightmare()) {
            this.rand.nextInt(1);
            this.textCounter++;
            if (this.textCounter < 10) this.textCounter = 10;
            if (this.textCounter > 59) this.textCounter = 10;
            String NTA = "horsenightmare";
            String NTB = String.valueOf(this.textCounter);
            NTB = NTB.substring(0, 1);
            String NTC = ".png";

            return MoCreatures.proxy.getModelTexture(NTA + NTB + NTC);
        }

        if (this.transformCounter != 0 && this.transformType != 0) {
            String newText;
            switch (this.transformType) {
                case 24:
                    newText = "horseundeadunicorn.png";
                    break;
                case 25:
                    newText = "horseundeadpegasus.png";
                    break;
                case 32:
                    newText = "horsebat.png";
                    break;
                case 36:
                    newText = "horseunicorn.png";
                    break;
                case 38:
                    newText = "horsenightmare1.png";
                    break;
                case 39:
                    newText = "horsepegasus.png";
                    break;
                case 40:
                    newText = "horseblackpegasus.png";
                    break;
                case 48:
                    newText = "horsefairyyellow.png";
                    break;
                case 49:
                    newText = "horsefairypurple.png";
                    break;
                case 50:
                    newText = "horsefairywhite.png";
                    break;
                case 51:
                    newText = "horsefairyblue.png";
                    break;
                case 52:
                    newText = "horsefairypink.png";
                    break;
                case 53:
                    newText = "horsefairylightgreen.png";
                    break;
                case 54:
                    newText = "horsefairyblack.png";
                    break;
                case 55:
                    newText = "horsefairyred.png";
                    break;
                case 56:
                    newText = "horsefairydarkblue.png";
                    break;
                case 57:
                    newText = "horsefairycyan.png";
                    break;
                case 58:
                    newText = "horsefairygreen.png";
                    break;
                case 59:
                    newText = "horsefairyorange.png";
                    break;
                default:
                    newText = "horseundead.png";
                    break;
            }

            if (this.transformCounter > 75 && this.transformCounter % 4 == 0)
                return MoCreatures.proxy.getModelTexture(newText);
        }

        return MoCreatures.proxy.getModelTexture(tempTexture);
    }

    /**
     * New networked to fix SMP issues
     */
    public byte getVanishC() {
        return (byte) this.vanishCounter;
    }

    /**
     * New networked to fix SMP issues
     */
    public void setVanishC(byte i) {
        this.vanishCounter = i;
    }

    /**
     * Breeding rules for the horses
     */
    //private int horseGenetics(MoCEntityHorse entityhorse, MoCEntityHorse entityhorse1)
    private int horseGenetics(int typeA, int typeB) {
        boolean flag = MoCreatures.proxy.easyHorseBreeding;
        //int typeA = entityhorse.getTypeMoC();
        //int typeB = entityhorse1.getTypeMoC();

        // identical horses have so spring
        if (typeA == typeB) {
            return typeA;
        }

        // zebras plus any horse
        if (typeA == 60 && typeB < 21 || typeB == 60 && typeA < 21) {
            return 61; // zorse
        }

        // dokey plus any horse
        if (typeA == 65 && typeB < 21 || typeB == 65 && typeA < 21) {
            return 66; // mule
        }

        // zebra plus donkey
        if (typeA == 60 && typeB == 65 || typeB == 60 && typeA == 65) {
            return 67; // zonky
        }

        if (typeA > 20 && typeB < 21 || typeB > 20 && typeA < 21) // rare horses plus  ordinary horse always returns ordinary horse
        {
            return Math.min(typeA, typeB);
        }

        // unicorn plus white pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 39 || typeB == 36 && typeA == 39) {
            return 50; // white fairy
        }

        // unicorn plus black pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 40 || typeB == 36 && typeA == 40) {
            return 54; // black fairy
        }

        // rare horse mixture: produces a regular horse 1-5
        if (typeA > 20) {
            return (this.rand.nextInt(5)) + 1;
        }

        // rest of cases will return either typeA, typeB or new mix
        if (!flag) {
            int chanceInt = (this.rand.nextInt(4)) + 1;
            // 25%
            if (chanceInt == 1) return typeA;
            // 25%
            if (chanceInt == 2) return typeB;
        }

        if ((typeA == 1 && typeB == 2) || (typeA == 2 && typeB == 1)) {
            return 6;
        }

        if ((typeA == 1 && typeB == 3) || (typeA == 3 && typeB == 1)) {
            return 2;
        }

        if ((typeA == 1 && typeB == 4) || (typeA == 4 && typeB == 1)) {
            return 7;
        }

        if ((typeA == 1 && typeB == 5) || (typeA == 5 && typeB == 1)) {
            return 9;
        }

        if ((typeA == 1 && typeB == 7) || (typeA == 7 && typeB == 1)) {
            return 12;
        }

        if ((typeA == 1 && typeB == 8) || (typeA == 8 && typeB == 1)) {
            return 7;
        }

        if ((typeA == 1 && typeB == 9) || (typeA == 9 && typeB == 1)) {
            return 13;
        }

        if ((typeA == 1 && typeB == 11) || (typeA == 11 && typeB == 1)) {
            return 12;
        }

        if ((typeA == 1 && typeB == 12) || (typeA == 12 && typeB == 1)) {
            return 13;
        }

        if ((typeA == 1 && typeB == 17) || (typeA == 17 && typeB == 1)) {
            return 16;
        }

        if ((typeA == 2 && typeB == 4) || (typeA == 4 && typeB == 2)) {
            return 3;
        }

        if ((typeA == 2 && typeB == 5) || (typeA == 5 && typeB == 2)) {
            return 4;
        }

        if ((typeA == 2 && typeB == 7) || (typeA == 7 && typeB == 2)) {
            return 8;
        }

        if ((typeA == 2 && typeB == 8) || (typeA == 8 && typeB == 2)) {
            return 3;
        }

        if ((typeA == 2 && typeB == 12) || (typeA == 12 && typeB == 2)) {
            return 6;
        }

        if ((typeA == 2 && typeB == 16) || (typeA == 16 && typeB == 2)) {
            return 13;
        }

        if ((typeA == 2 && typeB == 17) || (typeA == 17 && typeB == 2)) {
            return 12;
        }

        if ((typeA == 3 && typeB == 4) || (typeA == 4 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 5) || (typeA == 5 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 6) || (typeA == 6 && typeB == 3)) {
            return 2;
        }

        if ((typeA == 3 && typeB == 7) || (typeA == 7 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 9) || (typeA == 9 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 12) || (typeA == 12 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 16) || (typeA == 16 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 17) || (typeA == 17 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 4 && typeB == 6) || (typeA == 6 && typeB == 4)) {
            return 3;
        }

        if ((typeA == 4 && typeB == 7) || (typeA == 7 && typeB == 4)) {
            return 8;
        }

        if ((typeA == 4 && typeB == 9) || (typeA == 9 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 11) || (typeA == 11 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 12) || (typeA == 12 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 13) || (typeA == 13 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 16) || (typeA == 16 && typeB == 4)) {
            return 13;
        }

        if ((typeA == 4 && typeB == 17) || (typeA == 17 && typeB == 4)) {
            return 5;
        }

        if ((typeA == 5 && typeB == 6) || (typeA == 6 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 7) || (typeA == 7 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 8) || (typeA == 8 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 11) || (typeA == 11 && typeB == 5)) {
            return 17;
        }

        if ((typeA == 5 && typeB == 12) || (typeA == 12 && typeB == 5)) {
            return 13;
        }

        if ((typeA == 5 && typeB == 13) || (typeA == 13 && typeB == 5)) {
            return 16;
        }

        if ((typeA == 5 && typeB == 16) || (typeA == 16 && typeB == 5)) {
            return 17;
        }

        if ((typeA == 6 && typeB == 8) || (typeA == 8 && typeB == 6)) {
            return 2;
        }

        if ((typeA == 6 && typeB == 17) || (typeA == 17 && typeB == 6)) {
            return 7;
        }

        if ((typeA == 7 && typeB == 16) || (typeA == 16 && typeB == 7)) {
            return 13;
        }

        if ((typeA == 8 && typeB == 11) || (typeA == 11 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 12) || (typeA == 12 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 13) || (typeA == 13 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 16) || (typeA == 16 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 17) || (typeA == 17 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 9 && typeB == 16) || (typeA == 16 && typeB == 9)) {
            return 13;
        }

        if ((typeA == 11 && typeB == 16) || (typeA == 16 && typeB == 11)) {
            return 13;
        }

        if ((typeA == 11 && typeB == 17) || (typeA == 17 && typeB == 11)) {
            return 7;
        }

        if ((typeA == 12 && typeB == 16) || (typeA == 16 && typeB == 12)) {
            return 13;
        }

        if ((typeA == 13 && typeB == 17) || (typeA == 17 && typeB == 13)) {
            return 9;
        }

        return typeA; // breed is not in the table, so it will return the first parent type
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        final ActionResultType tameResult = this.processTameInteract(player, hand);

        if (tameResult != null) return tameResult;

        if (this.getTypeMoC() == 60 && !getIsTamed() && isZebraRunning()) return ActionResultType.FAIL;

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && !getIsRideable() && (stack.getItem() instanceof SaddleItem || stack.getItem() == MoCItems.horsesaddle)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            setRideable(true);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == Items.IRON_HORSE_ARMOR && isArmored()) {
            if (getArmorType() == 0) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            dropArmor();
            setArmorType((byte) 1);
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == Items.GOLDEN_HORSE_ARMOR && isArmored()) {
            if (getArmorType() == 0) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            dropArmor();
            setArmorType((byte) 2);
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == Items.DIAMOND_HORSE_ARMOR && isArmored()) {
            if (getArmorType() == 0) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            dropArmor();
            setArmorType((byte) 3);
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == MoCItems.horsearmorcrystal && isMagicHorse()) {
            if (getArmorType() == 0) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON.get());
            dropArmor();
            setArmorType((byte) 4);
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            return ActionResultType.SUCCESS;
        }

        // transform to undead, or heal undead horse
        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == MoCItems.essenceundead) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            else player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));

            if (this.isUndead() || getIsGhost()) this.setHealth(getMaxHealth());

            // pegasus, dark pegasus, or bat horse
            if (this.getTypeMoC() == 39 || this.getTypeMoC() == 32 || this.getTypeMoC() == 40) {
                // transformType = 25; //undead pegasus
                transform(25);

            } else if (this.getTypeMoC() == 36 || (this.getTypeMoC() > 47 && this.getTypeMoC() < 60)) // unicorn or fairies
            {
                // transformType = 24; //undead unicorn
                transform(24);
            } else if (this.getTypeMoC() < 21 || this.getTypeMoC() == 60 || this.getTypeMoC() == 61) // regular horses or zebras
            {
                // transformType = 23; //undead
                transform(23);
            }
            drinkingHorse();
            return ActionResultType.SUCCESS;
        }

        // to transform to nightmares: only pure breeds
        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == MoCItems.essencefire) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            else player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));

            if (this.isNightmare()) {
                if (getIsAdult() && getHealth() == getMaxHealth()) this.eatenPumpkin = true;
                this.setHealth(getMaxHealth());
            }
            if (this.getTypeMoC() == 61) {
                //nightmare
                transform(38);
            }
            drinkingHorse();
            return ActionResultType.SUCCESS;
        }

        // transform to dark pegasus
        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == MoCItems.essencedarkness) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            else player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));

            if (this.getTypeMoC() == 32) {
                if (getIsAdult() && getHealth() == getMaxHealth()) this.eatenPumpkin = true;
                this.setHealth(getMaxHealth());
            }
            if (this.getTypeMoC() == 61) {
                transform(32); //horsezorse to bat horse
            }
            if (this.getTypeMoC() == 39) // pegasus to darkpegasus
            {
                //darkpegasus
                transform(40);
            }
            drinkingHorse();
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == MoCItems.essencelight) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (this.isMagicHorse()) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenPumpkin = true;
                }
                this.setHealth(getMaxHealth());
            }
            if (this.isNightmare()) {
                // unicorn
                transform(36);
            }
            if (this.getTypeMoC() == 32 && this.getPosY() > 128D) // bathorse to pegasus
            {
                // pegasus
                transform(39);
            }
            // to return undead horses to pristine conditions
            if (this.isUndead() && this.getIsAdult() && !this.world.isRemote) {
                setAge(10);
                if (this.getTypeMoC() >= 26) setTypeMoC(getTypeMoC() - 3);
            }
            drinkingHorse();
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && this.isAmuletHorse() && getIsTamed()) {
            if ((this.getTypeMoC() == 26 || this.getTypeMoC() == 27 || this.getTypeMoC() == 28) && stack.getItem() == MoCItems.amuletbone) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return ActionResultType.SUCCESS;
            }
            if ((this.getTypeMoC() > 47 && this.getTypeMoC() < 60) && stack.getItem() == MoCItems.amuletfairy) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return ActionResultType.SUCCESS;
            }
            if ((this.getTypeMoC() == 39 || this.getTypeMoC() == 40) && (stack.getItem() == MoCItems.amuletpegasus)) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return ActionResultType.SUCCESS;
            }
            if ((this.getTypeMoC() == 21 || this.getTypeMoC() == 22) && (stack.getItem() == MoCItems.amuletghost)) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return ActionResultType.SUCCESS;
            }
        }

        if (!stack.isEmpty() && (stack.getItem() instanceof DyeItem) && this.getTypeMoC() == 50) {

            int colorInt = ((DyeItem)stack.getItem()).getDyeColor().getId();
            switch (colorInt) {
                case 1: //orange
                    transform(59);
                    break;
                case 2: //magenta TODO
                    //transform(46);
                    break;
                case 3: //light blue
                    transform(51);
                    break;
                case 4: //yellow
                    transform(48);
                    break;
                case 5: //light green
                    transform(53);
                    break;
                case 6: //pink
                    transform(52);
                    break;
                case 7: //gray TODO
                    //transform(50);
                    break;
                case 8: //light gray TODO
                    //transform(50);
                    break;
                case 9: //cyan
                    transform(57);
                    break;
                case 10: //purple
                    transform(49);
                    break;
                case 11: //dark blue
                    transform(56);
                    break;
                case 12: //brown TODO
                    //transform(50);
                    break;
                case 13: //green
                    transform(58);
                    break;
                case 14: //red
                    transform(55);
                    break;
                case 15: //black
                    transform(54);
                    break;
            }

            if (!player.abilities.isCreativeMode) stack.shrink(1);
            eatingHorse();
            return ActionResultType.SUCCESS;
        }

        // zebra easter egg
        if (!stack.isEmpty() && (this.getTypeMoC() == 60) && stack.getItem() instanceof MusicDiscItem && MoCreatures.proxy.easterEggs) {
            player.setHeldItem(hand, ItemStack.EMPTY);
            if (!this.world.isRemote) {
                ItemEntity entityitem1 = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.recordshuffle, 1));
                entityitem1.setPickupDelay(20);
                this.world.addEntity(entityitem1);
            }
            eatingHorse();
            return ActionResultType.SUCCESS;
        }
        if (!stack.isEmpty() && (stack.getItem() == Items.WHEAT) && !isMagicHorse() && !isUndead()) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) setTemper(getMaxTemper() - 5);
            }
            if ((getHealth() + 5) > getMaxHealth()) this.setHealth(getMaxHealth());
            eatingHorse();
            if (!getIsAdult() && (getAge() < getMaxAge())) setAge(getAge() + 1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.sugarlump) && !isMagicHorse() && !isUndead()) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) setTemper(getMaxTemper() - 5);
            }
            if ((getHealth() + 10) > getMaxHealth()) this.setHealth(getMaxHealth());
            eatingHorse();
            if (!getIsAdult() && (getAge() < getMaxAge())) setAge(getAge() + 2);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && (stack.getItem() == Items.BREAD) && !isMagicHorse() && !isUndead()) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) {
                setTemper(getTemper() + 100);
                if (getTemper() > getMaxTemper()) setTemper(getMaxTemper() - 5);
            }
            if ((getHealth() + 20) > getMaxHealth()) this.setHealth(getMaxHealth());
            eatingHorse();
            if (!getIsAdult() && (getAge() < getMaxAge())) setAge(getAge() + 3);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && ((stack.getItem() == Items.APPLE) || (stack.getItem() == Items.GOLDEN_APPLE)) && !isMagicHorse() && !isUndead()) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) MoCTools.tameWithName(player, this);
            this.setHealth(getMaxHealth());
            eatingHorse();
            if (!getIsAdult() && (getAge() < getMaxAge()) && !this.world.isRemote) setAge(getAge() + 1);
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) && (isBagger())) {
            if (getIsChested()) return ActionResultType.FAIL;
            if (!player.abilities.isCreativeMode) stack.shrink(1);

            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return ActionResultType.SUCCESS;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.haystack)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            setSitting(true);
            eatingHorse();
            if (!isMagicHorse() && !isUndead()) this.setHealth(getMaxHealth());
            return ActionResultType.SUCCESS;
        }
        if (getIsChested() && player.isSneaking()) {
            // if first time opening horse chest, we must initialize it
            if (this.localChest == null) this.localChest = new MoCAnimalChest("HorseChest", getInventorySize());// , new
            // only open this chest on server side
            if (!this.world.isRemote) player.openContainer(this.localChest);
            return ActionResultType.SUCCESS;

        }

        if (!stack.isEmpty()
                && ((stack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) || (stack.getItem() == Items.MUSHROOM_STEW)
                || (stack.getItem() == Items.CAKE) || (stack.getItem() == Items.GOLDEN_CARROT))) {
            if (!getIsAdult() || isMagicHorse() || isUndead()) return ActionResultType.FAIL;
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (stack.getItem() == Items.MUSHROOM_STEW) {
                if (stack.isEmpty()) player.setHeldItem(hand, new ItemStack(Items.BOWL));
                else player.inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
            } else if (stack.isEmpty()) player.setHeldItem(hand, ItemStack.EMPTY);
            this.eatenPumpkin = true;
            this.setHealth(getMaxHealth());
            eatingHorse();
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());// eatinghaystack = !eatinghaystack;
            setIsJumping(false);
            getNavigator().clearPath();
            setAttackTarget(null);
            return ActionResultType.SUCCESS;
        }

        if (getIsRideable() && getIsAdult() && (!this.isBeingRidden())) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
                this.gestationTime = 0;
            }
            return ActionResultType.SUCCESS;
        }

        return super.getEntityInteractionResult(player, hand);
    }

    /**
     * Can this horse be trapped in a special amulet?
     */
    public boolean isAmuletHorse() {
        return this.getTypeMoC() == 21
                || this.getTypeMoC() == 22
                || this.getTypeMoC() == 26
                || this.getTypeMoC() == 27
                || this.getTypeMoC() == 28
                || this.getTypeMoC() == 39
                || this.getTypeMoC() == 40
                || (this.getTypeMoC() > 47 && this.getTypeMoC() < 60);
    }

    /**
     * Can wear regular armor
     */
    public boolean isArmored() {
        return (this.getTypeMoC() < 21);
    }

    /**
     * able to carry bags
     */
    public boolean isBagger() {
        return (this.getTypeMoC() == 66) // mule
                || (this.getTypeMoC() == 65) // donkey
                || (this.getTypeMoC() == 67) // zonkey
                || (this.getTypeMoC() == 39) // pegasi
                || (this.getTypeMoC() == 40) // black pegasi
                || (this.getTypeMoC() == 25) // undead pegasi
                || (this.getTypeMoC() == 28) // skelly pegasi
                || (this.getTypeMoC() > 44 && this.getTypeMoC() < 60) // fairy
                ;
    }

    /**
     * Falls slowly
     */
    public boolean isFloater() {
        return this.getTypeMoC() == 36 // unicorn
                || this.getTypeMoC() == 27 // skelly unicorn
                || this.getTypeMoC() == 24 // undead unicorn
                || this.getTypeMoC() == 22; // not winged ghost

    }

    @Override
    public boolean isFlyer() {
        return this.getTypeMoC() == 39 // pegasus
                || this.getTypeMoC() == 40 // dark pegasus
                || (this.getTypeMoC() > 44 && this.getTypeMoC() < 60) //fairy
                || this.getTypeMoC() == 32 // bat horse
                || this.getTypeMoC() == 21 // ghost winged
                || this.getTypeMoC() == 25 // undead pegasus
                || this.getTypeMoC() == 28;// skelly pegasus
    }

    /**
     * Is this a ghost horse?
     */
    @Override
    public boolean getIsGhost() {
        return this.getTypeMoC() == 21 || this.getTypeMoC() == 22;
    }

    /**
     * Can wear magic armor
     */
    public boolean isMagicHorse() {
        return this.getTypeMoC() == 39
                || this.getTypeMoC() == 36
                || this.getTypeMoC() == 32
                || this.getTypeMoC() == 40
                || (this.getTypeMoC() > 44 && this.getTypeMoC() < 60) //fairy
                || this.getTypeMoC() == 21
                || this.getTypeMoC() == 22;
    }

    @Override
    public boolean isMovementCeased() {
        return this.getIsSitting() || this.isBeingRidden() || this.standCounter != 0 || this.shuffleCounter != 0 || this.getVanishC() != 0;
    }

    /**
     * Is this a Nightmare horse?
     */
    public boolean isNightmare() {
        return this.getTypeMoC() == 38;
    }

    /**
     * Rare horse that can be transformed into Nightmares or Bathorses or give
     * ghost horses on dead
     */
    public boolean isPureBreed() {
        return (this.getTypeMoC() > 10 && this.getTypeMoC() < 21);
    }

    /**
     * Mobs don't attack you if you're riding one of these they won't reproduce
     * either
     */
    public boolean isUndead() {
        return (this.getTypeMoC() > 22 && this.getTypeMoC() < 29);
    }

    /**
     * Has a unicorn? to render it and buckle entities!
     */
    public boolean isUnicorned() {
        return this.getTypeMoC() == 36 || (this.getTypeMoC() >= 45 && this.getTypeMoC() < 60) || this.getTypeMoC() == 27 || this.getTypeMoC() == 24;
    }

    public boolean isZebraRunning() {
        boolean flag = false;
        PlayerEntity ep1 = this.world.getClosestPlayer(this, 8D);
        if (ep1 != null) {
            flag = true;
            if (ep1.getRidingEntity() instanceof MoCEntityHorse) {
                MoCEntityHorse playerHorse = (MoCEntityHorse) ep1.getRidingEntity();
                if (playerHorse.getTypeMoC() == 16 || playerHorse.getTypeMoC() == 17 || playerHorse.getTypeMoC() == 60 || playerHorse.getTypeMoC() == 61) {
                    flag = false;
                }
            }
        }
        if (flag) {
            MoCTools.runLikeHell(this, ep1);
        }
        return flag;
    }

    public void LavaFX() {
        MoCreatures.proxy.LavaFX(this);
    }

    public void MaterializeFX() {
        MoCreatures.proxy.MaterializeFX(this);
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    public int nameYOffset() {
        if (this.getIsAdult()) return -80;
        else return (-5 - getAge());
    }

    private boolean nearMusicBox() {
        // only works server side
        if (this.world.isRemote || !MoCreatures.proxy.easterEggs) return false;
        boolean flag = false;
        JukeboxTileEntity jukebox = MoCTools.nearJukeBoxRecord(this, 6D);
        if (jukebox != null) {
            Item record = jukebox.getRecord().getItem();
            Item shuffleRecord = MoCItems.recordshuffle;
            if (record == shuffleRecord) {
                flag = true;
                if (this.shuffleCounter > 1000) {
                    this.shuffleCounter = 0;
                    MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 102));
                    flag = false;
                }
            }
        }
        return flag;
    }

    // changed to public since we need to send this info to server
    public void nightmareEffect() {
        if (!MoCTools.mobGriefing(this.world)) {
            setNightmareInt(getNightmareInt() - 1);
            return;
        }
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);
        BlockState blockstate = this.world.getBlockState(pos.add(-1, 0, -1));
        BlockEvent.BreakEvent event = null;
        if (!this.world.isRemote) {
            try {
                event = new BlockEvent.BreakEvent(this.world, pos, blockstate, FakePlayerFactory.get( this.getServer().getWorld(this.world.getDimensionKey()), MoCreatures.MOCFAKEPLAYER));
            } catch (Throwable ignored) {
            }
        }
        if (event != null && !event.isCanceled()) {
            this.world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 3);//MC1.5
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            if ((entityplayer != null) && (entityplayer.isBurning())) entityplayer.extinguish();
            setNightmareInt(getNightmareInt() - 1);
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!this.world.isRemote) {
            if ((this.rand.nextInt(10) == 0) && (this.getTypeMoC() == 23) || (this.getTypeMoC() == 24) || (this.getTypeMoC() == 25))
                MoCTools.spawnMaggots(this.world, this);

            if (getIsTamed() && (isMagicHorse() || isPureBreed()) && !getIsGhost() && this.rand.nextInt(4) == 0) {
                MoCEntityHorse entityhorse1 = MoCEntities.WILDHORSE.create(this.world);
                entityhorse1.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                this.world.addEntity(entityhorse1);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR.get());

                entityhorse1.setOwnerId(this.getOwnerId());
                entityhorse1.setTamed(true);
                PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                if (entityplayer != null) MoCTools.tameWithName(entityplayer, entityhorse1);

                entityhorse1.setAdult(false);
                entityhorse1.setAge(1);
                int l = 22;
                if (this.isFlyer()) l = 21;
                entityhorse1.setTypeMoC(l);
            }
        }
    }

    @Override
    public void livingTick() {
        /*
         * slow falling
         */
        if (!this.onGround && this.getMotion().getY() < 0.0D && (isFlyer() || isFloater()))
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));

        if (this.rand.nextInt(200) == 0) moveTail();

        if ((getTypeMoC() == 38) && (this.rand.nextInt(50) == 0) && this.world.isRemote) LavaFX();

        if ((getTypeMoC() == 36) && isOnAir() && this.world.isRemote) StarFX();

        if (!this.world.isRemote && isFlyer() && isOnAir()) {
            float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
            int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
            if (!this.isBeingRidden() || wingFlapFreq < 5) wingFlapFreq = 5;
            if (this.rand.nextInt(wingFlapFreq) == 0) wingFlap();
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) this.wingFlapCounter = 0;
            if (this.wingFlapCounter != 0 && this.wingFlapCounter % 5 == 0 && this.world.isRemote) StarFX();
            if (this.wingFlapCounter == 5 && !this.world.isRemote)
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP.get());
        }

        if (isUndead() && (this.getTypeMoC() < 26) && getIsAdult() && (this.rand.nextInt(20) == 0)) {
            if (!this.world.isRemote) {
                if (this.rand.nextInt(16) == 0) setAge(getAge() + 1);
                if (getAge() >= 399) setTypeMoC(this.getTypeMoC() + 3);
            } else UndeadFX();
        }

        super.livingTick();

        if (!this.world.isRemote) {
            /*
             * Shuffling LMFAO!
             */
            if (this.getTypeMoC() == 60 && getIsTamed() && this.rand.nextInt(50) == 0 && nearMusicBox() && shuffleCounter == 0) {
                shuffleCounter = 1;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 101));
            }

            if ((this.rand.nextInt(300) == 0) && (this.deathTime == 0)) {
                this.setHealth(getHealth() + 1);
                if (getHealth() > getMaxHealth()) this.setHealth(getMaxHealth());
            }

            if (!getIsSitting() && !getIsTamed() && this.rand.nextInt(300) == 0) setSitting(true);

            if (getIsSitting() && ++this.countEating > 50 && !getIsTamed()) {
                this.countEating = 0;
                setSitting(false);
            }

            if ((getTypeMoC() == 38) && (this.isBeingRidden()) && (getNightmareInt() > 0) && (this.rand.nextInt(2) == 0))
                nightmareEffect();

            /*
             * zebras on the run!
             */
            /*
            if (this.getTypeMoC() == 60 && !getIsTamed()) {
                boolean flag = isZebraRunning();
            }*/

            /*
             * foal following mommy!
             */
            /*if (!getIsAdult() && (this.rand.nextInt(200) == 0)) {
                setAge(getAge() + 1);
                if (getAge() >= 100) {
                    setAdult(true);
                    setBred(false);
                    MoCEntityHorse mommy = getClosestMommy(this, 16D);
                    if (mommy != null) {
                        mommy.setBred(false);
                    }
                }
            }*/

            /*
             * Buckling
             */
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && isUnicorned() && this.isBeingRidden()) {
                MoCTools.buckleMobs(this, 2D, this.world);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_HORSE_MAD.get());
            }

            if (isFlyer() && !getIsTamed() && this.rand.nextInt(100) == 0 && !isMovementCeased() && !getIsSitting())
                wingFlap();

            if (!readyForParenting(this)) return;

            int i = 0;

            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(8D, 3D, 8D));
            for (Entity entity : list) {
                if (entity instanceof MoCEntityHorse || entity instanceof HorseEntity) i++;
            }

            if (i > 1) return;

            List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4D, 2D, 4D));
            for (Entity horsemate : list1) {
                boolean flag = (horsemate instanceof HorseEntity);
                if (!(horsemate instanceof MoCEntityHorse || flag) || (horsemate == this)) continue;

                if (!flag && !readyForParenting((MoCEntityHorse) horsemate)) return;

                //if (this.rand.nextInt(100) == 0) this.gestationTime++;
                this.gestationTime++; // Always increment, instead of taking a 1% chance

                if (this.gestationTime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageHeart(this.getEntityId()));
                }

                //if (this.gestationTime <= 50) continue;
                if (this.gestationTime <= 300) continue; // Instead of RNG delay, it takes ~15 seconds to breed.

                MoCEntityHorse baby = MoCEntities.WILDHORSE.create(this.world);
                baby.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                this.world.addEntity(baby);
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                this.eatenPumpkin = false;
                this.gestationTime = 0;
                //this.setBred(true);

                int horsemateType;// = 0;
                if (flag) {
                    horsemateType = translateVanillaHorseType((HorseEntity) horsemate);
                    if (horsemateType == -1) return;
                } else {
                    horsemateType = ((MoCEntityHorse) horsemate).getTypeMoC();
                    ((MoCEntityHorse) horsemate).eatenPumpkin = false;
                    ((MoCEntityHorse) horsemate).gestationTime = 0;
                }
                int l = horseGenetics(this.getTypeMoC(), horsemateType);

                if (l == 50 || l == 54) // fairy horse!
                {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR.get());
                    if (!flag) ((MoCEntityHorse) horsemate).dissapearHorse();
                    this.dissapearHorse();
                }
                baby.setOwnerId(this.getOwnerId());
                baby.setTamed(true);
                //baby.setBred(true);
                baby.setAdult(false);
                UUID ownerId = this.getOwnerId();
                PlayerEntity entityplayer = null;
                if (ownerId != null) entityplayer = this.world.getPlayerByUuid(this.getOwnerId());
                if (entityplayer != null) MoCTools.tameWithName(entityplayer, baby);
                baby.setTypeMoC(l);
                break;
            }
        }
    }

    /**
     * Obtains the 'Type' of vanilla horse for inbreeding with MoC Horses
     */
    private int translateVanillaHorseType(AbstractHorseEntity horse) {
        if (horse instanceof DonkeyEntity) return 65; // donkey
        if (horse instanceof HorseEntity) {
            switch (((HorseEntity) horse).func_234239_eK_().getId()) {
                case 0: //white
                    return 1;
                case 1: //creamy
                    return 2;
                case 3: //brown
                    return 3;
                case 4: //black
                    return 5;
                case 5: //gray
                    return 9;
                case 6: //dark brown
                    return 4;
                default:
                    return 3;
            }
        }
        return -1;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.shuffleCounter > 0) {
            ++this.shuffleCounter;
            if (this.world.isRemote && this.shuffleCounter % 20 == 0) {
                double var2 = this.rand.nextGaussian() * 0.5D;
                double var4 = this.rand.nextGaussian() * -0.1D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.NOTE, this.getPosX() + this.rand.nextFloat() * this.getWidth() * 2.0F - this.getWidth(), this.getPosY()
                        + 0.5D + this.rand.nextFloat() * this.getHeight(), this.getPosZ() + this.rand.nextFloat() * this.getWidth() * 2.0F - this.getWidth(), var2, var4, var6);
            }

            if (!this.world.isRemote && !nearMusicBox()) {
                this.shuffleCounter = 0;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 102));
            }
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) this.mouthCounter = 0;

        if (this.standCounter > 0 && ++this.standCounter > 20) this.standCounter = 0;

        if (this.tailCounter > 0 && ++this.tailCounter > 8) this.tailCounter = 0;

        if (getVanishC() > 0) {

            setVanishC((byte) (getVanishC() + 1));

            if (getVanishC() < 15 && this.world.isRemote) VanishFX();

            if (getVanishC() > 100) {
                setVanishC((byte) 101);
                MoCTools.dropHorseAmulet(this);
                dissapearHorse();
            }

            if (getVanishC() == 1) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_VANISH.get());

            if (getVanishC() == 70) stand();
        }

        if (this.sprintCounter > 0) {
            ++this.sprintCounter;
            if (this.sprintCounter < 150 && this.sprintCounter % 2 == 0 && this.world.isRemote) StarFX();
            if (this.sprintCounter > 300) this.sprintCounter = 0;
        }

        /*if (this.wingFlapCounter > 0) {
            ++this.wingFlapCounter;
            if (this.wingFlapCounter % 5 == 0 && this.world.isRemote) {
                StarFX();
            }
            if (this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;

            }
        }*/

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM.get());

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    dropArmor();
                    setTypeMoC(this.transformType);
                }
            }
        }

        if (getIsGhost() && getAge() < 10 && this.rand.nextInt(7) == 0) setAge(getAge() + 1);

        if (getIsGhost() && getAge() == 9) {
            setAge(100);
            setAdult(true);
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public boolean readyForParenting(MoCEntityHorse entityhorse) {
        int i = entityhorse.getTypeMoC();
        return (!entityhorse.isBeingRidden()) && (entityhorse.getRidingEntity() == null) && entityhorse.getIsTamed() && entityhorse.eatenPumpkin
                && entityhorse.getIsAdult() && !entityhorse.isUndead() && !entityhorse.getIsGhost() && (i != 61) && (i < 66);
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    /**
     * Horse Types
     * <p>
     * 1 White . 2 Creamy. 3 Brown. 4 Dark Brown. 5 Black.
     * <p>
     * 6 Bright Creamy. 7 Speckled. 8 Pale Brown. 9 Grey. 10 11 Pinto . 12
     * Bright Pinto . 13 Pale Speckles.
     * <p>
     * 16 Spotted 17 Cow.
     * <p>
     * <p>
     * <p>
     * <p>
     * 21 Ghost (winged) 22 Ghost B
     * <p>
     * 23 Undead 24 Undead Unicorn 25 Undead Pegasus
     * <p>
     * 26 skeleton 27 skeleton unicorn 28 skeleton pegasus
     * <p>
     * 30 bug horse
     * <p>
     * 32 Bat Horse
     * <p>
     * 36 Unicorn
     * <p>
     * 38 Nightmare? 39 White Pegasus 40 Black Pegasus
     * <p>
     * 50 fairy white 51 fairy blue 52 fairy pink 53 fairy light green
     * <p>
     * 60 Zebra 61 Zorse
     * <p>
     * 65 Donkey 66 Mule 67 Zonky
     */

    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getTypeMoC() == 0) {
            if (this.rand.nextInt(5) == 0) setAdult(false);
            int j = this.rand.nextInt(100);
            if (j <= (33)) setTypeMoC(6);
            else if (j <= (66)) setTypeMoC(7);
            else if (j <= (99)) setTypeMoC(8);
            else setTypeMoC(60);// zebra
        }
    }

    public void setReproduced(boolean var1) {
        this.hasReproduced = var1;
    }

    private void stand() {
        if (!this.isBeingRidden() && !this.isOnAir()) this.standCounter = 1;
    }

    public void StarFX() {
        MoCreatures.proxy.StarFX(this);
    }

    /**
     * Used to flicker ghosts
     */
    public float tFloat() {
        if (++this.fCounter > 60) {
            this.fCounter = 0;
            this.transFloat = (this.rand.nextFloat() * (0.6F - 0.3F) + 0.3F);
        }

        if (getIsGhost() && getAge() < 10) this.transFloat = 0;

        return this.transFloat;
    }

    public void transform(int tType) {
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () ->  new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), tType));
        }

        this.transformType = tType;
        if (!this.isBeingRidden() && this.transformType != 0) {
            dropArmor();
            this.transformCounter = 1;
        }
    }

    public void UndeadFX() {
        MoCreatures.proxy.UndeadFX(this);
    }

    public void VanishFX() {
        MoCreatures.proxy.VanishFX(this);
    }

    /**
     * Called to vanish Horse
     */

    public void vanishHorse() {
        this.getNavigator().clearPath();
        this.setMotion(0.0D, this.getMotion().getY(), 0.0D);

        if (this.isBagger()) {
            MoCTools.dropInventory(this, this.localChest);
            dropBags();
        }
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageVanish(this.getEntityId()));
            setVanishC((byte) 1);
        }
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_VANISH.get());
    }

    @Override
    public void dropMyStuff() {
        dropArmor();
        MoCTools.dropSaddle(this, this.world);
        if (this.isBagger()) {
            MoCTools.dropInventory(this, this.localChest);
            dropBags();
        }
    }

    public void wingFlap() {
        if (this.isFlyer() && this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            if (!this.world.isRemote) {
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey())), new MoCMessageAnimation(this.getEntityId(), 3));
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("EatingHaystack", getIsSitting());
        nbttagcompound.putBoolean("ChestedHorse", getIsChested());
        nbttagcompound.putBoolean("HasReproduced", getHasReproduced());
        nbttagcompound.putBoolean("Bred", getHasBred());
        nbttagcompound.putInt("ArmorType", getArmorType());

        if (getIsChested() && this.localChest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localChest.getSizeInventory(); i++) {
                // grab the current item stack
                this.localStack = this.localChest.getStackInSlot(i);
                if (!this.localStack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localStack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setSitting(nbttagcompound.getBoolean("EatingHaystack"));
        setBred(nbttagcompound.getBoolean("Bred"));
        setIsChested(nbttagcompound.getBoolean("ChestedHorse"));
        setReproduced(nbttagcompound.getBoolean("HasReproduced"));
        setArmorType((byte) nbttagcompound.getInt("ArmorType"));
        if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localChest = new MoCAnimalChest("HorseChest", getInventorySize());
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localChest.getSizeInventory()) {
                    this.localChest.setInventorySlotContents(j, ItemStack.read(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        //23,24,25,32,36,38,39,40,51,52,53
        if (animationType >= 23 && animationType < 60) //transform
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }
        if (animationType == 3) //wing flap 
        {
            this.wingFlapCounter = 1;
        }
        if (animationType == 101) //zebra Shuffle starts
        {
            this.shuffleCounter = 1;
        }
        if (animationType == 102) //zebra Shuffle ends
        {
            this.shuffleCounter = 0;
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        if (isUndead()) return CreatureAttribute.UNDEAD;
        return super.getCreatureAttribute();
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return getIsTamed() && !isAmuletHorse();
    }

    @Override
    public void setTypeMoC(int i) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.isImmuneToFire = i == 38 || i == 40;
        super.setTypeMoC(i);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = getSizeFactor() * (0.25D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    // Adjusted to avoid most of the roof suffocation for now
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.9F;
    }

    @Override
    public boolean isImmuneToFire() {
        return this.isImmuneToFire ? true : super.isImmuneToFire();
    }
}
