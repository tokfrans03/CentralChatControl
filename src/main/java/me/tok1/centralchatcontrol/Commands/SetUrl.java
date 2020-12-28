package me.tok1.centralchatcontrol.Commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class SetUrl extends Base {
    public SetUrl() {
        super("SetUrl", new String[]{"SetUrl"}, "SetPorts for either diamond or redstone ore");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 2) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of("[CCC] Please provide a ore block to SetPort for as argument. Currently: diamond or redstone"), false);
            return;
        }
        String Port = args[1];


        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}
