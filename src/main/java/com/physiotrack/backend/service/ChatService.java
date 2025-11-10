package com.physiotrack.backend.service;

import com.physiotrack.backend.model.avaliacao.Avaliacao;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiKey;

    @Value("${gemini.api.url}")
    private String geminiUrl;

    private final AvaliacaoService avaliacaoService;

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

    public String perguntarGemini(Long pessoaId) throws IOException, InterruptedException {
        String pergunta = gerarPegunta(pessoaId);
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

    private String gerarPegunta(Long id){
        String pergunta = "Me passe um receita de bolo simples e pequena de nomaximo 1 paragrafo por extenso sem topicos";
        List<Avaliacao> avaliacoes = avaliacaoService.findAll(id);
       // Avaliacao primeira = avaliacoes.getFirst();
        //Avaliacao ultima = avaliacoes.getLast();
        return pergunta;
    }
}