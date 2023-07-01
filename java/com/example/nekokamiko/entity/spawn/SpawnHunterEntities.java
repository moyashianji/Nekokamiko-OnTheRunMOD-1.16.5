package com.example.nekokamiko.entity.spawn;

import com.example.nekokamiko.main.onImageGUI;
import com.example.nekokamiko.register.TypesRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.world.BiomeLoadingEvent;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpawnHunterEntities {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void entitySpawn(BiomeLoadingEvent event){

        event.getSpawns().addSpawn(EntityClassification.MONSTER
                , new MobSpawnInfo.Spawners(TypesRegistry.BIG_ENTITY.get()
                        , 100
                        ,2
                        ,4));

        event.getSpawns().addSpawn(EntityClassification.MONSTER
                , new MobSpawnInfo.Spawners(TypesRegistry.SILVER_ENTITY.get()
                        , 100
                        ,2
                        ,4));

        event.getSpawns().addSpawn(EntityClassification.MONSTER
                , new MobSpawnInfo.Spawners(TypesRegistry.PHANTOM_ENTITY.get()
                        , 100
                        ,2
                        ,4));

        event.getSpawns().addSpawn(EntityClassification.MONSTER
                , new MobSpawnInfo.Spawners(TypesRegistry.SMALL_ENTITY.get()
                        , 100
                        ,2
                        ,4));
        event.getSpawns().addSpawn(EntityClassification.MONSTER
                , new MobSpawnInfo.Spawners(TypesRegistry.SKELTON_ENTITY.get()
                        , 100
                        ,2
                        ,4));




        System.out.println("[NEKORUN]");

        }



    public static void register() {
        MinecraftForge.EVENT_BUS.register(SpawnHunterEntities.class);
    }


}
