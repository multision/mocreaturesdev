/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemArmor extends ArmorItem {

    public MoCItemArmor(Item.Properties properties, String name, IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn) {
        super(materialIn, equipmentSlotIn, properties.group(MoCreatures.tabMoC));
        this.setRegistryName(MoCConstants.MOD_ID, name);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String type) {
        String tempArmorTexture = "croc_1.png";
        if ((itemstack.getItem() == MoCItems.helmetCroc) || (itemstack.getItem() == MoCItems.plateCroc) || (itemstack.getItem() == MoCItems.bootsCroc)) {
            tempArmorTexture = "croc_1.png";
        }
        if (itemstack.getItem() == MoCItems.legsCroc) {
            tempArmorTexture = "croc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetFur) || (itemstack.getItem() == MoCItems.chestFur) || (itemstack.getItem() == MoCItems.bootsFur)) {
            tempArmorTexture = "fur_1.png";
        }
        if (itemstack.getItem() == MoCItems.legsFur) {
            tempArmorTexture = "fur_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetHide) || (itemstack.getItem() == MoCItems.chestHide) || (itemstack.getItem() == MoCItems.bootsHide)) {
            tempArmorTexture = "hide_1.png";
        }
        if (itemstack.getItem() == MoCItems.legsHide) {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetDirt) || (itemstack.getItem() == MoCItems.scorpPlateDirt) || (itemstack.getItem() == MoCItems.scorpBootsDirt)) {
            tempArmorTexture = "scorpd_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsDirt) {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetFrost) || (itemstack.getItem() == MoCItems.scorpPlateFrost) || (itemstack.getItem() == MoCItems.scorpBootsFrost)) {
            tempArmorTexture = "scorpf_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsFrost) {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetCave) || (itemstack.getItem() == MoCItems.scorpPlateCave) || (itemstack.getItem() == MoCItems.scorpBootsCave)) {
            tempArmorTexture = "scorpc_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsCave) {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetNether) || (itemstack.getItem() == MoCItems.scorpPlateNether) || (itemstack.getItem() == MoCItems.scorpBootsNether)) {
            tempArmorTexture = "scorpn_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsNether) {
            tempArmorTexture = "scorpn_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetUndead) || (itemstack.getItem() == MoCItems.scorpPlateUndead) || (itemstack.getItem() == MoCItems.scorpBootsUndead)) {
            tempArmorTexture = "scorpu_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsUndead) {
            tempArmorTexture = "scorpu_2.png";
        }

        return MoCreatures.proxy.getArmorTexture(tempArmorTexture).toString();
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     */
    @Override
    public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {
        if (player.ticksExisted % 40 == 0) {
            player.getItemStackFromSlot(EquipmentSlotType.FEET);
            ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (!stack.isEmpty() && stack.getItem() instanceof MoCItemArmor) {
                MoCTools.updatePlayerArmorEffects(player);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if ((this == MoCItems.scorpHelmetDirt) || (this == MoCItems.scorpPlateDirt) || (this == MoCItems.scorpLegsDirt) || (this == MoCItems.scorpBootsDirt)) {
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonus").setStyle(Style.EMPTY.setFormatting(TextFormatting.GRAY)));
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonusscorp1").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
        }

        if ((this == MoCItems.scorpHelmetFrost) || (this == MoCItems.scorpPlateFrost) || (this == MoCItems.scorpLegsFrost) || (this == MoCItems.scorpBootsFrost)) {
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonus").setStyle(Style.EMPTY.setFormatting(TextFormatting.GRAY)));
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonusscorp2").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
        }

        if ((this == MoCItems.scorpHelmetNether) || (this == MoCItems.scorpPlateNether) || (this == MoCItems.scorpLegsNether) || (this == MoCItems.scorpBootsNether)) {
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonus").setStyle(Style.EMPTY.setFormatting(TextFormatting.GRAY)));
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonusscorp3").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
        }

        if ((this == MoCItems.scorpHelmetCave) || (this == MoCItems.scorpPlateCave) || (this == MoCItems.scorpLegsCave) || (this == MoCItems.scorpBootsCave)) {
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonus").setStyle(Style.EMPTY.setFormatting(TextFormatting.GRAY)));
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonusscorp4").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
        }

        if ((this == MoCItems.scorpHelmetUndead) || (this == MoCItems.scorpPlateUndead) || (this == MoCItems.scorpLegsUndead) || (this == MoCItems.scorpBootsUndead)) {
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonus").setStyle(Style.EMPTY.setFormatting(TextFormatting.GRAY)));
            tooltip.add(new TranslationTextComponent("info.mocreatures.setbonusscorp5").setStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE)));
        }
    }
}
