/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class CompatScreen extends Screen {
    public static boolean showScreen = true;
    private final List<ITextComponent> messages = new ArrayList<>();
    private int textHeight;

    public CompatScreen() {
        super(StringTextComponent.EMPTY);
        this.messages.add(new TranslationTextComponent("msg.mocreatures.compat.cms"));
        this.messages.add(new StringTextComponent(""));
        this.messages.add(new StringTextComponent(""));
        this.messages.add(new TranslationTextComponent("msg.mocreatures.compat.cms1"));
        this.messages.add(new StringTextComponent(""));
        this.messages.add(new TranslationTextComponent("msg.mocreatures.compat.cms2"));
        this.messages.add(new StringTextComponent(""));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        int i = this.height / 2 - this.textHeight / 2;
        for (ITextComponent s : this.messages) {
            List<IReorderingProcessor> lines = this.font.trimStringToWidth(s, this.width);
            for (IReorderingProcessor line : lines) {
                this.font.drawTextWithShadow(matrixStack, line, (float)(this.width / 2 - this.font.func_243245_a(line) / 2), (float)i, 16777215);
                i += this.font.FONT_HEIGHT;
            }
        }
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void init() {
        this.buttons.clear();
        this.textHeight = 0;
        for (ITextComponent s : this.messages) {
            List<IReorderingProcessor> lines = this.font.trimStringToWidth(s, this.width);
            this.textHeight += lines.size() * this.font.FONT_HEIGHT;
        }
        this.addButton(new Button(this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.font.FONT_HEIGHT, this.height - 30), 200, 20, new TranslationTextComponent("gui.done"), (p_213089_1_) -> {
            this.minecraft.displayGuiScreen(new WorldSelectionScreen(this));
        }));
    }
}
