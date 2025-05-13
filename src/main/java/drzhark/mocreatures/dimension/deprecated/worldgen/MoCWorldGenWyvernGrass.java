///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.worldgen;
//
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.gen.feature.WorldGenerator;
//
//import java.util.Random;
//
//public class MoCWorldGenWyvernGrass extends WorldGenerator {
//
//    /**
//     * Stores ID for WorldGenTallGrass
//     */
//    private final BlockState blockStateGrass;
//
//    public MoCWorldGenWyvernGrass(BlockState iblockstategrass) {
//        this.blockStateGrass = iblockstategrass;
//    }
//
//    @Override
//    public boolean generate(World worldIn, Random rand, BlockPos position) {
//        BlockState blockstate;
//        do {
//            blockstate = worldIn.getBlockState(position);
//            Block block = blockstate.getBlock();
//            if (!block.isAir(blockstate, worldIn, position) && !block.isLeaves(blockstate, worldIn, position)) break;
//            position = position.down();
//        } while (position.getY() > 0);
//        for (int i = 0; i < 128; ++i) {
//            BlockPos blockpos1 = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
//            if (worldIn.isAirBlock(blockpos1) && canGrassStay(worldIn, blockpos1)) {
//                worldIn.setBlockState(blockpos1, blockStateGrass, 2);
//            }
//        }
//        return true;
//    }
//
//    public boolean canGrassStay(World worldIn, BlockPos pos) {
//        Block soil = worldIn.getBlockState(pos.down()).getBlock();
//        return soil == MoCBlocks.wyvgrass || soil == MoCBlocks.wyvdirt || soil instanceof BlockGrass || soil instanceof BlockDirt || soil instanceof BlockFarmland;
//    }
//}
