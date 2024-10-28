package fr.r0ane.farmerdelightplus.common.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.r0ane.farmerdelightplus.Main;
import fr.r0ane.farmerdelightplus.common.blockentity.FermentationBarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FermentationBarrelHudOverlay {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Main.MODID,
            "textures/gui/hud/fermentation_barrel_background.png");
    private static final ResourceLocation BACKGROUND_EMPTY = new ResourceLocation(Main.MODID,
            "textures/gui/hud/fermentation_barrel_background_empty.png");
    private static final String ITEM_TEXTURE = "textures/item/";

    public static final IGuiOverlay HUD = ((forgeGui, guiGraphics, partialTick, w, h) -> {
        int x = w / 2 + 10;
        int y = h / 2 + 5;

        Minecraft mc = Minecraft.getInstance();
        if (mc.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            HitResult objectMouseOver = mc.hitResult;
            if (objectMouseOver instanceof BlockHitResult blockHitResult) {
                ClientLevel level = mc.level;
                BlockEntity be = level.getBlockEntity(blockHitResult.getBlockPos());

                if (be instanceof FermentationBarrelBlockEntity blockEntity) {
                    if (!blockEntity.getHisType().isEmpty()) {
                        int result = switch (blockEntity.getHisType()) {
                            case "wine" -> 90;
                            case "vodka" -> 90;
                            case "beer" -> 16;
                            default -> 16;
                        };

                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.setShaderTexture(0, BACKGROUND);
                        guiGraphics.blit(BACKGROUND, x, y, 0, 0, 94, 43, 94, 43);

                        guiGraphics.blit(new ResourceLocation(Main.MODID, ITEM_TEXTURE + switch (blockEntity.getHisType()) {
                            case "wine" -> "bowl_of_crushed_grapes";
                            case "vodka" -> "potato_sugar_bottle";
                            case "beer" -> "barley_sugar_bottle";
                            default -> "";
                        } + ".png"), x + 3, y + 3, 0, 0, 16, 16, 16, 16);

                        guiGraphics.blit(new ResourceLocation(Main.MODID, ITEM_TEXTURE + switch (blockEntity.getHisType()) {
                            case "wine" -> "wine_bottle";
                            case "vodka" -> "vodka_bottle";
                            case "beer" -> "beer_bottle";
                            default -> "";
                        } + ".png"), x + 3, y + 24, 16, 16, 0, 0, result, result, result, result);

                        guiGraphics.drawString(Minecraft.getInstance().font, blockEntity.getQuantityA() + "/5000mB", x + 21, y + 8, 0xbf8b4d);
                        guiGraphics.drawString(Minecraft.getInstance().font, blockEntity.getQuantityB() + "/5000mB", x + 21, y + 29, 0xbf8b4d);
                    } else {
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.setShaderTexture(0, BACKGROUND_EMPTY);
                        guiGraphics.blit(BACKGROUND_EMPTY, x, y, 0, 0, 118, 35, 118, 35);

                        guiGraphics.drawString(Minecraft.getInstance().font, "Fill it with these items:", x + 5, y + 5, 0xbf8b4d);

                        guiGraphics.blit(new ResourceLocation(Main.MODID, ITEM_TEXTURE + "bowl_of_crushed_grapes" + ".png"), x + 5, y + 16 , 0, 0, 16, 16, 16, 16);
                        guiGraphics.blit(new ResourceLocation(Main.MODID, ITEM_TEXTURE + "potato_sugar_bottle" + ".png"), x + 25, y + 16 , 0, 0, 16, 16, 16, 16);
                        guiGraphics.blit(new ResourceLocation(Main.MODID, ITEM_TEXTURE + "barley_sugar_bottle" + ".png"), x + 45, y + 16 , 0, 0, 16, 16, 16, 16);
                    }
                }
            }
        }
    });
}
