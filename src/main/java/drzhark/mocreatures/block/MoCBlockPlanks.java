/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

public class MoCBlockPlanks extends Block {

    public MoCBlockPlanks(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.WOOD));
    }
}
