/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAttachedEntity {

    public int sourceEntityId;
    public int targetEntityId;

    public MoCMessageAttachedEntity() {
    }

    public MoCMessageAttachedEntity(int sourceEntityId, int targetEntityId) {
        this.sourceEntityId = sourceEntityId;
        this.targetEntityId = targetEntityId;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.sourceEntityId);
        buffer.writeInt(this.targetEntityId);
    }

    public MoCMessageAttachedEntity(ByteBuf buffer) {
        this.sourceEntityId = buffer.readInt();
        this.targetEntityId = buffer.readInt();
    }

    public static boolean onMessage(MoCMessageAttachedEntity message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Entity var2 = Minecraft.getInstance().player.world.getEntityByID(message.sourceEntityId);
        Entity var3 = Minecraft.getInstance().player.world.getEntityByID(message.targetEntityId);

        if (var2 != null) {
            (var2).startRiding(var3);
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAttachedEntity - sourceEntityId:%s, targetEntityId:%s", this.sourceEntityId, this.targetEntityId);
    }
}
