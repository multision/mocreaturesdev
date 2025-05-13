package drzhark.mocreatures.dimension.worldgen;

import drzhark.mocreatures.block.MoCBlockGrass;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class TallWyvGrassFeature extends Feature<BlockClusterFeatureConfig> {
    public TallWyvGrassFeature() {
        super(BlockClusterFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos origin, BlockClusterFeatureConfig config) {
        int placed = 0;

        for (int i = 0; i < config.tryCount; ++i) {
            BlockPos pos = origin.add(
                    rand.nextInt(8) - rand.nextInt(8),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8)
            );

            BlockPos below = pos.down();

            if (!world.isAirBlock(pos)) continue;
            if (world.getBlockState(pos).getBlock() == MoCBlocks.tallWyvgrass) continue;

            Block ground = world.getBlockState(below).getBlock();
            if (!(ground instanceof MoCBlockGrass || ground == MoCBlocks.wyvdirt)) continue;

            world.setBlockState(pos, MoCBlocks.tallWyvgrass.getDefaultState(), 2);
            placed++;
        }

        return placed > 0;
    }
}
