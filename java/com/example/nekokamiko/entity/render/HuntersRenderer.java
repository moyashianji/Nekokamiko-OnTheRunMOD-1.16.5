package com.example.nekokamiko.entity.render;


import com.example.nekokamiko.entity.HunterEntity;
import com.example.nekokamiko.entity.model.HunterModels;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class HuntersRenderer extends MobRenderer<HunterEntity, HunterModels<HunterEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nekokamiko", "textures/entity/mob/kurohuku.png");

    public HuntersRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HunterModels<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(HunterEntity entity) {
        return TEXTURE;
    }
}