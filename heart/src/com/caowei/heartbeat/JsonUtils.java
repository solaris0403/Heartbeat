package com.caowei.heartbeat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonUtils {
    private static JsonUtils instance;   // 单例模式

    private JsonUtils() {
    }

    public static JsonUtils getInstance() {
        if (instance == null) {
            instance = new JsonUtils();
        }
        return instance;
    }

    public JsonObject getHeartbeatConfig(){
        String fileName = "/Users/caowei/Documents/workspace/git/Heartbeat/heart/src/heartbeat.json";
        String config = readJsonFile(fileName);
        JsonReader jsonReader = new JsonReader( new StringReader(config) );
        JsonElement jsonElement = new JsonParser().parse(config);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject;
    }

    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
