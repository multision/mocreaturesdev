/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class MoCItemCrabClaw extends MoCItem {

    protected static final UUID REACH_DISTANCE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID TOUGHNESS_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    public final int armor;
    public final int enchantability;
    public final float reach;
    public final float toughness;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public MoCItemCrabClaw(Item.Properties properties, String name, int enchantability, float toughness, int armor, float reach) {
        super(properties, name);
        this.armor = armor;
        this.enchantability = enchantability;
        this.reach = reach;
        this.toughness = toughness;
    }

    // TODO: Damage claw while placing/destroying blocks or hitting mobs
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    	Item itemOffhand = attacker.getHeldItemOffhand().getItem();
    	
        if (itemOffhand instanceof MoCItemCrabClaw) {
            stack.damageItem(1, attacker, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
    	Item itemOffhand = entityLiving.getHeldItemOffhand().getItem();
    	
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0D && itemOffhand instanceof MoCItemCrabClaw) {
            stack.damageItem(1, entityLiving, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return enchantability;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == MoCItems.animalHide ? true : super.getIsRepairable(toRepair, repair);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        if (attributeModifiers == null){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ARMOR, new AttributeModifier(TOUGHNESS_MODIFIER, "Crab claw armor", armor, AttributeModifier.Operation.ADDITION));
            builder.put(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_DISTANCE_MODIFIER, "Crab claw reach", reach, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(TOUGHNESS_MODIFIER, "Crab claw toughness", toughness, AttributeModifier.Operation.ADDITION));
            this.attributeModifiers = builder.build();
        }
        return equipmentSlot == EquipmentSlotType.OFFHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }
}
