/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.entity.MoCEntityMob;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

// Just here for makeshift MIA compatibility
public class MoCEntityRat extends MoCEntityMob {
    public MoCEntityRat(EntityType<? extends MoCEntityRat> type, World world) {
        super(type, world);
    }
}
