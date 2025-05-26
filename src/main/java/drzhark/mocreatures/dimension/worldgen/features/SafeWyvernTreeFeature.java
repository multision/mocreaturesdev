package drzhark.mocreatures.dimension.worldgen.features;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SafeWyvernTreeFeature extends Feature<BaseTreeFeatureConfig> {

    private final ConfiguredFeature<BaseTreeFeatureConfig, ?>[] variants;

    public SafeWyvernTreeFeature(ConfiguredFeature<BaseTreeFeatureConfig, ?>... variants) {
        super(BaseTreeFeatureConfig.CODEC);
        this.variants = variants;
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
        int y = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());
        BlockPos surfacePos = new BlockPos(pos.getX(), y - 1, pos.getZ());
        BlockState ground = world.getBlockState(surfacePos);
        BlockPos below = surfacePos.down();

        //System.out.println("[MoC] SafeWyvernTreeFeature starting at pos: " + pos);
        //System.out.println("[MoC] Checking ground block at " + surfacePos + ": " + ground.getBlock().getRegistryName());

        if (ground.getBlock() != MoCBlocks.wyvgrass) {
            //System.out.println("[MoC] ✗ Block is not wyvgrass");
            return false;
        }

        if (world.isAirBlock(below)) {
            //System.out.println("[MoC] ✗ Block below wyvgrass is air (floating): " + below);
            return false;
        }

        for (BlockPos check : BlockPos.getAllInBoxMutable(surfacePos.add(-1, 0, -1), surfacePos.add(1, 0, 1))) {
            if (world.getBlockState(check).getBlock() != MoCBlocks.wyvgrass) {
                //System.out.println("[MoC] ✗ 3x3 check failed at " + check);
                return false;
            }
        }

        ConfiguredFeature<BaseTreeFeatureConfig, ?> selected = variants[rand.nextInt(variants.length)];
        BlockPos finalTreePos = surfacePos.up();
        //System.out.println("[MoC] ✓ Using variant: " + selected.getFeature().getRegistryName() + " at " + finalTreePos);

        return selected.generate(world, generator, rand, finalTreePos);
    }
}
