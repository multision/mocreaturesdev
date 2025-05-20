package drzhark.mocreatures.command;

import drzhark.mocreatures.network.command.CommandMoCreatures;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures")
public class MoCCommandHandler {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        MoCWyvernCommand.register(event.getDispatcher());
    }
}