/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class MoCEventHooks {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        // if overworld has been deleted or unloaded, reset our flag
        if (((World)event.getWorld()).getDimensionKey() == World.OVERWORLD) {
            MoCreatures.proxy.worldInitDone = false;
        }
    }

    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() != null && !MoCreatures.proxy.worldInitDone) // if overworld has loaded, use its mapstorage
        {
            MoCPetMapData data = ServerLifecycleHooks.getCurrentServer().getWorld(World.OVERWORLD).getSavedData().getOrCreate(() -> new MoCPetMapData(MoCConstants.MOD_ID), MoCConstants.MOD_ID);
            MoCreatures.instance.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
    }*/

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;

        ServerWorld serverWorld = (ServerWorld) event.getWorld();
        if (serverWorld.getDimensionKey() != World.OVERWORLD) return;

        if (!MoCreatures.proxy.worldInitDone) {
            MoCPetMapData data = serverWorld.getSavedData().getOrCreate(
                    () -> new MoCPetMapData(MoCConstants.MOD_ID), MoCConstants.MOD_ID);
            MoCreatures.instance.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
    }

//    @SubscribeEvent
//    public void onPopulateVillage(PopulateChunkEvent.Post event) { //TODO TheidenHD
//        // Village spawning
//        if (event.isHasVillageGenerated()) {
//            MoCEntityData data = MoCreatures.entityMap.get(MoCEntityKitty.class);
//            if (data == null) return;
//            World world = event.getWorld();
//            List<Integer> dimensionIDs = Ints.asList(data.getDimensions());
//            if (!dimensionIDs.contains(world.provider.getDimension()) || data.getFrequency() <= 0 || !data.getCanSpawn())
//                return;
//            if (event.getRand().nextInt(100) < MoCreatures.proxy.kittyVillageChance) {
//                BlockPos pos = new BlockPos(event.getChunkX() * 16, 100, event.getChunkZ() * 16);
//                MoCEntityKitty kitty = new MoCEntityKitty(world);
//                BlockPos spawnPos = getSafeSpawnPos(kitty, pos.add(8, 0, 8));
//                if (spawnPos == null) return;
//                kitty.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(kitty)), null);
//                kitty.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
//                world.addEntity(kitty);
//            }
//        }
//    }

    @SubscribeEvent
    public void onLivingSpawnEvent(LivingSpawnEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Class<? extends LivingEntity> entityClass = entity.getClass();
        MoCEntityData data = MoCreatures.entityMap.get(entityClass);
        if (data == null) return; // not a MoC entity
        World world = (World) event.getWorld();
        List<RegistryKey<World>> dimensionIDs = Arrays.asList(data.getDimensions());
        if (!dimensionIDs.contains(world.getDimensionKey())) {
            event.setResult(Event.Result.DENY);
        } else if (data.getFrequency() <= 0) {
            event.setResult(Event.Result.DENY);
        } else if (dimensionIDs.contains(MoCreatures.proxy.wyvernDimension) && world.getDimensionKey() == MoCreatures.proxy.wyvernDimension) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if (!event.getEntity().world.isRemote) {
            if (IMoCTameable.class.isAssignableFrom(event.getEntityLiving().getClass())) {
                IMoCTameable mocEntity = (IMoCTameable) event.getEntityLiving();
                if (mocEntity.getIsTamed() && mocEntity.getPetHealth() > 0 && !mocEntity.isRiderDisconnecting()) {
                    return;
                }

                if (mocEntity.getOwnerPetId() != -1) // required since getInt will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(mocEntity, mocEntity.getOwnerPetId());
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.getRidingEntity() instanceof IMoCTameable) {
            IMoCTameable mocEntity = (IMoCTameable) player.getRidingEntity();
            mocEntity.setRiderDisconnecting(true);
        }
    }

    private BlockPos getSafeSpawnPos(LivingEntity entity, BlockPos near) {
        int radius = 6;
        int maxTries = 24;
        BlockPos testing;
        for (int i = 0; i < maxTries; i++) {
            int x = near.getX() + entity.getEntityWorld().rand.nextInt(radius * 2) - radius;
            int z = near.getZ() + entity.getEntityWorld().rand.nextInt(radius * 2) - radius;
            int y = entity.getEntityWorld().getHeight(Heightmap.Type.MOTION_BLOCKING, new BlockPos(x, 0, z)).getY() + 16;
            testing = new BlockPos(x, y, z);
            while (entity.getEntityWorld().isAirBlock(testing) && testing.getY() > 0) {
                testing = testing.down(1);
            }
            BlockState iblockstate = entity.getEntityWorld().getBlockState(testing);
            if (iblockstate.canEntitySpawn(entity.getEntityWorld(), testing, entity.getType())) {
                return testing.up(1);
            }
        }
        return null;
    }
}
