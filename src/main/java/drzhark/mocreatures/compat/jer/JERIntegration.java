/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.jer;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityData;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

public class JERIntegration {

    public static void init() {
//        IJERAPI jerApi = JERAPI.getInstance();
//        IMobRegistry jerMobRegistry = jerApi.getMobRegistry();
//        World jerWorld = jerApi.getWorld();
//
//        jerMobRegistry.register(new MoCEntityAnchovy(jerWorld), LightLevel.any, getBiomeTypeNames("Anchovy"), MoCLootTables.ANCHOVY);
//        jerMobRegistry.register(new MoCEntityAngelFish(jerWorld), LightLevel.any, getBiomeTypeNames("AngelFish"), MoCLootTables.ANGELFISH);
//        jerMobRegistry.register(new MoCEntityAngler(jerWorld), LightLevel.any, getBiomeTypeNames("Angler"), MoCLootTables.ANGLERFISH);
//        jerMobRegistry.register(new MoCEntityAnt(jerWorld), LightLevel.any, getBiomeTypeNames("Ant"), MoCLootTables.ANT);
//        jerMobRegistry.register(new MoCEntityBass(jerWorld), LightLevel.any, getBiomeTypeNames("Bass"), MoCLootTables.BASS);
//        jerMobRegistry.register(new MoCEntityBee(jerWorld), LightLevel.any, getBiomeTypeNames("Bee"), MoCLootTables.BEE);
//        jerMobRegistry.register(new MoCEntityBird(jerWorld), LightLevel.any, getBiomeTypeNames("Bird"), MoCLootTables.BIRD);
//        jerMobRegistry.register(new MoCEntityBlackBear(jerWorld), LightLevel.any, getBiomeTypeNames("BlackBear"), MoCLootTables.BLACK_BEAR);
//        jerMobRegistry.register(new MoCEntityBoar(jerWorld), LightLevel.any, getBiomeTypeNames("Boar"), MoCLootTables.BOAR);
//        jerMobRegistry.register(new MoCEntityBunny(jerWorld), LightLevel.any, getBiomeTypeNames("Bunny"), MoCLootTables.BUNNY);
//        jerMobRegistry.register(new MoCEntityButterfly(jerWorld), LightLevel.any, getBiomeTypeNames("ButterFly"), MoCLootTables.BUTTERFLY);
//        jerMobRegistry.register(new MoCEntityCaveOgre(jerWorld), LightLevel.hostile, getBiomeTypeNames("CaveOgre"), MoCLootTables.CAVE_OGRE);
//        jerMobRegistry.register(new MoCEntityCaveScorpion(jerWorld), LightLevel.hostile, getBiomeTypeNames("CaveScorpion"), MoCLootTables.CAVE_SCORPION);
//        jerMobRegistry.register(new MoCEntityClownFish(jerWorld), LightLevel.any, getBiomeTypeNames("ClownFish"), MoCLootTables.CLOWNFISH);
//        jerMobRegistry.register(new MoCEntityCod(jerWorld), LightLevel.any, getBiomeTypeNames("Cod"), MoCLootTables.COD);
//        jerMobRegistry.register(new MoCEntityCrab(jerWorld), LightLevel.any, getBiomeTypeNames("Crab"), MoCLootTables.CRAB);
//        jerMobRegistry.register(new MoCEntityCricket(jerWorld), LightLevel.any, getBiomeTypeNames("Cricket"), MoCLootTables.CRICKET);
//        jerMobRegistry.register(new MoCEntityCrocodile(jerWorld), LightLevel.any, getBiomeTypeNames("Crocodile"), MoCLootTables.CROCODILE);
//        jerMobRegistry.register(new MoCEntityDarkManticore(jerWorld), LightLevel.hostile, getBiomeTypeNames("DarkManticore"), MoCLootTables.DARK_MANTICORE);
//        jerMobRegistry.register(new MoCEntityDeer(jerWorld), LightLevel.any, getBiomeTypeNames("Deer"), MoCLootTables.DEER);
//        jerMobRegistry.register(new MoCEntityDirtScorpion(jerWorld), LightLevel.hostile, getBiomeTypeNames("DirtScorpion"), MoCLootTables.DIRT_SCORPION);
//        jerMobRegistry.register(new MoCEntityDolphin(jerWorld), LightLevel.any, getBiomeTypeNames("Dolphin"), MoCLootTables.DOLPHIN);
//        jerMobRegistry.register(new MoCEntityDragonfly(jerWorld), LightLevel.any, getBiomeTypeNames("DragonFly"), MoCLootTables.DRAGONFLY);
//        jerMobRegistry.register(new MoCEntityDuck(jerWorld), LightLevel.any, getBiomeTypeNames("Duck"), MoCLootTables.DUCK);
//        jerMobRegistry.register(new MoCEntityFilchLizard(jerWorld), LightLevel.any, getBiomeTypeNames("FilchLizard"), MoCLootTables.FILCH_LIZARD_SPAWN);
//        jerMobRegistry.register(new MoCEntityFireManticore(jerWorld), LightLevel.hostile, getBiomeTypeNames("FireManticore"), MoCLootTables.FIRE_MANTICORE);
//        jerMobRegistry.register(new MoCEntityFireOgre(jerWorld), LightLevel.hostile, getBiomeTypeNames("FireOgre"), MoCLootTables.FIRE_OGRE);
//        jerMobRegistry.register(new MoCEntityFireScorpion(jerWorld), LightLevel.hostile, getBiomeTypeNames("FireScorpion"), MoCLootTables.FIRE_SCORPION);
//        jerMobRegistry.register(new MoCEntityFirefly(jerWorld), LightLevel.any, getBiomeTypeNames("Firefly"), MoCLootTables.FIREFLY);
//        jerMobRegistry.register(new MoCEntityFishy(jerWorld), LightLevel.any, getBiomeTypeNames("Fishy"), MoCLootTables.FISHY);
//        jerMobRegistry.register(new MoCEntityFlameWraith(jerWorld), LightLevel.hostile, getBiomeTypeNames("FlameWraith"), MoCLootTables.FLAME_WRAITH);
//        jerMobRegistry.register(new MoCEntityFly(jerWorld), LightLevel.any, getBiomeTypeNames("Fly"), MoCLootTables.FLY);
//        jerMobRegistry.register(new MoCEntityFox(jerWorld), LightLevel.any, getBiomeTypeNames("Fox"), MoCLootTables.FOX);
//        jerMobRegistry.register(new MoCEntityFrostManticore(jerWorld), LightLevel.hostile, getBiomeTypeNames("FrostManticore"), MoCLootTables.FROST_MANTICORE);
//        jerMobRegistry.register(new MoCEntityFrostScorpion(jerWorld), LightLevel.hostile, getBiomeTypeNames("FrostScorpion"), MoCLootTables.FROST_SCORPION);
//        jerMobRegistry.register(new MoCEntityGoat(jerWorld), LightLevel.any, getBiomeTypeNames("Goat"), MoCLootTables.GOAT);
//        jerMobRegistry.register(new MoCEntityGoldFish(jerWorld), LightLevel.any, getBiomeTypeNames("GoldFish"), MoCLootTables.GOLDFISH);
//        jerMobRegistry.register(new MoCEntityGolem(jerWorld), LightLevel.hostile, getBiomeTypeNames("BigGolem"), MoCLootTables.BIG_GOLEM);
//        jerMobRegistry.register(new MoCEntityGrasshopper(jerWorld), LightLevel.any, getBiomeTypeNames("Grasshopper"), MoCLootTables.GRASSHOPPER);
//        jerMobRegistry.register(new MoCEntityGreenOgre(jerWorld), LightLevel.hostile, getBiomeTypeNames("GreenOgre"), MoCLootTables.GREEN_OGRE);
//        jerMobRegistry.register(new MoCEntityGrizzlyBear(jerWorld), LightLevel.any, getBiomeTypeNames("GrizzlyBear"), MoCLootTables.GRIZZLY_BEAR);
//        jerMobRegistry.register(new MoCEntityHellRat(jerWorld), LightLevel.hostile, getBiomeTypeNames("HellRat"), MoCLootTables.HELL_RAT);
//        jerMobRegistry.register(new MoCEntityHippoTang(jerWorld), LightLevel.any, getBiomeTypeNames("HippoTang"), MoCLootTables.HIPPO_TANG);
//        jerMobRegistry.register(new MoCEntityJellyFish(jerWorld), LightLevel.any, getBiomeTypeNames("JellyFish"), MoCLootTables.JELLYFISH);
//        jerMobRegistry.register(new MoCEntityKitty(jerWorld), LightLevel.any, getBiomeTypeNames("Kitty"), MoCLootTables.KITTY);
//        jerMobRegistry.register(new MoCEntityKomodo(jerWorld), LightLevel.any, getBiomeTypeNames("KomodoDragon"), MoCLootTables.KOMODO_DRAGON);
//        jerMobRegistry.register(new MoCEntityLeoger(jerWorld), LightLevel.any, MoCLootTables.LEOGER);
//        jerMobRegistry.register(new MoCEntityLeopard(jerWorld), LightLevel.any, getBiomeTypeNames("Leopard"), MoCLootTables.LEOPARD);
//        jerMobRegistry.register(new MoCEntityLiard(jerWorld), LightLevel.any, MoCLootTables.LIARD);
//        jerMobRegistry.register(new MoCEntityLiger(jerWorld), LightLevel.any, MoCLootTables.LIGER);
//        jerMobRegistry.register(new MoCEntityLion(jerWorld), LightLevel.any, getBiomeTypeNames("Lion"), MoCLootTables.LION);
//        jerMobRegistry.register(new MoCEntityLither(jerWorld), LightLevel.any, MoCLootTables.LITHER);
//        jerMobRegistry.register(new MoCEntityMaggot(jerWorld), LightLevel.any, getBiomeTypeNames("Maggot"), MoCLootTables.MAGGOT);
//        jerMobRegistry.register(new MoCEntityManderin(jerWorld), LightLevel.any, getBiomeTypeNames("Manderin"), MoCLootTables.MANDARINFISH);
//        jerMobRegistry.register(new MoCEntityMantaRay(jerWorld), LightLevel.any, getBiomeTypeNames("MantaRay"), MoCLootTables.MANTA_RAY);
//        jerMobRegistry.register(new MoCEntityMiniGolem(jerWorld), LightLevel.hostile, getBiomeTypeNames("MiniGolem"), MoCLootTables.MINI_GOLEM);
//        jerMobRegistry.register(new MoCEntityMole(jerWorld), LightLevel.any, getBiomeTypeNames("Mole"), MoCLootTables.MOLE);
//        jerMobRegistry.register(new MoCEntityMouse(jerWorld), LightLevel.any, getBiomeTypeNames("Mouse"), MoCLootTables.MOUSE);
//        jerMobRegistry.register(new MoCEntityOstrich(jerWorld), LightLevel.any, getBiomeTypeNames("Ostrich"), MoCLootTables.OSTRICH);
//        jerMobRegistry.register(new MoCEntityPandaBear(jerWorld), LightLevel.any, getBiomeTypeNames("PandaBear"), MoCLootTables.PANDA_BEAR);
//        jerMobRegistry.register(new MoCEntityPanthard(jerWorld), LightLevel.any, MoCLootTables.PANTHARD);
//        jerMobRegistry.register(new MoCEntityPanther(jerWorld), LightLevel.any, getBiomeTypeNames("Panther"), MoCLootTables.PANTHER);
//        jerMobRegistry.register(new MoCEntityPanthger(jerWorld), LightLevel.any, MoCLootTables.PANTHGER);
//        jerMobRegistry.register(new MoCEntityPiranha(jerWorld), LightLevel.any, getBiomeTypeNames("Piranha"), MoCLootTables.PIRANHA);
//        jerMobRegistry.register(new MoCEntityPlainManticore(jerWorld), LightLevel.hostile, getBiomeTypeNames("PlainManticore"), MoCLootTables.PLAIN_MANTICORE);
//        jerMobRegistry.register(new MoCEntityPolarBear(jerWorld), LightLevel.any, getBiomeTypeNames("WildPolarBear"), MoCLootTables.POLAR_BEAR);
//        jerMobRegistry.register(new MoCEntityRaccoon(jerWorld), LightLevel.any, getBiomeTypeNames("Raccoon"), MoCLootTables.RACCOON);
//        jerMobRegistry.register(new MoCEntityRat(jerWorld), LightLevel.hostile, getBiomeTypeNames("Rat"), MoCLootTables.RAT);
//        jerMobRegistry.register(new MoCEntityRoach(jerWorld), LightLevel.any, getBiomeTypeNames("Roach"), MoCLootTables.ROACH);
//        jerMobRegistry.register(new MoCEntitySalmon(jerWorld), LightLevel.any, getBiomeTypeNames("Salmon"), MoCLootTables.SALMON);
//        jerMobRegistry.register(new MoCEntityShark(jerWorld), LightLevel.any, getBiomeTypeNames("Shark"), MoCLootTables.SHARK);
//        jerMobRegistry.register(new MoCEntitySilverSkeleton(jerWorld), LightLevel.hostile, getBiomeTypeNames("SilverSkeleton"), MoCLootTables.SILVER_SKELETON);
//        jerMobRegistry.register(new MoCEntitySnail(jerWorld), LightLevel.any, getBiomeTypeNames("Snail"), MoCLootTables.SNAIL);
//        jerMobRegistry.register(new MoCEntityStingRay(jerWorld), LightLevel.any, getBiomeTypeNames("StingRay"), MoCLootTables.STINGRAY);
//        jerMobRegistry.register(new MoCEntityTiger(jerWorld), LightLevel.any, getBiomeTypeNames("Tiger"), MoCLootTables.TIGER);
//        jerMobRegistry.register(new MoCEntityToxicManticore(jerWorld), LightLevel.hostile, getBiomeTypeNames("ToxicManticore"), MoCLootTables.TOXIC_MANTICORE);
//        jerMobRegistry.register(new MoCEntityTurkey(jerWorld), LightLevel.any, getBiomeTypeNames("Turkey"), MoCLootTables.TURKEY);
//        jerMobRegistry.register(new MoCEntityTurtle(jerWorld), LightLevel.any, getBiomeTypeNames("Turtle"), MoCLootTables.TURTLE);
//        jerMobRegistry.register(new MoCEntityUndeadScorpion(jerWorld), LightLevel.hostile, getBiomeTypeNames("UndeadScorpion"), MoCLootTables.UNDEAD_SCORPION);
//        jerMobRegistry.register(new MoCEntityWerewolf(jerWorld), LightLevel.hostile, getBiomeTypeNames("Werewolf"), MoCLootTables.WEREWOLF);
//        jerMobRegistry.register(new MoCEntityWWolf(jerWorld), LightLevel.hostile, getBiomeTypeNames("WWolf"), MoCLootTables.WILD_WOLF);
//        jerMobRegistry.register(new MoCEntityWraith(jerWorld), LightLevel.hostile, getBiomeTypeNames("Wraith"), MoCLootTables.WRAITH);
//        jerMobRegistry.register(new MoCEntityWyvern(jerWorld), LightLevel.any, getBiomeTypeNames("Wyvern"), MoCLootTables.WYVERN);
    }

    public static String[] getBiomeTypeNames(String entityName) {
        MoCEntityData entityData = MoCreatures.mocEntityMap.get(entityName);
        List<String> biomeTypes = new ArrayList<>();
        if (entityData != null) {
            for (BiomeDictionary.Type type : entityData.getBiomeTypes()) {
                biomeTypes.add(type.getName());
            }
        }
        return biomeTypes.toArray(new String[0]);
    }
}
