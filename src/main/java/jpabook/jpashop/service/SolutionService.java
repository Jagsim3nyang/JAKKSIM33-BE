package jpabook.jpashop.service;

import jpabook.jpashop.domain.Problem;
import jpabook.jpashop.domain.ProblemLog;
import jpabook.jpashop.dto.SubmitSolutionRequest;
import jpabook.jpashop.dto.SubmitSolutionResponse;
import jpabook.jpashop.repository.ProblemLogRepository;
import jpabook.jpashop.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SolutionService {

    private final ProblemRepository problemRepository;
    private final ProblemLogRepository problemLogRepository;

    @Transactional
    public SubmitSolutionResponse evaluateSolution(SubmitSolutionRequest request) {
        // 문제 조회
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found with ID: " + request.getProblemId()));

        // OpenAI API 호출
        String apiKey = "";
        String model = "gpt-4o";

        String pd = problem.getDescription();
        String code = request.getUserCode().getCode();

        String result = callOpenAIJudgeAPI(apiKey, model, pd, code);

        ProblemLog problemLog = new ProblemLog();
        problemLog.setProblemId(problemLog.getProblemId());
        problemLog.setResult(result);
        problemLog.setSolvedAt(Instant.now());

        problemLogRepository.save(problemLog);

        // 결과 반환 (True/False)
        return new SubmitSolutionResponse(result, 0, problemLog.getSolvedAt());
    }
    private String escapeForJson(String text) {
        if (text == null) {
            return "";
        }
        return text
                // first escape backslashes
                .replace("\\", "\\\\")
                // escape double quotes
                .replace("\"", "\\\"")
                // escape carriage return
                .replace("\r", "\\r")
                // escape new line
                .replace("\n", "\\n");
    }

    private String callOpenAIJudgeAPI(String apiKey, String model, String problemStatement, String userSolution) {
        String endpoint = "https://api.openai.com/v1/chat/completions";

        // Escape 입력
        problemStatement = escapeForJson(problemStatement);
        userSolution     = escapeForJson(userSolution);

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

            // Request Body 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 읽기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // JSON 응답에서 결과 추출
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim()
                    .toLowerCase();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
