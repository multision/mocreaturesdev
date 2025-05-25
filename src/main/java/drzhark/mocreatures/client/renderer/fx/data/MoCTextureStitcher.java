package drzhark.mocreatures.client.renderer.fx.data;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MoCTextureStitcher {
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_PARTICLES_TEXTURE)) {
            event.addSprite(new ResourceLocation("mocreatures", "particle/fx_undead1"));
            event.addSprite(new ResourceLocation("mocreatures", "particle/fx_undead2"));
        }
    }
}
