/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.server.command.EnumArgument;

public class CommandMoCSpawn {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("mocspawn").requires((p_198496_0_) -> {
            return p_198496_0_.hasPermissionLevel(2);
        }).then(Commands.argument("entityType", EnumArgument.enumArgument(Type.class)).executes((p_198493_0_) -> {
            return execute(p_198493_0_.getSource(), p_198493_0_.getArgument("entityType", Type.class), 0);
        }).then(Commands.argument("type", IntegerArgumentType.integer(1)).executes((p_198495_0_) -> {
            return execute(p_198495_0_.getSource(), p_198495_0_.getArgument("entityType", Type.class), IntegerArgumentType.getInteger(p_198495_0_, "type"));
        }))));
    }

    private static int execute(CommandSource source, Type entityType, int type) {
        try {
            ServerPlayerEntity player = source.asPlayer();
            MoCEntityTameableAnimal specialEntity;
            if (entityType == Type.horse) {
                specialEntity = MoCEntities.WILDHORSE.create(player.world);
                specialEntity.setAdult(true);
            } else if (entityType == Type.manticore) {
                specialEntity = MoCEntities.MANTICORE_PET.create(player.world);
                specialEntity.setAdult(true);
            } else if (entityType == Type.wyvern) {
                specialEntity = MoCEntities.WYVERN.create(player.world);
                specialEntity.setAdult(false);
            } else if (entityType == Type.wyvernghost) {
                specialEntity = MoCEntities.WYVERN.create(player.world);
                specialEntity.setAdult(false);
                ((MoCEntityWyvern) specialEntity).setIsGhost(true);
            } else {
                source.sendErrorMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The entity spawn type " + entityType + " is not a valid type."));
                return 1;
            }
            double dist = 3D;
            double newPosY = player.getPosY();
            double newPosX = player.getPosX() - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = player.getPosZ() - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            specialEntity.setPosition(newPosX, newPosY, newPosZ);
            specialEntity.setTamed(true);
            specialEntity.setOwnerId(null);
            specialEntity.setPetName("Rename_Me");
            specialEntity.setTypeMoC(type);

            if ((entityType == Type.horse && (type < 0 || type > 67))
                    || (entityType == Type.wyvern && (type < 0 || type > 12))
                    || (entityType == Type.manticore && (type < 0 || type > 4))) {
                source.sendErrorMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The spawn type " + type + " is not a valid type."));
                return 1;
            }
            player.world.addEntity(specialEntity);
            if (!player.world.isRemote) {
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(player.getPosX(), player.getPosY(), player.getPosZ(), 64, player.world.getDimensionKey())), new MoCMessageAppear(specialEntity.getEntityId()));
            }
            MoCTools.playCustomSound(specialEntity, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR.get());
        } catch (Exception e){}
        return 1;
    }

    private enum Type{
        horse,
        manticore,
        wyvern,
        wyvernghost
    }
}
