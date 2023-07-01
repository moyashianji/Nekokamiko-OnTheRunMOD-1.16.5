package com.example.nekokamiko.entity.render;


import com.example.nekokamiko.entity.BigHunter;
import com.example.nekokamiko.entity.model.HunterModels;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class BigRenderer extends MobRenderer<BigHunter, HunterModels<BigHunter>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nekokamiko", "textures/entity/mob/kurohuku.png");

    public BigRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HunterModels<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(BigHunter entity) {
        return TEXTURE;
    }

    protected void scale(BigHunter entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {

        float f = 2.4F;
        matrixStackIn.scale(f,f,f);//全部０．８売
    }
}