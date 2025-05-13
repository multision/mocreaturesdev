/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageInstaSpawn {

    public int entityId;
    public int numberToSpawn;

    public MoCMessageInstaSpawn(int entityId, int numberToSpawn) {
        this.entityId = entityId;
        this.numberToSpawn = numberToSpawn;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.numberToSpawn);
    }

    public MoCMessageInstaSpawn(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.numberToSpawn = buffer.readInt();
    }

    public static boolean onMessage(MoCMessageInstaSpawn message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        if ((MoCreatures.proxy.getProxyMode() == 1 && MoCreatures.proxy.allowInstaSpawn) || MoCreatures.proxy.getProxyMode() == 2) { // make sure the client has admin rights on server!
            MoCTools.spawnNearPlayer(ctx.get().getSender(), message.entityId, message.numberToSpawn);
            if (MoCreatures.proxy.debug) {
                MoCreatures.LOGGER.info("Player " + ctx.get().getSender().getName() + " used MoC instaspawner and got "
                        + message.numberToSpawn + " creatures spawned");
            }
        } else {
            if (MoCreatures.proxy.debug) {
                MoCreatures.LOGGER.info("Player " + ctx.get().getSender().getName()
                        + " tried to use MoC instaspawner, but the allowInstaSpawn setting is set to " + MoCreatures.proxy.allowInstaSpawn);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageInstaSpawn - entityId:%s, numberToSpawn:%s", this.entityId, this.numberToSpawn);
    }
}
