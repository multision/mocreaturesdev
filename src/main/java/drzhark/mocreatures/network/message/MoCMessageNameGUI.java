/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.gui.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageNameGUI {
    public int entityId;

    public MoCMessageNameGUI() {}

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
        if (DistExecutor.unsafeRunForDist(
                () -> () -> {
                    ctx.get().enqueueWork(() -> handleClient(message));
                    return true;
                },
                () -> () -> false)) {
            // Only executed on client side
        }
        ctx.get().setPacketHandled(true);
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(MoCMessageNameGUI message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null || mc.world == null) return;

        Entity ent = mc.world.getEntityByID(message.entityId);
        if (ent instanceof IMoCEntity) {
            IMoCEntity mocEntity = (IMoCEntity) ent;
            mc.displayGuiScreen(new MoCGUIEntityNamer(mocEntity, mocEntity.getPetName()));
        }
    }

    @Override
    public String toString() {
        return "MoCMessageNameGUI - entityId: " + this.entityId;
    }
}
