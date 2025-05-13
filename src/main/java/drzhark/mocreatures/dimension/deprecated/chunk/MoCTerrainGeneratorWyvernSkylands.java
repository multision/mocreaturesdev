///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.chunk;
//
//import drzhark.mocreatures.MoCTools;
//import drzhark.mocreatures.dimension.worldgen.MoCWorldGenMinable;
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockFalling;
//import net.minecraft.block.BlockFlower;
//import net.minecraft.block.Blocks;
//import net.minecraft.block.material.Material;
//import net.minecraft.entity.EntityClassification;
//import net.minecraft.entity.MobEntity;
//import net.minecraft.init.Biomes;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.ChunkPrimer;
//import net.minecraft.world.gen.MapGenCaves;
//import net.minecraft.world.gen.NoiseGeneratorOctaves;
//import net.minecraft.world.gen.NoiseGeneratorPerlin;
//
//import java.util.Random;
//
//// Courtesy of lhns
//public class MoCTerrainGeneratorWyvernSkylands extends MoCTerrainGenerator {
//    private final NoiseGeneratorOctaves terrainNoise1;
//    private final NoiseGeneratorOctaves terrainNoise2;
//    private final NoiseGeneratorOctaves terrainNoise3;
//    private final NoiseGeneratorOctaves biomeBlocksNoise;
//    private final NoiseGeneratorPerlin treeNoise;
//
//    private final MapGenCaves mapGenCaves = new MapGenCaves();
//
//    public MoCTerrainGeneratorWyvernSkylands(World world, Random random) {
//        super(world, random);
//
//        this.terrainNoise1 = new NoiseGeneratorOctaves(random, 16);
//        this.terrainNoise2 = new NoiseGeneratorOctaves(random, 16);
//        this.terrainNoise3 = new NoiseGeneratorOctaves(random, 8);
//        this.biomeBlocksNoise = new NoiseGeneratorOctaves(random, 4);
//        this.treeNoise = new NoiseGeneratorPerlin(random, 8);
//    }
//
//    @Override
//    public void generate(int chunkX, int chunkZ, ChunkPrimer primer) {
//        Biome[] biomes = world.getBiomeProvider().getBiomes(new Biome[0], chunkX * 16, chunkZ * 16, 16, 16);
//        generateStone(chunkX, chunkZ, primer);
//        replaceBiomeBlocks(chunkX, chunkZ, primer, biomes);
//        mapGenCaves.generate(world, chunkX, chunkZ, primer);
//    }
//
//    private double[] getNoiseArray(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize) {
//        double[] noiseArray = new double[xSize * ySize * zSize];
//
//        double hScale = 684.41200000000003D * 2D;
//        double vScale = 684.41200000000003D;
//
//        double[] terrainNoiseArray1 = terrainNoise1.generateNoiseOctaves(null, xOffset, yOffset, zOffset, xSize, ySize, zSize, hScale, vScale, hScale);
//        double[] terrainNoiseArray2 = terrainNoise2.generateNoiseOctaves(null, xOffset, yOffset, zOffset, xSize, ySize, zSize, hScale, vScale, hScale);
//        double[] terrainNoiseArray3 = terrainNoise3.generateNoiseOctaves(null, xOffset, yOffset, zOffset, xSize, ySize, zSize, hScale / 80D, vScale / 160D, hScale / 80D);
//
//        int index = 0;
//        for (int x = 0; x < xSize; x++) {
//            for (int z = 0; z < zSize; z++) {
//                for (int y = 0; y < ySize; y++) {
//                    double terrainNoiseValue1 = terrainNoiseArray1[index] / 512D;
//                    double terrainNoiseValue2 = terrainNoiseArray2[index] / 512D;
//                    double terrainNoiseValue3 = (terrainNoiseArray3[index] / 10D + 1.0D) / 2D;
//
//                    double noise;
//                    if (terrainNoiseValue3 < 0.0D) noise = terrainNoiseValue1;
//                    else if (terrainNoiseValue3 > 1.0D) noise = terrainNoiseValue2;
//                    else noise = terrainNoiseValue1 + (terrainNoiseValue2 - terrainNoiseValue1) * terrainNoiseValue3;
//
//                    noise -= 8D;
//
//                    if (y > ySize - 32) {
//                        double multiplicator = (y - (ySize - 32)) / (32 - 1.0);
//                        noise = noise * (1.0 - multiplicator) + -30 * multiplicator;
//                    }
//                    if (y < 8) {
//                        double multiplicator = (8 - y) / (8 - 1.0);
//                        noise = noise * (1.0 - multiplicator) + -30 * multiplicator;
//                    }
//                    noiseArray[index] = noise;
//                    index++;
//                }
//            }
//        }
//
//        return noiseArray;
//    }
//
//    private void generateStone(int chunkX, int chunkZ, ChunkPrimer primer) {
//        double[] noiseArray = getNoiseArray(chunkX * 2, 0, chunkZ * 2, 3, 33, 3);
//
//        for (int i1 = 0; i1 < 2; i1++) {
//            for (int j1 = 0; j1 < 2; j1++) {
//                for (int k1 = 0; k1 < 32; k1++) {
//                    double noiseOffset1 = noiseArray[(i1 * 3 + j1) * 33 + k1];
//                    double noiseOffset2 = noiseArray[(i1 * 3 + (j1 + 1)) * 33 + k1];
//                    double noiseOffset3 = noiseArray[((i1 + 1) * 3 + j1) * 33 + k1];
//                    double noiseOffset4 = noiseArray[((i1 + 1) * 3 + (j1 + 1)) * 33 + k1];
//
//                    double noiseIncreaseScale = 0.25D;
//                    double noiseIncrease1 = (noiseArray[(i1 * 3 + j1) * 33 + (k1 + 1)] - noiseOffset1) * noiseIncreaseScale;
//                    double noiseIncrease2 = (noiseArray[(i1 * 3 + (j1 + 1)) * 33 + (k1 + 1)] - noiseOffset2) * noiseIncreaseScale;
//                    double noiseIncrease3 = (noiseArray[((i1 + 1) * 3 + j1) * 33 + (k1 + 1)] - noiseOffset3) * noiseIncreaseScale;
//                    double noiseIncrease4 = (noiseArray[((i1 + 1) * 3 + (j1 + 1)) * 33 + (k1 + 1)] - noiseOffset4) * noiseIncreaseScale;
//
//                    for (int l1 = 0; l1 < 4; l1++) {
//                        double noiseIncrease2Scale = 0.125D;
//                        double noiseValue1 = noiseOffset1;
//                        double noiseValue2 = noiseOffset2;
//
//                        double noiseIncrease21 = (noiseOffset3 - noiseOffset1) * noiseIncrease2Scale;
//                        double noiseIncrease22 = (noiseOffset4 - noiseOffset2) * noiseIncrease2Scale;
//
//                        for (int i2 = 0; i2 < 8; i2++) {
//                            int x = j1 * 8;
//                            int y = k1 * 4 + l1;
//                            int z = i2 + i1 * 8;
//
//                            double terrainDensityIncreaseScale = 0.125D;
//                            double terrainDensity = noiseValue1;
//                            double terrainDensityIncrease = (noiseValue2 - noiseValue1) * terrainDensityIncreaseScale;
//
//                            for (int j2 = 0; j2 < 8; j2++) {
//                                primer.setBlockState(z, y, x, terrainDensity > 0.0D ? MoCBlocks.wyvstone.getDefaultState() : Blocks.AIR.getDefaultState());
//
//                                x++;
//                                terrainDensity += terrainDensityIncrease;
//                            }
//
//                            noiseValue1 += noiseIncrease21;
//                            noiseValue2 += noiseIncrease22;
//                        }
//
//                        noiseOffset1 += noiseIncrease1;
//                        noiseOffset2 += noiseIncrease2;
//                        noiseOffset3 += noiseIncrease3;
//                        noiseOffset4 += noiseIncrease4;
//                    }
//                }
//            }
//        }
//    }
//
//    private void replaceBiomeBlocks(int xChunk, int zChunk, ChunkPrimer primer, Biome[] biomes) {
//        double scale = 0.03125D;
//
//        double[] biomeBlocksNoiseArray = biomeBlocksNoise.generateNoiseOctaves(new double[256], xChunk * 16, zChunk * 16, 0, 16, 16, 1, scale * 2D, scale * 2D, scale * 2D);
//
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                Biome biome = biomes[x + z * 16];
//                int biomeBlocksNoiseValue = (int) (biomeBlocksNoiseArray[x + z * 16] / 3D + 3D + random.nextDouble() * 0.25D);
//
//                int biomeBlocksLeft = -1;
//                Block topBlock = biome.topBlock.getBlock();
//                Block fillerBlock = biome.fillerBlock.getBlock();
//
//                for (int y = 127; y >= 0; y--) {
//                    Block block = primer.getBlockState(x, y, z).getBlock();
//                    if (block == Blocks.AIR) biomeBlocksLeft = -1;
//                    else if (block == MoCBlocks.wyvstone) {
//                        if (biomeBlocksLeft == -1) {
//                            if (biomeBlocksNoiseValue <= 0) {
//                                topBlock = Blocks.AIR;
//                                fillerBlock = MoCBlocks.wyvstone;
//                            }
//
//                            biomeBlocksLeft = biomeBlocksNoiseValue;
//
//                            primer.setBlockState(x, y, z, y >= 0 ? topBlock.getDefaultState() : fillerBlock.getDefaultState());
//                        } else if (biomeBlocksLeft > 0) {
//                            biomeBlocksLeft--;
//                            primer.setBlockState(x, y, z, fillerBlock.getDefaultState());
//
//                            if (biomeBlocksLeft == 0 && fillerBlock == MoCBlocks.silverSand) {
//                                biomeBlocksLeft = random.nextInt(4);
//                                fillerBlock = MoCBlocks.silverSandstone;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void populate(int chunkX, int chunkZ) {
//        BlockFalling.fallInstantly = true;
//
//        BlockPos chunkWorldPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
//
//        Biome biome = world.getBiome(chunkWorldPos.add(16, 0, 16));
//
//        Random populateRandom = new Random(world.getSeed());
//        populateRandom.setSeed(chunkX * ((populateRandom.nextLong() / 2L) * 2L + 1L) + chunkZ * ((populateRandom.nextLong() / 2L) * 2L + 1L) ^ world.getSeed());
//
//        if (populateRandom.nextInt(4) == 0) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenLakes(Blocks.WATER).generate(world, populateRandom, pos);
//        }
//
//        if (populateRandom.nextInt(8) == 0) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(populateRandom.nextInt(120) + 8), populateRandom.nextInt(16) + 8);
//            if (pos.getY() < 64 || populateRandom.nextInt(10) == 0)
//                new WorldGenLakes(Blocks.LAVA).generate(world, populateRandom, pos);
//        }
//
//        // TODO: Coal Ore?
//        //for (int i = 0; i < 20; i++) {
//        //    BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16), 128 + populateRandom.nextInt(64), populateRandom.nextInt(16));
//        //    new MoCWorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 16).generate(world, populateRandom, pos);
//        //}
//
//        for (int i = 0; i < 20; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16), 128 + populateRandom.nextInt(64), populateRandom.nextInt(16));
//            new MoCWorldGenMinable(MoCBlocks.wyvernIronOre.getDefaultState(), 8).generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < 2; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16), 128 + populateRandom.nextInt(64), populateRandom.nextInt(16));
//            new MoCWorldGenMinable(MoCBlocks.wyvernGoldOre.getDefaultState(), 8).generate(world, populateRandom, pos);
//        }
//
//        // TODO: Redstone Ore?
//        //for (int i = 0; i < 8; i++) {
//        //    BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16), 128 + populateRandom.nextInt(64), populateRandom.nextInt(16));
//        //    new MoCWorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7).generate(world, populateRandom, pos);
//        //}
//
//        for (int i = 0; i < 1; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16), 128 + populateRandom.nextInt(64), populateRandom.nextInt(16));
//            new MoCWorldGenMinable(MoCBlocks.wyvernDiamondOre.getDefaultState(), 7).generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < 1; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16), 128 + populateRandom.nextInt(64), populateRandom.nextInt(16));
//            new MoCWorldGenMinable(MoCBlocks.wyvernLapisOre.getDefaultState(), 6).generate(world, populateRandom, pos);
//        }
//
//        int treeNoiseValue = (int) ((treeNoise.getValue(chunkWorldPos.getX() * 0.5D, chunkWorldPos.getZ() * 0.5D) / 8D + populateRandom.nextDouble() * 4D + 4D) / 3D);
//
//        int numTrees = 0;
//
//        if (populateRandom.nextInt(10) == 0) numTrees++;
//
//        if (biome == Biomes.FOREST) numTrees += treeNoiseValue + 5;
//        if (biome == Biomes.FOREST_HILLS) numTrees += treeNoiseValue + 5;
//        if (biome == Biomes.BIRCH_FOREST) numTrees += treeNoiseValue + 2;
//        if (biome == Biomes.TAIGA) numTrees += treeNoiseValue + 5;
//        if (biome == Biomes.DESERT) numTrees -= 20;
//        if (biome == Biomes.COLD_TAIGA) numTrees -= 20;
//        if (biome == Biomes.PLAINS) numTrees -= 20;
//
//        for (int i = 0; i < numTrees; i++) {
//            BlockPos xzPos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, 0, populateRandom.nextInt(16) + 8);
//            BlockPos pos = xzPos.add(0, world.getHeight(xzPos.getX(), xzPos.getZ()), 0);
//
//            WorldGenAbstractTree treeGenerator = biome.getRandomTreeFeature(populateRandom);
//            treeGenerator.generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < 2; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.DANDELION).generate(world, populateRandom, pos);
//        }
//
//        if (populateRandom.nextInt(2) == 0) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenFlowers(Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY).generate(world, populateRandom, pos);
//        }
//
//        if (populateRandom.nextInt(4) == 0) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenBush(Blocks.BROWN_MUSHROOM).generate(world, populateRandom, pos);
//        }
//
//        if (populateRandom.nextInt(8) == 0) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenBush(Blocks.RED_MUSHROOM).generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < 10; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenReed().generate(world, populateRandom, pos);
//        }
//
//        if (populateRandom.nextInt(32) == 0) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenPumpkin().generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < (biome == Biomes.DESERT ? 10 : 0); i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(128), populateRandom.nextInt(16) + 8);
//            new WorldGenCactus().generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < 50; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(populateRandom.nextInt(120) + 8), populateRandom.nextInt(16) + 8);
//            new WorldGenLiquids(Blocks.FLOWING_WATER).generate(world, populateRandom, pos);
//        }
//
//        for (int i = 0; i < 20; i++) {
//            BlockPos pos = chunkWorldPos.add(populateRandom.nextInt(16) + 8, populateRandom.nextInt(populateRandom.nextInt(populateRandom.nextInt(112) + 8) + 8), populateRandom.nextInt(16) + 8);
//            new WorldGenLiquids(Blocks.FLOWING_LAVA).generate(world, populateRandom, pos);
//        }
//
//        for (int x2 = 0; x2 < 16; x2++) {
//            for (int z2 = 0; z2 < 16; z2++) {
//                BlockPos xzPos = chunkWorldPos.add(x2 + 8, 0, z2 + 8);
//                BlockPos snowPos = world.getTopSolidOrLiquidBlock(xzPos);
//
//                if (world.canSnowAt(snowPos, true) && snowPos.getY() > 0 && snowPos.getY() < 128 && world.isAirBlock(snowPos) && world.getBlockState(snowPos.add(0, -1, 0)).getMaterial().isSolid() && world.getBlockState(snowPos.add(0, -1, 0)).getMaterial() != Material.ICE) {
//                    world.setBlockState(snowPos, Blocks.SNOW.getDefaultState());
//                }
//            }
//        }
//
//        biome.decorate(world, random, chunkWorldPos);
//
//        MoCTools.performCustomWorldGenSpawning(world, biome, chunkWorldPos.getX(), chunkWorldPos.getZ(), 16, 16, random, biome.getSpawnableList(EntityClassification.CREATURE), MobEntity.SpawnPlacementType.ON_GROUND);
//
//        BlockFalling.fallInstantly = false;
//    }
//}
