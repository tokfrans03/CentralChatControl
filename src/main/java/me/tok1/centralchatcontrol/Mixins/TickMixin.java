package me.tok1.centralchatcontrol.Mixins;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tok1.centralchatcontrol.Commands.Base;
import me.tok1.centralchatcontrol.Etc.Config;
import me.tok1.centralchatcontrol.Etc.Logger;
import me.tok1.centralchatcontrol.Etc.sendGet;
import me.tok1.centralchatcontrol.Etc.sendPost;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class TickMixin {

    public int i = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        i++;
        if (Config.check && (i % Config.tickinterval == 0)){
            Logger.info("Doin check");
            sendPost.executePost(
                    Config.url + ":" + Config.port + "/login", "{ \"name\": \"" + MinecraftClient.getInstance().player.getName().getString() + "\"}");
            String jobres = sendGet.executeGet(Config.url + ":" + Config.port + "/jobs");
            jobres = jobres.replaceAll("\\r", "");


            JsonParser parser = new JsonParser();
            JsonElement jobs = parser.parse(jobres);
            JsonObject rootObject  = jobs.getAsJsonObject();
            JsonArray arr = rootObject.get("jobs").getAsJsonArray();
            for (int i = Config.jobid; i <= rootObject.get("jobid").getAsInt() - 1; i++)
            {
                MinecraftClient.getInstance().player.sendChatMessage(arr.get(i).getAsString());
            }
            Config.jobid = rootObject.get("jobid").getAsInt();

            // nopnopnopnopnopnoopoooooooooo
            /*
            String topic        = "A";
            String content      = "Message from MqttPublishSample";
            int qos             = 2;
            String broker       = "tcp://localhost";
            int port            = 1883;
            String clientId     = "JavaSample";

            Mqtt3AsyncClient client = (Mqtt3AsyncClient) Mqtt3Client.builder()
                    .identifier(clientId)
                    .serverHost("broker.hivemq.com")
                    .serverPort(port)
                    .build();


            System.out.println("Connecting to broker: "+broker);

            client.connect();


            System.out.println("Connected");
            System.out.println("Publishing message: "+content);

            client.publishWith().topic("A").send();

            System.out.println("Message published");

            client.disconnect();

            System.out.println("Disconnected");
            */


            //Config.check = false;
        }
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String msg, CallbackInfo ci) {
        if (msg.toLowerCase().startsWith("@c3")) {
            ci.cancel();
            String[] args = msg.substring(3).trim().split(" +");
            String cmd = args[0].toLowerCase();
            Base cmd2r = Config.cmdmanager.getByName(cmd);
            cmd2r.run(args);
        }
    }
}