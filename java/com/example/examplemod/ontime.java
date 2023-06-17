package com.example.examplemod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("nekokamiko")

public class ontime {

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontRenderer = minecraft.font;

            int scaledWidth = minecraft.getWindow().getGuiScaledWidth();
            int scaledHeight = minecraft.getWindow().getGuiScaledHeight();

            String text = "sssssssssss";
            int textWidth = fontRenderer.width(text);
            int textHeight = fontRenderer.lineHeight;

            int x = MathHelper.floor((scaledWidth - textWidth) / 2.0);
            int y = MathHelper.floor((scaledHeight - textHeight) / 2.0);

            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.pushPose();

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);

            matrixStack.translate(x, y, 0);
            fontRenderer.draw(matrixStack, new StringTextComponent(text), 0, 0, -1);

            matrixStack.popPose();
        }
    }
    public static void register() {
        MinecraftForge.EVENT_BUS.register(ontime.class);
    }

}
