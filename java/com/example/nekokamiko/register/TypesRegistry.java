package com.example.nekokamiko.register;

import com.example.nekokamiko.entity.*;
import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class TypesRegistry {


    public static final DeferredRegister<EntityType<?>> CHASE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, "nekokamiko");

    public static final RegistryObject<EntityType<HunterEntity>> CHASE_ENTITY = chaseentity("titan", HunterEntity::new);
    public static final RegistryObject<EntityType<SilverHunter>> SILVER_ENTITY = silverentity("silver", SilverHunter::new);

    public static final RegistryObject<EntityType<PhantomHunter>> PHANTOM_ENTITY = phantomentity("phantom", PhantomHunter::new);
    public static final RegistryObject<EntityType<FatHunter>> FAT_ENTITY = fatentity("fat", FatHunter::new);
    public static final RegistryObject<EntityType<BigHunter>> BIG_ENTITY = bigentity("big", BigHunter::new);

    public static final RegistryObject<EntityType<SmallHunter>> SMALL_ENTITY = smallentity("small", SmallHunter::new);
    public static final RegistryObject<EntityType<SkeltonHunter>> SKELTON_ENTITY = skeltonhunter("skelton", SkeltonHunter::new);

    public static  <T extends Entity>RegistryObject<EntityType<T>> chaseentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.5F,1.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }

    public static  <T extends Entity>RegistryObject<EntityType<T>> silverentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.2F,0.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }
    public static  <T extends Entity>RegistryObject<EntityType<T>> phantomentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.2F,0.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }

    public static  <T extends Entity>RegistryObject<EntityType<T>> fatentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(1.0F,3.0F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }

    public static  <T extends Entity>RegistryObject<EntityType<T>> bigentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.5F,1.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }

    public static  <T extends Entity>RegistryObject<EntityType<T>> smallentity(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.5F,1.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }

    public static  <T extends Entity>RegistryObject<EntityType<T>> skeltonhunter(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.5F,1.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }
    public static  <T extends Entity>RegistryObject<EntityType<T>> enderhunter(String id, BiFunction<EntityType<T>, World, T> function){

        EntityType<T> type = EntityType
                .Builder
                .of(function::apply,
                        EntityClassification.MONSTER)
                .sized(0.5F,1.6F)
                .setTrackingRange(32)
                .build(id);


        return CHASE_REGISTER.register(id, ()->type);
    }




}
