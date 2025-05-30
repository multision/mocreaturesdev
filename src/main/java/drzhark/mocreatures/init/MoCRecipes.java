/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCRecipes {

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerFuels(FurnaceFuelBurnTimeEvent event) {
            if (event.getItemStack().getItem() == Item.getItemFromBlock(MoCBlocks.wyvwoodSapling)) {
                event.setBurnTime(100); // 0.5 items
            } else if (event.getItemStack().getItem() == MoCItems.firestoneChunk) {
                event.setBurnTime(2400); // 12 items
            } else if (event.getItemStack().getItem() == MoCItems.heartfire) {
                event.setBurnTime(3200); // 16 items
            } else if (event.getItemStack().getItem() == MoCItems.sharkaxe | event.getItemStack().getItem() == MoCItems.sharksword) {
                event.setBurnTime(200); // 1 item
            }
        }

        @SubscribeEvent
        public static void registerRecipes(final RegistryEvent.Register<?> event) { //TODO TheidenHD

            /*
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.ancientOre), new ItemStack(MoCItems.ancientSilverIngot, 3, 15), 0.2F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.cobbledDeepWyvstone), new ItemStack(MoCBlocks.deepWyvstone), 0.1F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.cobbledWyvstone), new ItemStack(MoCBlocks.wyvstone), 0.1F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.silverSand), new ItemStack(MoCBlocks.gleamingGlass), 0.1F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.wyvernDiamondOre), new ItemStack(Items.DIAMOND), 1.0F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.wyvernEmeraldOre), new ItemStack(Items.EMERALD), 1.0F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.wyvernGoldOre), new ItemStack(Items.GOLD_INGOT), 1.0F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.wyvernIronOre), new ItemStack(Items.IRON_INGOT), 0.7F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.wyvernLapisOre), new ItemStack(Items.LAPIS_LAZULI, 1, 4), 0.4F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.wyvwoodLog), new ItemStack(Items.COAL, 1, 1), 0.15F);

            GameRegistry.addSmelting(MoCItems.ancientSilverScrap, new ItemStack(MoCItems.ancientSilverIngot), 1.0F);
            GameRegistry.addSmelting(MoCItems.crabraw, new ItemStack(MoCItems.crabcooked), 0.35F);
            GameRegistry.addSmelting(MoCItems.duckRaw, new ItemStack(MoCItems.duckCooked), 0.35F);
            GameRegistry.addSmelting(MoCItems.ratRaw, new ItemStack(MoCItems.ratCooked), 0.35F);
            GameRegistry.addSmelting(MoCItems.mocegg, new ItemStack(MoCItems.omelet), 0.35F);
            GameRegistry.addSmelting(MoCItems.ostrichraw, new ItemStack(MoCItems.ostrichcooked), 0.35F);
            GameRegistry.addSmelting(MoCItems.rawTurkey, new ItemStack(MoCItems.cookedTurkey), 0.35F);
            GameRegistry.addSmelting(MoCItems.silveraxe, new ItemStack(MoCItems.ancientSilverNugget), 0.25F);
            GameRegistry.addSmelting(MoCItems.silversword, new ItemStack(MoCItems.ancientSilverNugget), 0.25F);
            GameRegistry.addSmelting(MoCItems.turtleraw, new ItemStack(MoCItems.turtlecooked), 0.35F);
            GameRegistry.addSmelting(MoCItems.venisonRaw, new ItemStack(MoCItems.venisonCooked), 0.35F);

            GameRegistry.addSmelting(Items.EGG, new ItemStack(MoCItems.omelet), 0.35F);
*/
//            OreDictionary.registerOre("blockGlass", new ItemStack(MoCBlocks.gleamingGlass));
//            OreDictionary.registerOre("blockGlassColorless", new ItemStack(MoCBlocks.gleamingGlass));
//            OreDictionary.registerOre("blockSilver", new ItemStack(MoCBlocks.ancientSilverBlock));
//            OreDictionary.registerOre("cobblestone", new ItemStack(MoCBlocks.cobbledDeepWyvstone));
//            OreDictionary.registerOre("cobblestone", new ItemStack(MoCBlocks.cobbledWyvstone));
//            OreDictionary.registerOre("dirt", new ItemStack(MoCBlocks.wyvdirt));
//            OreDictionary.registerOre("grass", new ItemStack(MoCBlocks.wyvgrass));
//            OreDictionary.registerOre("logWood", new ItemStack(MoCBlocks.wyvwoodLog));
//            OreDictionary.registerOre("oreDiamond", new ItemStack(MoCBlocks.wyvernDiamondOre));
//            OreDictionary.registerOre("oreEmerald", new ItemStack(MoCBlocks.wyvernEmeraldOre));
//            OreDictionary.registerOre("oreGold", new ItemStack(MoCBlocks.wyvernGoldOre));
//            OreDictionary.registerOre("oreIron", new ItemStack(MoCBlocks.wyvernIronOre));
//            OreDictionary.registerOre("oreLapis", new ItemStack(MoCBlocks.wyvernLapisOre));
//            OreDictionary.registerOre("plankWood", new ItemStack(MoCBlocks.wyvwoodPlanks));
//            OreDictionary.registerOre("sand", new ItemStack(MoCBlocks.silverSand));
//            OreDictionary.registerOre("sandstone", new ItemStack(MoCBlocks.carvedSilverSandstone));
//            OreDictionary.registerOre("sandstone", new ItemStack(MoCBlocks.silverSandstone));
//            OreDictionary.registerOre("sandstone", new ItemStack(MoCBlocks.smoothSilverSandstone));
//            OreDictionary.registerOre("stone", new ItemStack(MoCBlocks.deepWyvstone));
//            OreDictionary.registerOre("stone", new ItemStack(MoCBlocks.wyvstone));
//            OreDictionary.registerOre("treeLeaves", new ItemStack(MoCBlocks.wyvwoodLeaves));
//            OreDictionary.registerOre("treeSapling", new ItemStack(MoCBlocks.wyvwoodSapling));
//
//            OreDictionary.registerOre("chitinScorpion", MoCItems.chitinCave);
//            OreDictionary.registerOre("chitinScorpion", MoCItems.chitin);
//            OreDictionary.registerOre("chitinScorpion", MoCItems.chitinFrost);
//            OreDictionary.registerOre("chitinScorpion", MoCItems.chitinNether);
//            OreDictionary.registerOre("chitinScorpion", MoCItems.chitinUndead);
//            OreDictionary.registerOre("egg", new ItemStack(MoCItems.mocegg, 1, OreDictionary.WILDCARD_VALUE));
//            OreDictionary.registerOre("ingotSilver", MoCItems.ancientSilverIngot);
//            OreDictionary.registerOre("nuggetSilver", MoCItems.ancientSilverNugget);
//            OreDictionary.registerOre("record", MoCItems.recordshuffle);
//            OreDictionary.registerOre("stingerScorpion", MoCItems.scorpStingCave);
//            OreDictionary.registerOre("stingerScorpion", MoCItems.scorpStingDirt);
//            OreDictionary.registerOre("stingerScorpion", MoCItems.scorpStingFrost);
//            OreDictionary.registerOre("stingerScorpion", MoCItems.scorpStingNether);
//            OreDictionary.registerOre("stingerScorpion", MoCItems.scorpStingUndead);
//
//            // Fish's Undead Rising OreDictionaries
//            OreDictionary.registerOre("heartUndying", MoCItems.heartundead);
//
//            // Pam's HarvestCraft OreDictionaries
//            OreDictionary.registerOre("foodCrabcooked", MoCItems.crabcooked);
//            OreDictionary.registerOre("foodCrabraw", MoCItems.crabraw);
//            OreDictionary.registerOre("foodDuckcooked", MoCItems.duckCooked);
//            OreDictionary.registerOre("foodDuckraw", MoCItems.duckRaw);
//            OreDictionary.registerOre("foodOmelet", MoCItems.omelet);
//            OreDictionary.registerOre("foodOstrichcooked", MoCItems.ostrichcooked);
//            OreDictionary.registerOre("foodOstrichraw", MoCItems.ostrichraw);
//            OreDictionary.registerOre("foodRatburger", MoCItems.ratBurger);
//            OreDictionary.registerOre("foodRatcooked", MoCItems.ratCooked);
//            OreDictionary.registerOre("foodRatraw", MoCItems.ratRaw);
//            OreDictionary.registerOre("foodTurkeycooked", MoCItems.cookedTurkey);
//            OreDictionary.registerOre("foodTurkeyraw", MoCItems.rawTurkey);
//            OreDictionary.registerOre("foodTurtlecooked", MoCItems.turtlecooked);
//            OreDictionary.registerOre("foodTurtleraw", MoCItems.turtleraw);
//            OreDictionary.registerOre("foodTurtlesoup", MoCItems.turtlesoup);
//            OreDictionary.registerOre("foodVenisoncooked", MoCItems.venisonCooked);
//            OreDictionary.registerOre("foodVenisonraw", MoCItems.venisonRaw);
//            OreDictionary.registerOre("listAllduckcooked", MoCItems.duckCooked);
//            OreDictionary.registerOre("listAllduckraw", MoCItems.duckRaw);
//            OreDictionary.registerOre("listAllegg", new ItemStack(MoCItems.mocegg, 1, OreDictionary.WILDCARD_VALUE));
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.crabcooked);
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.duckCooked);
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.ostrichcooked);
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.ratCooked);
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.cookedTurkey);
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.turtlecooked);
//            OreDictionary.registerOre("listAllmeatcooked", MoCItems.venisonCooked);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.crabraw);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.duckRaw);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.ostrichraw);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.ratRaw);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.rawTurkey);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.turtleraw);
//            OreDictionary.registerOre("listAllmeatraw", MoCItems.venisonRaw);
//            OreDictionary.registerOre("listAllturkeycooked", MoCItems.cookedTurkey);
//            OreDictionary.registerOre("listAllturkeyraw", MoCItems.rawTurkey);
//            OreDictionary.registerOre("listAllvenisoncooked", MoCItems.venisonCooked);
//            OreDictionary.registerOre("listAllvenisonraw", MoCItems.venisonRaw);
        }
    }
}
