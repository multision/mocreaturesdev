//package drzhark.mocreatures.dimension.worldgen;
//
//import com.google.common.base.Predicate;
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.BlockState;
//import net.minecraft.world.gen.feature.WorldGenMinable;
//
//public class MoCWorldGenMinable extends WorldGenMinable {
//
//    public MoCWorldGenMinable(BlockState state, int blockCount) {
//        this(state, blockCount, new WyvstonePredicate());
//    }
//
//    public MoCWorldGenMinable(BlockState state, int blockCount, Predicate<BlockState> predicate) {
//        super(state, blockCount, predicate);
//    }
//
//    static class WyvstonePredicate implements Predicate<BlockState> {
//        private WyvstonePredicate() {
//        }
//
//        public boolean apply(BlockState blockState) {
//            return blockState != null && blockState.getBlock() == MoCBlocks.wyvstone;
//        }
//    }
//}
