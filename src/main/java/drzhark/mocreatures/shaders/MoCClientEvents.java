package drzhark.mocreatures.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.client.renderer.fx.MoCEntityFXUndead;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "mocreatures", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCClientEvents {

    public static IAnimatedSprite UNDEAD_SPRITE_SET;

    @SubscribeEvent
    public static void onParticleFactoryRegister(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(
                MoCParticles.UNDEAD_FX.get(),
                spriteSet -> {
                    UNDEAD_SPRITE_SET = spriteSet;
                    return new MoCEntityFXUndead.Factory(spriteSet);
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