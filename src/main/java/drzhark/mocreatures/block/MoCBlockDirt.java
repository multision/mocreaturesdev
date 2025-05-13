/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;

public class MoCBlockDirt extends Block {

    public MoCBlockDirt(AbstractBlock.Properties properties) {
        super(properties.harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND));
    }
}
