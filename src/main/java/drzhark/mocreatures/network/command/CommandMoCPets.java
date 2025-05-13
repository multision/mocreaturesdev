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
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CommandMoCPets {

    private static final List<String> commands = new ArrayList<>();
    private static final List<String> aliases = new ArrayList<>();

    static {
        commands.add("/mocpets");
    }


    private static int execute(CommandSource source) {
        /* int unloadedCount = 0;
        int loadedCount = 0;
        ArrayList<Integer> foundIds = new ArrayList<>();
        ArrayList<String> tamedlist = new ArrayList<>();
        if (!(sender instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) sender;
        UUID playerId = player.getUniqueID();
        // search for tamed entity
        for (int dimension : DimensionManager.getIDs()) {
            ServerWorld world = DimensionManager.getWorld(dimension);
            for (int j = 0; j < world.loadedEntityList.size(); j++) {
                Entity entity = world.loadedEntityList.get(j);
                if (IMoCTameable.class.isAssignableFrom(entity.getClass())) {
                    IMoCTameable mocreature = (IMoCTameable) entity;
                    if (mocreature.getOwnerId() != null && mocreature.getOwnerId().equals(playerId)) {
                        loadedCount++;
                        foundIds.add(mocreature.getOwnerPetId());
                        tamedlist.add(TextFormatting.WHITE + "Found pet with " + TextFormatting.DARK_AQUA + "Type" + TextFormatting.WHITE
                                + ":" + TextFormatting.GREEN + ((MobEntity) mocreature).getName() + TextFormatting.DARK_AQUA
                                + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN + mocreature.getPetName()
                                + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                + mocreature.getOwnerId() + TextFormatting.DARK_AQUA + ", PetId" + TextFormatting.WHITE + ":"
                                + TextFormatting.GREEN + mocreature.getOwnerPetId() + TextFormatting.DARK_AQUA + ", Dimension"
                                + TextFormatting.WHITE + ":" + TextFormatting.GREEN + entity.dimension + TextFormatting.DARK_AQUA
                                + ", Pos" + TextFormatting.WHITE + ":" + TextFormatting.LIGHT_PURPLE + Math.round(entity.getPosX())
                                + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.getPosY())
                                + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.getPosZ()));
                    }
                }
            }
        }
        MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
        if (ownerPetData != null) {
            MoCreatures.instance.mapData.forceSave(); // force save so we get correct information
            for (int i = 0; i < ownerPetData.getTamedList().size(); i++) {
                CompoundNBT nbt = ownerPetData.getTamedList().getCompound(i);
                if (nbt.contains("PetId") && !foundIds.contains(nbt.getInt("PetId"))) {
                    unloadedCount++;
                    double getPosX () = nbt.getList("Pos", 6).getDouble(0);
                    double getPosY () = nbt.getList("Pos", 6).getDouble(1);
                    double posZ  = nbt.getList("Pos", 6).getDouble(2);
                    if (nbt.getBoolean("InAmulet")) {
                        tamedlist.add(TextFormatting.WHITE + "Found unloaded pet in " + TextFormatting.DARK_PURPLE + "AMULET"
                                + TextFormatting.WHITE + " with " + TextFormatting.DARK_AQUA + "Type" + TextFormatting.WHITE + ":"
                                + TextFormatting.GREEN + (nbt.getString("id")).replace("MoCreatures.", "") + TextFormatting.DARK_AQUA
                                + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getString("Name")
                                + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                + nbt.getString("Owner") + TextFormatting.DARK_AQUA + ", PetId" + TextFormatting.WHITE + ":"
                                + TextFormatting.GREEN + nbt.getInt("PetId") + TextFormatting.WHITE + ".");
                    } else {
                        tamedlist.add(TextFormatting.WHITE + "Found unloaded pet with " + TextFormatting.DARK_AQUA + "Type"
                                + TextFormatting.WHITE + ":" + TextFormatting.GREEN + (nbt.getString("id")).replace("MoCreatures.", "")
                                + TextFormatting.DARK_AQUA + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                + nbt.getString("Name") + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":"
                                + TextFormatting.GREEN + nbt.getString("Owner") + TextFormatting.DARK_AQUA + ", PetId"
                                + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getInt("PetId") + TextFormatting.DARK_AQUA
                                + ", Dimension" + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getInt("Dimension")
                                + TextFormatting.DARK_AQUA + ", Pos" + TextFormatting.WHITE + ":" + TextFormatting.LIGHT_PURPLE
                                + Math.round(getPosX()) + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(getPosY())
                                + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(posZ));
                    }
                }
            }
        }

        if (tamedlist.size() > 0) {
            sendPageHelp(sender, (byte) 10, tamedlist, args);
            sender.sendMessage(new TranslationTextComponent("Loaded tamed count : " + TextFormatting.AQUA + loadedCount
                    + TextFormatting.WHITE + ", Unloaded count : " + TextFormatting.AQUA + unloadedCount + TextFormatting.WHITE
                    + ", Total count : " + TextFormatting.AQUA + (ownerPetData != null ? ownerPetData.getTamedList().size() : 0)));
        } else {
            sender.sendMessage(new TranslationTextComponent("Loaded tamed count : "
                    + TextFormatting.AQUA
                    + loadedCount
                    + TextFormatting.WHITE
                    + (!MoCreatures.isServer() ? ", Unloaded Count : " + TextFormatting.AQUA + unloadedCount + TextFormatting.WHITE
                    + ", Total count : " + TextFormatting.AQUA + (loadedCount + unloadedCount) : "")));
        } */
        return 0; //TODO TheidenHD
    }

/*
    public boolean teleportLoadedPet(ServerWorld world, ServerPlayerEntity player, int petId, String petName, ICommandSender par1ICommandSender) {
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
    }

    public void sendCommandHelp(ICommandSender sender) {
        sender.sendMessage(new TranslationTextComponent("§2Listing MoCreatures commands"));
        for (String command : commands) {
            sender.sendMessage(new TranslationTextComponent(command));
        }
    }

    public void sendPageHelp(ICommandSender sender, byte pagelimit, ArrayList<String> list, String[] par2ArrayOfStr) {
        int x = (list.size() - 1) / pagelimit;
        int j = 0;

        if (par2ArrayOfStr.length > 0 && Character.isDigit(par2ArrayOfStr[0].charAt(0))) {
            try {
                j = parseInt(par2ArrayOfStr[0], 1, x + 1) - 1;
            } catch (NumberInvalidException numberinvalidexception) {
                numberinvalidexception.printStackTrace();
            }
        }
        int k = Math.min((j + 1) * pagelimit, list.size());

        sender.sendMessage(new TranslationTextComponent(TextFormatting.DARK_GREEN + "--- Showing MoCreatures Help Info "
                + TextFormatting.AQUA + (j + 1) + TextFormatting.WHITE + " of " + TextFormatting.AQUA
                + (x + 1) + TextFormatting.GRAY + " (/mocpets <page>)" + TextFormatting.DARK_GREEN + "---"));

        for (int l = j * pagelimit; l < k; ++l) {
            String tamedInfo = list.get(l);
            sender.sendMessage(new TranslationTextComponent(tamedInfo));
        }
    }*/
}
