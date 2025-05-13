package drzhark.mocreatures.util;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum MoCItemTier implements IItemTier {

    SHARK(1, 161, 7.0F, 2.5F, 15, () -> {
        return Ingredient.fromItems(MoCItems.sharkteeth);
    }),
    SILVER(2, 304, 9.5F, 3.0F, 19, () -> {
        return Ingredient.EMPTY;
    }),
    SCORPC(2, 371, 7.5F, 2.5F, 16, () -> {
        return Ingredient.fromItems(MoCItems.chitinCave);
    }),
    SCORPF(2, 371, 7.5F, 2.5F, 16, () -> {
        return Ingredient.fromItems(MoCItems.chitinFrost);
    }),
    SCORPN(2, 371, 7.5F, 2.5F, 16, () -> {
        return Ingredient.fromItems(MoCItems.chitinNether);
    }),
    SCORPD(2, 371, 7.5F, 2.5F, 16, () -> {
        return Ingredient.fromItems(MoCItems.chitin);
    }),
    SCORPU(2, 371, 7.5F, 2.5F, 16, () -> {
        return Ingredient.fromItems(MoCItems.chitinUndead);
    }),
    STING(0, 8, 6.0F, 0.0F, 5, () -> {
        return Ingredient.EMPTY;
    });
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private MoCItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
