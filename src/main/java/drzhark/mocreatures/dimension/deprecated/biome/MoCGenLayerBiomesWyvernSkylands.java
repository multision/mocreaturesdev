///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.biome;
//
//import drzhark.mocreatures.init.MoCBiomes;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.gen.layer.GenLayer;
//import net.minecraft.world.gen.layer.IntCache;
//
//// Courtesy of Dragonisser
//public class MoCGenLayerBiomesWyvernSkylands extends GenLayer {
//
//    protected Biome[] allowedBiomes = {MoCBiomes.wyvernIsles, MoCBiomes.wyvernIsles, MoCBiomes.wyvernDesertIsles};
//
//    public MoCGenLayerBiomesWyvernSkylands(long seed, GenLayer genlayer) {
//        super(seed);
//        this.parent = genlayer;
//    }
//
//    public MoCGenLayerBiomesWyvernSkylands(long seed) {
//        super(seed);
//    }
//
//    @Override
//    public int[] getInts(int x, int z, int width, int depth) {
//        int[] dest = IntCache.getIntCache(width * depth);
//
//        for (int dz = 0; dz < depth; dz++) {
//            for (int dx = 0; dx < width; dx++) {
//                this.initChunkSeed(dx + x, dz + z);
//                dest[(dx + dz * width)] = Biome.getIdForBiome(this.allowedBiomes[nextInt(this.allowedBiomes.length)]);
//            }
//        }
//        return dest;
//    }
//}
