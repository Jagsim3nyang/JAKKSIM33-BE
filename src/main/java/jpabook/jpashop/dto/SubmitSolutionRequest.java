package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmitSolutionRequest {
    private Long problemId;       // 문제 ID
    private UserCode userCode;    // 사용자 코드 정보

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserCode {
        private String code;      // 제출한 코드
        private String language;  // 언어 (예: Python, Java 등)
    }
}

