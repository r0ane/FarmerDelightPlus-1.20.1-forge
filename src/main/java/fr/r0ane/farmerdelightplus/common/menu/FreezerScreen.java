package fr.r0ane.farmerdelightplus.common.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.r0ane.farmerdelightplus.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;

public class FreezerScreen extends AbstractContainerScreen<FreezerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MODID, "textures/gui/freezer_menu.png");

    public FreezerScreen(AbstractContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super((FreezerMenu) pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderIceBiome(guiGraphics, x, y);
        renderProgressIce(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 80, y + 35, 177, 13, menu.getScaledProgress(), 17);
        }
    }

    private void renderProgressIce(GuiGraphics guiGraphics, int x, int y) {
        int iceScaledProgress = menu.getIceScaledProgress();
        guiGraphics.blit(TEXTURE, x + 58, y + 36 + iceScaledProgress, 176, iceScaledProgress, 13, 13 - iceScaledProgress);
    }

    private void renderIceBiome(GuiGraphics guiGraphics, int x, int y) {
        if(menu.itIsIntoAnIceBiome()) {
            guiGraphics.blit(TEXTURE, x + 56, y + 53, 176, 30, 17, 16);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
