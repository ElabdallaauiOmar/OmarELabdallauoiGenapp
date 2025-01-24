package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HuggingFaceApi {

    private static final String API_URL = "https://api-inference.huggingface.co/models/mistralai/Mistral-Nemo-Instruct-2407/v1/chat/completions";
    private static final String API_TOKEN = "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"; // Remplacez par votre token réel

    public static void main(String[] args) throws IOException, InterruptedException {
    	String requestBody = "{\"model\": \"mistralai/Mistral-Nemo-Instruct-2407\", \"messages\": [{\"role\": \"user\", \"content\": \"en tantque exper en ceonception logiciels donner moi des uses case pour la creation d une app de gestion hopital\"}], \"max_tokens\": 500, \"stream\": false}";



        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
             System.out.println(response.body()); // Affiche la réponse JSON de l'API.
        } else {
            System.out.println("Erreur lors de l'appel à l'API: " + response.statusCode());
            System.out.println(response.body()); // Affiche le message d'erreur de l'API
        }
    }
}