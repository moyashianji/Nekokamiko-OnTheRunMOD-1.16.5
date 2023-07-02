package com.example.nekokamiko.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.nekokamiko.main.Nekokamiko.COLOR_BACKGROUND;

public class ConfigFile {


    public void ConfigFile(){

    }
    public static void generateConfigFile(){
        File modsFolder = new File("mods");
        File modFolder = new File(modsFolder, "nekokamiko");

        System.out.println(COLOR_BACKGROUND+"Already File Created");

        //モッド名のフォルダが存在しなければ生成する
        if(!modFolder.exists()){
            modFolder.mkdirs();
            System.out.println(COLOR_BACKGROUND+"Folder Created");
        }

        File configFile = new File(modFolder, "config.txt");
        System.out.println(COLOR_BACKGROUND+"Already Config Created");

        if(!configFile.exists()){
            try{
                configFile.createNewFile();

                FileWriter writer = new FileWriter(configFile);
                writer.write("\u30fb\u901a\u5e38\u30cf\u30f3\u30bf\u30fc");
                writer.write("\n" +
                        "\tHP\t:100");
                writer.write("\n" +
                        "\tSpeed\t:0.5");
                writer.write("\n" +
                        "\tRate\t:100");
                writer.write("\n" +
                        "\tMin\t:2");
                writer.write("\n" +
                        "\tMax\t:4");
                writer.write("\n" +
                        "\tDistance\t:64");
                writer.write("\n"+
                        "\u30fb\u5927\u304d\u3044\u30cf\u30f3\u30bf\u30fc");
                writer.write("\n" +
                        "\tHP\t:100");
                writer.write("\n" +
                        "\tSpeed\t:1.0");
                writer.write("\n" +
                        "\tRate\t:100");
                writer.write("\n" +
                        "\tMin\t:2");
                writer.write("\n" +
                        "\tMax\t:4");
                writer.write("\n" +
                        "\tDistance\t:3");
                writer.write("\n"+
                        "\u30fb\u5c0f\u3055\u3044\u30cf\u30f3\u30bf\u30fc");
                writer.write("\n" +
                        "\tHP\t:100");
                writer.write("\n" +
                        "\tSpeed\t:1.0");
                writer.write("\n" +
                        "\tRate\t:100");
                writer.write("\n" +
                        "\tMin\t:2");
                writer.write("\n" +
                        "\tMax\t:4");
                writer.write("\n" +
                        "\tDistance\t:30");
                writer.write("\n"+
                        "\u30fb\u30d5\u30a1\u30f3\u30c8\u30e0\u30cf\u30f3\u30bf\u30fc");
                writer.write("\n" +
                        "\tHP\t:100");
                writer.write("\n" +
                        "\tSpeed\t:0.3");
                writer.write("\n" +
                        "\tRate\t:100");
                writer.write("\n" +
                        "\tMin\t:2");
                writer.write("\n" +
                        "\tMax\t:4");
                writer.write("\n" +
                        "\tDistance\t:30");
                writer.write("\n"+
                        "\u30fb\u30b9\u30b1\u30eb\u30c8\u30f3\u30cf\u30f3\u30bf\u30fc");
                writer.write("\n" +
                        "\tHP\t:10");
                writer.write("\n" +
                        "\tSpeed\t:0.5");
                writer.write("\n" +
                        "\tRate\t:100");
                writer.write("\n" +
                        "\tMin\t:2");
                writer.write("\n" +
                        "\tMax\t:4");
                writer.write("\n" +
                        "\tDistance\t:64");

                writer.close();
                System.out.println(COLOR_BACKGROUND+"Config Created");


            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

}
