package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RandomProblemRequest {
    private String category;   // 문제 분류 (예: DP, Greedy 등)
    private String language;   // 언어 (예: Python, Java 등)
    private String difficulty; // 난이도 (예: Easy, Medium, Hard)
}

