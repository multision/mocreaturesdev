/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageEntityJump {

    public MoCMessageEntityJump() {
    }

    public void encode(ByteBuf buffer) {
    }

    public MoCMessageEntityJump(ByteBuf buffer) {
    }

    public static boolean onMessage(MoCMessageEntityJump message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        if (ctx.get().getSender().getRidingEntity() instanceof IMoCEntity) {
            ((IMoCEntity) ctx.get().getSender().getRidingEntity()).makeEntityJump();
        }
        return true;
    }

    @Override
    public String toString() {
        return "MoCMessageEntityJump";
    }
}
