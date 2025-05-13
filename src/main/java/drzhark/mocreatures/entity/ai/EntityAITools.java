/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityAITools {

    protected static boolean IsNearPlayer(MobEntity entityliving, double d) {
        PlayerEntity entityplayer1 = entityliving.world.getClosestPlayer(entityliving, d);
        return entityplayer1 != null;
    }

    protected static PlayerEntity getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerId() == null) {
            return null;
        }

        for (int i = 0; i < ((MobEntity) pet).world.getPlayers().size(); ++i) {
            PlayerEntity entityplayer = ((MobEntity) pet).world.getPlayers().get(i);

            if (pet.getOwnerId().equals(entityplayer.getUniqueID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
