/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.gui.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
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
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc == null || mc.player == null || mc.world == null) return;

            Entity ent = mc.world.getEntityByID(message.entityId);

            System.out.println("[MoCreatures] Looking for entity ID: " + message.entityId);
            if (ent != null) {
                System.out.println("[MoCreatures] Found entity: " + ent.getClass().getName());
            } else {
                System.out.println("[MoCreatures] Entity was null");
            }


            if (ent instanceof IMoCEntity) {
                IMoCEntity mocEntity = (IMoCEntity) ent;
                mc.displayGuiScreen(new MoCGUIEntityNamer(mocEntity, mocEntity.getPetName()));
            } else {
                System.err.println("[MoCreatures] Failed to open name GUI: Entity with ID " + message.entityId + " is null or not an IMoCEntity");
            }
        });

        ctx.get().setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}
