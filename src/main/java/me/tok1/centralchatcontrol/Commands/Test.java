package me.tok1.centralchatcontrol.Commands;

import me.tok1.centralchatcontrol.Etc.Config;
import me.tok1.centralchatcontrol.Etc.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Test extends Base {
    public Test() {
        super("Test", new String[]{"Test", "t"}, "Test");
    }

    @Override
    public void run(String[] args) {
        Config.mqttgooo = true;

        MinecraftClient.getInstance().player.sendMessage(Text.of("[CCC] Doin le thing"), false);

        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}