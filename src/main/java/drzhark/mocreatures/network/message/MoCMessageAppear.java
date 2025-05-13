/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAppear {

    public int entityId;

    public MoCMessageAppear() {
    }

    public MoCMessageAppear(int entityId) {
        this.entityId = entityId;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
    }

    public MoCMessageAppear(ByteBuf buffer) {
        this.entityId = buffer.readInt();
    }

    public static boolean onMessage(MoCMessageAppear message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Entity ent = Minecraft.getInstance().player.world.getEntityByID(message.entityId);
        if (ent instanceof MoCEntityHorse) {
            ((MoCEntityHorse) ent).MaterializeFX();
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAppear - entityId:%s", this.entityId);
    }
}
