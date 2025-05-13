/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.network.message.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MoCMessageHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MoCConstants.MOD_ID, "channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void init() {
        INSTANCE.messageBuilder(MoCMessageAnimation.class, 0).encoder(MoCMessageAnimation::encode).decoder(MoCMessageAnimation::new).consumer(MoCMessageAnimation::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageAppear.class, 1).encoder(MoCMessageAppear::encode).decoder(MoCMessageAppear::new).consumer(MoCMessageAppear::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageAttachedEntity.class, 2).encoder(MoCMessageAttachedEntity::encode).decoder(MoCMessageAttachedEntity::new).consumer(MoCMessageAttachedEntity::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageEntityDive.class, 3).encoder(MoCMessageEntityDive::encode).decoder(MoCMessageEntityDive::new).consumer(MoCMessageEntityDive::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageEntityJump.class, 4).encoder(MoCMessageEntityJump::encode).decoder(MoCMessageEntityJump::new).consumer(MoCMessageEntityJump::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageExplode.class, 5).encoder(MoCMessageExplode::encode).decoder(MoCMessageExplode::new).consumer(MoCMessageExplode::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageHealth.class, 6).encoder(MoCMessageHealth::encode).decoder(MoCMessageHealth::new).consumer(MoCMessageHealth::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageHeart.class, 7).encoder(MoCMessageHeart::encode).decoder(MoCMessageHeart::new).consumer(MoCMessageHeart::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageInstaSpawn.class, 8).encoder(MoCMessageInstaSpawn::encode).decoder(MoCMessageInstaSpawn::new).consumer(MoCMessageInstaSpawn::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageNameGUI.class, 9).encoder(MoCMessageNameGUI::encode).decoder(MoCMessageNameGUI::new).consumer(MoCMessageNameGUI::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageUpdatePetName.class, 10).encoder(MoCMessageUpdatePetName::encode).decoder(MoCMessageUpdatePetName::new).consumer(MoCMessageUpdatePetName::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageShuffle.class, 11).encoder(MoCMessageShuffle::encode).decoder(MoCMessageShuffle::new).consumer(MoCMessageShuffle::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageTwoBytes.class, 12).encoder(MoCMessageTwoBytes::encode).decoder(MoCMessageTwoBytes::new).consumer(MoCMessageTwoBytes::onMessage).add();
        INSTANCE.messageBuilder(MoCMessageVanish.class, 13).encoder(MoCMessageVanish::encode).decoder(MoCMessageVanish::new).consumer(MoCMessageVanish::onMessage).add();
    }
}
