/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MoCItemHorseAmulet extends MoCItem {

    private int ageCounter;
    private String name;
    private float health;
    private int age;
    private int creatureType;
    private String spawnClass;
    private boolean isGhost;
    private boolean rideable;
    private byte armor;
    private boolean adult;
    private UUID ownerUniqueId;
    private String ownerName;
    private int PetId;

    public MoCItemHorseAmulet(Item.Properties properties, String name) {
        super(properties.maxStackSize(1), name);
        this.ageCounter = 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (++this.ageCounter < 2) {
            return new ActionResult<>(ActionResultType.PASS, stack);
        }

        if (!worldIn.isRemote) {
            initAndReadNBT(stack);
        }

        double dist = 3D;
        double newPosY = player.getPosY();
        double newPosX = player.getPosX() - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = player.getPosZ() - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));

        if (!player.world.isRemote) {
            try {
                MoCEntityTameableAnimal storedCreature;
                this.spawnClass = this.spawnClass.replace(MoCConstants.MOD_PREFIX, "").toLowerCase();
                if (this.spawnClass.equalsIgnoreCase("Wyvern")) { //ghost wyvern
                    storedCreature = MoCEntities.WYVERN.create(worldIn);
                    ((MoCEntityWyvern) storedCreature).setIsGhost(true);
                    this.isGhost = true;
                } else if (this.spawnClass.equalsIgnoreCase("WildHorse")) {
                    storedCreature = MoCEntities.WILDHORSE.create(worldIn);
                } else {
                    storedCreature = (MoCEntityTameableAnimal) EntityType.byKey(new ResourceLocation(MoCConstants.MOD_PREFIX + this.spawnClass.toLowerCase()).toString()).get().create(worldIn);
                    if (storedCreature instanceof MoCEntityBigCat) {
                        this.isGhost = true;
                        ((MoCEntityBigCat) storedCreature).setIsGhost(true);
                    }
                }

                storedCreature.setPosition(newPosX, newPosY, newPosZ);
                storedCreature.setTypeMoC(this.creatureType);
                storedCreature.setTamed(true);
                storedCreature.setRideable(this.rideable);
                storedCreature.setAge(this.age);
                storedCreature.setPetName(this.name);
                storedCreature.setHealth(this.health);
                storedCreature.setAdult(this.adult);
                storedCreature.setArmorType(this.armor);
                storedCreature.setOwnerPetId(this.PetId);
                storedCreature.setOwnerId(player.getUniqueID());
                this.ownerName = player.getName().getString();

                if (this.ownerUniqueId == null) {
                    this.ownerUniqueId = player.getUniqueID();
                    if (MoCreatures.instance.mapData != null) {
                        final MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
                        int maxCount = MoCreatures.proxy.maxTamed;
                        if (MoCTools.isThisPlayerAnOP(player)) {
                            maxCount = MoCreatures.proxy.maxOPTamed;
                        }
                        if (newOwner == null) {
                            if (maxCount > 0 || !MoCreatures.proxy.enableOwnership) {
                                // create new PetData for new owner
                                MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                            }
                        } else // add pet to existing pet data
                        {
                            if (newOwner.getTamedList().size() < maxCount || !MoCreatures.proxy.enableOwnership) {
                                MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                            }
                        }
                    }
                } else {
                    //if the player using the amulet is different from the original owner
                    if (!(this.ownerUniqueId.equals(player.getUniqueID())) && MoCreatures.instance.mapData != null) {
                        final MoCPetData oldOwner = MoCreatures.instance.mapData.getPetData(this.ownerUniqueId);
                        final MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
                        int maxCount = MoCreatures.proxy.maxTamed;
                        if (MoCTools.isThisPlayerAnOP(player)) {
                            maxCount = MoCreatures.proxy.maxOPTamed;
                        }
                        if (newOwner == null) {
                            if (maxCount > 0 || !MoCreatures.proxy.enableOwnership) {
                                // create new PetData for new owner
                                MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                            }
                        } else // add pet to existing pet data
                        {
                            if (newOwner.getTamedList().size() < maxCount || !MoCreatures.proxy.enableOwnership) {
                                MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                            }
                        }
                        // remove pet entry from old owner
                        if (oldOwner != null) {
                            for (int j = 0; j < oldOwner.getTamedList().size(); j++) {
                                CompoundNBT petEntry = oldOwner.getTamedList().getCompound(j);
                                if (petEntry.getInt("PetId") == this.PetId) {
                                    // found match, remove
                                    oldOwner.getTamedList().remove(j);
                                }
                            }
                        }
                    }
                }

                if (player.world.addEntity(storedCreature)) {
                    MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(player.getPosX(), player.getPosY(), player.getPosZ(), 64, player.world.getDimensionKey())), new MoCMessageAppear(storedCreature.getEntityId()));
                    MoCTools.playCustomSound(storedCreature, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR.get());
                    //gives an empty amulet
                    if (storedCreature instanceof MoCEntityBigCat || storedCreature instanceof MoCEntityWyvern || this.creatureType == 21 || this.creatureType == 22) {
                        player.setHeldItem(hand, new ItemStack(MoCItems.amuletghost, 1));
                    } else if (this.creatureType == 26 || this.creatureType == 27 || this.creatureType == 28) {
                        player.setHeldItem(hand, new ItemStack(MoCItems.amuletbone, 1));
                    } else if ((this.creatureType > 47 && this.creatureType < 60)) {
                        player.setHeldItem(hand, new ItemStack(MoCItems.amuletfairy, 1));
                    } else if (this.creatureType == 39 || this.creatureType == 40) {
                        player.setHeldItem(hand, new ItemStack(MoCItems.amuletpegasus, 1));
                    }
                    MoCPetData petData = MoCreatures.instance.mapData.getPetData(storedCreature.getOwnerId());
                    if (petData != null) {
                        petData.setInAmulet(storedCreature.getOwnerPetId(), false);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Unable to find class for entity " + this.spawnClass);
                ex.printStackTrace();
            }
        }
        this.ageCounter = 0;

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    public void readFromNBT(CompoundNBT nbt) {
        this.PetId = nbt.getInt("PetId");
        this.creatureType = nbt.getInt("CreatureType");
        this.health = nbt.getFloat("Health");
        this.age = nbt.getInt("Edad");
        this.name = nbt.getString("Name");
        int spawnClassOld = nbt.getInt("SpawnClass");
        if (spawnClassOld > 0) {
            if (spawnClassOld == 100) {
                this.spawnClass = "Wyvern";
                this.isGhost = true;
            } else {
                this.spawnClass = "WildHorse";
            }
            nbt.remove("SpawnClass");
        } else {
            this.spawnClass = nbt.getString("SpawnClass");
        }
        this.rideable = nbt.getBoolean("Rideable");
        this.armor = nbt.getByte("Armor");
        this.adult = nbt.getBoolean("Adult");
        this.ownerName = nbt.getString("OwnerName");
        if (nbt.hasUniqueId("OwnerUUID")) {
            this.ownerUniqueId = nbt.getUniqueId("OwnerUUID");
        }
    }

    public void writeToNBT(CompoundNBT nbt) {
        nbt.putInt("PetId", this.PetId);
        nbt.putInt("CreatureType", this.creatureType);
        nbt.putFloat("Health", this.health);
        nbt.putInt("Edad", this.age);
        nbt.putString("Name", this.name);
        nbt.putString("SpawnClass", this.spawnClass);
        nbt.putBoolean("Rideable", this.rideable);
        nbt.putByte("Armor", this.armor);
        nbt.putBoolean("Adult", this.adult);
        nbt.putString("OwnerName", this.ownerName);
        if (this.ownerUniqueId != null) {
            nbt.putUniqueId("OwnerUUID", ownerUniqueId);
        }
    }

    @OnlyIn(Dist.CLIENT)
    /*
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        initAndReadNBT(stack);
        tooltip.add(new StringTextComponent(this.spawnClass).setStyle(Style.EMPTY.setFormatting(TextFormatting.AQUA)));
        if (!this.name.equals("")) {
            tooltip.add(new StringTextComponent(this.name).setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
        }
        if (!this.ownerName.equals("")) {
            tooltip.add(new StringTextComponent("Owned by " + this.ownerName).setStyle(Style.EMPTY.setFormatting(TextFormatting.DARK_BLUE)));
        }
    }

    private void initAndReadNBT(ItemStack itemstack) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundNBT());
        }
        CompoundNBT nbtcompound = itemstack.getTag();
        readFromNBT(nbtcompound);
    }
}
