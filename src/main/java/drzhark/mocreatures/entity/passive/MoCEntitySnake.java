/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

// Just here for makeshift MIA compatibility
public class MoCEntitySnake extends MoCEntityTameableAnimal {
    public MoCEntitySnake(EntityType<? extends MoCEntitySnake> type, World world) {
        super(type, world);
    }
}
