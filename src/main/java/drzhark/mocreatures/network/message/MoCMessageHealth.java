/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageHealth {

    public int entityId;
    public float health;

    public MoCMessageHealth() {
    }

    public MoCMessageHealth(int entityId, float health) {
        this.entityId = entityId;
        this.health = health;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(this.health);
    }

    public MoCMessageHealth(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.health = buffer.readFloat();
    }

    public static boolean onMessage(MoCMessageHealth message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Entity ent = Minecraft.getInstance().player.world.getEntityByID(message.entityId);
        if (ent instanceof MobEntity) {
            ((MobEntity) ent).setHealth(message.health);
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHealth - entityId:%s, health:%s", this.entityId, this.health);
    }
}
