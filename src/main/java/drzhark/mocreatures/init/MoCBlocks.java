/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import com.google.common.base.Preconditions;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.JungleTree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCBlocks {

    public static MoCBlockOre ancientOre;
    public static Block ancientSilverBlock;
    public static Block carvedSilverSandstone;
    public static Block cobbledWyvstone;
    public static Block cobbledDeepWyvstone;
    public static Block deepWyvstone;
    public static Block firestone;
    public static Block gleamingGlass;
    public static Block mossyCobbledWyvstone;
    public static Block mossyCobbledDeepWyvstone;
    public static Block silverSand;
    public static Block silverSandstone;
    public static Block smoothSilverSandstone;
    public static Block tallWyvgrass;
    public static MoCBlockOre wyvernDiamondOre;
    public static MoCBlockOre wyvernEmeraldOre;
    public static MoCBlockOre wyvernGoldOre;
    public static MoCBlockOre wyvernIronOre;
    public static MoCBlockOre wyvernLapisOre;
    public static MoCBlockNest wyvernNestBlock;
    public static Block wyvstone;
    public static Block wyvgrass;
    public static Block wyvdirt;
    public static Block wyvwoodLeaves;
    public static Block wyvwoodSapling;
    public static Block wyvwoodLog;
    public static Block wyvwoodPlanks;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ancientSilverBlock = setup(new MoCBlockMetal(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(3.0F, 10.0F)), "ancient_silver_block");
        cobbledWyvstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 10.0F)), "cobbled_wyvstone");
        cobbledDeepWyvstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.5F, 10.0F)), "cobbled_deep_wyvstone");
        wyvstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 10.0F)), "wyvstone");
        deepWyvstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 10.0F)), "deep_wyvstone");
        mossyCobbledWyvstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 10.0F)), "mossy_cobbled_wyvstone");
        mossyCobbledDeepWyvstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 10.0F)), "mossy_cobbled_deep_wyvstone");
        gleamingGlass = setup(new MoCBlockGlass(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.4F)), "gleaming_glass");
        silverSand = setup(new MoCBlockSand(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.CLAY).hardnessAndResistance(0.6F)), "silver_sand");
        silverSandstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CLAY).hardnessAndResistance(1.2F)), "silver_sandstone");
        carvedSilverSandstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CLAY).hardnessAndResistance(1.2F)), "carved_silver_sandstone");
        smoothSilverSandstone = setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CLAY).hardnessAndResistance(1.2F)), "smooth_silver_sandstone");
        ancientOre = setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 5.0F)), "ancient_ore");
        firestone = setup(new MoCBlockFirestone(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).hardnessAndResistance(3.0F).setLightLevel(state -> 7)), "firestone");
        wyvernDiamondOre = setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(4.5F, 5.0F)), "wyvern_diamond_ore");
        wyvernEmeraldOre = setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(4.5F, 5.0F)), "wyvern_emerald_ore");
        wyvernGoldOre = setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 5.0F)), "wyvern_gold_ore");
        wyvernIronOre = setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 5.0F)), "wyvern_iron_ore");
        wyvernLapisOre = setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 5.0F)), "wyvern_lapis_ore");
        wyvgrass = setup(new MoCBlockGrass(AbstractBlock.Properties.create(Material.ORGANIC ,MaterialColor.BLUE_TERRACOTTA).hardnessAndResistance(0.7F)), "wyvgrass");
        wyvdirt = setup(new MoCBlockDirt(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.6F)), "wyvdirt");
        wyvwoodLeaves = setup(new MoCBlockLeaf(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.DIAMOND).hardnessAndResistance(0.2F)), "wyvwood_leaves");
        wyvwoodSapling = setup(new MoCBlockSapling(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.FOLIAGE).zeroHardnessAndResistance()), "wyvwood_sapling");
        wyvwoodLog = setup(new MoCBlockLog(AbstractBlock.Properties.create(Material.WOOD ,MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F)), "wyvwood_log");
        tallWyvgrass = setup(new MoCBlockTallGrass(AbstractBlock.Properties.create(Material.PLANTS ,MaterialColor.LIGHT_BLUE_TERRACOTTA).zeroHardnessAndResistance()), "tall_wyvgrass");
        wyvwoodPlanks = setup(new MoCBlockPlanks(AbstractBlock.Properties.create(Material.WOOD ,MaterialColor.DIAMOND).hardnessAndResistance(2.0F, 5.0F)), "wyvwood_planks");
        wyvernNestBlock = setup(new MoCBlockNest(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F)), "wyvern_nest_block");

        if (FMLEnvironment.dist == Dist.CLIENT) {
            RenderTypeLookup.setRenderLayer(tallWyvgrass, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(wyvwoodSapling, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(gleamingGlass, RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(wyvwoodLeaves, RenderType.getTripwire());
        }
    }

    @Nonnull
    public static <T extends Block> T  setup(T entry, String name) {
        ForgeRegistries.BLOCKS.register(setup(entry, new ResourceLocation(MoCConstants.MOD_ID, name)));
        ForgeRegistries.ITEMS.register(setup(new BlockItem(entry, new Item.Properties().group(MoCreatures.tabMoC)), new ResourceLocation(MoCConstants.MOD_ID, name)));
        return entry;
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry to setup must not be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign must not be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}
