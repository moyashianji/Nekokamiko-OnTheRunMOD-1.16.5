package com.example.nekokamiko.main;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class onTimerGUI {
    public static boolean stopwatchRunning = false;
    private static long startTime = 0;

    private static long pausedTime = 0;

    private static long elapsedTime = 0;

    public static long decrementValue = 200;

    public static long pausedValue = 0;

    public static long displayedValue = 0;

    public static long decrementAmount = 0;

    private static long currentValue = 0;

    static long prizeMoney = 0;

    static long pauseMoney = 0;
    static long increaseAmount = 200;// 毎秒増える金額の初期値
    private static Timer timer = new Timer();

    static long timeToShow;
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        event.getServer().getCommands().getDispatcher().register(
                Commands.literal("neko")

                        .then(Commands.literal("time")
                                .then(Commands.literal("reset")
                                        .executes(context -> startStopwatch(context.getSource())
                                        )
                                )
                                .then(Commands.literal("stop")
                                        .executes(context -> pauseStopwatch(context.getSource())
                                        )
                                )
                                .then(Commands.literal("start")
                                        .executes(contect -> resumeStopwatch(contect.getSource())
                                        )
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("hours", IntegerArgumentType.integer())
                                                .then(Commands.argument("minutes",IntegerArgumentType.integer())
                                                        .then(Commands.argument("seconds", IntegerArgumentType.integer())
                                                                .executes(context -> setStartTime
                                                                        (context.getSource(), IntegerArgumentType.getInteger
                                                                                (context, "hours"), IntegerArgumentType.getInteger
                                                                                (context, "minutes"), IntegerArgumentType.getInteger
                                                                                (context, "seconds"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                        .then(Commands.literal("load")
                                .then(Commands.argument("filename", StringArgumentType.string())
                                        .executes(context -> loadFile(context.getSource(), StringArgumentType.getString(context, "filename")))
                                )
                        )
                        .then(Commands.literal("value")
                                .then(Commands.argument("multivalue", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            increaseAmount = IntegerArgumentType.getInteger(context, "multivalue");
                                            return 0;
                                        })
                                )
                        )



        );
        MinecraftForge.EVENT_BUS.register(onTimerGUI.class);
    }

    private static int startStopwatch(CommandSource source) {
        PlayerEntity player = source.getEntity() instanceof PlayerEntity ? (PlayerEntity) source.getEntity() : null;
        if (player != null) {
            if (!stopwatchRunning) {


                startTime = System.currentTimeMillis();
                stopwatchRunning = true;
                timer();

                prizeMoney = -200;


                player.sendMessage(new StringTextComponent("Stopwatch started."), player.getUUID());
            } else {
                player.sendMessage(new StringTextComponent("Stopwatch is already running."), player.getUUID());
            }
        }
        return 0;
    }
    private static int loadFile(CommandSource source, String filename) {
        PlayerEntity player = source.getEntity() instanceof  PlayerEntity ? (PlayerEntity) source.getEntity() : null;
        File file = new File("mods/nekokamiko/" + filename); // ファイルパスを指定してFileオブジェクトを作成

        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".txt")) {
            player.sendMessage(new StringTextComponent("Invalid file."), player.getUUID());
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                if (lineNumber == 2) {
                    String[] timeValues = line.trim().split(":");
                    if (timeValues.length == 3) {
                        int hours = Integer.parseInt(timeValues[0]);
                        int minutes = Integer.parseInt(timeValues[1]);
                        int seconds = Integer.parseInt(timeValues[2]);

                        long specifiedTime = (hours * 3600000L) + (minutes * 60000L) + (seconds * 1000L);
                        startTime = System.currentTimeMillis() - specifiedTime;
                        stopwatchRunning = true;
                        player.sendMessage(new StringTextComponent("Stopwatch started from file."), player.getUUID());
                        return 1;
                    } else {
                        player.sendMessage(new StringTextComponent("Invalid time format in file."), player.getUUID());
                        return 0;
                    }
                }
                lineNumber++;
            }
            player.sendMessage(new StringTextComponent("Invalid file format."), player.getUUID());
        } catch (IOException e) {
            player.sendMessage(new StringTextComponent("Failed to load file."), player.getUUID());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            player.sendMessage(new StringTextComponent("Invalid onValue in file."), player.getUUID());
        }
        return 0;
    }

    private static int pauseStopwatch(CommandSource source){
        //三項演算子　条件式 ? 真の場合の値　:　偽りの場合の値
        PlayerEntity player = source.getEntity() instanceof  PlayerEntity ? (PlayerEntity) source.getEntity() : null;
        if(player != null){
            if(stopwatchRunning){
                pausedTime = elapsedTime;
                pausedValue = displayedValue;
                pauseMoney = prizeMoney;
                stopwatchRunning = false;
                player.sendMessage(new StringTextComponent("Stopwatch paused"), player.getUUID());
            }else{
                player.sendMessage(new StringTextComponent("Already paused"), player.getUUID());
            }
        }
        return 0;
    }

    public static int resumeStopwatch(CommandSource source){

        PlayerEntity player = source.getEntity() instanceof PlayerEntity ? (PlayerEntity) source.getEntity() : null;
        if(player != null){
            if(!stopwatchRunning && pausedTime>0){

                startTime = System.currentTimeMillis() - pausedTime;
                pausedTime = 0;
                stopwatchRunning = true;
                player.sendMessage(new StringTextComponent("Stopwatch resume"), player.getUUID());
            }else{
                player.sendMessage(new StringTextComponent("/neko time stop してから実行してください"), player.getUUID());

            }
        }
        return 0;
    }

    private static int setStartTime(CommandSource source, int hours, int minutes, int seconds){
        PlayerEntity player = source.getEntity() instanceof PlayerEntity ? (PlayerEntity) source.getEntity():null;
        if(player != null){
            if(!stopwatchRunning){
                long currentTime = System.currentTimeMillis();
                long specifiedTime = currentTime - (hours * 3600000L) - (minutes * 60000L) - (seconds * 1000L);
                startTime = specifiedTime;
                stopwatchRunning = true;
                player.sendMessage(new StringTextComponent("Stopwatch start time set to: " + hours + ":" + minutes + ":" + seconds), player.getUUID());
            } else {
                player.sendMessage(new StringTextComponent("Stopwatch is already running."), player.getUUID());
            }
        }return 0;
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

            Minecraft mc = Minecraft.getInstance();
            FontRenderer fontRenderer = mc.font;
            timeToShow = 0;

            if (stopwatchRunning) {
                elapsedTime = System.currentTimeMillis() - startTime;
            }

            if (stopwatchRunning || pausedTime > 0 || !stopwatchRunning) {
                timeToShow = stopwatchRunning ? elapsedTime : pausedTime;

                int seconds = (int) (timeToShow / 1000) % 60;
                int minutes = (int) ((timeToShow / (1000 * 60)) % 60);
                int hours = (int) ((timeToShow / (1000 * 60 * 60)) % 24);

                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                int scaledWidth = mc.getWindow().getGuiScaledWidth();
                int scaledHeight = mc.getWindow().getGuiScaledHeight();

                int textWidth = fontRenderer.width(timeString);
                int textHeight = fontRenderer.lineHeight;

                int x = scaledWidth - textWidth - 100;
                int y = scaledHeight - textHeight - 100;

                String all = timeString;
                MatrixStack matrixStack = event.getMatrixStack();
                matrixStack.pushPose();

                RenderSystem.enableBlend();

                matrixStack.translate(x, y, 0);

                fontRenderer.draw(matrixStack, all, 0, 0, -1);

                matrixStack.popPose();
            }
        }
    }
    public static void timer() {

        if (timer != null) {
            timer.cancel();
            timer = new Timer();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (stopwatchRunning) {
                    prizeMoney += increaseAmount;
                    System.out.println("返済額:" + prizeMoney);
                } else {
                    System.out.println("返済額:" + pauseMoney);
                }
            }
        }, 0, 1000); // 1秒ごとに実行
    }
    @SubscribeEvent
    public static void onrRenderGameOverlay(RenderGameOverlayEvent event) {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontsRenderer = mc.font;

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            // 描画処理のパラメータを設定
            int posX = 10; // X座標
            int posY = 10; // Y座標
            int color = 0xFFFFFF; // テキストの色 (白色)
            int fontSize = 12; // フォントサイズ

            MatrixStack matrixsStack = event.getMatrixStack();
            matrixsStack.pushPose();

            RenderSystem.enableBlend();
            matrixsStack.translate(posX, posY, 0);

            if (stopwatchRunning) {
                // prizeMoneyの値を取得して描画
                fontsRenderer.draw(matrixsStack, String.valueOf(prizeMoney), 0, 0, -1);

            } else {
                // pauseMoneyの値を取得して描画
                fontsRenderer.draw(matrixsStack, String.valueOf(pauseMoney), 0, 0, -1);
            }
            matrixsStack.popPose();
        }
    }
    public static void register() {
        MinecraftForge.EVENT_BUS.register(onTimerGUI.class);
    }
}