package drzhark.mocreatures.dimension.worldgen;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class WyvernNestFeature {

    public static Feature<NoFeatureConfig> WYVERN_NEST_FEATURE;
    public static ConfiguredFeature<?, ?> WYVERN_NEST_CONFIGURED;

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        if (!event.getName().getNamespace().equals("mocreatures")) return;
        if (event.getName().getPath().startsWith("wyvernlair")) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> WYVERN_NEST_CONFIGURED);
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class FeatureRegisterHandler {
        @SubscribeEvent
        public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
            WYVERN_NEST_FEATURE = new Feature<NoFeatureConfig>(NoFeatureConfig.CODEC) {
                @Override
                public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
                    int x = pos.getX();
                    int z = pos.getZ();
                    int y = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                    BlockPos base = new BlockPos(x, y - 1, z);

                    for (int dx = -2; dx <= 2; dx++) {
                        for (int dz = -2; dz <= 2; dz++) {
                            BlockPos check = base.add(dx, 0, dz);
                            if (world.isAirBlock(check.down()) || world.getBlockState(check).getBlock() != MoCBlocks.wyvgrass) {
                                return false;
                            }
                        }
                    }

                    BlockState log = rand.nextBoolean() ? MoCBlocks.wyvwoodLog.getDefaultState() : Blocks.BONE_BLOCK.getDefaultState();
                    for (int i = 0; i < 8; i++) {
                        world.setBlockState(base.up(i), log, 2);
                    }

                    BlockState nest = MoCBlocks.wyvernNestBlock.getDefaultState();
                    BlockPos top = base.up(8);

                    // Build the circular nest platform
                    for (int dx = -3; dx <= 3; dx++) {
                        for (int dz = -3; dz <= 3; dz++) {
                            int distSq = dx * dx + dz * dz;
                            if (distSq <= 9) {
                                BlockPos nestPos = top.add(dx, 0, dz);
                                world.setBlockState(nestPos, nest, 2);
                            }
                        }
                    }

                    // Raise a ring wall around the nest (1 block higher)
                    for (int dx = -4; dx <= 4; dx++) {
                        for (int dz = -4; dz <= 4; dz++) {
                            int distSq = dx * dx + dz * dz;
                            if (distSq >= 10 && distSq <= 13) {
                                BlockPos wallPos = top.add(dx, 1, dz);
                                world.setBlockState(wallPos, nest, 2);
                            }
                        }
                    }

                    // Place loot chest on top of the central nest
                    BlockPos chestPos = top.up();
                    world.setBlockState(chestPos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, net.minecraft.util.Direction.NORTH), 2);
                    TileEntity tile = world.getTileEntity(chestPos);
                    if (tile instanceof LockableLootTileEntity) {
                        ((LockableLootTileEntity) tile).setLootTable(new ResourceLocation("mocreatures:chests/wyvern_nest"), rand.nextLong());
                        //((LockableLootTileEntity) tile).setLootTable(new ResourceLocation("minecraft:chests/simple_dungeon"), rand.nextLong());
                    }

                    return true;
                }
            };

            WYVERN_NEST_FEATURE.setRegistryName("mocreatures", "wyvern_nest");
            event.getRegistry().register(WYVERN_NEST_FEATURE);

            WYVERN_NEST_CONFIGURED = WYVERN_NEST_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Placement.CHANCE.configure(new net.minecraft.world.gen.placement.ChanceConfig(10)));

            net.minecraft.util.registry.Registry.register(
                    net.minecraft.util.registry.WorldGenRegistries.CONFIGURED_FEATURE,
                    new ResourceLocation("mocreatures", "wyvern_nest"),
                    WYVERN_NEST_CONFIGURED);
        }
    }
}
