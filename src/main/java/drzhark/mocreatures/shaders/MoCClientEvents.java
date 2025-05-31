package drzhark.mocreatures.shaders;

import drzhark.mocreatures.client.renderer.fx.impl.MoCEntityFXStar;
import drzhark.mocreatures.client.renderer.fx.impl.MoCEntityFXUndead;
import drzhark.mocreatures.client.renderer.fx.impl.MoCEntityFXVacuum;
import drzhark.mocreatures.client.renderer.fx.impl.MoCEntityFXVanish;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "mocreatures", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCClientEvents {

    public static IAnimatedSprite UNDEAD_SPRITE_SET, VANISH_SPRITE_SET, STAR_SPRITE_SET, VACUUM_SPRITE_SET;

    @SubscribeEvent
    public static void onParticleFactoryRegister(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(
                MoCParticles.UNDEAD_FX.get(),
                spriteSet -> {
                    UNDEAD_SPRITE_SET = spriteSet;
                    return new MoCEntityFXUndead.Factory(spriteSet);
                }
        );

        Minecraft.getInstance().particles.registerFactory(
                MoCParticles.VANISH_FX.get(),
                spriteSet -> {
                    VANISH_SPRITE_SET = spriteSet;
                    return new MoCEntityFXVanish.Factory(spriteSet);
                }
        );

        Minecraft.getInstance().particles.registerFactory(
                MoCParticles.STAR_FX.get(),
                spriteSet -> {
                    STAR_SPRITE_SET = spriteSet;
                    return new MoCEntityFXStar.Factory(spriteSet);
                }
        );

        Minecraft.getInstance().particles.registerFactory(
                MoCParticles.VACUUM_FX.get(),
                spriteSet -> {
                    VACUUM_SPRITE_SET = spriteSet;
                    return new MoCEntityFXVacuum.Factory(spriteSet);
                }
        );

    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderType cutout = RenderType.getCutout();

        // ✅ Register waving render types for shader compatibility
        RenderTypeLookup.setRenderLayer(MoCBlocks.wyvwoodLeaves, cutout);
        RenderTypeLookup.setRenderLayer(MoCBlocks.tallWyvgrass, cutout);

        System.out.println("[MoC] Shader cutout layers applied for leaves and grass.");
    }
}