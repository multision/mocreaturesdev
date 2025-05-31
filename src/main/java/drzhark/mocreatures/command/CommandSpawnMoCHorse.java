package drzhark.mocreatures.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Optional;

public class CommandSpawnMoCHorse {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("summonmoc")
                .requires(source -> source.hasPermissionLevel(2))
                .then(Commands.literal("horse")
                        .then(Commands.argument("type", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    return ISuggestionProvider.suggest(MoCHorseType.getNames(), builder);
                                })
                                .executes(ctx -> {
                                    ServerPlayerEntity player = ctx.getSource().asPlayer();
                                    String typeName = StringArgumentType.getString(ctx, "type");
                                    return summonHorse(player, typeName, false);
                                })
                                .then(Commands.literal("tame")
                                        .executes(ctx -> {
                                            ServerPlayerEntity player = ctx.getSource().asPlayer();
                                            String typeName = StringArgumentType.getString(ctx, "type");
                                            return summonHorse(player, typeName, true);
                                        })
                                )
                        )
                )
        );
    }

    private static int summonHorse(ServerPlayerEntity player, String typeName, boolean tame) {
        World world = player.getEntityWorld();
        BlockPos pos = player.getPosition();
        Optional<MoCHorseType> horseType = MoCHorseType.fromName(typeName);

        if (!horseType.isPresent()) {
            player.sendMessage(new StringTextComponent("Invalid horse type: " + typeName), player.getUniqueID());
            return 0;
        }

        MoCEntityHorse horse = new MoCEntityHorse(MoCEntities.WILDHORSE, world);
        horse.setPosition(pos.getX(), pos.getY(), pos.getZ());
        horse.setTypeMoC(horseType.get().getId());

        if (tame) {
            MoCTools.tameWithName(player, horse);
        }

        world.addEntity(horse);
        player.sendMessage(new StringTextComponent("Spawned MoC Horse of type " + typeName + (tame ? " (Tamed)" : "")), player.getUniqueID());
        return 1;
    }

}

