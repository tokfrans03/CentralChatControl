package me.tok1.centralchatcontrol.Commands;

import me.tok1.centralchatcontrol.Etc.Config;
import me.tok1.centralchatcontrol.Etc.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class check  extends Base{
    public check() {
        super("check", new String[]{"check", "c"}, "toggles interval checking of commands");
    }

    @Override
    public void run(String[] args) {

        Config.check = !Config.check;
        Logger.print((Config.check ? "En" : "Dis") + "abled interval checking.");


        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}
