/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.texture;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxy;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;

public class MoCTextures {

    private static final Object2ObjectOpenHashMap<String, ResourceLocation> RESOURCE_CACHE = new Object2ObjectOpenHashMap<>();

    public ResourceLocation getArmorTexture(String texture) {
        return getTexture(MoCProxy.ARMOR_TEXTURE, texture);
    }

    public ResourceLocation getBlockTexture(String texture) {
        return getTexture(MoCProxy.BLOCK_TEXTURE, texture);
    }

    public ResourceLocation getItemTexture(String texture) {
        return getTexture(MoCProxy.ITEM_TEXTURE, texture);
    }

    public ResourceLocation getModelTexture(String texture) {
        return getTexture(MoCProxy.MODEL_TEXTURE, texture);
    }

    public ResourceLocation getGuiTexture(String texture) {
        return getTexture(MoCProxy.GUI_TEXTURE, texture);
    }

    public ResourceLocation getMiscTexture(String texture) {
        return getTexture(MoCProxy.MISC_TEXTURE, texture);
    }

    public ResourceLocation getTexture(String category, String texture) {
        ResourceLocation resourceLocation = RESOURCE_CACHE.get(texture);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(MoCConstants.MOD_PREFIX + category + texture);
            RESOURCE_CACHE.put(texture, resourceLocation);
        }
        return resourceLocation;
    }
}
