/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.command;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandMoCTP {


    static {
        //commands.add("/moctp <entityid> <playername>");
        //commands.add("/moctp <petname> <playername>");
        //aliases.add("moctp");
        //tabCompletionStrings.add("moctp");
    }

    public int execute(CommandSource source) {
        return 0;
        /*int petId;
        if (args.length == 0) {
            sender.sendMessage(new TranslationTextComponent(TextFormatting.RED + "Error" + TextFormatting.WHITE
                    + ": You must enter a valid entity ID."));
            return;
        }
        if (!(sender instanceof PlayerEntity)) {
            return;
        }
        try {
            petId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            petId = -1;
        }
        String playername = sender.getName();
        PlayerEntity player = (PlayerEntity) sender;//FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playername);
        // search for tamed entity in mocreatures.dat
        MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
        if (ownerPetData != null) {
            for (int i = 0; i < ownerPetData.getTamedList().size(); i++) {
                CompoundNBT nbt = ownerPetData.getTamedList().getCompound(i);
                if (nbt.contains("PetId") && nbt.getInt("PetId") == petId) {
                    String petName = nbt.getString("Name");
                    ServerWorld world = DimensionManager.getWorld(nbt.getInt("Dimension"));
                    if (!teleportLoadedPet(world, player, petId, petName, sender)) {
                        double getPosX () = nbt.getList("Pos", 6).getDouble(0);
                        double getPosY () = nbt.getList("Pos", 6).getDouble(1);
                        double getPosZ () = nbt.getList("Pos", 6).getDouble(2);
                        int x = MathHelper.floor(getPosX());
                        int y = MathHelper.floor(getPosY());
                        int z = MathHelper.floor(getPosZ());
                        sender.sendMessage(new TranslationTextComponent("Found unloaded pet " + TextFormatting.GREEN
                                + nbt.getString("id") + TextFormatting.WHITE + " with name " + TextFormatting.AQUA + nbt.getString("Name")
                                + TextFormatting.WHITE + " at location " + TextFormatting.LIGHT_PURPLE + x + TextFormatting.WHITE + ", "
                                + TextFormatting.LIGHT_PURPLE + y + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + z
                                + TextFormatting.WHITE + " with Pet ID " + TextFormatting.BLUE + nbt.getInt("PetId")));
                        boolean result = teleportLoadedPet(world, player, petId, petName, sender); // attempt to TP again
                        if (!result) {
                            sender.sendMessage(new TranslationTextComponent("Unable to transfer entity ID " + TextFormatting.GREEN
                                    + petId + TextFormatting.WHITE + ". It may only be transferred to " + TextFormatting.AQUA
                                    + player.getName()));
                        }
                    }
                    break;
                }
            }
        } else {
            sender.sendMessage(new TranslationTextComponent("Tamed entity could not be located."));
        }*/
    }

/*
    public boolean teleportLoadedPet(ServerWorld world, PlayerEntity player, int petId, String petName, ICommandSender par1ICommandSender) {
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = world.loadedEntityList.get(j);
            // search for entities that are MoCEntityAnimal's
            if (IMoCTameable.class.isAssignableFrom(entity.getClass()) && !((IMoCTameable) entity).getPetName().equals("")
                    && ((IMoCTameable) entity).getOwnerPetId() == petId) {
                // grab the entity data
                CompoundNBT compound = new CompoundNBT();
                entity.writeToNBT(compound);
                if (!compound.isEmpty() && !compound.getString("Owner").isEmpty()) {
                    String owner = compound.getString("Owner");
                    String name = compound.getString("Name");
                    if (!owner.isEmpty() && owner.equalsIgnoreCase(player.getName())) {
                        // check if in same dimension
                        if (entity.dimension == player.dimension) {
                            entity.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                        } else if (!player.world.isRemote)// transfer entity to player dimension
                        {
                            Entity newEntity = EntityList.newEntity(entity.getClass(), player.world);
                            if (newEntity != null) {
                                MoCTools.copyDataFromOld(newEntity, entity); // transfer all existing data to our new entity
                                newEntity.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                                DimensionManager.getWorld(player.dimension).addEntity(newEntity);
                            }
                            if (entity.getRidingEntity() != null) {
                                entity.getRidingEntity().dismountRidingEntity();
                            }
                            entity.removed = true;
                            world.resetUpdateEntityTick();
                            DimensionManager.getWorld(player.dimension).resetUpdateEntityTick();
                        }
                        par1ICommandSender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + name + TextFormatting.WHITE
                                + " has been tp'd to location " + Math.round(player.getPosX()) + ", " + Math.round(player.getPosY()) + ", "
                                + Math.round(player.getPosZ()) + " in dimension " + player.dimension));
                        return true;
                    }
                }
            }
        }
        return false;
    }*/
}
