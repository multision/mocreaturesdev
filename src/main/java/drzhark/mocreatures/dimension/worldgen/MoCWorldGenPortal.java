package drzhark.mocreatures.dimension.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MoCWorldGenPortal {

    private final BlockState pillarBlock;
    private final BlockState stairBlock;
    private final BlockState wallBlock;
    private final BlockState centerBlock;

    public MoCWorldGenPortal(BlockState pillar, BlockState stair, BlockState wall, BlockState center) {
        this.pillarBlock = pillar;
        this.stairBlock = stair;
        this.wallBlock = wall;
        this.centerBlock = center;
    }

    public void generatePillar(ServerWorld world, BlockPos pos) {
        for (int y = 0; y < 6; y++) {
            BlockPos target = pos.up(y);
            world.setBlockState(target, pillarBlock, 2);
        }
    }

    public void generate(ServerWorld world, Random rand, BlockPos pos) {
        if (!world.isAirBlock(pos)) return;

        int x = pos.getX();
        int y = pos.getY() - 1;
        int z = pos.getZ();

        // Place stairs
        for (int nZ = z - 3; nZ <= z + 2; nZ += 5) {
            Direction facing = (nZ < z) ? Direction.SOUTH : Direction.NORTH;
            for (int nX = x - 2; nX < x + 2; nX++) {
                BlockPos stairPos = new BlockPos(nX, y + 1, nZ);
                BlockState facingStair = stairBlock.with(StairsBlock.FACING, facing);
                world.setBlockState(stairPos, facingStair, 2);
            }
        }

        // Inner wall
        for (int nX = x - 2; nX < x + 2; nX++) {
            for (int nZ = z - 2; nZ < z + 2; nZ++) {
                world.setBlockState(new BlockPos(nX, y + 1, nZ), wallBlock, 2);
            }
        }

        // Center platform
        for (int nX = x - 1; nX < x + 1; nX++) {
            for (int nZ = z - 1; nZ < z + 1; nZ++) {
                world.setBlockState(new BlockPos(nX, y + 1, nZ), centerBlock, 2);
            }
        }

        // Top blocks
        for (int j = x - 3; j < x + 3; j += 5) {
            for (int nZ = z - 3; nZ < z + 3; nZ++) {
                world.setBlockState(new BlockPos(j, y + 6, nZ), wallBlock, 2);
            }
        }

        // Pillars
        generatePillar(world, new BlockPos(x - 3, y, z - 3));
        generatePillar(world, new BlockPos(x - 3, y, z + 2));
        generatePillar(world, new BlockPos(x + 2, y, z - 3));
        generatePillar(world, new BlockPos(x + 2, y, z + 2));
    }
}
