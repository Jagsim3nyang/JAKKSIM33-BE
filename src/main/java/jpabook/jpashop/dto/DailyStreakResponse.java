package jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyStreakResponse {
    private int highest_streak;  // 최고 기록
    private int current_streak;  // 현재 연속 기록
}
