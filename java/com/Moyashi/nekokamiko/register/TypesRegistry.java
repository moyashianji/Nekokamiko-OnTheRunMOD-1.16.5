package com.Moyashi.nekokamiko.register;

import com.Moyashi.nekokamiko.entity.HunterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class TypesRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "nekokamiko");

    public static final DeferredRegister<EntityType<?>> CHASE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, "nekokamiko");

    public static final RegistryObject<EntityType<HunterEntity>> CHASE_ENTITY = chaseentity("titan", HunterEntity::new);

    public static  <T extends Entity>RegistryObject<EntityType<T>> chaseentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MISC)
                .sized(0.5F,1.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }

}
