package jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class SubmitSolutionResponse {
    private String result;       // 결과 ("O" 또는 "X")
    private int earnedCoins;     // 획득한 코인 수
    private Instant solvedAt;
}


