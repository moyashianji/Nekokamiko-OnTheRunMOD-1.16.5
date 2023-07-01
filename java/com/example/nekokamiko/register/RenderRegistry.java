package com.example.nekokamiko.register;

import com.example.nekokamiko.entity.render.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class RenderRegistry {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent eventttt){

        RenderRegister();
    }


    private static void RenderRegister(){
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.CHASE_ENTITY.get(), HuntersRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.SILVER_ENTITY.get(), SilverRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.PHANTOM_ENTITY.get(), PhantomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.FAT_ENTITY.get(), FatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.BIG_ENTITY.get(), BigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.SMALL_ENTITY.get(), SmallRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TypesRegistry.SKELTON_ENTITY.get(), SkeltonRenderer::new);

    }
}