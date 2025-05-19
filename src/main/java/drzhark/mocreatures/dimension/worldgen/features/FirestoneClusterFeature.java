package drzhark.mocreatures.dimension.worldgen.features;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FirestoneClusterFeature extends Feature<NoFeatureConfig> {
    public FirestoneClusterFeature() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean success = false;

        // Try a few clusters per call
        for (int attempt = 0; attempt < 3; ++attempt) {
            BlockPos base = origin.add(
                    rand.nextInt(16),
                    rand.nextInt(64) + 32,  // Y range: 32–96
                    rand.nextInt(16)
            );

            // Find a good hanging spot
            for (int offsetY = 0; offsetY < 12; ++offsetY) {
                BlockPos check = base.down(offsetY);
                if (world.isAirBlock(check) && world.getBlockState(check.up()).isSolidSide(world, check.up(), Direction.DOWN)) {

                    // Place the core block
                    world.setBlockState(check, MoCBlocks.firestone.getDefaultState(), 2);
                    success = true;

                    // Place additional blocks around it (blob effect)
                    for (int i = 0; i < 8 + rand.nextInt(5); ++i) {
                        BlockPos nearby = check.add(
                                rand.nextInt(3) - 1,
                                -rand.nextInt(2),
                                rand.nextInt(3) - 1
                        );

                        if (world.isAirBlock(nearby)) {
                            world.setBlockState(nearby, MoCBlocks.firestone.getDefaultState(), 2);
                        }
                    }

                    break;
                }
            }
        }

        return success;
    }
}
