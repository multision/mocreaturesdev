///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.biome;
//
//import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
//import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
//import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
//import drzhark.mocreatures.entity.passive.MoCEntityFilchLizard;
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.material.Material;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.MobSpawnInfo;
//import net.minecraft.world.chunk.ChunkPrimer;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.Random;
//
//public class MoCBiomeWyvernIslesDesert extends Biome {
//
//    public MoCBiomeWyvernIslesDesert(BiomeProperties biomeProperties) {
//        super(biomeProperties);
//        this.spawnableCreatureList.clear();
//        this.spawnableMonsterList.clear();
//        this.spawnableWaterCreatureList.clear();
//        this.spawnableCaveCreatureList.clear();
//        this.modSpawnableLists.clear();
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntityFilchLizard.class, 6, 2, 4));
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntityGrasshopper.class, 6, 2, 4));
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntitySnake.class, 6, 1, 3));
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntityWyvern.class, 12, 2, 3));
//        this.topBlock = MoCBlocks.silverSand.getDefaultState();
//        this.fillerBlock = MoCBlocks.silverSandstone.getDefaultState();
//        this.decorator = new MoCBiomeWyvernIslesDesertDecorator();
//    }
//
//    @Override
//    public void genTerrainBlocks(World world, Random random, ChunkPrimer primer, int x, int z, double noiseVal) {
//        int i = world.getSeaLevel();
//        BlockState iblockstate = this.topBlock;
//        BlockState iblockstate1 = this.fillerBlock;
//        int j = -1;
//        int k = (int) (noiseVal / 3.0D + 3.0D + random.nextDouble() * 0.25D);
//        int l = x & 15;
//        int i1 = z & 15;
//        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
//
//        for (int j1 = 255; j1 >= 0; --j1) {
//            if (j1 <= random.nextInt(5)) {
//                primer.setBlockState(i1, j1, l, BEDROCK);
//            } else {
//                BlockState iblockstate2 = primer.getBlockState(i1, j1, l);
//
//                if (iblockstate2.getMaterial() == Material.AIR) {
//                    j = -1;
//                } else if (iblockstate2.getBlock() == Blocks.STONE || iblockstate2.getBlock() == MoCBlocks.wyvstone) {
//                    if (j == -1) {
//                        if (k <= 0) {
//                            iblockstate = AIR;
//                            iblockstate1 = STONE;
//                        } else if (j1 >= i - 4 && j1 <= i + 1) {
//                            iblockstate = this.topBlock;
//                            iblockstate1 = this.fillerBlock;
//                        }
//
//                        if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
//                            if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
//                                iblockstate = ICE;
//                            } else {
//                                iblockstate = WATER;
//                            }
//                        }
//
//                        j = k;
//
//                        if (j1 >= i - 1) {
//                            primer.setBlockState(i1, j1, l, iblockstate);
//                        } else if (j1 < i - 7 - k) {
//                            iblockstate = AIR;
//                            iblockstate1 = STONE;
//                            primer.setBlockState(i1, j1, l, GRAVEL);
//                        } else {
//                            primer.setBlockState(i1, j1, l, iblockstate1);
//                        }
//                    } else if (j > 0) {
//                        --j;
//                        primer.setBlockState(i1, j1, l, iblockstate1);
//
//                        if (j == 0 && iblockstate1.getBlock() == MoCBlocks.silverSand && k > 1) {
//                            j = random.nextInt(4) + Math.max(0, j1 - 63);
//                            iblockstate1 = MoCBlocks.silverSandstone.getDefaultState();
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void decorate(World world, Random random, BlockPos pos) {
//        super.decorate(world, random, pos);
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getModdedBiomeFoliageColor(final int original) {
//        return 0x8C95A5;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getModdedBiomeGrassColor(final int original) {
//        return 0x8C95A5;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getSkyColorByTemp(final float currentTemperature) {
//        return 0xADB7CC;
//    }
//}
