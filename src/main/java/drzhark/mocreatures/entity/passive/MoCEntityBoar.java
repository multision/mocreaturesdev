/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

// Just here for makeshift MIA compatibility
public class MoCEntityBoar extends MoCEntityAnimal {
    public MoCEntityBoar(EntityType<? extends MoCEntityBoar> type, World world) {
        super(type, world);
    }
}
