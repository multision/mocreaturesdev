/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;


public class MoCBlockGlass extends AbstractGlassBlock {

    // TODO: PRIVATE ACCESS
    public MoCBlockGlass(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.GLASS).notSolid()/*.setAllowsSpawn(Blocks::neverAllowSpawn).setOpaque(Blocks::isntSolid).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid)*/.harvestLevel(0).harvestTool(ToolType.PICKAXE));
    }
}
