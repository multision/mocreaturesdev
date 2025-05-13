/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.gui.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.proxy.MoCProxyClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageNameGUI {

    public int entityId;

    public MoCMessageNameGUI() {
    }

    public MoCMessageNameGUI(int entityId) {
        this.entityId = entityId;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
    }

    public MoCMessageNameGUI(ByteBuf buffer) {
        this.entityId = buffer.readInt();
    }

    public static boolean onMessage(MoCMessageNameGUI message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Entity ent = Minecraft.getInstance().player.world.getEntityByID(message.entityId);
        MoCProxyClient.mc.displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) ent), ((IMoCEntity) ent).getPetName()));
        return true;
    }


    @Override
    public String toString() {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}
