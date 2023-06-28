package com.Moyashi.nekokamiko.entity.render;


import com.Moyashi.nekokamiko.entity.FatHunter;
import com.Moyashi.nekokamiko.entity.HunterEntity;
import com.Moyashi.nekokamiko.entity.model.HunterModels;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class FatRenderer extends MobRenderer<FatHunter, HunterModels<FatHunter>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nekokamiko", "textures/entity/mob/kurohuku.png");

    public FatRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HunterModels<>(), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(FatHunter entity) {
        return TEXTURE;
    }

    protected void scale(FatHunter entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {

        float scalex = 3.0F;
        float scaley = 1.0F;
        float scalez = 1.5F;
        matrixStackIn.scale(scalex, scaley, scalez);
    }
}