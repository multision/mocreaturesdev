/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.init.MoCEntities;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoCEventHooksTerrain {

    public static final Object2ObjectOpenHashMap<ResourceLocation, List<MobSpawnInfo.Spawners>> creatureSpawnMap = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<ResourceLocation, List<MobSpawnInfo.Spawners>> waterCreatureSpawnMap = new Object2ObjectOpenHashMap<>();



    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
    //public static void buildWorldGenSpawnLists() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            ResourceLocation biomeKey = ForgeRegistries.BIOMES.getKey(biome);
            if (biomeKey == null) continue;

            List<MobSpawnInfo.Spawners> creatureList = new ArrayList<>(biome.getMobSpawnInfo().getSpawners(EntityClassification.CREATURE));
            List<MobSpawnInfo.Spawners> waterList = new ArrayList<>(biome.getMobSpawnInfo().getSpawners(EntityClassification.WATER_CREATURE));

            /*creatureList.removeIf(entry -> {
                try {
                    return entry.itemWeight <= 0 || !(entry.type.create(null) instanceof IMoCEntity);
                } catch (Exception e) {
                    return true;
                }
            });

            waterList.removeIf(entry -> {
                try {
                    return entry.itemWeight <= 0 || !(entry.type.create(null) instanceof IMoCEntity);
                } catch (Exception e) {
                    return true;
                }
            });*/

            creatureSpawnMap.put(biomeKey, creatureList);
            waterCreatureSpawnMap.put(biomeKey, waterList);
        }
    }

    /*@SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        List<MobSpawnInfo.Spawners> creatureList = new ArrayList<>(event.getSpawns().getSpawner(EntityClassification.CREATURE));
        List<MobSpawnInfo.Spawners> waterList = new ArrayList<>(event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE));

        //creatureList.removeIf(entry -> entry.weight == 0); // Simplified filter, rely on registration elsewhere
        //waterList.removeIf(entry -> entry.weight == 0);

        creatureSpawnMap.put(event.getName(), creatureList);
        waterCreatureSpawnMap.put(event.getName(), waterList);
    }*/

    @SuppressWarnings("DataFlowIssue")
    public static void addBiomeTypes() {
        // Extreme Hills replacements (Forge 1.16+)
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("minecraft:mountains")), BiomeDictionary.Type.MOUNTAIN);
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("minecraft:wooded_mountains")), BiomeDictionary.Type.MOUNTAIN);

        // Mesa biome support
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("minecraft:badlands")), BiomeDictionary.Type.MESA);
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("minecraft:eroded_badlands")), BiomeDictionary.Type.MESA);
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("minecraft:wooded_badlands_plateau")), BiomeDictionary.Type.MESA);

        // Traverse support
        ResourceLocation rockyPlateau = new ResourceLocation("traverse:rocky_plateau");
        if (ForgeRegistries.BIOMES.containsKey(rockyPlateau)) {
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, rockyPlateau), BiomeDictionary.Type.PLAINS);
        }

        ResourceLocation aridHighland = new ResourceLocation("traverse:arid_highland");
        if (ForgeRegistries.BIOMES.containsKey(aridHighland)) {
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, aridHighland), BiomeDictionary.Type.SAVANNA);
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;

        ServerWorld world = (ServerWorld) event.getWorld();
        Chunk chunk = (Chunk) event.getChunk();
        BlockPos pos = new BlockPos(chunk.getPos().getXStart() + 8, 0, chunk.getPos().getZStart() + 8);
        Biome biome = world.getBiome(pos);

        ResourceLocation biomeKey = ForgeRegistries.BIOMES.getKey(biome);
        if (biomeKey == null) return;

        Random rand = world.rand;

        List<MobSpawnInfo.Spawners> creatureList = creatureSpawnMap.get(biomeKey);
        List<MobSpawnInfo.Spawners> waterList = waterCreatureSpawnMap.get(biomeKey);

        if (creatureList != null) {
            MoCTools.performCustomWorldGenSpawning(world, biome, pos.getX(), pos.getZ(), 16, 16, rand, creatureList, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
        }

        if (waterList != null) {
            MoCTools.performCustomWorldGenSpawning(world, biome, pos.getX(), pos.getZ(), 16, 16, rand, waterList, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
        }
    }
}



//public class MoCEventHooksTerrain {

//    public static List<Biome.MobSpawnInfo.Spawners> creatureList = new ArrayList<>(); //TODO FINISHED
//    public static List<Biome.MobSpawnInfo.Spawners> waterCreatureList = new ArrayList<>();
//    public static Object2ObjectOpenHashMap<Biome, List<Biome.MobSpawnInfo.Spawners>> creatureSpawnMap = new Object2ObjectOpenHashMap<>();
//    public static Object2ObjectOpenHashMap<Biome, List<Biome.MobSpawnInfo.Spawners>> waterCreatureSpawnMap = new Object2ObjectOpenHashMap<>();
//
//    public static void buildWorldGenSpawnLists() {
//        for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) {
//            creatureList = new ArrayList<>(biome.getSpawnableList(EntityClassification.CREATURE));
//            creatureList.removeIf(entry -> entry.itemWeight == 0 || !IMoCEntity.class.isAssignableFrom(entry.entityClass));
//            creatureSpawnMap.put(biome, creatureList);
//
//            waterCreatureList = new ArrayList<>(biome.getSpawnableList(EntityClassification.WATER_CREATURE));
//            waterCreatureList.removeIf(entry -> entry.itemWeight == 0 || !IMoCEntity.class.isAssignableFrom(entry.entityClass));
//            waterCreatureSpawnMap.put(biome, waterCreatureList);
//        }
//    }
//
//    @SuppressWarnings("DataFlowIssue")
//    public static void addBiomeTypes() {
//        // Extreme Hills
//        BiomeDictionary.addTypes(Biomes.EXTREME_HILLS, MoCEntities.STEEP);
//        BiomeDictionary.addTypes(Biomes.EXTREME_HILLS_EDGE, MoCEntities.STEEP);
//        BiomeDictionary.addTypes(Biomes.MUTATED_EXTREME_HILLS, MoCEntities.STEEP);
//        BiomeDictionary.addTypes(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, MoCEntities.STEEP);
//
//        // Mesa
//        BiomeDictionary.addTypes(Biomes.MUTATED_MESA, BiomeDictionary.Type.MESA);
//        BiomeDictionary.addTypes(Biomes.MUTATED_MESA_ROCK, BiomeDictionary.Type.MESA);
//        BiomeDictionary.addTypes(Biomes.MUTATED_MESA_CLEAR_ROCK, BiomeDictionary.Type.MESA);
//
//        // Traverse
//        ResourceLocation rockyPlateau = new ResourceLocation("traverse:rocky_plateau");
//        if (ForgeRegistries.BIOMES.containsKey(rockyPlateau)) {
//            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, rockyPlateau), BiomeDictionary.Type.PLAINS);
//        }
//        ResourceLocation aridHighland = new ResourceLocation("traverse:arid_highland");
//        if (ForgeRegistries.BIOMES.containsKey(aridHighland)) {
//            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, aridHighland), BiomeDictionary.Type.SAVANNA);
//        }
//    }
//
//    @SubscribeEvent
//    public void onPopulateChunk(PopulateChunkEvent.Populate event) {
//        // Regular spawning
//        if (event.getType() == PopulateChunkEvent.Populate.EventType.ANIMALS) {
//            int chunkX = event.getChunkX() * 16;
//            int chunkZ = event.getChunkZ() * 16;
//            int centerX = chunkX + 8;
//            int centerZ = chunkZ + 8;
//            World world = event.getWorld();
//            Random rand = event.getRand();
//            BlockPos blockPos = new BlockPos(chunkX, 0, chunkZ);
//            Biome biome = world.getBiome(blockPos.add(16, 0, 16));
//
//            MoCTools.performCustomWorldGenSpawning(world, biome, centerX, centerZ, 16, 16, rand, creatureSpawnMap.get(biome), MobEntity.SpawnPlacementType.ON_GROUND);
//            MoCTools.performCustomWorldGenSpawning(world, biome, centerX, centerZ, 16, 16, rand, waterCreatureSpawnMap.get(biome), MobEntity.SpawnPlacementType.IN_WATER);
//        }
//    }
//}