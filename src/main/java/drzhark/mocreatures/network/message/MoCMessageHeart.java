/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageHeart {

    public int entityId;

    public MoCMessageHeart() {
    }

    public MoCMessageHeart(int entityId) {
        this.entityId = entityId;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
    }

    public MoCMessageHeart(ByteBuf buffer) {
        this.entityId = buffer.readInt();
    }

    public static boolean onMessage(MoCMessageHeart message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Entity ent = Minecraft.getInstance().player.world.getEntityByID(message.entityId);
        if (ent instanceof IMoCTameable) {
            ((IMoCTameable) ent).spawnHeart();
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHeart - entityId:%s", this.entityId);
    }
}
