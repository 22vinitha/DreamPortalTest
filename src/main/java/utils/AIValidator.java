package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIValidator {

    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private static boolean aiFailed = false;

    // ---------- MAIN ----------
    public static String classifyDream(String dream) {

        String ai = callOpenAI(dream);

        if (ai != null) {
            return ai;
        }

        aiFailed = true;
        return fallback(dream);
    }

    // ---------- OPENAI CALL ----------
    private static String callOpenAI(String dream) {

        try {
            if (API_KEY == null || API_KEY.isEmpty()) {
                aiFailed = true;
                return null;
            }

            String body =
                    "{"
                            + "\"model\":\"gpt-4o-mini\","
                            + "\"messages\":[{"
                            + "\"role\":\"user\","
                            + "\"content\":\"Classify dream as Good or Bad: " + dream + "\""
                            + "}],"
                            + "\"max_tokens\":10"
                            + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString());

            String res = response.body().toLowerCase();

            if (res.contains("good")) return "Good";
            if (res.contains("bad")) return "Bad";

            return null;

        } catch (Exception e) {
            aiFailed = true;
            return null;
        }
    }

    // ---------- FALLBACK ----------
    private static String fallback(String dream) {

        dream = dream.toLowerCase();

        if (dream.contains("flying") ||
                dream.contains("lottery") ||
                dream.contains("dolphin") ||
                dream.contains("travel")) {
            return "Good";
        }

        return "Bad";
    }

    // ---------- STATUS ----------
    public static boolean isAIFailed() {
        return aiFailed;
    }

    // ---------- MULTI ----------
    public static Map<String, String> classifyDreams(List<String> dreams) {

        Map<String, String> results = new HashMap<>();

        for (String d : dreams) {
            results.put(d, classifyDream(d));
        }

        return results;
    }
}