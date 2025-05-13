/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

public class MoCBlockSand extends FallingBlock {

    public MoCBlockSand(AbstractBlock.Properties properties) {
        super(properties.harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND));
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return 12107978;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction, IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.offset(direction));

        if (plant.getBlock() == Blocks.CACTUS || plant.getBlock() == Blocks.DEAD_BUSH) {
            return this == MoCBlocks.silverSand;
        }

        return super.canSustainPlant(state, world, pos, direction, plantable);
    }
}
