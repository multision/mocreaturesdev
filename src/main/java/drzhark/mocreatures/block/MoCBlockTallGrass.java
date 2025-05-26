/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.*;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "mocreatures")
public class MoCBlockTallGrass extends TallGrassBlock {

    private static Feature<BlockClusterFeatureConfig> feature = null;
    public static ConfiguredFeature<?, ?> configuredFeature;

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public MoCBlockTallGrass(AbstractBlock.Properties properties) {
        //super(Effects.HASTE, 100, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f, 0.0f).setLightLevel(s -> 1));
        super(properties.sound(SoundType.PLANT).doesNotBlockMovement());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XYZ;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block soil = worldIn.getBlockState(pos.down()).getBlock();
        return soil instanceof MoCBlockGrass || soil instanceof MoCBlockDirt || soil instanceof GrassBlock || net.minecraftforge.common.Tags.Blocks.DIRT.contains(soil) || soil instanceof FarmlandBlock;
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    private static class FeatureRegisterHandler {
        private FeatureRegisterHandler() {
        }

        @SubscribeEvent
        public void registerFeature(RegistryEvent.Register<Feature<?>> event) {
            feature = new RandomPatchFeature(BlockClusterFeatureConfig.CODEC){

                public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, BlockClusterFeatureConfig config) {
                    RegistryKey dimensionType = world.getWorld().getDimensionKey();
                    boolean dimensionCriteria = false;
                    if (dimensionType == RegistryKey.getOrCreateKey((RegistryKey) Registry.WORLD_KEY, (ResourceLocation)new ResourceLocation("mocreatures:wyvernlairworld"))) {
                        dimensionCriteria = true;
                    }
                    if (!dimensionCriteria) {
                        return false;
                    }
                    return super.generate(world, generator, random, pos, config);
                }
            };


            feature.setRegistryName("mocreatures", "tall_wyvgrass");
            event.getRegistry().register(feature);

            configuredFeature = feature.withConfiguration(new BlockClusterFeatureConfig.Builder((BlockStateProvider) new SimpleBlockStateProvider(MoCBlocks.tallWyvgrass.getDefaultState()), (BlockPlacer)new SimpleBlockPlacer()).tries(64).build()).withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8, 0, 8)));
            Registry.register((Registry) WorldGenRegistries.CONFIGURED_FEATURE, (ResourceLocation)new ResourceLocation("mocreatures:tall_wyvgrass"), (Object)configuredFeature);
        }
    }
}
