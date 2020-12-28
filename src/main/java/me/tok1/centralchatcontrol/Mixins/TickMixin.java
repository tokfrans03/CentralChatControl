package me.tok1.centralchatcontrol.Mixins;

import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import me.tok1.centralchatcontrol.Commands.Base;
import me.tok1.centralchatcontrol.Etc.Config;
import me.tok1.centralchatcontrol.Etc.Logger;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class TickMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (Config.mqttgooo){
            Logger.info("Starting mqtt thing");

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



            Config.mqttgooo = false;
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