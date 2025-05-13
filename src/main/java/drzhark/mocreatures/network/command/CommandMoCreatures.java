/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.command;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.config.MoCConfigCategory;
import drzhark.mocreatures.config.MoCConfiguration;
import drzhark.mocreatures.config.MoCProperty;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandMoCreatures {

    private static final List<String> commands = new ArrayList<>();
    private static final List<String> aliases = new ArrayList<>();
    private static final List<String> tabCompletionStrings = new ArrayList<>();

    static {
        commands.add("/moc attackdolphins <boolean>");
        commands.add("/moc attackhorses <boolean>");
        commands.add("/moc attackwolves <boolean>");
        commands.add("/moc canspawn <boolean>");
        commands.add("/moc caveogrechance <float>");
        commands.add("/moc caveogrestrength <float>");
        commands.add("/moc debug <boolean>");
        // TODO commands.add("/moc deletepets <playername>");
        commands.add("/moc destroydrops <boolean>");
        commands.add("/moc enablehunters <boolean>");
        commands.add("/moc easybreeding <boolean>");
        commands.add("/moc elephantbulldozer <boolean>");
        commands.add("/moc enableownership <boolean>");
        commands.add("/moc enableresetownerscroll <boolean>");
        commands.add("/moc fireogrechance <int>");
        commands.add("/moc fireogrestrength <float>");
        commands.add("/moc frequency <entity> <int>");
        commands.add("/moc golemdestroyblocks <boolean>");
        commands.add("/moc tamed");
        commands.add("/moc tamed <playername>");
        commands.add("/moc maxchunk <entity> <int>");
        commands.add("/moc maxspawn <entity> <int>");
        commands.add("/moc maxtamedperop <int>");
        commands.add("/moc maxtamedperplayer <int>");
        commands.add("/moc minspawn <entity> <int>");
        commands.add("/moc motherwyverneggdropchance <int>");
        commands.add("/moc ogreattackrange <int>");
        commands.add("/moc ogrestrength <float>");
        commands.add("/moc ostricheggdropchance <int>");
        commands.add("/moc rareitemdropchance <int>");
        commands.add("/moc spawnhorse <int>");
        commands.add("/moc spawnwyvern <int>");
        commands.add("/moc tamedcount <playername>");
        commands.add("/moc tp <petid> <playername>");
        commands.add("/moc <command> value");
        commands.add("/moc wyverneggdropchance <int>");
        commands.add("/moc zebrachance <int>");
        aliases.add("moc");
        tabCompletionStrings.add("attackdolphins");
        tabCompletionStrings.add("attackhorses");
        tabCompletionStrings.add("attackwolves");
        tabCompletionStrings.add("canspawn");
        tabCompletionStrings.add("caveogrechance");
        tabCompletionStrings.add("caveogrestrength");
        tabCompletionStrings.add("debug");
        // TODO tabCompletionStrings.add("deletepets");
        tabCompletionStrings.add("destroydrops");
        tabCompletionStrings.add("easybreeding");
        tabCompletionStrings.add("elephantbulldozer");
        tabCompletionStrings.add("enableownership");
        tabCompletionStrings.add("enableresetownerscroll");
        tabCompletionStrings.add("fireogrechance");
        tabCompletionStrings.add("fireogrestrength");
        tabCompletionStrings.add("forcedespawns");
        tabCompletionStrings.add("frequency");
        tabCompletionStrings.add("golemdestroyblocks");
        tabCompletionStrings.add("tamed");
        tabCompletionStrings.add("maxchunk");
        tabCompletionStrings.add("maxspawn");
        tabCompletionStrings.add("maxtamedperop");
        tabCompletionStrings.add("maxtamedperplayer");
        tabCompletionStrings.add("minspawn");
        tabCompletionStrings.add("motherwyverneggdropchance");
        tabCompletionStrings.add("ogreattackrange");
        tabCompletionStrings.add("ogreattackstrength");
        tabCompletionStrings.add("ostricheggdropchance");
        tabCompletionStrings.add("rareitemdropchance");
        tabCompletionStrings.add("spawnhorse");
        tabCompletionStrings.add("spawnwyvern");
        tabCompletionStrings.add("tamedcount");
        tabCompletionStrings.add("tp");
        tabCompletionStrings.add("wyverneggdropchance");
        tabCompletionStrings.add("zebrachance");
    }


    public int execute(CommandSource source, MinecraftServer server, String[] args) {
        /*String command;
        if (args.length == 0) {
            command = "help";
        } else {
            command = args[0];
        }
        String par2 = "";
        if (args.length > 1) {
            par2 = args[1];
        }
        String par3 = "";
        if (args.length == 3) {
            par3 = args[2];
        }

        MoCConfiguration config = MoCreatures.proxy.mocSettingsConfig;
        boolean saved = false;

        if (command.equalsIgnoreCase("tamed") || command.equalsIgnoreCase("tame")) {
            if (args.length == 2 && !Character.isDigit(args[1].charAt(0))) {
                int unloadedCount = 0;
                int loadedCount = 0;
                ArrayList<Integer> foundIds = new ArrayList<>();
                ArrayList<String> tamedlist = new ArrayList<>();
                GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(par2);
                if (profile == null) {
                    return;
                }
                // search for tamed entity
                for (int dimension : DimensionManager.getIDs()) {
                    ServerWorld world = DimensionManager.getWorld(dimension);
                    for (int j = 0; j < world.loadedEntityList.size(); j++) {
                        Entity entity = world.loadedEntityList.get(j);
                        if (IMoCTameable.class.isAssignableFrom(entity.getClass())) {
                            IMoCTameable mocreature = (IMoCTameable) entity;
                            if (mocreature.getOwnerId().equals(profile.getId())) {
                                loadedCount++;
                                foundIds.add(mocreature.getOwnerPetId());
                                tamedlist.add(TextFormatting.WHITE + "Found pet with " + TextFormatting.DARK_AQUA + "Type"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + ((MobEntity) mocreature).getName() + TextFormatting.DARK_AQUA + ", Name"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + mocreature.getPetName()
                                        + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + profile.getName() + TextFormatting.DARK_AQUA + ", PetId" + TextFormatting.WHITE + ":"
                                        + TextFormatting.GREEN + mocreature.getOwnerPetId() + TextFormatting.DARK_AQUA + ", Dimension"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + entity.dimension + TextFormatting.DARK_AQUA
                                        + ", Pos" + TextFormatting.WHITE + ":" + TextFormatting.LIGHT_PURPLE + Math.round(entity.getPosX())
                                        + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.getPosY())
                                        + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.getPosZ()));
                            }
                        }
                    }
                }
                MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(profile.getId());
                if (ownerPetData != null) {
                    for (int i = 0; i < ownerPetData.getTamedList().size(); i++) {
                        CompoundNBT nbt = ownerPetData.getTamedList().getCompound(i);
                        if (nbt.contains("PetId") && !foundIds.contains(nbt.getInt("PetId"))) {
                            unloadedCount++;
                            double getPosX () = nbt.getList("Pos", 6).getDouble(0);
                            double getPosY () = nbt.getList("Pos", 6).getDouble(1);
                            double getPosZ () = nbt.getList("Pos", 6).getDouble(2);
                            tamedlist.add(TextFormatting.WHITE + "Found unloaded pet with " + TextFormatting.DARK_AQUA + "Type"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getString("EntityName")
                                    + TextFormatting.DARK_AQUA + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getString("Name") + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":"
                                    + TextFormatting.GREEN + nbt.getString("Owner") + TextFormatting.DARK_AQUA + ", PetId"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getInt("PetId")
                                    + TextFormatting.DARK_AQUA + ", Dimension" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getInt("Dimension") + TextFormatting.DARK_AQUA + ", Pos" + TextFormatting.WHITE + ":"
                                    + TextFormatting.LIGHT_PURPLE + Math.round(getPosX()) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(getPosY()) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(getPosZ()));
                        }
                    }
                }
                if (tamedlist.size() > 0) {
                    sendPageHelp(sender, (byte) 10, tamedlist, args, "Listing tamed pets");
                    sender.sendMessage(new TranslationTextComponent("Loaded tamed count : " + TextFormatting.AQUA + loadedCount
                            + TextFormatting.WHITE + ", Unloaded count : " + TextFormatting.AQUA + unloadedCount + TextFormatting.WHITE
                            + ", Total count : " + TextFormatting.AQUA + (ownerPetData != null ? ownerPetData.getTamedList().size() : 0)));
                } else {
                    sender.sendMessage(new TranslationTextComponent("Player " + TextFormatting.GREEN + par2
                            + TextFormatting.WHITE + " does not have any tamed animals."));
                }
            } else if (command.equalsIgnoreCase("tamed") || command.equalsIgnoreCase("tame") && !par2.equals("")) {
                int unloadedCount = 0;
                int loadedCount = 0;
                ArrayList<Integer> foundIds = new ArrayList<>();
                ArrayList<String> tamedlist = new ArrayList<>();
                // search for mocreature tamed entities
                for (int dimension : DimensionManager.getIDs()) {
                    ServerWorld world = DimensionManager.getWorld(dimension);
                    for (int j = 0; j < world.loadedEntityList.size(); j++) {
                        Entity entity = world.loadedEntityList.get(j);
                        if (IMoCTameable.class.isAssignableFrom(entity.getClass())) {
                            IMoCTameable mocreature = (IMoCTameable) entity;
                            if (mocreature.getOwnerPetId() != -1) {
                                loadedCount++;
                                foundIds.add(mocreature.getOwnerPetId());
                                tamedlist.add(TextFormatting.WHITE + "Found pet with " + TextFormatting.DARK_AQUA + "Type"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + ((MobEntity) mocreature).getName() + TextFormatting.DARK_AQUA + ", Name"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + mocreature.getPetName()
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
                // if (!MoCreatures.isServer())
                // {
                for (MoCPetData ownerPetData : MoCreatures.instance.mapData.getPetMap().values()) {
                    for (int i = 0; i < ownerPetData.getTamedList().size(); i++) {
                        CompoundNBT nbt = ownerPetData.getTamedList().getCompound(i);
                        if (nbt.contains("PetId") && !foundIds.contains(nbt.getInt("PetId"))) {
                            unloadedCount++;
                            double posX = nbt.getList("Pos", 10).getDouble(0);
                            double posY = nbt.getList("Pos", 10).getDouble(1);
                            double posZ = nbt.getList("Pos", 10).getDouble(2);
                            tamedlist.add(TextFormatting.WHITE + "Found unloaded pet with " + TextFormatting.DARK_AQUA + "Type"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getString("EntityName")
                                    + TextFormatting.DARK_AQUA + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getString("Name") + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":"
                                    + TextFormatting.GREEN + nbt.getString("Owner") + TextFormatting.DARK_AQUA + ", PetId"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getInt("PetId")
                                    + TextFormatting.DARK_AQUA + ", Dimension" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getInt("Dimension") + TextFormatting.DARK_AQUA + ", Pos" + TextFormatting.WHITE + ":"
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posX) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posY) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posZ));
                        }
                    }
                }
                //}
                sendPageHelp(sender, (byte) 10, tamedlist, args, "Listing tamed pets");
                sender.sendMessage(new TranslationTextComponent("Loaded tamed count : "
                        + TextFormatting.AQUA
                        + loadedCount
                        + TextFormatting.WHITE
                        + (!MoCreatures.isServer() ? ", Unloaded Count : " + TextFormatting.AQUA + unloadedCount + TextFormatting.WHITE
                        + ", Total count : " + TextFormatting.AQUA + (loadedCount + unloadedCount) : "")));
            }
        } else if (command.equalsIgnoreCase("tp") && args.length == 3) {
            int petId;
            try {
                petId = Integer.parseInt(par2);
            } catch (NumberFormatException e) {
                petId = -1;
            }
            String playername = args[2];
            ServerPlayerEntity player =
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playername);
            if (player == null) {
                return;
            }
            // search for tamed entity in mocreatures.dat
            MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
            if (ownerPetData != null) {
                for (int i = 0; i < ownerPetData.getTamedList().size(); i++) {
                    CompoundNBT nbt = ownerPetData.getTamedList().getCompound(i);
                    if (nbt.contains("PetId") && nbt.getInt("PetId") == petId) {
                        String petName = nbt.getString("Name");
                        ServerWorld world = server.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(nbt.getString("Dimension"))));
                        if (!teleportLoadedPet(world, player, petId, petName, sender)) {
                            double posX = nbt.getList("Pos", 10).getDouble(0);
                            double posY = nbt.getList("Pos", 10).getDouble(1);
                            double posZ = nbt.getList("Pos", 10).getDouble(2);
                            sender.sendMessage(new TranslationTextComponent("Found unloaded pet " + TextFormatting.GREEN
                                    + nbt.getString("id") + TextFormatting.WHITE + " with name " + TextFormatting.AQUA
                                    + nbt.getString("Name") + TextFormatting.WHITE + " at location " + TextFormatting.LIGHT_PURPLE
                                    + Math.round(posX) + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(posY)
                                    + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(posZ) + TextFormatting.WHITE
                                    + " with Pet ID " + TextFormatting.BLUE + nbt.getInt("PetId")));
                            boolean result = teleportLoadedPet(world, player, petId, petName, sender); // attempt to TP again
                            if (!result) {
                                sender.sendMessage(new TranslationTextComponent("Unable to transfer entity ID "
                                        + TextFormatting.GREEN + petId + TextFormatting.WHITE + ". It may only be transferred to "
                                        + TextFormatting.AQUA + player.getName()));
                            }
                        }
                        break;
                    }
                }
            } else {
                sender.sendMessage(new TranslationTextComponent("Tamed entity could not be located."));
            }
        } else if (command.equalsIgnoreCase("tamedcount")) {
            List<ServerPlayerEntity> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
            for (ServerPlayerEntity player : players) {
                if (player.getName().equalsIgnoreCase(par2)) {
                    int tamedCount = MoCTools.numberTamedByPlayer(player);
                    sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + par2
                            + "'s recorded tamed count is " + TextFormatting.AQUA + tamedCount));
                }
            }
            sender.sendMessage(new TranslationTextComponent(TextFormatting.RED + "Could not find player "
                    + TextFormatting.GREEN + par2 + TextFormatting.RED
                    + ". Please verify the player is online and/or name was entered correctly."));
        }
        // START ENTITY FREQUENCY/BIOME SECTION
        else if (args.length >= 2
                && (command.equalsIgnoreCase("frequency") || command.equalsIgnoreCase("minspawn") || command.equalsIgnoreCase("maxspawn")
                || command.equalsIgnoreCase("maxchunk") || command.equalsIgnoreCase("canspawn"))) {
            MoCEntityData entityData = MoCreatures.mocEntityMap.get(par2);//modEntry.getValue().getCreature(name);

            if (entityData != null) {
                if (command.equalsIgnoreCase("frequency")) {
                    if (par3 == null) {
                        sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " frequency is " + TextFormatting.AQUA + entityData.getFrequency()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setFrequency(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "frequency");
                            prop.value = par3;
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " frequency to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("min") || command.equalsIgnoreCase("minspawn")) {
                    if (par3 == null) {
                        sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " minGroupSpawn is " + TextFormatting.AQUA + entityData.getMinSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMinSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "minspawn");
                            prop.value = par3;
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " minGroupSpawn to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("max") || command.equalsIgnoreCase("maxspawn")) {
                    if (par3 == null) {
                        sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " maxGroupSpawn is " + TextFormatting.AQUA + entityData.getMaxSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "maxspawn");
                            prop.value = par3;
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " maxGroupSpawn to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("chunk") || command.equalsIgnoreCase("maxchunk")) {
                    if (par3 == null) {
                        sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " maxInChunk is " + TextFormatting.AQUA + entityData.getMaxInChunk()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "maxchunk");
                            prop.value = par3;
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " maxInChunk to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("canspawn")) {
                    if (par3 == null) {
                        sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " canSpawn is " + TextFormatting.AQUA + entityData.getCanSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setCanSpawn(Boolean.parseBoolean(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "canspawn");
                            prop.set(par3);
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " canSpawn to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            sendCommandHelp(sender);
                        }
                    }
                }
                // TODO - remove spawnlist entry from vanilla list and readd it
            }
        } else if (args.length == 1) {
            OUTER:
            for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet()) {
                String catName = catEntry.getValue().getQualifiedName();
                if (catName.equalsIgnoreCase("custom-id-settings")) {
                    continue;
                }

                for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet()) {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(command)) {
                        continue;
                    }
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().value;
                    if (propList == null && propValue == null) {
                        continue;
                    }
                    if (par2.equals("")) {
                        sender.sendMessage(new TranslationTextComponent(TextFormatting.GREEN + propEntry.getKey()
                                + TextFormatting.WHITE + " is " + TextFormatting.AQUA + propValue));
                        break OUTER;
                    }
                }
            }
        }
        // END ENTITY FREQUENCY/BIOME SECTION
        // START OTHER SECTIONS
        else {
            for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet()) {
                for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet()) {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(command)) {
                        continue;
                    }
                    MoCProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().getString();

                    if (propList == null && propValue == null) {
                        continue;
                    }

                    if (propEntry.getValue().getTypeMoC() == MoCProperty.Type.BOOLEAN) {
                        if (par2.equalsIgnoreCase("true") || par2.equalsIgnoreCase("false")) {
                            property.set(par2);
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        }
                    } else if (propEntry.getValue().getTypeMoC() == MoCProperty.Type.INTEGER) {
                        try {
                            Integer.parseInt(par2);
                            property.set(par2);
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        } catch (NumberFormatException ex) {
                            sender.sendMessage(new TranslationTextComponent(TextFormatting.RED
                                    + "Invalid value entered. Please enter a valid number."));
                        }

                    } else if (propEntry.getValue().getTypeMoC() == MoCProperty.Type.DOUBLE) {
                        try {
                            Double.parseDouble(par2);
                            property.set(par2);
                            saved = true;
                            sender.sendMessage(new TranslationTextComponent("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        } catch (NumberFormatException ex) {
                            sender.sendMessage(new TranslationTextComponent(TextFormatting.RED
                                    + "Invalid value entered. Please enter a valid number."));
                        }
                    }
                    //break OUTER; // exit since we found the property we need to save
                }
            }
        }
        // START HELP COMMAND
        if (command.equalsIgnoreCase("help")) {
            List<String> list = this.getSortedPossibleCommands(sender);
            byte b0 = 10;
            int i = (list.size() - 1) / b0;
            int j = 0;

            if (args.length > 1) {
                try {
                    j = parseInt(args[1], 1, i + 1) - 1;
                } catch (NumberInvalidException numberinvalidexception) {
                    numberinvalidexception.printStackTrace();
                }
            }

            int k = Math.min((j + 1) * b0, list.size());
            sender.sendMessage(new TranslationTextComponent(TextFormatting.DARK_GREEN + "--- Showing MoCreatures help page "
                    + (j + 1) + " of " + (i + 1) + "(/moc help <page>)---"));

            for (int l = j * b0; l < k; ++l) {
                String commandToSend = list.get(l);
                sender.sendMessage(new TranslationTextComponent(commandToSend));
            }
        }
        // END HELP COMMAND
        if (saved) {
            // TODO: update only what is needed instead of everything
            config.save();
            MoCreatures.proxy.readGlobalConfigValues();
        }*/
        return  0; //TODO TheidenHD
    }


    /*public boolean teleportLoadedPet(ServerWorld world, ServerPlayerEntity player, int petId, String petName, ICommandSender par1ICommandSender) {
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

    public void sendPageHelp(ICommandSender sender, byte pagelimit, ArrayList<String> list, String[] par2ArrayOfStr, String title) {
        int x = (list.size() - 1) / pagelimit;
        int j = 0;

        if (Character.isDigit(par2ArrayOfStr[par2ArrayOfStr.length - 1].charAt(0))) {
            try {
                j = parseInt(par2ArrayOfStr[par2ArrayOfStr.length - 1], 1, x + 1) - 1;
            } catch (NumberInvalidException numberinvalidexception) {
                numberinvalidexception.printStackTrace();
            }
        }
        int k = Math.min((j + 1) * pagelimit, list.size());

        sender.sendMessage(new TranslationTextComponent(TextFormatting.WHITE + title + " (pg " + TextFormatting.WHITE
                + (j + 1) + TextFormatting.DARK_GREEN + "/" + TextFormatting.WHITE + (x + 1) + ")"));

        for (int l = j * pagelimit; l < k; ++l) {
            String tamedInfo = list.get(l);
            sender.sendMessage(new TranslationTextComponent(tamedInfo));
        }
    }*/
}
