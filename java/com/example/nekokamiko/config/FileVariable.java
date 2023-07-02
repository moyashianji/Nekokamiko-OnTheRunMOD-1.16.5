package com.example.nekokamiko.config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.nekokamiko.main.Nekokamiko.COLOR_BACKGROUND;

public class FileVariable {
    public static double HunterHp = 100;
    public static double HunterSpeed = 0.5;
    public static int HunterRate = 100;
    public static int HunterMin = 2;
    public static int HunterMax = 4;
    public static double HunterDistance = 64;

    public static double BigHunterHp = 100;
    public static double BigHunterSpeed = 0.5;
    public static int BigHunterRate = 100;
    public static int BigHunterMin = 2;
    public static int BigHunterMax = 4;
    public static double BigHunterDistance = 64;

    public static double SmallHunterHp = 100;
    public static double SmallHunterSpeed = 0.5;
    public static int SmallHunterRate = 100;
    public static int SmallHunterMin = 2;
    public static int SmallHunterMax = 4;
    public static double SmallHunterDistance = 64;

    public static double PhantomHunterHp = 100;
    public static double PhantomHunterSpeed = 0.5;
    public static int PhantomHunterRate = 100;
    public static int PhantomHunterMin = 2;
    public static int PhantomHunterMax = 4;
    public static double PhantomHunterDistance = 64;

    public static double SkeletonHunterHp = 100;
    public static double SkeletonHunterSpeed = 0.5;
    public static int SkeletonHunterRate = 100;
    public static int SkeletonHunterMin = 2;
    public static int SkeletonHunterMax = 4;
    public static double SkeletonHunterDistance = 64;

    public static void main(String[] args) {

    }

    public static void extractDoubleFromTextFile() {
        File modsFolder = new File("mods");
        File modFolder = new File(modsFolder, "nekokamiko");
        File configFile = new File(modFolder, "config.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                if (lineNumber == 2) {
                    HunterHp = extractDoubleValue(line);
                    System.out.println(HunterHp);
                } else if (lineNumber == 3) {
                    HunterSpeed = extractDoubleValue(line);
                    System.out.println(HunterSpeed);
                } else if (lineNumber == 4) {
                    HunterRate = (int) extractDoubleValue(line);
                    System.out.println(HunterRate);
                } else if (lineNumber == 5) {
                    HunterMax = (int) extractDoubleValue(line);
                    System.out.println(HunterMax);
                } else if (lineNumber == 6) {
                    HunterMin = (int) extractDoubleValue(line);
                    System.out.println(HunterMin);
                } else if (lineNumber == 7) {
                    HunterDistance = extractDoubleValue(line);
                    System.out.println(HunterDistance);
                } else if (lineNumber == 9) {
                    BigHunterHp = extractDoubleValue(line);
                    System.out.println(BigHunterHp);
                } else if (lineNumber == 10) {
                    BigHunterSpeed = extractDoubleValue(line);
                    System.out.println(BigHunterSpeed);
                } else if (lineNumber == 11) {
                    BigHunterRate = (int) extractDoubleValue(line);
                    System.out.println(BigHunterRate);
                } else if (lineNumber == 12) {
                    BigHunterMax = (int) extractDoubleValue(line);
                    System.out.println(BigHunterMax);
                } else if (lineNumber == 13) {
                    BigHunterMin = (int) extractDoubleValue(line);
                    System.out.println(BigHunterMin);
                } else if (lineNumber == 14) {
                    BigHunterDistance = extractDoubleValue(line);
                    System.out.println(BigHunterDistance);
                } else if (lineNumber == 16) {
                    SmallHunterHp = extractDoubleValue(line);
                    System.out.println(SmallHunterHp);
                } else if (lineNumber == 17) {
                    SmallHunterSpeed = extractDoubleValue(line);
                    System.out.println(SmallHunterSpeed);
                } else if (lineNumber == 18) {
                    SmallHunterRate = (int) extractDoubleValue(line);
                    System.out.println(SmallHunterRate);
                } else if (lineNumber == 19) {
                    SmallHunterMax = (int) extractDoubleValue(line);
                    System.out.println(SmallHunterMax);
                } else if (lineNumber == 20) {
                    SmallHunterMin = (int) extractDoubleValue(line);
                    System.out.println(SmallHunterMin);
                } else if (lineNumber == 21) {
                    SmallHunterDistance = extractDoubleValue(line);
                    System.out.println(SmallHunterDistance);
                } else if (lineNumber == 23) {
                    PhantomHunterHp = extractDoubleValue(line);
                    System.out.println(PhantomHunterHp);
                } else if (lineNumber == 24) {
                    PhantomHunterSpeed = extractDoubleValue(line);
                    System.out.println(PhantomHunterSpeed);
                } else if (lineNumber == 25) {
                    PhantomHunterRate = (int) extractDoubleValue(line);
                    System.out.println(PhantomHunterRate);
                } else if (lineNumber == 26) {
                    PhantomHunterMax = (int) extractDoubleValue(line);
                    System.out.println(PhantomHunterMax);
                } else if (lineNumber == 27) {
                    PhantomHunterMin = (int) extractDoubleValue(line);
                    System.out.println(PhantomHunterMin);
                } else if (lineNumber == 28) {
                    PhantomHunterDistance = extractDoubleValue(line);
                    System.out.println(PhantomHunterDistance);
                } else if (lineNumber == 30) {
                    SkeletonHunterHp = extractDoubleValue(line);
                    System.out.println(SkeletonHunterHp);
                } else if (lineNumber == 31) {
                    SkeletonHunterSpeed = extractDoubleValue(line);
                    System.out.println(SkeletonHunterSpeed);
                } else if (lineNumber == 32) {
                    SkeletonHunterRate = (int) extractDoubleValue(line);
                    System.out.println(SkeletonHunterRate);
                } else if (lineNumber == 33) {
                    SkeletonHunterMax = (int) extractDoubleValue(line);
                    System.out.println(SkeletonHunterMax);
                } else if (lineNumber == 34) {
                    SkeletonHunterMin = (int) extractDoubleValue(line);
                    System.out.println(SkeletonHunterMin);
                } else if (lineNumber == 35) {
                    SkeletonHunterDistance = extractDoubleValue(line);
                    System.out.println(SkeletonHunterDistance);
                    break; // 最後の行を読み込んだらループを終了
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double extractDoubleValue(String line) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 0.0;
    }
}