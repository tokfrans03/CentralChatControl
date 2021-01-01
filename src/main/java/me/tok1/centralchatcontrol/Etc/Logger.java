package me.tok1.centralchatcontrol.Etc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Logger {
    public static void info(String text) {
        System.out.println("[CCC] " + text);
    }
    public static void print(String text) {
        MinecraftClient.getInstance().player.sendMessage(Text.of("[CCC] " + text), false);
    }
}
