package com.example.nekokamiko.main;

import com.example.nekokamiko.entity.*;
import com.example.nekokamiko.register.TypesRegistry;
import com.ibm.icu.util.CodePointTrie;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.sql.Types;

public class SummonDelete {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity) event.getEntity();
            EntityType<?> entityType = mobEntity.getType();

            if (entityType.getCategory() == EntityClassification.MONSTER
                    && entityType != TypesRegistry.BIG_ENTITY.get()
                    && entityType != TypesRegistry.SILVER_ENTITY.get()
                    && entityType != TypesRegistry.PHANTOM_ENTITY.get()
                    && entityType != TypesRegistry.SKELTON_ENTITY.get()
                    && entityType != TypesRegistry.SMALL_ENTITY.get()) {
                event.setCanceled(true);
            }
        }
    }
    public static void register() {
        MinecraftForge.EVENT_BUS.register(SummonDelete.class);
    }

}
