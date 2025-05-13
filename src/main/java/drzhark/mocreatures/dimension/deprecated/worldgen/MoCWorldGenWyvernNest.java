///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.worldgen;
//
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.BlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.gen.feature.WorldGenerator;
//
//import java.util.Random;
//
//public class MoCWorldGenWyvernNest extends WorldGenerator {
//
//    @Override
//    public boolean generate(World world, Random rand, BlockPos position) {
//        // Generate the log pillar
//        int pillarHeight = 8;
//        BlockState logState = MoCBlocks.wyvwoodLog.getDefaultState();
//
//        for (int i = 0; i < pillarHeight; i++) {
//            BlockPos currentPos = position.up(i);
//            world.setBlockState(currentPos, logState, 2);
//        }
//
//        // Generate the log circle on top of the pillar
//        int circleRadius = 3;
//        BlockState nestState = MoCBlocks.wyvernNestBlock.getDefaultState();
//
//        for (int y = 0; y <= 1; y++) {
//            for (int x = -circleRadius - y; x <= circleRadius + y; x++) {
//                for (int z = -circleRadius - y; z <= circleRadius + y; z++) {
//                    if (x * x + z * z <= (circleRadius + y) * (circleRadius + y)) {
//                        BlockPos currentPos = position.add(x, pillarHeight + y, z);
//                        world.setBlockState(currentPos, logState, 2);
//
//                        // Place nest blocks on top
//                        world.setBlockState(currentPos.up(), nestState, 2);
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//}
