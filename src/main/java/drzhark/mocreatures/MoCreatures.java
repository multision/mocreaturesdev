/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.MoCKeyHandler;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import drzhark.mocreatures.compat.CompatHandler;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import drzhark.mocreatures.event.MoCEventHooks;
import drzhark.mocreatures.event.MoCEventHooksClient;
import drzhark.mocreatures.event.MoCEventHooksTerrain;
import drzhark.mocreatures.init.MoCBiomesInit;
import drzhark.mocreatures.init.MoCCreativeTabs;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.proxy.MoCProxy;
import drzhark.mocreatures.proxy.MoCProxyClient;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(MoCConstants.MOD_ID)
public class MoCreatures {

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    public static final ItemGroup tabMoC = new MoCCreativeTabs(ItemGroup.GROUPS.length, "MoCreaturesTab");
    public static final String MOC_LOGO = TextFormatting.WHITE + "[" + TextFormatting.AQUA + MoCConstants.MOD_NAME + TextFormatting.WHITE + "]";
    public static MoCreatures instance;
    public static MoCProxy proxy;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");
    public static DimensionType WYVERN_SKYLANDS;
    public static RegistryKey<World> wyvernSkylandsDimensionID;
    public static Object2ObjectLinkedOpenHashMap<String, MoCEntityData> mocEntityMap = new Object2ObjectLinkedOpenHashMap<>();
    public static Object2ObjectOpenHashMap<EntityType<?>, MoCEntityData> entityMap = new Object2ObjectOpenHashMap<>();
    public static Int2ObjectOpenHashMap<Class<? extends MobEntity>> instaSpawnerMap = new Int2ObjectOpenHashMap<>();
    public MoCPetMapData mapData;

    public MoCreatures(){
        instance = this;


        this.proxy = DistExecutor.unsafeRunForDist(() -> MoCProxyClient::new, () -> MoCProxy::new);
        //this.proxy.initialize();
        //this.proxy.attachLifecycle(FMLJavaModLoadingContext.get().getModEventBus());
        //this.proxy.attachEventHandlers(MinecraftForge.EVENT_BUS);
        MoCMessageHandler.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        MinecraftForge.EVENT_BUS.register(new MoCEventHooksTerrain());
        //proxy.configInit();
        if (true) {
            MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
            MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
        }
        //MoCEntities.registerEntities();
        CompatHandler.preInit();

        wyvernSkylandsDimensionID = proxy.wyvernDimension;
        //proxy.mocSettingsConfig.save();
        proxy.configInit();
        proxy.registerRenderers();
        proxy.registerRenderInformation();

        MoCEventHooksTerrain.addBiomeTypes(); //TODO FINISHED
        //MoCBiomes.registerBiomes();
        MoCBiomesInit.setupSpawnRules();
        MoCEntities.registerEntities();

        CompatHandler.init();
        registerDeferredRegistries(eventBus);

        //ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(MoCConstants.MOD_ID, MoCConstants.DATAFIXER_VERSION); //TODO TheidenHD
        //modFixer.registerFix(FixTypes.BLOCK_ENTITY, new BlockIDFixer());
        //modFixer.registerFix(FixTypes.ENTITY, new EntityIDFixer());
    }

    public static boolean isServer(World world) {
        return !world.isRemote();
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        MoCSoundEvents.SOUND_DEFERRED.register(modBus);
        MoCParticles.PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


//    @EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//        CompatHandler.postInit();
//    }
//
//    @EventHandler
//    public void serverStarting(FMLServerStartingEvent event) {
//        event.registerServerCommand(new CommandMoCreatures());
//        event.registerServerCommand(new CommandMoCTP());
//        event.registerServerCommand(new CommandMoCPets());
//        if (isServer()) {
//            if (FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
//                event.registerServerCommand(new CommandMoCSpawn());
//            }
//        }
//    }
}
