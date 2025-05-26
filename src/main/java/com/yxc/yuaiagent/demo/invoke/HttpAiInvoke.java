package com.yxc.yuaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class HttpAiInvoke {

    public static void main(String[] args) {
        TestApiKey testApiKey = new TestApiKey();

        // 替换为你的实际 API Key
        String apiKey = testApiKey.apiKey; // 从环境变量中获取
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("DASHSCOPE_API_KEY 环境变量未设置");
        }

        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

        // 构建请求体 JSON
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "qwen-plus");

        JSONObject input = new JSONObject();
        JSONArray messages = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant.");
        messages.add(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "我是fishstar,我正在做一个AI项目");
        messages.add(userMessage);

        input.put("messages", messages);
        requestBody.put("input", input);

        JSONObject parameters = new JSONObject();
        parameters.put("result_format", "message");
        requestBody.put("parameters", parameters);

        // 发起请求
        HttpResponse response = HttpRequest.post(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .timeout(10000) // 可选：设置超时时间
                .execute();

        // 输出响应
        System.out.println("Status Code: " + response.getStatus());
        System.out.println("Response Body: " + response.body());
    }
}