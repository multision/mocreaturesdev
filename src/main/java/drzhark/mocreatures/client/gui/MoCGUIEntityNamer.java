/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageUpdatePetName;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCGUIEntityNamer extends Screen {

    private static final TextureManager textureManager = MoCProxyClient.mc.getTextureManager();
    private static final ResourceLocation TEXTURE_MOCNAME = MoCreatures.proxy.getGuiTexture("pet_naming.png");
    private final IMoCEntity namedEntity;
    protected String screenTitle;
    protected int xSize;
    protected int ySize;
    @SuppressWarnings("unused")
    private int updateCounter;
    private TextInputUtil textInputUtil;
    private String  nameToSet;

    public MoCGUIEntityNamer(IMoCEntity mocanimal, String s) {
        super(new StringTextComponent(("Choose your Pet's name:")));
        this.xSize = 256;
        this.ySize = 181;
        this.screenTitle = "Choose your Pet's name:";
        this.namedEntity = mocanimal;
        this.nameToSet = s;
        this.textInputUtil = new TextInputUtil(() -> nameToSet, (p_238850_1_) -> nameToSet = p_238850_1_,
                TextInputUtil.getClipboardTextSupplier(this.minecraft), TextInputUtil.getClipboardTextSetter(this.minecraft), (p_238848_1_) -> true);
    }

    @Override
    public void init() {
        this.buttons.clear();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width / 2 - 100, (this.height - (this.ySize + 16)) / 2 + 150, 150, 20, new StringTextComponent("Done"), (guibutton) -> {
            if (!guibutton.active) {
                return;
            }
            if (this.nameToSet != null) {
                updateName();
            }
        }));
    }

    public void updateName() {
        this.namedEntity.setPetName(nameToSet);
        MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageUpdatePetName(((MobEntity) this.namedEntity).getEntityId(), this.nameToSet));
        this.minecraft.displayGuiScreen(null);
    }

    @Override
    public void render(MatrixStack matrixStack, int i, int j, float f) {
        renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        textureManager.bindTexture(TEXTURE_MOCNAME);
        int l = (this.width - this.xSize) / 2;
        int i1 = (this.height - (this.ySize + 16)) / 2;
        blit(matrixStack, l, i1, 0, 0, this.xSize, this.ySize);
        drawCenteredString(matrixStack, this.font, this.screenTitle, this.width / 2, (this.height - (this.ySize + 16)) / 2 + 29, 0xffffff);
        drawCenteredString(matrixStack, this.font, this.nameToSet + "_", this.width / 2, (this.height - (this.ySize + 16)) / 2 + 74, 0xffffff);
        super.render(matrixStack, i, j, f);
    }

    public boolean charTyped(char codePoint, int modifiers) {
        this.textInputUtil.putChar(codePoint);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 257) {
            updateName();
        }
        return this.textInputUtil.specialKeyPressed(keyCode) ? true : super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        if (this.namedEntity instanceof IMoCTameable) {
            IMoCTameable tamedEntity = (IMoCTameable) this.namedEntity;
            tamedEntity.playTameEffect(true);
        }
    }

    @Override
    public void tick() {
        this.updateCounter++;
    }
}
