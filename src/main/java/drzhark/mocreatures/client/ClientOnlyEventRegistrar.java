package drzhark.mocreatures.client;

import drzhark.mocreatures.event.MoCEventHooksClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientOnlyEventRegistrar {
    public static void registerClientEvents() {
        MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
        MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
    }
}