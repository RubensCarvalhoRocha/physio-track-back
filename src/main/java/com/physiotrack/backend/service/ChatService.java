package com.physiotrack.backend.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiKey;

    @Value("${gemini.api.url}")
    private String geminiUrl;

    public String perguntar(String pergunta) throws IOException, InterruptedException {
        // Corpo JSON da requisição
        String jsonBody = """
        {
          "model": "gpt-4o-mini",
          "messages": [
            {"role": "system", "content": "Você é um assistente útil."},
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(pergunta);

        // Cria cliente e requisição
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        // Envia e lê resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro da OpenAI: " + response.body());
        }

        // Extrai resposta JSON
        JSONObject json = new JSONObject(response.body());
        return json
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();
    }

    public String perguntarGemini(String pergunta) throws IOException, InterruptedException {
        // Endpoint correto com a chave na URL
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiKey;

        // Corpo JSON esperado pela API Gemini
        String jsonBody = """
    {
      "contents": [
        {
          "role": "user",
          "parts": [
            {"text": "%s"}
          ]
        }
      ]
    }
    """.formatted(pergunta.replace("\"", "\\\"")); // escapa aspas para segurança

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro do Gemini: " + response.body());
        }

        JSONObject json = new JSONObject(response.body());
        return json
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")
                .trim();
    }
}