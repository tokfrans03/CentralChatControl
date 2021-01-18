package me.tok1.centralchatcontrol.Commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tok1.centralchatcontrol.Etc.*;

import net.minecraft.client.MinecraftClient;


public class Test extends Base {
    public Test() {
        super("Test", new String[]{"Test", "t"}, "Test");
    }

    @Override
    public void run(String[] args) {

        //MinecraftClient.getInstance().player.sendMessage(Text.of("[CCC] Doin le thing BBBBBBBBBBB"), false);

        JsonObject userinfo = new JsonObject();
        userinfo.addProperty("name", MinecraftClient.getInstance().player.getName().getString());
        userinfo.addProperty("health", MinecraftClient.getInstance().player.getHealth());

        Logger.print("Userinfo: " + userinfo);

        String loginres = send.post(
                Config.url + ":" + Config.port + "/login", userinfo.toString());
        loginres = loginres.replaceAll("\\r", "");

        //Logger.print(loginres);
        //Logger.info(loginres);
        Logger.info(new JsonParser().parse(loginres).getAsJsonObject().get("message").getAsString());
        Logger.print(new JsonParser().parse(loginres).getAsJsonObject().get("message").getAsString());




        //Logger.print("loading....");

        //String jobres = "{\"jobs\":[],\"jobid\":0}";

        String jobres = send.get(Config.url + ":" + Config.port + "/jobs");
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
        //Logger.print("jobs: " +jobs.getJSONArray("jobs").toString());

        //Logger.print("jobid: " +jobs.getInt("jobid"));

        //JSONObject obj = new JSONObject("{\"Hello\": \"World\"}");
        //String pageName = obj.getString("Hello");





        //Logger.print(sendPost.executePost(Config.url + ":" + Config.port, "{aAAAAAAAAAAA}"));

        assert MinecraftClient.getInstance().player != null;
        super.run(args);
    }
}