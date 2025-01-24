package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.example.*;

import com.example.demo.entitie.UserCase;
import com.example.demo.entitie.Case;
import com.example.demo.service.UserCaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/huggingface")
public class HuggingFaceApi2 {

    private static final String API_URL = "https://api-inference.huggingface.co/models/mistralai/Mistral-Nemo-Instruct-2407/v1/chat/completions";
    private static final String API_TOKEN = "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";

    private final UserCaseService userCaseService;

    public HuggingFaceApi2(UserCaseService userCaseService) {
        this.userCaseService = userCaseService;
    }

    /**
     * Generate user cases based on a "besoin"
     */
    @PostMapping("/generate-user-cases")
    public ResponseEntity<?> generateUserCases(@RequestBody BesoinRequest besoinRequest) {
        try {
            if (!isValidRequest(besoinRequest.getBesoin(), besoinRequest.getMaxTokens())) {
                return ResponseEntity.badRequest().body("Invalid request: 'besoin' is required and 'maxTokens' must be greater than 0.");
            }

            String prompt = String.format(
                "En tant qu'expert en conception logicielle, générez des cas d'utilisation détaillés pour le besoin suivant : \"%s\"",
                sanitizeInput(besoinRequest.getBesoin())
            );

            String apiResponse = callHuggingFaceAPI(prompt, besoinRequest.getMaxTokens());
            if (apiResponse == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to call the Hugging Face API.");
            }

            Map<String, Object> formattedResponse = formatUserCases(apiResponse);
            if ("success".equals(formattedResponse.get("status"))) {
                String userCases = (String) formattedResponse.get("userCases");

                // Save the user cases as a single UserCase
                UserCase savedUserCase = userCaseService.saveUserCase(userCases);

                return ResponseEntity.ok(savedUserCase);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(formattedResponse.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Split raw user cases into structured cases
     */
    @PostMapping("/split-user-cases/{id}")
    public ResponseEntity<?> splitUserCases(@RequestBody SplitRequest splitRequest) {
        if (!isValidRequest(splitRequest.getUserCases(), splitRequest.getMaxTokens())) {
            return ResponseEntity.badRequest().body("Invalid request: 'userCases' is required and 'maxTokens' must be greater than 0.");
        }

        String prompt = String.format(
            "Prenez les cas d'utilisation suivants et divisez-les en cas d'utilisation individuels et structurés : \"%s\"",
            sanitizeInput(splitRequest.getUserCases())
        );

        String apiResponse = callHuggingFaceAPI(prompt, splitRequest.getMaxTokens());
        if (apiResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to call the Hugging Face API.");
        }

        Map<String, Object> structuredCases = formatStructuredCases(apiResponse);
        return ResponseEntity.ok(structuredCases);
    }

    /**
     * Utility: Validate request input
     */
    private boolean isValidRequest(String input, int maxTokens) {
        return input != null && !input.trim().isEmpty() && maxTokens > 0;
    }

    /**
     * Utility: Sanitize user input
     */
    private String sanitizeInput(String input) {
        return input.replace("\"", "\\\""); // Escape double quotes
    }

    /**
     * Call the Hugging Face API
     */
    private String callHuggingFaceAPI(String prompt, int maxTokens) {
        try {
            // Prepare the payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", "mistralai/Mistral-Nemo-Instruct-2407");
            payload.put("messages", List.of(Map.of("role", "user", "content", prompt)));
            payload.put("max_tokens", maxTokens);
            payload.put("stream", false);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(payload);

            // Log for debugging
            System.out.println("Request Body: " + requestBody);

            // Prepare HTTP request
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_TOKEN);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Call the API
            ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                entity,
                String.class
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Format user cases from API response
     */
    private Map<String, Object> formatUserCases(String apiResponse) {
        Map<String, Object> result = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(apiResponse, Map.class);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
            if (choices != null && !choices.isEmpty()) {
                String content = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
                result.put("status", "success");
                result.put("userCases", content);
            } else {
                result.put("status", "error");
                result.put("message", "No user cases found in the API response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Error parsing the API response: " + e.getMessage());
        }
        return result;
    }

    /**
     * Format structured user cases
     */
    private Map<String, Object> formatStructuredCases(String apiResponse) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("structuredCases", apiResponse); // Raw response for now
        return result;
    }

    // Request DTOs
    public static class BesoinRequest {
        private String besoin;
        private int maxTokens = 500;

        public String getBesoin() {
            return besoin;
        }

        public void setBesoin(String besoin) {
            this.besoin = besoin;
        }

        public int getMaxTokens() {
            return maxTokens;
        }

        public void setMaxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
        }
    }

    public static class SplitRequest {
        private String userCases;
        private int maxTokens = 500;

        public String getUserCases() {
            return userCases;
        }

        public void setUserCases(String userCases) {
            this.userCases = userCases;
        }

        public int getMaxTokens() {
            return maxTokens;
        }

        public void setMaxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
        }
    }
}
