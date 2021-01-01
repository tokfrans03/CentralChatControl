package me.tok1.centralchatcontrol.Etc;

import me.tok1.centralchatcontrol.Commands.Manager;
import net.minecraft.client.MinecraftClient;

public class Config {
    public static String url = "http://localhost";
    public static int port = 8000;
    public static int jobid = 0;
    public static int tickinterval = 20;
    public static Manager cmdmanager = new Manager();
    public static boolean check = false;
}
