/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;


import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.*;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MoCBlockLeaf extends LeavesBlock {

    // TODO : PRIVATE ACCESS

    public MoCBlockLeaf(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.PLANT).notSolid()/*.setAllowsSpawn(Blocks::allowsSpawnOnLeaves).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid)*/);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistanceOverride(facingState) + 1;
        if (i != 1 || stateIn.get(DISTANCE) != i) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }
        return stateIn;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockState(pos, updateDistanceOverride(state, worldIn, pos), 3);
    }

    private BlockState updateDistanceOverride(BlockState state, IWorld worldIn, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.values()) {
            mutable.setAndMove(pos, direction);
            i = Math.min(i, getDistanceOverride(worldIn.getBlockState(mutable)) + 1);
            if (i == 1) break;
        }

        return state.with(DISTANCE, i);
    }

    private int getDistanceOverride(BlockState state) {
        Block block = state.getBlock();

        // Recognize your custom log block
        if (block == MoCBlocks.wyvwoodLog || BlockTags.LOGS.contains(block)) {
            return 0;
        } else if (block instanceof LeavesBlock) {
            return state.get(DISTANCE);
        } else {
            return 7;
        }
    }

}
