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
import java.time.LocalDate;
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
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=" + geminiKey;

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
        List<Avaliacao> avaliacoes = avaliacaoService.findAll(id);
        StringBuilder pergunta = new StringBuilder();
        if(avaliacoes.size() > 1) {
            Avaliacao primeira = avaliacoes.get(0);
            Avaliacao ultima = avaliacoes.get(avaliacoes.size() - 1);
            String dadosPrimeiraAvaliacao = dadosPrimeiraAvaliacao(primeira);
            String dadosUltimaAvaliacao = dadosUltimaAvaliacao(ultima);
            pergunta.append("Regras para a resposta:");
            pergunta.append("Inicie a resposta com 'Analise geral do histórico do Paciente'");
            pergunta.append("Sempre responda como se você fosse um fisioterapeuta. ");
            pergunta.append("É permitido que use topico mas sem exageros e sem formatações exageradas. ");
            pergunta.append("Tenta manter a resposta curta.");
            pergunta.append("Sempre apresente considerações finais ou resumo. ");
            pergunta.append("Pedido: Faça a analise das informações do primerio e ultimo atendimento de um paciente de um fisitorapeuta usando os dados da primeira avaliação e os dados da ultima avaliação.");
            pergunta.append("Dados da primeira avaliação: " + dadosPrimeiraAvaliacao);
            pergunta.append(" ");
            pergunta.append("Dados da ultima avaliação: " + dadosUltimaAvaliacao);
        }else{
            pergunta.append("Retorne literalmente a mensagem a seguir: 'Não é possível analise com pacientes com menos de dois atendimentos'");
        }
        return pergunta.toString();
    }

    private String dadosPrimeiraAvaliacao (Avaliacao primeira){
        //
        LocalDate primeiraData = primeira.getData();
        Double primeiraAltura = primeira.getAltura();
        Double primeiraPeso = primeira.getPeso();
        Double primeiraImc = primeira.getImc();
        String primeiraEsporte = primeira.getEsporte();
        String primeiraQueixas = primeira.getQueixas();
        String primeiraHistoricoSaude = primeira.getHistoricoSaude();
        String primeiraMedicamentos = primeira.getMedicamentos();
        String primeiraCirurgia = primeira.getCirurgia();
        String primeiraTratamentoAnterior = primeira.getTratamentoAnterior();
        String primeiraDiagnosticoMedico = primeira.getDiagnosticoMedico();
        String primeiraObjTratamento = primeira.getObjTratamento();
        Double primeiraPerimetriaMedida1D = primeira.getPerimetriaMedida1D();
        Double primeiraPerimetriaPanturrilhaD = primeira.getPerimetriaPanturrilhaD();
        Double primeiraPerimetriaAssimetriaMedida1E = primeira.getPerimetriaAssimetriaMedida1E();
        Double primeiraPerimetriaPanturrilhaE = primeira.getPerimetriaPanturrilhaE();
        Double primeiraPerimetriaMedidaAss1 = primeira.getPerimetriaMedidaAss1();
        Double primeiraPerimetriaPanturrilhaAss = primeira.getPerimetriaPanturrilhaAss();
        Double primeiraLungeD = primeira.getLungeD();
        Double primeiraLungeE = primeira.getLungeE();
        Double primeiraLungeAss = primeira.getLungeAss();
        Double primeiraRotQuadInterD = primeira.getRotQuadInterD();
        Double primeiraRotQuadInterE = primeira.getRotQuadInterE();
        Double primeiraRotQuadInterAss = primeira.getRotQuadInterAss();
        Double primeiraRotQuadExterD = primeira.getRotQuadExterD();
        Double primeiraRotQuadExterE = primeira.getRotQuadExterE();
        Double primeiraRotQuadExterAss = primeira.getRotQuadExterAss();
        Double primeiraExtJoelhoD = primeira.getExtJoelhoD();
        Double primeiraExtJoelhoE = primeira.getExtJoelhoE();
        Double primeiraExtJoelhoAss = primeira.getExtJoelhoAss();
        Double primeiraFlexJoelhoD = primeira.getFlexJoelhoD();
        Double primeiraFlexJoelhoE = primeira.getFlexJoelhoE();
        Double primeiraFlexJoelhoAss = primeira.getFlexJoelhoAss();
        Double primeiraShTestMediaD = primeira.getShTestMediaD();
        Double primeiraShTestMediaE = primeira.getShTestMediaE();
        Double primeiraShTestScore = primeira.getShTestScore();
        Double primeiraSdhTestMediaD = primeira.getSdhTestMediaD();
        Double primeiraSdhTestMediaE = primeira.getSdhTestMediaE();
        Double primeiraSdhTestScore = primeira.getSdhTestScore();
        Double primeiraSlbTestD = primeira.getSlbTestD();
        Double primeiraSlbTestE = primeira.getSlbTestE();
        //
        StringBuilder dadosPrimeiraAvaliacao = new StringBuilder();
        //
        if( primeiraData != null){
        dadosPrimeiraAvaliacao.append("Data 1º av.: " + primeiraData);
        }
        if( primeiraAltura != null){
            dadosPrimeiraAvaliacao.append(" Altura 1º av.: " + primeiraAltura);
        }
        if( primeiraPeso != null){
            dadosPrimeiraAvaliacao.append(" Peso 1º av.: " + primeiraPeso);
        }
        if( primeiraImc != null){
            dadosPrimeiraAvaliacao.append(" IMC 1º av.: " + primeiraImc);
        }
        if( primeiraEsporte != null){
            dadosPrimeiraAvaliacao.append(" Esporte 1º av.: " + primeiraEsporte);
        }
        if( primeiraQueixas != null){
            dadosPrimeiraAvaliacao.append(" Queixa 1º av.: " + primeiraQueixas);
        }
        if( primeiraHistoricoSaude != null){
            dadosPrimeiraAvaliacao.append(" Historico saude 1º av.: " + primeiraHistoricoSaude);
        }
        if( primeiraMedicamentos != null){
            dadosPrimeiraAvaliacao.append(" Medicamentos usados 1º av.: " + primeiraMedicamentos);
        }
        if( primeiraCirurgia != null){
            dadosPrimeiraAvaliacao.append(" Cirurgias ja realizadas 1º av.: " + primeiraCirurgia);
        }
        if( primeiraTratamentoAnterior != null){
            dadosPrimeiraAvaliacao.append(" tratamento anterior 1º av.: " + primeiraTratamentoAnterior);
        }
        if( primeiraDiagnosticoMedico != null){
            dadosPrimeiraAvaliacao.append(" diagnostico medico 1º av.: " + primeiraDiagnosticoMedico);
        }
        if( primeiraObjTratamento != null){
            dadosPrimeiraAvaliacao.append(" objetivo tratamento 1º av.: " + primeiraObjTratamento);
        }
        if( primeiraPerimetriaMedida1D != null){
            dadosPrimeiraAvaliacao.append(" primeiraPerimetriaMedida1D 1º av.: " + primeiraPerimetriaMedida1D);
        }
        if( primeiraPerimetriaPanturrilhaD != null){
            dadosPrimeiraAvaliacao.append(" primeiraPerimetriaPanturrilhaD 1º av.: " + primeiraPerimetriaPanturrilhaD);
        }
        if( primeiraPerimetriaAssimetriaMedida1E != null){
            dadosPrimeiraAvaliacao.append(" primeiraPerimetriaAssimetriaMedida1E 1º av.: " + primeiraPerimetriaAssimetriaMedida1E);
        }
        if( primeiraPerimetriaPanturrilhaE != null){
            dadosPrimeiraAvaliacao.append(" primeiraPerimetriaPanturrilhaE 1º av.: " + primeiraPerimetriaPanturrilhaE);
        }
        if( primeiraPerimetriaMedidaAss1 != null){
            dadosPrimeiraAvaliacao.append(" primeiraPerimetriaMedidaAss1 1º av.: " + primeiraPerimetriaMedidaAss1);
        }
        if( primeiraPerimetriaPanturrilhaAss != null){
            dadosPrimeiraAvaliacao.append(" primeiraPerimetriaPanturrilhaAss 1º av.: " + primeiraPerimetriaPanturrilhaAss);
        }
        if( primeiraLungeD != null){
            dadosPrimeiraAvaliacao.append(" primeiraLungeD 1º av.: " + primeiraLungeD);
        }
        if( primeiraLungeE != null){
            dadosPrimeiraAvaliacao.append(" primeiraLungeE 1º av.: " + primeiraLungeE);
        }
        if( primeiraLungeAss != null){
            dadosPrimeiraAvaliacao.append(" primeiraLungeAss 1º av.: " + primeiraLungeAss);
        }
        if( primeiraRotQuadInterD != null){
            dadosPrimeiraAvaliacao.append(" primeiraRotQuadInterD 1º av.: " + primeiraRotQuadInterD);
        }
        if( primeiraRotQuadInterE != null){
            dadosPrimeiraAvaliacao.append(" primeiraRotQuadInterE 1º av.: " + primeiraRotQuadInterE);
        }
        if( primeiraRotQuadInterAss != null){
            dadosPrimeiraAvaliacao.append(" primeiraRotQuadInterAss 1º av.: " + primeiraRotQuadInterAss);
        }
        if( primeiraRotQuadExterD != null){
            dadosPrimeiraAvaliacao.append(" primeiraRotQuadInterD 1º av.: " + primeiraRotQuadInterD);
        }
        if( primeiraRotQuadExterE != null){
            dadosPrimeiraAvaliacao.append(" primeiraRotQuadInterE 1º av.: " + primeiraRotQuadInterE);
        }
        if( primeiraRotQuadExterAss != null){
            dadosPrimeiraAvaliacao.append(" primeiraRotQuadInterAss 1º av.: " + primeiraRotQuadInterAss);
        }
        if( primeiraExtJoelhoD != null){
            dadosPrimeiraAvaliacao.append(" primeiraExtJoelhoD 1º av.: " + primeiraExtJoelhoD);
        }
        if( primeiraExtJoelhoE != null){
            dadosPrimeiraAvaliacao.append(" primeiraExtJoelhoE 1º av.: " + primeiraExtJoelhoE);
        }
        if( primeiraExtJoelhoAss != null){
            dadosPrimeiraAvaliacao.append(" primeiraExtJoelhoAss 1º av.: " + primeiraExtJoelhoAss);
        }
        if( primeiraFlexJoelhoD != null){
            dadosPrimeiraAvaliacao.append(" primeiraFlexJoelhoD 1º av.: " + primeiraFlexJoelhoD);
        }
        if( primeiraFlexJoelhoE != null){
            dadosPrimeiraAvaliacao.append(" primeiraFlexJoelhoE 1º av.: " + primeiraFlexJoelhoE);
        }
        if( primeiraFlexJoelhoAss != null){
            dadosPrimeiraAvaliacao.append(" primeiraFlexJoelhoAss 1º av.: " + primeiraFlexJoelhoAss);
        }
        if( primeiraShTestMediaD != null){
            dadosPrimeiraAvaliacao.append(" primeiraShTestMediaD 1º av.: " + primeiraShTestMediaD);
        }
        if( primeiraShTestMediaE != null){
            dadosPrimeiraAvaliacao.append(" primeiraShTestMediaE 1º av.: " + primeiraShTestMediaE);
        }
        if( primeiraShTestScore != null){
            dadosPrimeiraAvaliacao.append(" primeiraShTestScore 1º av.: " + primeiraShTestScore);
        }
        if( primeiraSdhTestMediaD != null){
            dadosPrimeiraAvaliacao.append(" primeiraSdhTestMediaD 1º av.: " + primeiraSdhTestMediaD);
        }
        if( primeiraSdhTestMediaE != null){
            dadosPrimeiraAvaliacao.append(" primeiraSdhTestMediaE 1º av.: " + primeiraSdhTestMediaE);
        }
        if( primeiraSdhTestScore != null){
            dadosPrimeiraAvaliacao.append(" primeiraSdhTestScore 1º av.: " + primeiraSdhTestScore);
        }
        if( primeiraSlbTestD != null){
            dadosPrimeiraAvaliacao.append(" primeiraSlbTestD 1º av.: " + primeiraSlbTestD);
        }
        if( primeiraSlbTestE != null){
            dadosPrimeiraAvaliacao.append(" primeiraSlbTestE 1º av.: " + primeiraSlbTestE);
        }
        //
        return dadosPrimeiraAvaliacao.toString();
    }

    private String dadosUltimaAvaliacao(Avaliacao ultima){
        LocalDate ultimaData = ultima.getData();
        Double ultimaAltura = ultima.getAltura();
        Double ultimaPeso = ultima.getPeso();
        Double ultimaImc = ultima.getImc();
        String ultimaEsporte = ultima.getEsporte();
        String ultimaQueixas = ultima.getQueixas();
        String ultimaHistoricoSaude = ultima.getHistoricoSaude();
        String ultimaMedicamentos = ultima.getMedicamentos();
        String ultimaCirurgia = ultima.getCirurgia();
        String ultimaTratamentoAnterior = ultima.getTratamentoAnterior();
        String ultimaDiagnosticoMedico = ultima.getDiagnosticoMedico();
        String ultimaObjTratamento = ultima.getObjTratamento();
        Double ultimaPerimetriaMedida1D = ultima.getPerimetriaMedida1D();
        Double ultimaPerimetriaPanturrilhaD = ultima.getPerimetriaPanturrilhaD();
        Double ultimaPerimetriaAssimetriaMedida1E = ultima.getPerimetriaAssimetriaMedida1E();
        Double ultimaPerimetriaPanturrilhaE = ultima.getPerimetriaPanturrilhaE();
        Double ultimaPerimetriaMedidaAss1 = ultima.getPerimetriaMedidaAss1();
        Double ultimaPerimetriaPanturrilhaAss = ultima.getPerimetriaPanturrilhaAss();
        Double ultimaLungeD = ultima.getLungeD();
        Double ultimaLungeE = ultima.getLungeE();
        Double ultimaLungeAss = ultima.getLungeAss();
        Double ultimaRotQuadInterD = ultima.getRotQuadInterD();
        Double ultimaRotQuadInterE = ultima.getRotQuadInterE();
        Double ultimaRotQuadInterAss = ultima.getRotQuadInterAss();
        Double ultimaRotQuadExterD = ultima.getRotQuadExterD();
        Double ultimaRotQuadExterE = ultima.getRotQuadExterE();
        Double ultimaRotQuadExterAss = ultima.getRotQuadExterAss();
        Double ultimaExtJoelhoD = ultima.getExtJoelhoD();
        Double ultimaExtJoelhoE = ultima.getExtJoelhoE();
        Double ultimaExtJoelhoAss = ultima.getExtJoelhoAss();
        Double ultimaFlexJoelhoD = ultima.getFlexJoelhoD();
        Double ultimaFlexJoelhoE = ultima.getFlexJoelhoE();
        Double ultimaFlexJoelhoAss = ultima.getFlexJoelhoAss();
        Double ultimaShTestMediaD = ultima.getShTestMediaD();
        Double ultimaShTestMediaE = ultima.getShTestMediaE();
        Double ultimaShTestScore = ultima.getShTestScore();
        Double ultimaSdhTestMediaD = ultima.getSdhTestMediaD();
        Double ultimaSdhTestMediaE = ultima.getSdhTestMediaE();
        Double ultimaSdhTestScore = ultima.getSdhTestScore();
        Double ultimaSlbTestD = ultima.getSlbTestD();
        Double ultimaSlbTestE = ultima.getSlbTestE();
        //
        StringBuilder dadosUltimaAvaliacao = new StringBuilder();
        //
        if( ultimaData != null){
            dadosUltimaAvaliacao.append("Data ultima av.: " + ultimaData);
        }
        if( ultimaAltura != null){
            dadosUltimaAvaliacao.append(" Altura ultima av.: " + ultimaAltura);
        }
        if( ultimaPeso != null){
            dadosUltimaAvaliacao.append(" Peso ultima av.: " + ultimaPeso);
        }
        if( ultimaImc != null){
            dadosUltimaAvaliacao.append(" IMC ultima av.: " + ultimaImc);
        }
        if( ultimaEsporte != null){
            dadosUltimaAvaliacao.append(" Esporte ultima av.: " + ultimaEsporte);
        }
        if( ultimaQueixas != null){
            dadosUltimaAvaliacao.append(" Queixa ultima av.: " + ultimaQueixas);
        }
        if( ultimaHistoricoSaude != null){
            dadosUltimaAvaliacao.append(" Historico saude ultima av.: " + ultimaHistoricoSaude);
        }
        if( ultimaMedicamentos != null){
            dadosUltimaAvaliacao.append(" Medicamentos usados ultima av.: " + ultimaMedicamentos);
        }
        if( ultimaCirurgia != null){
            dadosUltimaAvaliacao.append(" Cirurgias ja realizadas ultima av.: " + ultimaCirurgia);
        }
        if( ultimaTratamentoAnterior != null){
            dadosUltimaAvaliacao.append(" Tratamento ultima av.: " + ultimaTratamentoAnterior);
        }
        if( ultimaDiagnosticoMedico != null){
            dadosUltimaAvaliacao.append(" Diagnostico Medico ultima av.: " + ultimaDiagnosticoMedico);
        }
        if( ultimaObjTratamento != null){
            dadosUltimaAvaliacao.append(" Objtivo Tratamento ultima av.: " + ultimaObjTratamento);
        }
        if( ultimaPerimetriaMedida1D != null){
            dadosUltimaAvaliacao.append(" ultimaPerimetriaMedida1D av.: " + ultimaPerimetriaMedida1D);
        }
        if( ultimaPerimetriaPanturrilhaD != null){
            dadosUltimaAvaliacao.append(" ultimaPerimetriaPanturrilhaD av.: " + ultimaPerimetriaPanturrilhaD);
        }
        if( ultimaPerimetriaAssimetriaMedida1E != null){
            dadosUltimaAvaliacao.append(" ultimaPerimetriaAssimetriaMedida1E av.: " + ultimaPerimetriaAssimetriaMedida1E);
        }
        if( ultimaPerimetriaPanturrilhaE != null){
            dadosUltimaAvaliacao.append(" ultimaPerimetriaPanturrilhaE av.: " + ultimaPerimetriaPanturrilhaE);
        }
        if( ultimaPerimetriaMedidaAss1 != null){
            dadosUltimaAvaliacao.append(" ultimaPerimetriaMedidaAss1 av.: " + ultimaPerimetriaMedidaAss1);
        }
        if( ultimaPerimetriaPanturrilhaAss != null){
            dadosUltimaAvaliacao.append(" ultimaPerimetriaPanturrilhaAss av.: " + ultimaPerimetriaPanturrilhaAss);
        }
        if( ultimaLungeD != null){
            dadosUltimaAvaliacao.append(" ultimaLungeD av.: " + ultimaLungeD);
        }
        if( ultimaLungeE != null){
            dadosUltimaAvaliacao.append(" ultimaLungeE av.: " + ultimaLungeE);
        }
        if( ultimaLungeAss != null){
            dadosUltimaAvaliacao.append(" ultimaLungeAss av.: " + ultimaLungeAss);
        }
        if( ultimaRotQuadInterD != null){
            dadosUltimaAvaliacao.append(" ultimaRotQuadInterD av.: " + ultimaRotQuadInterD);
        }
        if( ultimaRotQuadInterE != null){
            dadosUltimaAvaliacao.append(" ultimaRotQuadInterE  av.: " + ultimaRotQuadInterE);
        }
        if( ultimaRotQuadInterAss != null){
            dadosUltimaAvaliacao.append(" ultimaRotQuadInterAss av.: " + ultimaRotQuadInterAss);
        }
        if( ultimaRotQuadExterD != null){
            dadosUltimaAvaliacao.append(" ultimaRotQuadExterD av.: " + ultimaRotQuadExterD);
        }
        if( ultimaRotQuadExterE != null){
            dadosUltimaAvaliacao.append(" ultimaRotQuadExterE av.: " + ultimaRotQuadExterE);
        }
        if( ultimaRotQuadExterAss != null){
            dadosUltimaAvaliacao.append(" ultimaRotQuadExterAss av.: " + ultimaRotQuadExterAss);
        }
        if( ultimaExtJoelhoD != null){
            dadosUltimaAvaliacao.append(" ultimaExtJoelhoD av.: " + ultimaExtJoelhoD);
        }
        if( ultimaExtJoelhoE != null){
            dadosUltimaAvaliacao.append(" ultimaExtJoelhoE av.: " + ultimaExtJoelhoE);
        }
        if( ultimaExtJoelhoAss != null){
            dadosUltimaAvaliacao.append(" ultimaExtJoelhoAss av.: " + ultimaExtJoelhoAss);
        }
        if( ultimaFlexJoelhoD != null){
            dadosUltimaAvaliacao.append(" ultimaFlexJoelhoD av.: " + ultimaFlexJoelhoD);
        }
        if( ultimaFlexJoelhoE != null){
            dadosUltimaAvaliacao.append(" ultimaFlexJoelhoE av.: " + ultimaFlexJoelhoE);
        }
        if( ultimaFlexJoelhoAss != null){
            dadosUltimaAvaliacao.append(" ultimaFlexJoelhoAss av.: " + ultimaFlexJoelhoAss);
        }
        if( ultimaShTestMediaD != null){
            dadosUltimaAvaliacao.append(" ultimaShTestMediaD av.: " + ultimaShTestMediaD);
        }
        if( ultimaShTestMediaE != null){
            dadosUltimaAvaliacao.append(" ultimaShTestMediaE av.: " + ultimaShTestMediaE);
        }
        if( ultimaShTestScore != null){
            dadosUltimaAvaliacao.append(" ultimaShTestScore av.: " + ultimaShTestScore);
        }
        if( ultimaSdhTestMediaD != null){
            dadosUltimaAvaliacao.append(" ultimaSdhTestMediaD av.: " + ultimaSdhTestMediaD);
        }
        if( ultimaSdhTestMediaE != null){
            dadosUltimaAvaliacao.append(" ultimaSdhTestMediaE av.: " + ultimaSdhTestMediaE);
        }
        if( ultimaSdhTestScore != null){
            dadosUltimaAvaliacao.append(" ultimaSdhTestScore av.: " + ultimaSdhTestScore);
        }
        if( ultimaSlbTestD != null){
            dadosUltimaAvaliacao.append(" ultimaSlbTestD av.: " + ultimaSlbTestD);
        }
        if( ultimaSlbTestE != null){
            dadosUltimaAvaliacao.append(" ultimaSlbTestE av.: " + ultimaSlbTestE);
        }
        //
        return dadosUltimaAvaliacao.toString();
    }
}