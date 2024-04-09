package io.github.GrassyDev.pvzmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.wispforest.owo.mixin.ui.RenderSystemMixin;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BotanyStationScreen extends HandledScreen<BotanyStationScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(PvZCubed.MOD_ID, "textures/gui/botany_station_gui.png");

    public BotanyStationScreen(BotanyStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 200;
        playerInventoryTitleY = 200;
    }

    @Override
    protected void drawBackground(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        graphics.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(graphics, x, y);

    }

    private void renderProgressArrow(GuiGraphics graphics, int x, int y) {
        if(handler.isCrafting()){
            graphics.drawTexture(TEXTURE, x + 101, y, 192, 0, handler.getScaledProgress(),40);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics, mouseX, mouseY, delta);
        super.render(graphics, mouseX, mouseY, delta);
        drawMouseoverTooltip(graphics, mouseX, mouseY);
    }
}