package me.tok1.centralchatcontrol.Etc;

import me.tok1.centralchatcontrol.Commands.Manager;

public class Config {
    public static String mqtturl = "localhost";
    public static int mqttport = 1883;
    public static Manager cmdmanager = new Manager();
}
