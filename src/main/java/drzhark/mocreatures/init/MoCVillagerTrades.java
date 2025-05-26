package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCVillagerTrades {

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event) {

        if (event.getType() == VillagerProfession.BUTCHER) {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(MoCItems.duckRaw, 14 + random.nextInt(5)), new ItemStack(Items.EMERALD), 10, 2, 0.05F));
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(MoCItems.ostrichraw, 10 + random.nextInt(3)), new ItemStack(Items.EMERALD), 10, 2, 0.05F));
            event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(MoCItems.duckCooked, 6 + random.nextInt(3)), 10, 5, 0.05F));
            event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(MoCItems.ostrichcooked, 5 + random.nextInt(3)), 10, 5, 0.05F));
        }

        if (event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(MoCItems.ancientSilverScrap, 4 + random.nextInt(3)), new ItemStack(Items.EMERALD), 10, 2, 0.05F));
            event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(MoCItems.mysticPear, 2 + random.nextInt(2)), 3, 15, 0.1F));
        }

        if (event.getType() == VillagerProfession.FISHERMAN) {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2 + random.nextInt(3)), new ItemStack(MoCItems.fishnet), 10, 5, 0.05F));
            event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD), new ItemStack(MoCItems.crabcooked, 6 + random.nextInt(3)), 10, 5, 0.05F));
        }

        if (event.getType() == VillagerProfession.LEATHERWORKER) {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(MoCItems.fur, 9 + random.nextInt(4)), new ItemStack(Items.EMERALD), 10, 2, 0.05F));
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(MoCItems.animalHide, 6 + random.nextInt(4)), new ItemStack(Items.EMERALD), 10, 2, 0.05F));
        }

        if (event.getType() == VillagerProfession.LIBRARIAN) {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.PAPER), new ItemStack(Items.FEATHER), new ItemStack(MoCItems.scrollFreedom), 12, 10, 0.1F));
        }

        if (event.getType() == VillagerProfession.TOOLSMITH || event.getType() == VillagerProfession.WEAPONSMITH || event.getType() == VillagerProfession.ARMORER) {
            event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4 + random.nextInt(3)), new ItemStack(MoCItems.ancientSilverIngot), 10, 5, 0.05F));
            event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(MoCItems.ancientSilverIngot, 3 + random.nextInt(2)), new ItemStack(Items.EMERALD), 10, 5, 0.05F));
        }
    }
}
