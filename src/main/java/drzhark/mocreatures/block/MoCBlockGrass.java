/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Random;

public class MoCBlockGrass extends GrassBlock implements IGrowable {

    public MoCBlockGrass(AbstractBlock.Properties properties) {
        super(properties.tickRandomly().harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.PLANT));
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            if (!worldIn.isAreaLoaded(pos, 3)) return;
            if (worldIn.getLight(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getOpacity(worldIn, pos.up()) > 2) {
                if (this == MoCBlocks.wyvgrass) worldIn.setBlockState(pos, MoCBlocks.wyvdirt.getDefaultState());
            } else {
                if (worldIn.getLight(pos.up()) >= 9) {
                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                            return;
                        }

                        BlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        BlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == MoCBlocks.wyvdirt && worldIn.getLight(blockpos.up()) >= 4 && iblockstate.getOpacity(worldIn, pos.up()) <= 2) {
                            worldIn.setBlockState(blockpos, MoCBlocks.wyvgrass.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.up();

        if (this == MoCBlocks.wyvgrass) {
            for (int i = 0; i < 128; ++i) {
                BlockPos blockpos1 = blockpos;
                int j = 0;

                while (true) {
                    if (j >= i / 16) {
                        if (worldIn.isAirBlock(blockpos1)) {
                            if (rand.nextInt(8) == 0) {
                                List<ConfiguredFeature<?, ?>> list = worldIn.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
                                if (list.isEmpty()) {
                                    continue;
                                }

                                ConfiguredFeature<?, ?> configuredfeature = list.get(0);
                                FlowersFeature flowersfeature = (FlowersFeature)configuredfeature.feature;
                                BlockState blockstate1 = flowersfeature.getFlowerToPlace(rand, blockpos1, configuredfeature.getConfig());
                                if (blockstate1.isValidPosition(worldIn, blockpos1)) {
                                    worldIn.setBlockState(blockpos1, blockstate1, 3);
                                }
                            } else {
                                BlockState iblockstate1 = MoCBlocks.tallWyvgrass.getDefaultState();

                                if (((MoCBlockTallGrass) MoCBlocks.tallWyvgrass).isValidGround(iblockstate1, worldIn, blockpos1)) {
                                    worldIn.setBlockState(blockpos1, iblockstate1, 3);
                                }
                            }
                        }

                        break;
                    }

                    blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                    if (worldIn.getBlockState(blockpos1.down()).getBlock() != MoCBlocks.wyvgrass || worldIn.getBlockState(blockpos1).isNormalCube(worldIn, blockpos1)) {
                        break;
                    }

                    ++j;
                }
            }
        }
    }
}
