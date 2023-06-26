package com.Moyashi.nekokamiko.register;

import com.Moyashi.nekokamiko.entity.HunterEntity;
import com.Moyashi.nekokamiko.register.TypesRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class CommonEventBusSubscriber {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent eventt){
        mobAttributes();

    }
    private static void mobAttributes(){

        DeferredWorkQueue.runLater(()-> {
            GlobalEntityTypeAttributes.put(TypesRegistry.CHASE_ENTITY.get(), HunterEntity.registerAttributes().build());//さっき作ったステータスを実装するよ

        });



    }

    //
    //DeferredWorkQueueは、Minecraft Forgeのクラスであり、非同期のタスクをキューに登録して後で実行するための機能を提供します。
    //Minecraftでは、ゲーム内の多くの処理はメインスレッドで行われますが、一部の処理はメインスレッドの負荷を軽減するために非同期に実行する必要があります。
    // DeferredWorkQueueを使用することで、非同期タスクのスケジューリングと実行を簡単に管理できます。
    //DeferredWorkQueueは、以下のような状況で使用されます：
    //チャンクの生成や読み込み: チャンクは大量のブロックデータを含むため、その生成や読み込みは時間がかかる場合があります。
    // DeferredWorkQueueを使用して、チャンク生成や読み込みを非同期に行うことで、スムーズなゲームプレイを提供することができます。
    //データの保存や読み込み: 大容量のデータをファイルに保存する場合や、外部データベースとの通信を行う場合など、時間がかかる処理は非同期に実行する必要があります。
    // DeferredWorkQueueを使用して、データの保存や読み込みを非同期に行うことで、メインスレッドのパフォーマンスを向上させることができます。
    //
    //ラムダ式はインターフェイスの抽象クラスに入れることができる
    //この場合runLater(interface)というRunnableインタフェイスが入っているので、ラムダ式を使いそこの抽象クラスに入れることで非同期実行している
    //

    //
    //Minecraftのエンティティは、さまざまな属性を持っています。
    // 例えば、体力（Health）、移動速度（Movement Speed）、攻撃力（Attack Damage）などがエンティティの属性として扱われます。
    // GlobalEntityTypeAttributesを使用することで、Mod内で追加されたエンティティの属性を設定できます。
    //GlobalEntityTypeAttributesクラスは、以下のような形式で使用されます：
    //java
    //Copy code
    //GlobalEntityTypeAttributes.put(entityType, attributeSupplier);
    //entityTypeは、属性を設定したいエンティティの種類（EntityType）を指定します。
    //attributeSupplierは、属性を提供するためのSupplierオブジェクトを指定します。このSupplierオブジェクトは、エンティティの属性を生成するためのコードを含んでいます。
    //
}
