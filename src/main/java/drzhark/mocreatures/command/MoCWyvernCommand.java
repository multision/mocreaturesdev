// MoCWyvernCommand.java
package drzhark.mocreatures.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MoCWyvernCommand {
    public static final RegistryKey<World> WYVERN_DIM = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("mocreatures", "wyvernlairworld"));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("tpwyvern")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().asPlayer();
                    ServerWorld wyvernWorld = player.getServer().getWorld(WYVERN_DIM);
                    if (wyvernWorld != null) {
                        player.teleport(wyvernWorld, 0, 100, 0, player.rotationYaw, player.rotationPitch);
                        context.getSource().sendFeedback(new StringTextComponent("Teleported to Wyvern Dimension"), true);
                        return 1;
                    } else {
                        context.getSource().sendErrorMessage(new StringTextComponent("Wyvern dimension not found."));
                        return 0;
                    }
                }));
    }
}
