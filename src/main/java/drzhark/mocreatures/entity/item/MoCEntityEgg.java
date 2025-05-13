/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.hunter.MoCEntityKomodo;
import drzhark.mocreatures.entity.hunter.MoCEntityManticorePet;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MoCEntityEgg extends MobEntity {

    public int eggType;
    private int tCounter;
    private int lCounter;

    public MoCEntityEgg(EntityType<? extends MoCEntityEgg> type, World world) {
        super(type, world);
        this.tCounter = 0;
        this.lCounter = 0;
    }


    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("egg.png");
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D); // setMaxHealth
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityplayer) {
        int i = this.eggType;
        if (i == 30) {
            i = 31;
        }
        if ((this.lCounter > 10) && entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCItems.mocegg, 1))) {
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!this.world.isRemote) {
                entityplayer.onItemPickup(this, 1);

            }
            remove();
        }
    }

    @Override
    public void livingTick() {
        this.moveStrafing = 0.0F;
        this.moveForward = 0.0F;
        travel(new Vector3d(this.moveStrafing, this.moveVertical, this.moveForward));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (this.rand.nextInt(20) == 0) {
                this.lCounter++;
            }

            if (this.lCounter > 500) {
                PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 24D);
                if (entityplayer1 == null) {
                    this.remove();
                }
            }

            if (isInWater() && (getEggType() < 12 || getEggType() > 69) && (this.rand.nextInt(20) == 0)) {
                this.tCounter++;
                if (this.tCounter % 5 == 0) {
                    this.setMotion(this.getMotion().add(0.0D, 0.2D, 0.0D));
                }

                if (this.tCounter == 5) {
                    notifyEggHatching();
                }

                if (this.tCounter >= 30) {
                    if (getEggType() <= 10) // fishy
                    {
                        MoCEntityFishy entityspawn = MoCEntities.FISHY.create(this.world);
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setTypeMoC(getEggType());
                        entityspawn.setAge(30);
                        this.world.addEntity(entityspawn);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    } else if (getEggType() == 11) // shark
                    {
                        MoCEntityShark entityspawn = MoCEntities.SHARK.create(this.world);
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setAge(30);
                        this.world.addEntity(entityspawn);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    } else if (getEggType() == 90) // piranha
                    {
                        MoCEntityPiranha entityspawn = MoCEntities.PIRANHA.create(this.world);
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        this.world.addEntity(entityspawn);
                        entityspawn.setAge(30);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    } else if (getEggType() >= 80 && getEggType() < (80 + MoCEntitySmallFish.fishNames.length)) // smallfish
                    {
                        final int type = getEggType() - 79;
                        MoCEntitySmallFish entityspawn = MoCEntitySmallFish.createEntity(this.world, type);
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        this.world.addEntity(entityspawn);
                        entityspawn.setAge(30);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    } else if (getEggType() >= 70 && getEggType() < (70 + MoCEntityMediumFish.fishNames.length)) // mediumfish
                    {
                        final int type = getEggType() - 69;
                        MoCEntityMediumFish entityspawn = MoCEntityMediumFish.createEntity(this.world, type);
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        this.world.addEntity(entityspawn);
                        entityspawn.setAge(30);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    remove();
                }
            } else if (!isInWater() && getEggType() > 20 && (this.rand.nextInt(20) == 0)) // non aquatic creatures
            {
                this.tCounter++;
                //if (getEggType() == 30) tCounter = 0; //with this, wild ostriches won't spawn eggs.

                if (this.tCounter % 5 == 0) {
                    this.setMotion(this.getMotion().add(0.0D, 0.2D, 0.0D));
                }

                if (this.tCounter == 5) {
                    notifyEggHatching();
                }

                if (this.tCounter >= 30) {
                    if (getEggType() > 20 && getEggType() < 29) // snakes
                    {
                        MoCEntitySnake entityspawn = MoCEntities.SNAKE.create(this.world);

                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setTypeMoC(getEggType() - 20);
                        entityspawn.setAge(50);
                        this.world.addEntity(entityspawn);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) // Ostriches. 30 = wild egg, 31 = stolen egg
                    {
                        MoCEntityOstrich entityspawn = MoCEntities.OSTRICH.create(this.world);
                        int typeInt = 1;
                        if (this.world.getDimensionType().isUltrawarm() || getEggType() == 32) {
                            typeInt = 5;
                        }
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setTypeMoC(typeInt);
                        entityspawn.setAge(35);
                        this.world.addEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());

                        if (getEggType() == 31)//stolen egg that hatches a tamed ostrich
                        {
                            PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                            if (entityplayer != null) {
                                MoCTools.tameWithName(entityplayer, entityspawn);
                            }
                        }
                    }

                    if (getEggType() == 33) // Komodo
                    {
                        MoCEntityKomodo entityspawn = MoCEntities.KOMODO_DRAGON.create(this.world);

                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setAge(30);
                        this.world.addEntity(entityspawn);
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 40 && getEggType() < 46) //scorpions for now it uses 41 - 45
                    {
                        MoCEntityPetScorpion entityspawn = MoCEntities.PET_SCORPION.create(this.world);
                        int typeInt = getEggType() - 40;
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setTypeMoC(typeInt);
                        entityspawn.setAdult(false);
                        this.world.addEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 49 && getEggType() < 62) //wyverns for now it uses 50 - 61
                    {
                        MoCEntityWyvern entityspawn = MoCEntities.WYVERN.create(this.world);
                        int typeInt = getEggType() - 49;
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setTypeMoC(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setAge(30);
                        this.world.addEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    if (getEggType() > 61 && getEggType() < 67) //manticorePets for now it uses 62 - 66
                    {
                        MoCEntityManticorePet entityspawn = MoCEntities.MANTICORE_PET.create(this.world);
                        int typeInt = getEggType() - 61;
                        entityspawn.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                        entityspawn.setTypeMoC(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setAge(30);
                        this.world.addEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    remove();
                }
            }
        }
    }

    private void notifyEggHatching() {
        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
        if (entityplayer != null) {
            entityplayer.sendMessage(new TranslationTextComponent("msg.mocreatures.egg", (int) this.getPosX(), (int) this.getPosY(), (int) this.getPosZ()), entityplayer.getUniqueID());
        }
    }

    public int getSize() {
        if (getEggType() == 30 || getEggType() == 31) {
            return 170;
        }
        return 100;
    }

    public int getEggType() {
        return this.eggType;
    }

    public void setEggType(int eggType) {
        this.eggType = eggType;
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        setEggType(nbttagcompound.getInt("EggType"));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putInt("EggType", getEggType());
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }
}
