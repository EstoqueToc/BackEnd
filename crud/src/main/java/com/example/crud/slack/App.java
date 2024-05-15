package com.example.crud.slack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException  {
        JSONObject json = new JSONObject();
        json.put("text", "Hello, World!");
        Slack.sendMessage(json);
    }
}
