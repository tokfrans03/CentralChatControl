package me.tok1.centralchatcontrol.Commands;

import me.tok1.centralchatcontrol.Etc.Config;
import me.tok1.centralchatcontrol.Etc.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class tickinterval extends Base{
    public tickinterval() {
        super("tickinterval", new String[]{"tickinterval", "i"}, "How many ticks per interval");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 2) {
            assert MinecraftClient.getInstance().player != null;
            Logger.print("[CCC] Please a integer (20 for once per second)");
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
        Config.tickinterval = newargI;
        Logger.print("Set new Tick interval to " + newarg);


        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}
