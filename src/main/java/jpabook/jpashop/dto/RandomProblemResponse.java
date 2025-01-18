package jpabook.jpashop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RandomProblemResponse {
    private String description; // DB에서 가져온 문제 설명 (JSON 형식의 String)
}

