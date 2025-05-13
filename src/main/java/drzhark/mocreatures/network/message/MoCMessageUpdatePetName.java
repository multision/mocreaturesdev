/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.message;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fml.network.NetworkEvent;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Supplier;

public class MoCMessageUpdatePetName {

    public String name;
    public int entityId;

    public MoCMessageUpdatePetName(int entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(name.length());
        buffer.writeCharSequence(this.name, StandardCharsets.UTF_8);
        buffer.writeInt(this.entityId);
    }

    public MoCMessageUpdatePetName(ByteBuf buffer) {
        this.name = buffer.readCharSequence(buffer.readInt(), StandardCharsets.UTF_8).toString();
        this.entityId = buffer.readInt();
    }

    public static boolean onMessage(MoCMessageUpdatePetName message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Entity pet = null;

        UUID ownerUniqueId = null;

        Entity ent = ctx.get().getSender().world.getEntityByID(message.entityId);
        if (ent.getEntityId() == message.entityId && ent instanceof IMoCTameable) {
            ((IMoCEntity) ent).setPetName(message.name);
            ownerUniqueId = ((IMoCEntity) ent).getOwnerId();
            pet = ent;

        }
        // update petdata
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(ownerUniqueId);
        if (petData != null && pet != null && ((IMoCTameable) pet).getOwnerPetId() != -1) {
            int id = ((IMoCTameable) pet).getOwnerPetId();
            ListNBT tag = petData.getOwnerRootNBT().getList("TamedList", 10);
            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT nbt = tag.getCompound(i);
                if (nbt.getInt("PetId") == id) {
                    nbt.putString("Name", message.name);
                    ((IMoCTameable) pet).setPetName(message.name);
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageUpdatePetName - entityId:%s, name:%s", this.entityId, this.name);
    }
}
