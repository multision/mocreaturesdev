package drzhark.mocreatures.dimension;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

// TODO: 2084, 64, 2011 <- These coords will be where we take the player.

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD)
public class WyvernLairWorldDimension {

    private static PointOfInterestType poi = null;


    public WyvernLairWorldDimension() {
        //FMLJavaModLoadingContext.get().getModEventBus().register();
    }

    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {
        HashSet<Block> replaceableBlocks = new HashSet<Block>();

        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlairlandsforest"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getTop().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlairlandsforest"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getUnder().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlairlands"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getTop().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlairlands"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getUnder().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlair_mountains"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getTop().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlair_mountains"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getUnder().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlair_desertlands"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getTop().getBlock());
        replaceableBlocks.add(((ConfiguredSurfaceBuilder)((Biome)ForgeRegistries.BIOMES.getValue(new ResourceLocation("mocreatures:wyvernlair_desertlands"))).getGenerationSettings().getSurfaceBuilder().get()).getConfig().getUnder().getBlock());

        DeferredWorkQueue.runLater(() -> {
            try {
                Set<Block> caveReplaceable = ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CAVE, "field_222718_j");
                Set<Block> canyonReplaceable = ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CANYON, "field_222718_j");

                ImmutableSet<Block> newCaveSet = ImmutableSet.<Block>builder()
                        .addAll(caveReplaceable)
                        .addAll(replaceableBlocks)
                        .build();

                ImmutableSet<Block> newCanyonSet = ImmutableSet.<Block>builder()
                        .addAll(canyonReplaceable)
                        .addAll(replaceableBlocks)
                        .build();

                ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CAVE, newCaveSet, "field_222718_j");
                ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CANYON, newCanyonSet, "field_222718_j");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnlyIn(value = Dist.CLIENT)
    @SubscribeEvent
    public void clientLoad(FMLClientSetupEvent event) {
        DimensionRenderInfo customEffect = new DimensionRenderInfo(128.0f, true, DimensionRenderInfo.FogType.NORMAL, false, false) {
            @Override
            public Vector3d func_230494_a_(Vector3d color, float sunHeight) {
                return color.mul((double)sunHeight * 0.94 + 0.06, (double)sunHeight * 0.94 + 0.06, (double)sunHeight * 0.91 + 0.09);
            }

            @Override
            public boolean func_230493_a_(int x, int y) {
                return false;
            }
        };

        DeferredWorkQueue.runLater(() -> {
           try {
               Object2ObjectMap effectsRegistry = (Object2ObjectMap) ObfuscationReflectionHelper.getPrivateValue(DimensionRenderInfo.class, null, (String) "field_239208_a_");
               effectsRegistry.put((Object)new ResourceLocation("mocreatures:wyvernlairworld"), (Object)customEffect);
           } catch (Exception e) {
               e.printStackTrace();
           }
        });
    }

}
