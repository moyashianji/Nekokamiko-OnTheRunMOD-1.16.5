package com.Moyashi.nekokamiko.register;

import com.Moyashi.nekokamiko.entity.HunterEntity;
import com.Moyashi.nekokamiko.entity.render.HuntersRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "nekokamiko", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModBusSubscriber {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "nekokamiko");
    public static final RegistryObject<EntityType<HunterEntity>> TITAN_ENTITY = ENTITY_TYPES.register("titan",
            () -> EntityType.Builder.<HunterEntity>of(HunterEntity::new, EntityClassification.MONSTER)
                    .sized(1.5F, 6.0F)
                    .build("nekokamiko:titan"));

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(TITAN_ENTITY.get(), HuntersRenderer::new);
    }


}