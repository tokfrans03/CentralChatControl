package me.tok1.centralchatcontrol.Mixins;

import me.tok1.centralchatcontrol.Commands.Base;
import me.tok1.centralchatcontrol.Etc.Config;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class TickMixin {


    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String msg, CallbackInfo ci) {
        if (msg.toLowerCase().startsWith("@c3")) {
            ci.cancel();
            String[] args = msg.substring(4).trim().split(" +");
            String cmd = args[0].toLowerCase();
            Base cmd2r = Config.cmdmanager.getByName(cmd);
            cmd2r.run(args);
        }
    }
}