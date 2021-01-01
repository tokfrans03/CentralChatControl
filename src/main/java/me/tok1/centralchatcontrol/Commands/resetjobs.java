package me.tok1.centralchatcontrol.Commands;

import me.tok1.centralchatcontrol.Etc.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class resetjobs extends Base {
    public resetjobs() {
        super("resetjobs", new String[]{"resetjobs", "r"}, "Resets jobid to 0 (do all jobs)");
    }

    @Override
    public void run(String[] args) {
        Config.jobid = 0;


        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}
