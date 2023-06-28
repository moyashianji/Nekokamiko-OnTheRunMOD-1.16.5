package com.Moyashi.nekokamiko.entity.render;


import com.Moyashi.nekokamiko.entity.HunterEntity;
import com.Moyashi.nekokamiko.entity.SilverHunter;
import com.Moyashi.nekokamiko.entity.model.HunterModels;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class SilverRenderer extends MobRenderer<SilverHunter, HunterModels<SilverHunter>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nekokamiko", "textures/entity/mob/kurohuku.png");

    public SilverRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HunterModels<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SilverHunter entity) {
        return TEXTURE;
    }
}