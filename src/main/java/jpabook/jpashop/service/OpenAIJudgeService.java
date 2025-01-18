package jpabook.jpashop.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OpenAIJudgeService {

    public String callOpenAIJudgeAPI(String apiKey, String model, String problemStatement, String userSolution) {
        String endpoint = "https://api.openai.com/v1/chat/completions";

        // Escape problemStatement and userSolution
        problemStatement = problemStatement.replace("\n", "\\n").replace("\"", "\\\"");
        userSolution = userSolution.replace("\n", "\\n").replace("\"", "\\\"");

        String prompt = "당신은 코딩 문제에 대한 정답을 판별하는 AI입니다.\\n"
                + "사용자가 입력한 정답코드와 문제를 확인하고, 해당코드가 정답이면 'True', 아니면 'False'를 출력.\\n"
                + "다른 메세지는 절대 출력하지 말고 'True' or 'False'만 출력할 것.\\n\\n"
                + "### 출력 형식\\n"
                + "- 유저의 정답이 올바른 경우: `True`\\n"
                + "- 유저의 정답이 틀린 경우: `False`\\n\\n"
                + "문제: " + problemStatement + "\\n"
                + "유저의 제출: " + userSolution;

        String jsonInputString = String.format(
                "{\"model\": \"%s\", \"messages\": [{\"role\": \"system\", \"content\": \"%s\"}]}",
                model, prompt);

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            // Send the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse the response and extract the assistant's reply
            JSONObject jsonResponse = new JSONObject(response.toString());
            String assistantReply = jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return assistantReply.trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}

