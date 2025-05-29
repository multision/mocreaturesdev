package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.hunter.*;
import drzhark.mocreatures.entity.neutral.*;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MoCItemWhip extends MoCItem {

    public MoCItemWhip(Item.Properties properties, String name) {
        super(properties.maxStackSize(1).maxDamage(24), name);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.FAIL;

        ItemStack stack = player.getHeldItem(context.getHand());
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        Block block = world.getBlockState(pos).getBlock();
        Block blockAbove = world.getBlockState(pos.up()).getBlock();

        if (context.getFace() != Direction.DOWN && blockAbove == Blocks.AIR && block != Blocks.AIR && !(block instanceof StandingSignBlock)) {
            whipFX(world, pos);
            world.playSound(player, pos, MoCSoundEvents.ENTITY_GENERIC_WHIP.get(), SoundCategory.PLAYERS, 0.5F, 0.4F / ((random.nextFloat() * 0.4F) + 0.8F));

            stack.damageItem(1, player, (p) -> p.sendBreakAnimation(context.getHand()));

            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(12D));
            for (Entity entity : list) {
                if (entity instanceof MoCEntityAnimal) {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCreatures.proxy.enableOwnership && animal.getOwnerId() != null && !player.getUniqueID().equals(animal.getOwnerId()) && !MoCTools.isThisPlayerAnOP(player)) {
                        continue;
                    }
                }

                if (entity instanceof MoCEntityBigCat) {
                    MoCEntityBigCat bigCat = (MoCEntityBigCat) entity;
                    if (bigCat.getIsTamed()) {
                        bigCat.setSitting(!bigCat.getIsSitting());
                        bigCat.setIsJumping(false);
                        bigCat.getNavigator().clearPath();
                        bigCat.setAttackTarget(null);
                    } else if (world.getDifficulty().getId() > 0 && bigCat.getIsAdult()) {
                        bigCat.setAttackTarget(player);
                    }
                }

                if (entity instanceof MoCEntityHorse) {
                    MoCEntityHorse horse = (MoCEntityHorse) entity;
                    if (horse.getIsTamed()) {
                        if (horse.getRidingEntity() == null) {
                            horse.setSitting(!horse.getIsSitting());
                            horse.setIsJumping(false);
                            horse.getNavigator().clearPath();
                            horse.setAttackTarget(null);
                        } else if (horse.isNightmare()) {
                            horse.setNightmareInt(100);
                        } else if (horse.sprintCounter == 0) {
                            horse.sprintCounter = 1;
                        }
                    }
                }

                if (entity instanceof MoCEntityKitty) {
                    MoCEntityKitty kitty = (MoCEntityKitty) entity;
                    if (kitty.getKittyState() > 2 && kitty.whipable()) {
                        kitty.setSitting(!kitty.getIsSitting());
                        kitty.setIsJumping(false);
                        kitty.getNavigator().clearPath();
                        kitty.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityWyvern) {
                    MoCEntityWyvern wyvern = (MoCEntityWyvern) entity;
                    if (wyvern.getIsTamed() && wyvern.getRidingEntity() == null && !wyvern.isOnAir()) {
                        wyvern.setSitting(!wyvern.getIsSitting());
                        wyvern.setIsJumping(false);
                        wyvern.getNavigator().clearPath();
                        wyvern.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityPetScorpion) {
                    MoCEntityPetScorpion scorpion = (MoCEntityPetScorpion) entity;
                    if (scorpion.getIsTamed() && scorpion.getRidingEntity() == null) {
                        scorpion.setSitting(!scorpion.getIsSitting());
                        scorpion.setIsJumping(false);
                        scorpion.getNavigator().clearPath();
                        scorpion.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityOstrich) {
                    MoCEntityOstrich ostrich = (MoCEntityOstrich) entity;
                    if (ostrich.isBeingRidden() && ostrich.sprintCounter == 0) {
                        ostrich.sprintCounter = 1;
                    }
                    if (ostrich.getIsTamed() && ostrich.getRidingEntity() == null) {
                        ostrich.setHiding(!ostrich.getHiding());
                        ostrich.setIsJumping(false);
                        ostrich.getNavigator().clearPath();
                        ostrich.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityElephant) {
                    MoCEntityElephant elephant = (MoCEntityElephant) entity;
                    if (elephant.isBeingRidden() && elephant.sprintCounter == 0) {
                        elephant.sprintCounter = 1;
                    }
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    private void whipFX(World world, BlockPos pos) {
        double d = pos.getX() + 0.5F;
        double d1 = pos.getY() + 1.0F;
        double d2 = pos.getZ() + 0.5F;
        double spread = 0.27D;
        double rise = 0.22D;

        world.addParticle(ParticleTypes.FLAME, d - spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d + spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1 + rise, d2 - spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1 + rise, d2 + spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);

        world.addParticle(ParticleTypes.SMOKE, d - spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1 + rise, d2 - spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1 + rise, d2 + spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
