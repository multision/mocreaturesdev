/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.dimension.worldgen.MoCDirectTeleporter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class ItemStaffPortal extends MoCItem {

    private int portalPosX;
    private int portalPosY;
    private int portalPosZ;
    private RegistryKey<World> portalDimension;
    public static final RegistryKey<World> WYVERN_DIM = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("mocreatures", "wyvernlairworld"));

    public ItemStaffPortal(Item.Properties properties, String name) {
        super(properties.maxStackSize(1).maxDamage(3), name);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        final ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
        if (context.getWorld().isRemote) {
            return ActionResultType.FAIL;
        }
        final boolean hasMending = EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack) > 0;
        final boolean hasUnbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) > 0;
        if (hasMending || hasUnbreaking) {
            String enchantments = hasMending && hasUnbreaking ? "mending, unbreaking" : (hasMending ? "mending" : "unbreaking");
            context.getPlayer().sendMessage(new TranslationTextComponent(MoCreatures.MOC_LOGO + TextFormatting.RED + " Detected illegal enchantment(s) '" + TextFormatting.GREEN + enchantments + TextFormatting.RED + "' on Staff Portal!\nThe item has been removed from your inventory."), context.getPlayer().getUniqueID());
            context.getPlayer().inventory.deleteStack(stack);
            return ActionResultType.SUCCESS;
        }

        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
        }

        CompoundNBT nbtcompound = stack.getTag();
        ServerPlayerEntity playerMP = (ServerPlayerEntity) context.getPlayer();

        if (context.getPlayer().getRidingEntity() != null || context.getPlayer().isBeingRidden()) {
            return ActionResultType.FAIL;
        }

        // Optional: clear fall distance just in case
        playerMP.fallDistance = 0.0F;

        if (context.getWorld().getDimensionKey() != WYVERN_DIM) {
            this.portalDimension = context.getWorld().getDimensionKey();
            this.portalPosX = (int) playerMP.getPosX();
            this.portalPosY = (int) playerMP.getPosY();
            this.portalPosZ = (int) playerMP.getPosZ();
            writeToNBT(nbtcompound);

            ServerWorld targetWorld = playerMP.getServer().getWorld(WYVERN_DIM);
            BlockPos fixedSpawn = new BlockPos(2084, 64, 2011);
            targetWorld.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(fixedSpawn), 1, fixedSpawn);
            playerMP.changeDimension(targetWorld, new MoCDirectTeleporter(fixedSpawn, true));

            stack.damageItem(1, playerMP, (player) -> player.sendBreakAnimation(context.getHand()));
        } else {
            readFromNBT(nbtcompound);

            RegistryKey<World> targetKey = this.portalDimension != null ? this.portalDimension : World.OVERWORLD;
            ServerWorld targetWorld = playerMP.getServer().getWorld(targetKey);

            if (targetWorld != null) {
                BlockPos targetPos = new BlockPos(portalPosX, portalPosY + 1, portalPosZ);
                targetWorld.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(targetPos), 1, targetPos);
                playerMP.changeDimension(targetWorld, new MoCDirectTeleporter(targetPos, false));
                stack.damageItem(1, playerMP, (player) -> player.sendBreakAnimation(context.getHand()));
            } else {
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public void readFromNBT(CompoundNBT nbt) {
        this.portalPosX = nbt.getInt("portalPosX");
        this.portalPosY = nbt.getInt("portalPosY");
        this.portalPosZ = nbt.getInt("portalPosZ");
        this.portalDimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(nbt.getString("portalDimension")));
    }

    public void writeToNBT(CompoundNBT nbt) {
        nbt.putInt("portalPosX", this.portalPosX);
        nbt.putInt("portalPosY", this.portalPosY);
        nbt.putInt("portalPosZ", this.portalPosZ);
        nbt.putString("portalDimension", this.portalDimension.getLocation().toString());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
