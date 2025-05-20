/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.item.*;
import drzhark.mocreatures.util.MoCArmorMaterial;
import drzhark.mocreatures.util.MoCItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;

public class MoCItems {

    public static final Set<Item> ITEMS = new HashSet<>();
    // Misc
    public static final ItemHorseGuide horseGuide = (ItemHorseGuide) new ItemHorseGuide(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)).setRegistryName("mocreatures", "horseguide");;
    public static final MoCItemRecord recordshuffle = new MoCItemRecord(15, "recordshuffle", MoCSoundEvents.ITEM_RECORD_SHUFFLING::get, (new Item.Properties()).maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE));
    public static final MoCItem horsesaddle = new MoCItemHorseSaddle((new Item.Properties()), "horsesaddle");
    public static final MoCItem sharkteeth = new MoCItem((new Item.Properties()), "sharkteeth");
    public static final MoCItem haystack = new MoCItem((new Item.Properties().maxStackSize(16)), "haystack");
    public static final MoCItemFood sugarlump = new MoCItemFood.Builder((new Item.Properties()), "sugarlump", 1, 0.1F, false, 12).setPotionEffect(new EffectInstance(Effects.NAUSEA, 4 * 20, 0), 0.15F).setPotionEffect(new EffectInstance(Effects.SPEED, 4 * 20, 0), 1.0F).build();
    public static final MoCItem mocegg = new MoCItemEgg((new Item.Properties()), "mocegg");
    public static final MoCItem bigcatclaw = new MoCItem((new Item.Properties()), "bigcatclaw");
    public static final MoCItem whip = new MoCItemWhip((new Item.Properties()), "whip");
    public static final MoCItem staffPortal = new ItemStaffPortal((new Item.Properties()), "staffportal");
    public static final MoCItem medallion = new MoCItem((new Item.Properties()), "medallion");
    public static final MoCItemKittyBed[] kittybed = new MoCItemKittyBed[16];
    public static final MoCItem litterbox = new MoCItemLitterBox((new Item.Properties()), "kittylitter");
    public static final MoCItem woolball = new MoCItem((new Item.Properties()), "woolball");
    public static final MoCItem petfood = new MoCItem((new Item.Properties()), "petfood");
    // Both disabled until we figure out what to do with them
    //public static final MoCItem staffTeleport = new ItemStaffTeleport((new Item.Properties()), "staffteleport");
    //public static final MoCItem builderHammer = new ItemBuilderHammer((new Item.Properties()), "builderhammer");
    public static final MoCItem hideCroc = new MoCItem((new Item.Properties()), "reptilehide");
    public static final MoCItem fur = new MoCItem((new Item.Properties()), "fur");
    public static final MoCItem essencedarkness = new MoCItem((new Item.Properties()), "essencedarkness");
    public static final MoCItem essencefire = new MoCItem((new Item.Properties()), "essencefire");
    public static final MoCItem essenceundead = new MoCItem((new Item.Properties()), "essenceundead");
    public static final MoCItem essencelight = new MoCItem((new Item.Properties()), "essencelight");
    public static final MoCItem amuletbone = new MoCItemHorseAmulet((new Item.Properties()), "amuletbone");
    public static final MoCItem amuletbonefull = new MoCItemHorseAmulet((new Item.Properties()), "amuletbonefull");
    public static final MoCItem amuletghost = new MoCItemHorseAmulet((new Item.Properties()), "amuletghost");
    public static final MoCItem amuletghostfull = new MoCItemHorseAmulet((new Item.Properties()), "amuletghostfull");
    public static final MoCItem amuletfairy = new MoCItemHorseAmulet((new Item.Properties()), "amuletfairy");
    public static final MoCItem amuletfairyfull = new MoCItemHorseAmulet((new Item.Properties()), "amuletfairyfull");
    public static final MoCItem amuletpegasus = new MoCItemHorseAmulet((new Item.Properties()), "amuletpegasus");
    public static final MoCItem amuletpegasusfull = new MoCItemHorseAmulet((new Item.Properties()), "amuletpegasusfull");
    public static final MoCItem fishnet = new MoCItemPetAmulet((new Item.Properties()), "fishnet");
    public static final MoCItem fishnetfull = new MoCItemPetAmulet((new Item.Properties()), "fishnetfull");
    public static final MoCItem petamulet = new MoCItemPetAmulet((new Item.Properties()), "petamulet", 1);
    public static final MoCItem petamuletfull = new MoCItemPetAmulet((new Item.Properties()), "petamuletfull", 1);
    public static final MoCItem heartdarkness = new MoCItem((new Item.Properties()), "heartdarkness");
    public static final MoCItem heartfire = new MoCItem((new Item.Properties()), "heartfire");
    public static final MoCItem heartundead = new MoCItem((new Item.Properties()), "heartundead");
    public static final MoCItem unicornhorn = new MoCItem((new Item.Properties()), "unicornhorn");
    public static final MoCItem horsearmorcrystal = new MoCItem((new Item.Properties()), "horsearmorcrystal");
    public static final MoCItem animalHide = new MoCItem((new Item.Properties()), "hide");
    public static final MoCItem chitinCave = new MoCItem((new Item.Properties()), "chitinblack");
    public static final MoCItem chitinFrost = new MoCItem((new Item.Properties()), "chitinfrost");
    public static final MoCItem chitinNether = new MoCItem((new Item.Properties()), "chitinnether");
    public static final MoCItem chitinUndead = new MoCItem((new Item.Properties()), "chitinundead");
    public static final MoCItem chitin = new MoCItem((new Item.Properties()), "chitin");
    public static final MoCItem tusksWood = new MoCItem((new Item.Properties()), "tuskswood");
    public static final MoCItem tusksIron = new MoCItem((new Item.Properties()), "tusksiron");
    public static final MoCItem tusksDiamond = new MoCItem((new Item.Properties()), "tusksdiamond");
    public static final MoCItem elephantHarness = new MoCItem((new Item.Properties()), "elephantharness");
    public static final MoCItem elephantChest = new MoCItem((new Item.Properties()), "elephantchest");
    public static final MoCItem elephantGarment = new MoCItem((new Item.Properties()), "elephantgarment");
    public static final MoCItem elephantHowdah = new MoCItem((new Item.Properties()), "elephanthowdah");
    public static final MoCItem mammothPlatform = new MoCItem((new Item.Properties()), "mammothplatform");
    public static final MoCItem scrollFreedom = new MoCItem((new Item.Properties()), "scrolloffreedom");
    public static final MoCItem scrollOfSale = new MoCItem((new Item.Properties()), "scrollofsale");
    public static final MoCItem scrollOfOwner = new MoCItem((new Item.Properties()), "scrollofowner");
    public static final MoCItem ancientSilverScrap = new MoCItem((new Item.Properties()), "ancientsilverscrap");
    public static final MoCItem ancientSilverIngot = new MoCItem((new Item.Properties()), "ancientsilveringot");
    public static final MoCItem ancientSilverNugget = new MoCItem((new Item.Properties()), "ancientsilvernugget");
    public static final MoCItem firestoneChunk = new MoCItem((new Item.Properties()), "firestonechunk");
    // Never finished, even in new MoCreatures
    // public static final MoCItemCrabClaw brackishClaw = new MoCItemCrabClaw((new Item.Properties().defaultMaxDamage(768)), "brackish_claw", 15, 0.0F, 1, 2.0F);
    // Food
    public static final MoCItemFood cookedTurkey = new MoCItemFood.Builder((new Item.Properties()), "turkeycooked", 7, 0.8F, true).build();
    public static final MoCItemFood crabraw = new MoCItemFood.Builder((new Item.Properties()), "crabraw", 2, 0.1F, true).setPotionEffect(new EffectInstance(Effects.HUNGER, 30 * 20, 0), 0.8F).build();
    public static final MoCItemFood crabcooked = new MoCItemFood.Builder((new Item.Properties()), "crabcooked", 4, 0.6F, true).build();
    public static final MoCItemFood duckCooked = new MoCItemFood.Builder((new Item.Properties()), "duckcooked", 6, 0.7F, true).build();
    public static final MoCItemFood duckRaw = new MoCItemFood.Builder((new Item.Properties()), "duckraw", 2, 0.4F, true).build();
    public static final MoCItemFood mysticPear = new MoCItemFood.Builder((new Item.Properties()), "mysticpear", 4, 0.8F, false, 16).setAlwaysEdible().setPotionEffect(new EffectInstance(Effects.RESISTANCE, 10 * 20, 1), 1.0F).setPotionEffect(new EffectInstance(Effects.SPEED, 10 * 20, 1), 1.0F).build();
    public static final MoCItemFood omelet = new MoCItemFood.Builder((new Item.Properties()), "omelet", 3, 0.5F, false).build();
    public static final MoCItemFood ostrichraw = new MoCItemFood.Builder((new Item.Properties()), "ostrichraw", 3, 0.4F, true).setPotionEffect(new EffectInstance(Effects.HUNGER, 30 * 20, 0), 0.8F).build();
    public static final MoCItemFood ostrichcooked = new MoCItemFood.Builder((new Item.Properties()), "ostrichcooked", 7, 0.8F, true).build();
    public static final MoCItemFood ratBurger = new MoCItemFood.Builder((new Item.Properties()), "ratburger", 9, 0.5F, false).build();
    public static final MoCItemFood ratCooked = new MoCItemFood.Builder((new Item.Properties()), "ratcooked", 4, 0.5F, true).build();
    public static final MoCItemFood ratRaw = new MoCItemFood.Builder((new Item.Properties()), "ratraw", 2, 0.1F, true).setPotionEffect(new EffectInstance(Effects.HUNGER, 30 * 20, 0), 0.8F).build();
    public static final MoCItemFood rawTurkey = new MoCItemFood.Builder((new Item.Properties()), "turkeyraw", 3, 0.4F, true).setPotionEffect(new EffectInstance(Effects.HUNGER, 30 * 20, 0), 0.8F).build();
    public static final MoCItemFood turtlecooked = new MoCItemFood.Builder((new Item.Properties()), "turtlecooked", 6, 0.7F, true).build();
    public static final MoCItemFood turtleraw = new MoCItemFood.Builder((new Item.Properties()), "turtleraw", 2, 0.2F, true).build();
    public static final MoCItemFood turtlesoup = new MoCItemTurtleSoup.Builder((new Item.Properties()), "turtlesoup", 8, 0.8F, false).build();
    public static final MoCItemFood venisonCooked = new MoCItemFood.Builder((new Item.Properties()), "venisoncooked", 8, 0.9F, true).build();
    public static final MoCItemFood venisonRaw = new MoCItemFood.Builder((new Item.Properties()), "venisonraw", 3, 0.4F, true).build();
    // Weapons
    public static final MoCItemSword nunchaku = new MoCItemSword(new Item.Properties(), "nunchaku", ItemTier.IRON);
    public static final MoCItemSword sai = new MoCItemSword(new Item.Properties(), "sai", ItemTier.IRON);
    public static final MoCItemSword bo = new MoCItemSword(new Item.Properties(), "bo", ItemTier.IRON);
    public static final MoCItemSword katana = new MoCItemSword(new Item.Properties(), "katana", ItemTier.IRON);
    public static final MoCItemSword sharksword = new MoCItemSword(new Item.Properties(), "sharksword", MoCItemTier.SHARK);
    public static final MoCItemAxe sharkaxe = new MoCItemAxe(new Item.Properties(), "sharkaxe", MoCItemTier.SHARK, 9.5F, 1.0F);
    public static final MoCItemSword silversword = new MoCItemSword(new Item.Properties(), "silversword", MoCItemTier.SILVER);
    public static final MoCItemAxe silveraxe = new MoCItemAxe(new Item.Properties(), "silveraxe", MoCItemTier.SILVER, 10.0F, 1.1F);
    public static final MoCItemSword scorpSwordCave = new MoCItemSword(new Item.Properties(), "scorpswordcave", MoCItemTier.SCORPC, 4);
    public static final MoCItemAxe scorpAxeCave = new MoCItemAxe(new Item.Properties(), "scorpaxecave", MoCItemTier.SCORPC, 9.5F, 1.0F, 4);
    public static final MoCItemSword scorpSwordFrost = new MoCItemSword(new Item.Properties(), "scorpswordfrost", MoCItemTier.SCORPF, 2);
    public static final MoCItemAxe scorpAxeFrost = new MoCItemAxe(new Item.Properties(), "scorpaxefrost", MoCItemTier.SCORPF, 9.5F, 1.0F, 2);
    public static final MoCItemSword scorpSwordNether = new MoCItemSword(new Item.Properties(), "scorpswordnether", MoCItemTier.SCORPN, 3);
    public static final MoCItemAxe scorpAxeNether = new MoCItemAxe(new Item.Properties(), "scorpaxenether", MoCItemTier.SCORPN, 9.5F, 1.0F, 3);
    public static final MoCItemSword scorpSwordDirt = new MoCItemSword(new Item.Properties(), "scorpsworddirt", MoCItemTier.SCORPD, 1);
    public static final MoCItemAxe scorpAxeDirt = new MoCItemAxe(new Item.Properties(), "scorpaxedirt", MoCItemTier.SCORPD, 9.5F, 1.0F, 1);
    public static final MoCItemSword scorpSwordUndead = new MoCItemSword(new Item.Properties(), "scorpswordundead", MoCItemTier.SCORPU, 5);
    public static final MoCItemAxe scorpAxeUndead = new MoCItemAxe(new Item.Properties(), "scorpaxeundead", MoCItemTier.SCORPU, 9.5F, 1.0F, 5);
    public static final MoCItemWeapon scorpStingCave = new MoCItemWeapon(new Item.Properties(), "scorpstingcave", MoCItemTier.STING, 4);
    public static final MoCItemWeapon scorpStingFrost = new MoCItemWeapon(new Item.Properties(), "scorpstingfrost", MoCItemTier.STING, 2);
    public static final MoCItemWeapon scorpStingNether = new MoCItemWeapon(new Item.Properties(), "scorpstingnether", MoCItemTier.STING, 3);
    public static final MoCItemWeapon scorpStingDirt = new MoCItemWeapon(new Item.Properties(), "scorpstingdirt", MoCItemTier.STING, 1);
    public static final MoCItemWeapon scorpStingUndead = new MoCItemWeapon(new Item.Properties(), "scorpstingundead", MoCItemTier.STING, 5);
    // Armor
    public static final MoCItemArmor plateCroc = new MoCItemArmor(new Item.Properties(), "reptileplate", MoCArmorMaterial.crocARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor helmetCroc = new MoCItemArmor(new Item.Properties(), "reptilehelmet", MoCArmorMaterial.crocARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor legsCroc = new MoCItemArmor(new Item.Properties(), "reptilelegs", MoCArmorMaterial.crocARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor bootsCroc = new MoCItemArmor(new Item.Properties(), "reptileboots", MoCArmorMaterial.crocARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor scorpPlateDirt = new MoCItemArmor(new Item.Properties(), "scorpplatedirt", MoCArmorMaterial.scorpdARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor scorpHelmetDirt = new MoCItemArmor(new Item.Properties(), "scorphelmetdirt", MoCArmorMaterial.scorpdARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor scorpLegsDirt = new MoCItemArmor(new Item.Properties(), "scorplegsdirt", MoCArmorMaterial.scorpdARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor scorpBootsDirt = new MoCItemArmor(new Item.Properties(), "scorpbootsdirt", MoCArmorMaterial.scorpdARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor scorpPlateFrost = new MoCItemArmor(new Item.Properties(), "scorpplatefrost", MoCArmorMaterial.scorpfARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor scorpHelmetFrost = new MoCItemArmor(new Item.Properties(), "scorphelmetfrost", MoCArmorMaterial.scorpfARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor scorpLegsFrost = new MoCItemArmor(new Item.Properties(), "scorplegsfrost", MoCArmorMaterial.scorpfARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor scorpBootsFrost = new MoCItemArmor(new Item.Properties(), "scorpbootsfrost", MoCArmorMaterial.scorpfARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor scorpPlateNether = new MoCItemArmor(new Item.Properties(), "scorpplatenether", MoCArmorMaterial.scorpnARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor scorpHelmetNether = new MoCItemArmor(new Item.Properties(), "scorphelmetnether", MoCArmorMaterial.scorpnARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor scorpLegsNether = new MoCItemArmor(new Item.Properties(), "scorplegsnether", MoCArmorMaterial.scorpnARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor scorpBootsNether = new MoCItemArmor(new Item.Properties(), "scorpbootsnether", MoCArmorMaterial.scorpnARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor scorpPlateCave = new MoCItemArmor(new Item.Properties(), "scorpplatecave", MoCArmorMaterial.scorpcARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor scorpHelmetCave = new MoCItemArmor(new Item.Properties(), "scorphelmetcave", MoCArmorMaterial.scorpcARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor scorpLegsCave = new MoCItemArmor(new Item.Properties(), "scorplegscave", MoCArmorMaterial.scorpcARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor scorpBootsCave = new MoCItemArmor(new Item.Properties(), "scorpbootscave", MoCArmorMaterial.scorpcARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor scorpPlateUndead = new MoCItemArmor(new Item.Properties(), "scorpplateundead", MoCArmorMaterial.scorpuARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor scorpHelmetUndead = new MoCItemArmor(new Item.Properties(), "scorphelmetundead", MoCArmorMaterial.scorpuARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor scorpLegsUndead = new MoCItemArmor(new Item.Properties(), "scorplegsundead", MoCArmorMaterial.scorpuARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor scorpBootsUndead = new MoCItemArmor(new Item.Properties(), "scorpbootsundead", MoCArmorMaterial.scorpuARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor chestFur = new MoCItemArmor(new Item.Properties(), "furchest", MoCArmorMaterial.furARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor helmetFur = new MoCItemArmor(new Item.Properties(), "furhelmet", MoCArmorMaterial.furARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor legsFur = new MoCItemArmor(new Item.Properties(), "furlegs", MoCArmorMaterial.furARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor bootsFur = new MoCItemArmor(new Item.Properties(), "furboots", MoCArmorMaterial.furARMOR, EquipmentSlotType.FEET);
    public static final MoCItemArmor chestHide = new MoCItemArmor(new Item.Properties(), "hidechest", MoCArmorMaterial.hideARMOR, EquipmentSlotType.CHEST);
    public static final MoCItemArmor helmetHide = new MoCItemArmor(new Item.Properties(), "hidehelmet", MoCArmorMaterial.hideARMOR, EquipmentSlotType.HEAD);
    public static final MoCItemArmor legsHide = new MoCItemArmor(new Item.Properties(), "hidelegs", MoCArmorMaterial.hideARMOR, EquipmentSlotType.LEGS);
    public static final MoCItemArmor bootsHide = new MoCItemArmor(new Item.Properties(), "hideboots", MoCArmorMaterial.hideARMOR, EquipmentSlotType.FEET);

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            List<Item> items = new ArrayList<>(Arrays.asList(
                    horseGuide,
                    horsesaddle,
                    sharkteeth,
                    haystack,
                    sugarlump,
                    mocegg,
                    bigcatclaw,
                    whip,
                    medallion,
                    litterbox,
                    woolball,
                    petfood,
                    hideCroc,
                    plateCroc,
                    helmetCroc,
                    legsCroc,
                    bootsCroc,
                    fur,
                    omelet,
                    turtleraw,
                    turtlecooked,
                    turtlesoup,
                    staffPortal,
                    /*staffTeleport,
                    builderHammer,*/

                    ancientSilverScrap,
                    ancientSilverIngot,
                    ancientSilverNugget,
                    firestoneChunk,

                    nunchaku,
                    sai,
                    bo,
                    katana,
                    sharksword,
                    sharkaxe,
                    silversword,
                    silveraxe,

                    essencedarkness,
                    essencefire,
                    essenceundead,
                    essencelight,

                    amuletbone,
                    amuletbonefull,
                    amuletghost,
                    amuletghostfull,
                    amuletfairy,
                    amuletfairyfull,
                    amuletpegasus,
                    amuletpegasusfull,
                    fishnet,
                    fishnetfull,
                    petamulet,
                    petamuletfull,

                    chestFur,
                    helmetFur,
                    legsFur,
                    bootsFur,

                    heartdarkness,
                    heartfire,
                    heartundead,
                    ostrichraw,
                    ostrichcooked,
                    unicornhorn,
                    horsearmorcrystal,
                    mysticPear,
                    recordshuffle,

                    animalHide,
                    rawTurkey,
                    cookedTurkey,
                    duckRaw,
                    duckCooked,
                    chestHide,
                    helmetHide,
                    legsHide,
                    bootsHide,
                    ratRaw,
                    ratCooked,
                    ratBurger,
                    venisonRaw,
                    venisonCooked,

                    chitinCave,
                    chitin,
                    chitinNether,
                    chitinFrost,
                    chitinUndead,

                    scorpSwordCave,
                    scorpAxeCave,
                    scorpSwordDirt,
                    scorpAxeDirt,
                    scorpSwordNether,
                    scorpAxeNether,
                    scorpSwordFrost,
                    scorpAxeFrost,
                    scorpSwordUndead,
                    scorpAxeUndead,

                    scorpHelmetCave,
                    scorpPlateCave,
                    scorpLegsCave,
                    scorpBootsCave,
                    scorpHelmetDirt,
                    scorpPlateDirt,
                    scorpLegsDirt,
                    scorpBootsDirt,
                    scorpHelmetNether,
                    scorpPlateNether,
                    scorpLegsNether,
                    scorpBootsNether,
                    scorpHelmetFrost,
                    scorpPlateFrost,
                    scorpLegsFrost,
                    scorpBootsFrost,
                    scorpHelmetUndead,
                    scorpPlateUndead,
                    scorpLegsUndead,
                    scorpBootsUndead,

                    scorpStingCave,
                    scorpStingDirt,
                    scorpStingNether,
                    scorpStingFrost,
                    scorpStingUndead,

                    tusksWood,
                    tusksIron,
                    tusksDiamond,
                    elephantChest,
                    elephantGarment,
                    elephantHarness,
                    elephantHowdah,
                    mammothPlatform,

                    scrollFreedom,
                    scrollOfSale,
                    scrollOfOwner,
                    crabraw,
                    crabcooked//,
                    //brackishClaw
            ));

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (int i = 0; i < 16; i++) {
                String s = DyeColor.byId(i).getTranslationKey().toLowerCase();
                if (s.equalsIgnoreCase("lightBlue")) s = "light_blue";
                kittybed[i] = new MoCItemKittyBed((new Item.Properties()), "kittybed_" + s, i);
                registry.register(kittybed[i]);
            }

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }
}
