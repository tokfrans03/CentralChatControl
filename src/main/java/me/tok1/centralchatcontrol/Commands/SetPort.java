package me.tok1.centralchatcontrol.Commands;

import me.tok1.centralchatcontrol.Etc.Config;
import me.tok1.centralchatcontrol.Etc.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class SetPort extends Base {
    public SetPort() {
        super("SetPort", new String[]{"SetPort", "o"}, "Set Port for server");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 2) {
            assert MinecraftClient.getInstance().player != null;
            Logger.print("Please provide integer");
            return;
        }
        String newarg = args[1];
        int newargI;
        try {
            newargI = Integer.parseInt(newarg);
        } catch (Exception ex) {
            assert MinecraftClient.getInstance().player != null;
            Logger.print("Please provide a VALID number as argument.");
            return;
        }
        Config.port = newargI;


        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}
