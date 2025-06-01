/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.*;
import drzhark.mocreatures.entity.ambient.*;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.hostile.*;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import drzhark.mocreatures.entity.hunter.*;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.neutral.MoCEntityBoar;
import drzhark.mocreatures.entity.neutral.*;
import drzhark.mocreatures.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCEntities {
    public static BiomeDictionary.Type STEEP = BiomeDictionary.Type.getType("STEEP");
    public static BiomeDictionary.Type WYVERN_LAIR = BiomeDictionary.Type.getType("WYVERN_LAIR");
    public static Map<EntityType<? extends LivingEntity>, Supplier<AttributeModifierMap.MutableAttribute>> ENTITIES = new HashMap<>();
    private static final List<Item> SPAWN_EGGS = new ArrayList<>();

    /**
     * Animal
     */
    public static EntityType<MoCEntityBird> BIRD = createEntityEntry(EntityType.Builder.create(MoCEntityBird::new, EntityClassification.CREATURE).size(0.5F, 0.9F), MoCEntityBird::registerAttributes, "Bird", 37109, 4609629, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityBlackBear> BEAR = createEntityEntry(EntityType.Builder.create(MoCEntityBlackBear::new, EntityClassification.CREATURE).size(0.85F, 1.175F), MoCEntityBlackBear::registerAttributes, "BlackBear", 986897, 8609347, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityBoar> BOAR = createEntityEntry(EntityType.Builder.create(MoCEntityBoar::new, EntityClassification.CREATURE).size(0.9F, 0.9F), MoCEntityBoar::registerAttributes, "Boar", 2037783, 4995892, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityBunny> BUNNY = createEntityEntry(EntityType.Builder.create(MoCEntityBunny::new, EntityClassification.CREATURE).size(0.5F, 0.5F), MoCEntityBunny::registerAttributes, "Bunny", 8741934, 14527570, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityCrocodile> CROCODILE = createEntityEntry(EntityType.Builder.create(MoCEntityCrocodile::new, EntityClassification.CREATURE).size(0.9F, 0.5F), MoCEntityCrocodile::registerAttributes, "Crocodile", 2698525, 10720356, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityDuck> DUCK = createEntityEntry(EntityType.Builder.create(MoCEntityDuck::new, EntityClassification.CREATURE).size(0.4F, 0.7F), MoCEntityDuck::registerAttributes, "Duck", 3161353, 14011565, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityDeer> DEER = createEntityEntry(EntityType.Builder.create(MoCEntityDeer::new, EntityClassification.CREATURE).size(0.9F, 1.425F), MoCEntityDeer::registerAttributes, "Deer", 11572843, 13752020, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityElephant> ELEPHANT = createEntityEntry(EntityType.Builder.create(MoCEntityElephant::new, EntityClassification.CREATURE).size(1.1F, 3F), MoCEntityElephant::registerAttributes, "Elephant", 4274216, 9337176, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityEnt> ENT = createEntityEntry(EntityType.Builder.create(MoCEntityEnt::new, EntityClassification.CREATURE).size(1.4F, 7F), MoCEntityEnt::registerAttributes, "Ent", 9794886, 5800509, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityFilchLizard> FILCH_LIZARD = createEntityEntry(EntityType.Builder.create(MoCEntityFilchLizard::new, EntityClassification.CREATURE).size(0.6f, 0.5f), MoCEntityFilchLizard::registerAttributes, "FilchLizard", 9930060, 5580310, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityFox> FOX = createEntityEntry(EntityType.Builder.create(MoCEntityFox::new, EntityClassification.CREATURE).size(0.7F, 0.85F), MoCEntityFox::registerAttributes, "Fox", 15966491, 4009236, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityGoat> GOAT = createEntityEntry(EntityType.Builder.create(MoCEntityGoat::new, EntityClassification.CREATURE).size(0.8F, 0.9F), MoCEntityGoat::registerAttributes, "Goat", 15262682, 4404517, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityGrizzlyBear> GRIZZLY_BEAR = createEntityEntry(EntityType.Builder.create(MoCEntityGrizzlyBear::new, EntityClassification.CREATURE).size(1.125F, 1.57F), MoCEntityGrizzlyBear::registerAttributes, "GrizzlyBear", 3547151, 11371099, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityKitty> KITTY = createEntityEntry(EntityType.Builder.create(MoCEntityKitty::new, EntityClassification.CREATURE).size(0.8F, 0.8F), MoCEntityKitty::registerAttributes, "Kitty", 16707009, 14861419, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityKitty::getCanSpawnHere);
    public static EntityType<MoCEntityKomodo> KOMODO_DRAGON = createEntityEntry(EntityType.Builder.create(MoCEntityKomodo::new, EntityClassification.CREATURE).size(1.25F, 0.9F), MoCEntityKomodo::registerAttributes, "KomodoDragon", 8615512, 3025185, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityLeoger> LEOGER = createEntityEntry(EntityType.Builder.create(MoCEntityLeoger::new, EntityClassification.CREATURE).size(1.3F, 1.3815F), MoCEntityLeoger::registerAttributes, "Leoger", 13274957, 6638124, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityLeopard> LEOPARD = createEntityEntry(EntityType.Builder.create(MoCEntityLeopard::new, EntityClassification.CREATURE).size(1.165F, 1.01F), MoCEntityLeopard::registerAttributes, "Leopard", 13478009, 3682085, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityLiard> LIARD = createEntityEntry(EntityType.Builder.create(MoCEntityLiard::new, EntityClassification.CREATURE).size(1.175F, 1.065F), MoCEntityLiard::registerAttributes, "Liard", 11965543, 8215850, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityLion> LION = createEntityEntry(EntityType.Builder.create(MoCEntityLion::new, EntityClassification.CREATURE).size(1.25F, 1.275F), MoCEntityLion::registerAttributes, "Lion", 11503958, 2234383, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityLiger> LIGER = createEntityEntry(EntityType.Builder.create(MoCEntityLiger::new, EntityClassification.CREATURE).size(1.35F, 1.43525F), MoCEntityLiger::registerAttributes, "Liger", 13347170, 9068088, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityLither> LITHER = createEntityEntry(EntityType.Builder.create(MoCEntityLither::new, EntityClassification.CREATURE).size(1.175F, 1.17F), MoCEntityLither::registerAttributes, "Lither", 2234897, 7821878, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityManticorePet> MANTICORE_PET = createEntityEntry(EntityType.Builder.create(MoCEntityManticorePet::new, EntityClassification.CREATURE).size(1.4F, 1.3F), MoCEntityManticorePet::registerAttributes, "ManticorePet");
    public static EntityType<MoCEntityMole> MOLE = createEntityEntry(EntityType.Builder.create(MoCEntityMole::new, EntityClassification.CREATURE).size(1F, 0.5F), MoCEntityMole::registerAttributes, "Mole", 263173, 10646113, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityMouse> MOUSE = createEntityEntry(EntityType.Builder.create(MoCEntityMouse::new, EntityClassification.CREATURE).size(0.45F, 0.3F), MoCEntityMouse::registerAttributes, "Mouse", 7428164, 15510186, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityOstrich> OSTRICH = createEntityEntry(EntityType.Builder.create(MoCEntityOstrich::new, EntityClassification.CREATURE).size(0.8F, 2.225F), MoCEntityOstrich::registerAttributes, "Ostrich", 12884106, 10646377, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityPandaBear> PANDA_BEAR = createEntityEntry(EntityType.Builder.create(MoCEntityPandaBear::new, EntityClassification.CREATURE).size(0.8F, 1.05F), MoCEntityPandaBear::registerAttributes, "PandaBear", 13354393, 789516, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityPanthard> PANTHARD = createEntityEntry(EntityType.Builder.create(MoCEntityPanthard::new, EntityClassification.CREATURE).size(1.14F, 1.063175F), MoCEntityPanthard::registerAttributes, "Panthard", 591108, 9005068, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityPanther> PANTHER = createEntityEntry(EntityType.Builder.create(MoCEntityPanther::new, EntityClassification.CREATURE).size(1.175F, 1.065F), MoCEntityPanther::registerAttributes, "Panther", 1709584, 16768078, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityPanthger> PANTHGER = createEntityEntry(EntityType.Builder.create(MoCEntityPanthger::new, EntityClassification.CREATURE).size(1.225F, 1.2225F), MoCEntityPanthger::registerAttributes, "Panthger", 2826517, 14348086, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityPetScorpion> PET_SCORPION = createEntityEntry(EntityType.Builder.create(MoCEntityPetScorpion::new, EntityClassification.CREATURE).size(1.4F, 0.9F), MoCEntityPetScorpion::registerAttributes, "PetScorpion");
    public static EntityType<MoCEntityPolarBear> POLAR_BEAR = createEntityEntry(EntityType.Builder.create(MoCEntityPolarBear::new, EntityClassification.CREATURE).size(1.5F, 1.834F), MoCEntityPolarBear::registerAttributes, "WildPolarBear", 15131867, 11380879, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityRaccoon> RACCOON = createEntityEntry(EntityType.Builder.create(MoCEntityRaccoon::new, EntityClassification.CREATURE).size(0.6F, 0.525F), MoCEntityRaccoon::registerAttributes, "Raccoon", 6115913, 1578001, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntitySnake> SNAKE = createEntityEntry(EntityType.Builder.create(MoCEntitySnake::new, EntityClassification.CREATURE).size(1.4F, 0.5F), MoCEntitySnake::registerAttributes, "Snake", 670976, 11309312, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityTiger> TIGER = createEntityEntry(EntityType.Builder.create(MoCEntityTiger::new, EntityClassification.CREATURE).size(1.25F, 1.275F), MoCEntityTiger::registerAttributes, "Tiger", 12476160, 2956299, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityTurtle> TURTLE = createEntityEntry(EntityType.Builder.create(MoCEntityTurtle::new, EntityClassification.CREATURE).size(0.6F, 0.425F), MoCEntityTurtle::registerAttributes, "Turtle", 6505237, 10524955, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityTurkey> TURKEY = createEntityEntry(EntityType.Builder.create(MoCEntityTurkey::new, EntityClassification.CREATURE).size(0.6F, 0.9F), MoCEntityTurkey::registerAttributes, "Turkey", 12268098, 6991322, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityHorse> WILDHORSE = createEntityEntry(EntityType.Builder.create(MoCEntityHorse::new, EntityClassification.CREATURE).size(1.3964844F, 1.6F), MoCEntityHorse::registerAttributes, "WildHorse", 9204829, 11379712, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAnimal::getCanSpawnHere);
    public static EntityType<MoCEntityWyvern> WYVERN = createEntityEntry(EntityType.Builder.create(MoCEntityWyvern::new, EntityClassification.CREATURE).size(1.45F, 1.55F), MoCEntityWyvern::registerAttributes, "Wyvern", 11440923, 15526339, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityWyvern::getCanSpawnHere);
    /**
     * Monster
     */
    public static EntityType<MoCEntityCaveOgre> CAVE_OGRE = createEntityEntry(EntityType.Builder.create(MoCEntityCaveOgre::new, EntityClassification.MONSTER).size(1.8F, 3.05F), MoCEntityCaveOgre::registerAttributes, "CaveOgre", 5079480, 12581631, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityCaveOgre::getCanSpawnHere);
    public static EntityType<MoCEntityFlameWraith> FLAME_WRAITH = createEntityEntry(EntityType.Builder.create(MoCEntityFlameWraith::new, EntityClassification.MONSTER).size(0.6F, 2.0F), MoCEntityFlameWraith::registerAttributes, "FlameWraith", 8988239, 16748288, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityFireOgre> FIRE_OGRE = createEntityEntry(EntityType.Builder.create(MoCEntityFireOgre::new, EntityClassification.MONSTER).size(1.8F, 3.05F), MoCEntityFireOgre::registerAttributes, "FireOgre", 6882304, 16430080, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityGreenOgre> GREEN_OGRE = createEntityEntry(EntityType.Builder.create(MoCEntityGreenOgre::new, EntityClassification.MONSTER).size(1.8F, 3.05F), MoCEntityGreenOgre::registerAttributes, "GreenOgre", 1607501, 2032997, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityGolem> BIG_GOLEM = createEntityEntry(EntityType.Builder.create(MoCEntityGolem::new, EntityClassification.MONSTER).size(1.8F, 4.3F), MoCEntityGolem::registerAttributes, "BigGolem", 4868682, 52411, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityGolem::getCanSpawnHere);
    public static EntityType<MoCEntityHorseMob> HORSEMOB = createEntityEntry(EntityType.Builder.create(MoCEntityHorseMob::new, EntityClassification.MONSTER).size(1.3964844F, 1.6F), MoCEntityHorseMob::registerAttributes, "HorseMob", 6326628, 12369062, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityHellRat> HELLRAT = createEntityEntry(EntityType.Builder.create(MoCEntityHellRat::new, EntityClassification.MONSTER).size(0.88F, 0.755F), MoCEntityHellRat::registerAttributes, "HellRat", 1049090, 15956249, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityDarkManticore> DARK_MANTICORE = createEntityEntry(EntityType.Builder.create(MoCEntityDarkManticore::new, EntityClassification.MONSTER).size(1.35F, 1.45F), MoCEntityDarkManticore::registerAttributes, "DarkManticore", 3289650, 657930, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityDarkManticore::getCanSpawnHere);
    public static EntityType<MoCEntityFireManticore> FIRE_MANTICORE = createEntityEntry(EntityType.Builder.create(MoCEntityFireManticore::new, EntityClassification.MONSTER).size(1.35F, 1.45F), MoCEntityFireManticore::registerAttributes, "FireManticore", 7148552, 2819585, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityFrostManticore> FROST_MANTICORE = createEntityEntry(EntityType.Builder.create(MoCEntityFrostManticore::new, EntityClassification.MONSTER).size(1.35F, 1.45F), MoCEntityFrostManticore::registerAttributes, "FrostManticore", 3559006, 2041389, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityPlainManticore> PLAIN_MANTICORE = createEntityEntry(EntityType.Builder.create(MoCEntityPlainManticore::new, EntityClassification.MONSTER).size(1.35F, 1.45F), MoCEntityPlainManticore::registerAttributes, "PlainManticore", 7623465, 5510656, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityToxicManticore> TOXIC_MANTICORE = createEntityEntry(EntityType.Builder.create(MoCEntityToxicManticore::new, EntityClassification.MONSTER).size(1.35F, 1.45F), MoCEntityToxicManticore::registerAttributes, "ToxicManticore", 6252034, 3365689, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityMiniGolem> MINI_GOLEM = createEntityEntry(EntityType.Builder.create(MoCEntityMiniGolem::new, EntityClassification.MONSTER).size(0.9F, 1.2F), MoCEntityMiniGolem::registerAttributes, "MiniGolem", 7895160, 8512741, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityRat> RAT = createEntityEntry(EntityType.Builder.create(MoCEntityRat::new, EntityClassification.MONSTER).size(0.58F, 0.455F), MoCEntityRat::registerAttributes, "Rat", 3685435, 15838633, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntitySilverSkeleton> SILVER_SKELETON = createEntityEntry(EntityType.Builder.create(MoCEntitySilverSkeleton::new, EntityClassification.MONSTER).size(0.6F, 2.125F), MoCEntitySilverSkeleton::registerAttributes, "SilverSkeleton", 13421750, 8158847, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityCaveScorpion> CAVE_SCORPION = createEntityEntry(EntityType.Builder.create(MoCEntityCaveScorpion::new, EntityClassification.MONSTER).size(1.4F, 0.9F), MoCEntityCaveScorpion::registerAttributes, "CaveScorpion", 789516, 3223866, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityCaveScorpion::getCanSpawnHere);
    public static EntityType<MoCEntityDirtScorpion> DIRT_SCORPION = createEntityEntry(EntityType.Builder.create(MoCEntityDirtScorpion::new, EntityClassification.MONSTER).size(1.4F, 0.9F), MoCEntityDirtScorpion::registerAttributes, "DirtScorpion", 4134919, 13139755, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityFrostScorpion> FROST_SCORPION = createEntityEntry(EntityType.Builder.create(MoCEntityFrostScorpion::new, EntityClassification.MONSTER).size(1.4F, 0.9F), MoCEntityFrostScorpion::registerAttributes, "FrostScorpion", 333608, 5218691, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityFireScorpion> FIRE_SCORPION = createEntityEntry(EntityType.Builder.create(MoCEntityFireScorpion::new, EntityClassification.MONSTER).size(1.4F, 0.9F), MoCEntityFireScorpion::registerAttributes, "FireScorpion", 2163457, 9515286, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityUndeadScorpion> UNDEAD_SCORPION = createEntityEntry(EntityType.Builder.create(MoCEntityUndeadScorpion::new, EntityClassification.MONSTER).size(1.4F, 0.9F), MoCEntityUndeadScorpion::registerAttributes, "UndeadScorpion", 1118208, 7899732, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityWerewolf> WEREWOLF = createEntityEntry(EntityType.Builder.create(MoCEntityWerewolf::new, EntityClassification.MONSTER).size(0.7F, 2.0F), MoCEntityWerewolf::registerAttributes, "Werewolf", 1970698, 7032379, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityWerewolf::getCanSpawnHere);
    public static EntityType<MoCEntityWraith> WRAITH = createEntityEntry(EntityType.Builder.create(MoCEntityWraith::new, EntityClassification.MONSTER).size(0.6F, 2.0F), MoCEntityWraith::registerAttributes, "Wraith", 5987163, 16711680, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityMob::getCanSpawnHere);
    public static EntityType<MoCEntityWWolf> WWOLF = createEntityEntry(EntityType.Builder.create(MoCEntityWWolf::new, EntityClassification.MONSTER).size(0.8F, 1.1F), MoCEntityWWolf::registerAttributes, "WWolf", 5657166, 13223102, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityWWolf::getCanSpawnHere);
    /**
     * Aquatic
     */
    public static EntityType<MoCEntityAnchovy> ANCHOVY = createEntityEntry(EntityType.Builder.create(MoCEntityAnchovy::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "Anchovy", 7039838, 12763545, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityAngelFish> ANGELFISH = createEntityEntry(EntityType.Builder.create(MoCEntityAngelFish::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "AngelFish", 12040119, 15970609, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityAngler> ANGLER = createEntityEntry(EntityType.Builder.create(MoCEntityAngler::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "Angler", 5257257, 6225864, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityBass> BASS = createEntityEntry(EntityType.Builder.create(MoCEntityBass::new, EntityClassification.WATER_CREATURE).size(0.7f, 0.45f), MoCEntityAquatic::registerAttributes, "Bass", 4341299, 10051649, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityClownFish> CLOWNFISH = createEntityEntry(EntityType.Builder.create(MoCEntityClownFish::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "ClownFish", 16439491, 15425029, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityCod> COD = createEntityEntry(EntityType.Builder.create(MoCEntityCod::new, EntityClassification.WATER_CREATURE).size(0.7f, 0.45f), MoCEntityAquatic::registerAttributes, "Cod", 5459520, 14600592, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityDolphin> DOLPHIN = createEntityEntry(EntityType.Builder.create(MoCEntityDolphin::new, EntityClassification.WATER_CREATURE).size(1.3F, 0.605F), MoCEntityAquatic::registerAttributes, "Dolphin", 4086148, 11251396, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityFishy> FISHY = createEntityEntry(EntityType.Builder.create(MoCEntityFishy::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "Fishy", 5665535, 2037680, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityGoldFish> GOLDFISH = createEntityEntry(EntityType.Builder.create(MoCEntityGoldFish::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "GoldFish", 15577089, 16735257, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityHippoTang> HIPPOTANG = createEntityEntry(EntityType.Builder.create(MoCEntityHippoTang::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "HippoTang", 4280267, 12893441, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityJellyFish> JELLYFISH = createEntityEntry(EntityType.Builder.create(MoCEntityJellyFish::new, EntityClassification.WATER_CREATURE).size(0.45F, 0.575F), MoCEntityAquatic::registerAttributes, "JellyFish", 12758461, 9465021, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityManderin> MANDERIN = createEntityEntry(EntityType.Builder.create(MoCEntityManderin::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "Manderin", 14764801, 5935359, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityPiranha> PIRANHA = createEntityEntry(EntityType.Builder.create(MoCEntityPiranha::new, EntityClassification.WATER_CREATURE).size(0.5f, 0.3f), MoCEntityAquatic::registerAttributes, "Piranha", 10756121, 3160114, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntitySalmon> SALMON = createEntityEntry(EntityType.Builder.create(MoCEntitySalmon::new, EntityClassification.WATER_CREATURE).size(0.7f, 0.45f), MoCEntityAquatic::registerAttributes, "Salmon", 5262951, 10716540, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityMantaRay> MANTARAY = createEntityEntry(EntityType.Builder.create(MoCEntityMantaRay::new, EntityClassification.WATER_CREATURE).size(1.4F, 0.4F), MoCEntityAquatic::registerAttributes, "MantaRay", 5791360, 11580358, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityShark> SHARK = createEntityEntry(EntityType.Builder.create(MoCEntityShark::new, EntityClassification.WATER_CREATURE).size(1.65F, 0.9F), MoCEntityAquatic::registerAttributes, "Shark", 3817558, 11580358, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    public static EntityType<MoCEntityStingRay> STINGRAY = createEntityEntry(EntityType.Builder.create(MoCEntityStingRay::new, EntityClassification.WATER_CREATURE).size(0.7F, 0.3F), MoCEntityAquatic::registerAttributes, "StingRay", 3679519, 8418674, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, MoCEntityAquatic::getCanSpawnHere);
    /**
     * Ambient
     */
    public static EntityType<MoCEntityAnt> ANT = createEntityEntry(EntityType.Builder.create(MoCEntityAnt::new, EntityClassification.AMBIENT).size(0.3F, 0.2F), MoCEntityAnt::registerAttributes, "Ant", 5915945, 2693905, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityBee> BEE = createEntityEntry(EntityType.Builder.create(MoCEntityBee::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityBee::registerAttributes, "Bee", 15912747, 526604, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityButterfly> BUTTERFLY = createEntityEntry(EntityType.Builder.create(MoCEntityButterfly::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityButterfly::registerAttributes, "ButterFly", 12615169, 2956801, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityCrab> CRAB = createEntityEntry(EntityType.Builder.create(MoCEntityCrab::new, EntityClassification.AMBIENT).size(0.45F, 0.3F), MoCEntityCrab::registerAttributes, "Crab", 11880978, 15514213, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityCricket> CRICKET = createEntityEntry(EntityType.Builder.create(MoCEntityCricket::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityCricket::registerAttributes, "Cricket", 4071430, 8612672, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityDragonfly> DRAGONFLY = createEntityEntry(EntityType.Builder.create(MoCEntityDragonfly::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityDragonfly::registerAttributes, "DragonFly", 665770, 2207231, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityFirefly> FIREFLY = createEntityEntry(EntityType.Builder.create(MoCEntityFirefly::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityFirefly::registerAttributes, "Firefly", 2102294, 8501028, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityFly> FLY = createEntityEntry(EntityType.Builder.create(MoCEntityFly::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityFly::registerAttributes, "Fly", 1184284, 11077640, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityGrasshopper> GRASSHOPPER = createEntityEntry(EntityType.Builder.create(MoCEntityGrasshopper::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityGrasshopper::registerAttributes, "Grasshopper", 7830593, 3747075, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityMaggot> MAGGOT = createEntityEntry(EntityType.Builder.create(MoCEntityMaggot::new, EntityClassification.AMBIENT).size(0.2F, 0.2F), MoCEntityMaggot::registerAttributes, "Maggot", 14076037, 6839592, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntitySnail> SNAIL = createEntityEntry(EntityType.Builder.create(MoCEntitySnail::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntitySnail::registerAttributes, "Snail", 10850932, 7225384, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    public static EntityType<MoCEntityRoach> ROACH = createEntityEntry(EntityType.Builder.create(MoCEntityRoach::new, EntityClassification.AMBIENT).size(0.4F, 0.3F), MoCEntityRoach::registerAttributes, "Roach", 5185289, 10245148, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, MoCEntityAmbient::getCanSpawnHere);
    /**
     * Other
     */
    public static EntityType<MoCEntityEgg> EGG = createEntityEntry(EntityType.Builder.create(MoCEntityEgg::new, EntityClassification.MISC).size(0.25F, 0.25F), MoCEntityEgg::registerAttributes, "Egg");
    public static EntityType<MoCEntityKittyBed> KITTY_BED = createEntityEntry(EntityType.Builder.create(MoCEntityKittyBed::new, EntityClassification.MISC).size(1.0F, 0.15F), MoCEntityKittyBed::registerAttributes, "KittyBed");
    public static EntityType<MoCEntityLitterBox> LITTERBOX = createEntityEntry(EntityType.Builder.create(MoCEntityLitterBox::new, EntityClassification.MISC).size(1.0F, 0.15F), MoCEntityLitterBox::registerAttributes, "LitterBox");
    public static EntityType<MoCEntityThrowableRock> TROCK = createRock(EntityType.Builder.create(MoCEntityThrowableRock::new, EntityClassification.MISC).size(1.0F, 1.0F), "TRock");
    static int MoCEntityID = 0;

    private static <T extends Entity> EntityType createRock(EntityType.Builder<T> builder, String name) {
        EntityType entity = builder.build(name);
        entity.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
        return entity;
    }

    private static <T extends Entity> EntityType createEntityEntry(EntityType.Builder<T> builder, Supplier<AttributeModifierMap.MutableAttribute> attributes, String name) {
        EntityType entity = builder.build(name);
        entity.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
        ENTITIES.put(entity, attributes);
        return entity;
    }

    private static <T extends LivingEntity> EntityType createEntityEntry(EntityType.Builder<T> builder, Supplier<AttributeModifierMap.MutableAttribute> attributes, String name, int primaryColorIn, int secondaryColorIn, EntitySpawnPlacementRegistry.PlacementType type, EntitySpawnPlacementRegistry.IPlacementPredicate placementPredicate) {
        EntityType entity = builder.build(name);
        entity.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
        EntitySpawnPlacementRegistry.register(entity, type, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, placementPredicate);
        Item spawnEgg = new SpawnEggItem(entity, primaryColorIn, secondaryColorIn, (new Item.Properties()).group(ItemGroup.MISC));
        spawnEgg.setRegistryName(new ResourceLocation(MoCConstants.MOD_ID, name.toLowerCase() + "_spawn_egg"));
        SPAWN_EGGS.add(spawnEgg);
        ENTITIES.put(entity, attributes);
        return entity;
    }

    public static void registerEntities() {
        MoCreatures.LOGGER.info("Registering entities...");

        RegistryKey<World>[] overworld = new RegistryKey[]{World.OVERWORLD};
        RegistryKey<World>[] nether = new RegistryKey[]{World.THE_NETHER};
        RegistryKey<World>[] overworldNether = new RegistryKey[]{World.OVERWORLD, World.THE_NETHER};
        RegistryKey<World>[] wyvernLair = new RegistryKey[]{MoCreatures.proxy.wyvernDimension};
        RegistryKey<World>[] overworldWyvernLair = new RegistryKey[]{World.OVERWORLD, MoCreatures.proxy.wyvernDimension};

        /*
         * Animal
         */
        MoCreatures.mocEntityMap.put("BlackBear", new MoCEntityData("BlackBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BEAR, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.CONIFEROUS))));
        MoCreatures.mocEntityMap.put("GrizzlyBear", new MoCEntityData("GrizzlyBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(GRIZZLY_BEAR, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
        MoCreatures.mocEntityMap.put("WildPolarBear", new MoCEntityData("WildPolarBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(POLAR_BEAR, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.SNOWY))));
        MoCreatures.mocEntityMap.put("PandaBear", new MoCEntityData("PandaBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(PANDA_BEAR, 7, 1, 3), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BIRD, 16, 2, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.MESA, Type.LUSH, STEEP))));
        MoCreatures.mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BOAR, 12, 2, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, overworldWyvernLair, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BUNNY, 12, 2, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.SNOWY, Type.CONIFEROUS, STEEP))));
        MoCreatures.mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CROCODILE, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP))));
        MoCreatures.mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(DEER, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.CONIFEROUS))));
        MoCreatures.mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(DUCK, 12, 2, 4), new ArrayList<>(Arrays.asList(Type.RIVER, Type.LUSH))));
        MoCreatures.mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ELEPHANT, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.JUNGLE, Type.SAVANNA, Type.SNOWY)), new ArrayList<>(Arrays.asList(Type.MESA))));
        MoCreatures.mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ENT, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
        MoCreatures.mocEntityMap.put("FilchLizard", new MoCEntityData("FilchLizard", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(FILCH_LIZARD, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY, Type.MESA))));
        MoCreatures.mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(FOX, 10, 1, 1), new ArrayList<>(Arrays.asList(Type.FOREST, Type.SNOWY, Type.CONIFEROUS))));
        MoCreatures.mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(GOAT, 12, 1, 3), new ArrayList<>(Arrays.asList(Type.PLAINS, STEEP))));
        MoCreatures.mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(KITTY, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.FOREST)))); // spawns in villages
        MoCreatures.mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(KOMODO_DRAGON, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Leopard", new MoCEntityData("Leopard", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(LEOPARD, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.SNOWY, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Lion", new MoCEntityData("Lion", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(LION, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY, Type.MESA))));
        MoCreatures.mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MOLE, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.LUSH, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MOUSE, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.MESA, STEEP))));
        MoCreatures.mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(OSTRICH, 7, 1, 1), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY)), new ArrayList<>(Arrays.asList(Type.MESA))));
        MoCreatures.mocEntityMap.put("Panther", new MoCEntityData("Panther", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(PANTHER, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(RACCOON, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
        MoCreatures.mocEntityMap.put("Snake", new MoCEntityData("Snake", 3, overworldWyvernLair, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(SNAKE, 14, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.PLAINS, Type.FOREST, Type.SWAMP, Type.LUSH, Type.JUNGLE, STEEP))));
        MoCreatures.mocEntityMap.put("Tiger", new MoCEntityData("Tiger", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TIGER, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TURKEY, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TURTLE, 12, 1, 3), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER))));
        MoCreatures.mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(WILDHORSE, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, wyvernLair, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(WYVERN, 12, 1, 3), new ArrayList<>(Arrays.asList(WYVERN_LAIR))));
        /*
         * Monster
         */
        MoCreatures.mocEntityMap.put("BigGolem", new MoCEntityData("BigGolem", 1, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BIG_GOLEM, 3, 1, 1), new ArrayList<>(Arrays.asList(Type.SANDY, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        MoCreatures.mocEntityMap.put("MiniGolem", new MoCEntityData("MiniGolem", 2, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MINI_GOLEM, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        MoCreatures.mocEntityMap.put("HorseMob", new MoCEntityData("HorseMob", 3, overworldNether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(HORSEMOB, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER, Type.PLAINS, Type.SAVANNA, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("CaveScorpion", new MoCEntityData("CaveScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAVE_SCORPION, 4, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.SNOWY, Type.MESA, Type.DRY, Type.HOT, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("DirtScorpion", new MoCEntityData("DirtScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(DIRT_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.DRY, Type.HOT))));
        MoCreatures.mocEntityMap.put("FireScorpion", new MoCEntityData("FireScorpion", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(FIRE_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("FrostScorpion", new MoCEntityData("FrostScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(FROST_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SNOWY))));
        MoCreatures.mocEntityMap.put("UndeadScorpion", new MoCEntityData("UndeadScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(UNDEAD_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("SilverSkeleton", new MoCEntityData("SilverSkeleton", 4, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(SILVER_SKELETON, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.SANDY, Type.SNOWY, Type.MESA, Type.PLAINS, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("Werewolf", new MoCEntityData("Werewolf", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(WEREWOLF, 8, 1, 4), new ArrayList<>(Arrays.asList(Type.CONIFEROUS, Type.FOREST))));
        MoCreatures.mocEntityMap.put("WWolf", new MoCEntityData("WWolf", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(WWOLF, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.SNOWY, Type.WASTELAND))));
        MoCreatures.mocEntityMap.put("DarkManticore", new MoCEntityData("DarkManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(DARK_MANTICORE, 5, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MOUNTAIN, Type.PLAINS, Type.SNOWY, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("FireManticore", new MoCEntityData("FireManticore", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(FIRE_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("FrostManticore", new MoCEntityData("FrostManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(FROST_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SNOWY))));
        MoCreatures.mocEntityMap.put("PlainManticore", new MoCEntityData("PlainManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(PLAIN_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MOUNTAIN, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("ToxicManticore", new MoCEntityData("ToxicManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(TOXIC_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("GreenOgre", new MoCEntityData("GreenOgre", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(GREEN_OGRE, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.SWAMP, Type.LUSH, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("CaveOgre", new MoCEntityData("CaveOgre", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAVE_OGRE, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("FireOgre", new MoCEntityData("FireOgre", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(FIRE_OGRE, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("Wraith", new MoCEntityData("Wraith", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(WRAITH, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.CONIFEROUS, Type.DEAD, Type.DENSE, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("FlameWraith", new MoCEntityData("FlameWraith", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(FLAME_WRAITH, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("Rat", new MoCEntityData("Rat", 2, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(RAT, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.MESA, STEEP))));
        MoCreatures.mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(HELLRAT, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.NETHER))));
        /*
         * Aquatic
         */
        MoCreatures.mocEntityMap.put("Bass", new MoCEntityData("Bass", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(BASS, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER, Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Cod", new MoCEntityData("Cod", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(COD, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(DOLPHIN, 6, 2, 4), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(FISHY, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER, Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(JELLYFISH, 8, 1, 4), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Salmon", new MoCEntityData("Salmon", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(SALMON, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER, Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(PIRANHA, 4, 1, 3), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.JUNGLE, Type.LUSH))));
        MoCreatures.mocEntityMap.put("MantaRay", new MoCEntityData("MantaRay", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MANTARAY, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("StingRay", new MoCEntityData("StingRay", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(STINGRAY, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER))));
        MoCreatures.mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(SHARK, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Anchovy", new MoCEntityData("Anchovy", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(ANCHOVY, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN, Type.RIVER))));
        MoCreatures.mocEntityMap.put("AngelFish", new MoCEntityData("AngelFish", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(ANGELFISH, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER, Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Angler", new MoCEntityData("Angler", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(ANGLER, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("ClownFish", new MoCEntityData("ClownFish", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CLOWNFISH, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("GoldFish", new MoCEntityData("GoldFish", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(GOLDFISH, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.RIVER))));
        MoCreatures.mocEntityMap.put("HippoTang", new MoCEntityData("HippoTang", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(HIPPOTANG, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Manderin", new MoCEntityData("Manderin", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MANDERIN, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        /*
         * Ambient
         */
        MoCreatures.mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(ANT, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.MESA, Type.PLAINS, Type.SWAMP, Type.HOT, Type.DRY, Type.LUSH, Type.SPARSE, STEEP))));
        MoCreatures.mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(BEE, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(BUTTERFLY, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(CRAB, 11, 1, 2), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER))));
        MoCreatures.mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(CRICKET, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
        MoCreatures.mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, overworldWyvernLair, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(DRAGONFLY, 9, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP, Type.BEACH, Type.WET))));
        MoCreatures.mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(FIREFLY, 9, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.SWAMP, Type.LUSH, Type.DENSE))));
        MoCreatures.mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(FLY, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.MESA, Type.WET, Type.SWAMP, Type.HOT))));
        MoCreatures.mocEntityMap.put("Grasshopper", new MoCEntityData("Grasshopper", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(GRASSHOPPER, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MAGGOT, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.MESA, Type.WET, Type.SWAMP, Type.HOT))));
        MoCreatures.mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(SNAIL, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.DENSE, Type.LUSH))));
        MoCreatures.mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(ROACH, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.HOT))));
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType<?> entity : ENTITIES.keySet()) {
            event.getRegistry().register(entity);
        }
        event.getRegistry().register(TROCK);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        for (Map.Entry<EntityType<? extends LivingEntity>, Supplier<AttributeModifierMap.MutableAttribute>> entry : ENTITIES.entrySet()) {
            AttributeModifierMap map = entry.getValue().get().create();
            event.put(entry.getKey(), map);

            // ✅ Attribute validation log
            if (!map.hasAttribute(Attributes.FOLLOW_RANGE)) {
                //MoCreatures.LOGGER.warn("[Attribute Missing] " + entry.getKey().getRegistryName() + " lacks FOLLOW_RANGE");
            } else {
                //MoCreatures.LOGGER.debug("[Attribute OK] " + entry.getKey().getRegistryName() + " includes FOLLOW_RANGE");
            }
        }
    }


    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : SPAWN_EGGS) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {

        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void registerSpawns(BiomeLoadingEvent event) {
            MoCreatures.proxy.readMocConfigValues();

            if (event.getName() != null) {
                Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
                if (biome == null) return;

                RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, event.getName());
                Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biomeKey);

                for (MoCEntityData entityData : MoCreatures.mocEntityMap.values()) {
                    if (!entityData.getCanSpawn() || entityData.getFrequency() <= 0) {
                        if (MoCreatures.proxy.debug) {
                            MoCreatures.LOGGER.debug("[Spawn Skip] " + entityData.getEntityName() + " is disabled or has 0 frequency");
                        }
                        continue;
                    }

                    List<BiomeDictionary.Type> includeList = entityData.getBiomeTypes();
                    List<BiomeDictionary.Type> excludeList = entityData.getBlockedBiomeTypes();

                    boolean biomeAllowed = biomeTypes.stream().noneMatch(excludeList::contains)
                            && biomeTypes.stream().anyMatch(includeList::contains);

                    if (!biomeAllowed) {
                        if (MoCreatures.proxy.debug) {
                            MoCreatures.LOGGER.debug("[Biome Skip] " + entityData.getEntityName()
                                    + " does not match biome " + event.getName()
                                    + " (types=" + biomeTypes + ", includes=" + includeList + ", excludes=" + excludeList + ")");
                        }
                        continue;
                    }

                    event.getSpawns().getSpawner(entityData.getType()).add(entityData.getSpawnListEntry());

                    if (MoCreatures.proxy.debug) {
                        MoCreatures.LOGGER.info("[Spawn Registered] " + entityData.getEntityName()
                                + " in biome " + event.getName()
                                + " with weight=" + entityData.getSpawnListEntry().itemWeight
                                + ", min=" + entityData.getSpawnListEntry().minCount
                                + ", max=" + entityData.getSpawnListEntry().maxCount);
                    }
                }
            }
        }
    }
}
