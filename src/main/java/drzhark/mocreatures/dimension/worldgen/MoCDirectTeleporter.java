package drzhark.mocreatures.dimension.worldgen;

import com.mojang.serialization.Codec;
import drzhark.mocreatures.dimension.worldgen.MoCWorldGenPortal;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Random;
import java.util.function.Function;

public class MoCDirectTeleporter implements ITeleporter {
    private final BlockPos pos;
    private final boolean generateStructure;

    public MoCDirectTeleporter(BlockPos pos, boolean generateStructure) {
        this.pos = pos;
        this.generateStructure = generateStructure;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false);

        destWorld.getServer().deferTask(() -> {
            if (newEntity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) newEntity;
                player.connection.setPlayerLocation(
                        pos.getX() + 0.5,
                        pos.getY() + 1,
                        pos.getZ() + 0.5,
                        yaw,
                        player.rotationPitch
                );
                player.fallDistance = 0.0F;
                player.setMotion(Vector3d.ZERO);
                System.out.println("[MoC Portal] Forced player location to " + pos);
            } else {
                newEntity.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, yaw, newEntity.rotationPitch);
                newEntity.setMotion(Vector3d.ZERO);
                newEntity.fallDistance = 0.0F;
            }
        });


        if (generateStructure) {
            BlockState block = destWorld.getBlockState(pos);
            if (!block.matchesBlock(Blocks.QUARTZ_BLOCK)) {
                MoCWorldGenPortal portalGen = new MoCWorldGenPortal(
                        Blocks.QUARTZ_PILLAR.getDefaultState(),
                        Blocks.QUARTZ_STAIRS.getDefaultState(),
                        Blocks.QUARTZ_BLOCK.getDefaultState(),
                        Blocks.QUARTZ_BLOCK.getDefaultState()
                );
                portalGen.generate(destWorld, destWorld.getRandom(), pos);
            }
        }

        return newEntity;
    }
}

