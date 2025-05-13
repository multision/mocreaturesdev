///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension;
//
//import drzhark.mocreatures.MoCreatures;
//import drzhark.mocreatures.dimension.biome.MoCBiomeProviderWyvernSkylands;
//import drzhark.mocreatures.dimension.chunk.MoCChunkProviderWyvernSkylands;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.material.Material;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.vector.Vector3d;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraft.world.DimensionType;
//import net.minecraft.world.WorldProviderSurface;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeProvider;
//import net.minecraft.world.chunk.Chunk;
//import net.minecraft.world.gen.IChunkGenerator;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.List;
//import java.util.Random;
//
//public class MoCWorldProviderWyvernSkylands extends WorldProviderSurface {
//
//    @Override
//    protected void init() {
//        this.biomeProvider = new MoCBiomeProviderWyvernSkylands(this.world);
//        this.hasSkyLight = true;
//        setDimension(MoCreatures.wyvernSkylandsDimensionID);
//        setCustomSky();
//    }
//
//    @Override
//    public IChunkGenerator createChunkGenerator() {
//        return new MoCChunkProviderWyvernSkylands(this.world);
//    }
//
//    private void setCustomSky() {
//        if (!this.world.isRemote) {
//            return;
//        }
//
//        // It'll do for now until the custom sky renderer is ever expanded upon
//        if (MoCreatures.proxy.legacyWyvernLairSky) setSkyRenderer(new MoCSkyRenderer());
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
//        return MoCreatures.proxy.legacyWyvernLairSky ? null : super.calcSunriseSunsetColors(celestialAngle, partialTicks);
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public Vector3d getFogColor(float par1, float par2) {
//        float var4 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
//
//        // Classic Sky
//        if (MoCreatures.proxy.legacyWyvernLairSky) {
//            if (var4 < 0.0F) {
//                var4 = 0.0F;
//            }
//
//            if (var4 > 1.0F) {
//                var4 = 1.0F;
//            }
//
//            float var5 = 0 / 255.0F;
//            float var6 = 98 / 255.0F;
//            float var7 = 73 / 255.0F;
//
//            var5 *= var4 * 0.0F + 0.15F;
//            var6 *= var4 * 0.0F + 0.15F;
//            var7 *= var4 * 0.0F + 0.15F;
//
//            return new Vector3d(var5, var6, var7);
//        }
//
//        // New Sky
//        else {
//            if (var4 < 0.0F) {
//                var4 = 0.0F;
//            }
//
//            if (var4 > 1.0F) {
//                var4 = 1.0F;
//            }
//
//            float var5 = 200 / 255.0F;
//            float var6 = 220 / 255.0F;
//            float var7 = 190 / 255.0F;
//
//            var5 *= var4 * (var4 * 0.94F + 0.06F);
//            var6 *= var4 * (var4 * 0.94F + 0.06F);
//            var7 *= var4 * (var4 * 0.91F + 0.09F);
//
//            return new Vector3d(var5, var6, var7);
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public boolean isSkyColored() {
//        return true;
//    }
//
//    @Override
//    public boolean canRespawnHere() {
//        return false;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public float getCloudHeight() {
//        return -5.0F;
//    }
//
//    @Override
//    public double getHorizon() {
//        return 0.0;
//    }
//
//    @Override
//    public boolean canCoordinateBeSpawn(int xPos, int zPos) {
//        BlockPos pos = this.world.getTopSolidOrLiquidBlock(new BlockPos(xPos, 0, zPos));
//        BlockState blockState = this.world.getBlockState(pos);
//        Block block = blockState.getBlock();
//        Material material = blockState.getMaterial();
//        return material.blocksMovement() && !block.isLeaves(blockState, this.world, pos) && !block.isFoliage(this.world, pos);
//    }
//
//    @Override
//    public BlockPos getSpawnCoordinate() {
//        BiomeProvider biomeprovider = this.getBiomeProvider();
//        List<Biome> list = biomeprovider.getBiomesToSpawnIn();
//        Random random = new Random(this.getSeed());
//        BlockPos blockpos = biomeprovider.findBiomePosition(0, 0, 256, list, random);
//        int i = 8;
//        int j = 128;
//        int k = 8;
//
//        if (blockpos != null) {
//            i = blockpos.getX();
//            k = blockpos.getZ();
//        } else {
//            MoCreatures.LOGGER.warn("Unable to find spawn biome");
//        }
//
//        int attempts = 0;
//        while (attempts < 1000) {
//            i += random.nextInt(64) - random.nextInt(64);
//            k += random.nextInt(64) - random.nextInt(64);
//
//            // Check for a valid spawn point
//            if (canCoordinateBeSpawn(i, k)) {
//                return this.world.getHeight(new BlockPos(i, j, k)).up();
//            }
//
//            attempts++;
//        }
//
//        // If no valid spawn point is found after 1000 attempts, return a default spawn point
//        return this.world.getHeight(new BlockPos(i, j, k)).up();
//    }
//
//    // No bed explosions allowed
//    @Override
//    public WorldSleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
//        Random message = player.world.rand;
//        int random;
//        random = message.nextInt(4);
//        switch (random) {
//            case 0:
//                player.sendStatusMessage(new TranslationTextComponent("msg.mocreatures.bed1"), true);
//                break;
//            case 1:
//                player.sendStatusMessage(new TranslationTextComponent("msg.mocreatures.bed2"), true);
//                break;
//            case 2:
//                player.sendStatusMessage(new TranslationTextComponent("msg.mocreatures.bed3"), true);
//                break;
//            case 3:
//                player.sendStatusMessage(new TranslationTextComponent("msg.mocreatures.bed4"), true);
//                break;
//        }
//
//        return WorldSleepResult.DENY;
//    }
//
//    @Override
//    public boolean canDoLightning(Chunk chunk) {
//        return false;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public boolean doesXZShowFog(int par1, int par2) {
//        return MoCreatures.proxy.foggyWyvernLair;
//    }
//
//    @Override
//    public DimensionType getDimensionType() {
//        return MoCreatures.WYVERN_SKYLANDS;
//    }
//
//    // No custom sun and moon yet but the textures are both here for now
//    public String getSunTexture() {
//        return MoCreatures.proxy.getMiscTexture("twin_suns.png").toString();
//    }
//
//    public String getMoonTexture() {
//        return MoCreatures.proxy.getMiscTexture("moon_phases.png").toString();
//    }
//
//    @Override
//    public double getMovementFactor() {
//        return 1.0;
//    }
//}
