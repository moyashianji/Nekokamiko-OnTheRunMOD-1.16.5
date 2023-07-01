package com.example.nekokamiko.entity.render;


import com.example.nekokamiko.entity.SmallHunter;
import com.example.nekokamiko.entity.model.HunterModels;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class SmallRenderer extends MobRenderer<SmallHunter, HunterModels<SmallHunter>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nekokamiko", "textures/entity/mob/kurohuku.png");

    public SmallRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HunterModels<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SmallHunter entity) {
        return TEXTURE;
    }

    protected void scale(SmallHunter entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {

        float f = 0.5F;
        matrixStackIn.scale(f,f,f);//全部０．８売
    }
}