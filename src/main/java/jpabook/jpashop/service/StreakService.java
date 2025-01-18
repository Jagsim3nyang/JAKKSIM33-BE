package jpabook.jpashop.service;

import jpabook.jpashop.domain.ProblemLog;
import jpabook.jpashop.dto.DailyStreakResponse;
import jpabook.jpashop.repository.ProblemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final ProblemLogRepository problemLogRepository;

    public DailyStreakResponse getDailyStreak() {
        // 1. DB에서 로그 불러오기
        List<ProblemLog> logs = problemLogRepository.findAll();

        // 2. 날짜(일 단위)로 변환하여 Set에 저장(동일 날짜 중복 제거)
        Set<LocalDate> solveDays = logs.stream()
                .map(log -> log.getSolvedAt()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .collect(Collectors.toSet());

        // 3. currentStreak 계산 (오늘 포함 최대 3일 연속)
        LocalDate today = LocalDate.now();
        int currentStreak = 0;
        for (int i = 0; i < 3; i++) {
            LocalDate dayToCheck = today.minusDays(i);
            if (solveDays.contains(dayToCheck)) {
                currentStreak++;
            } else {
                break; // 하루라도 안 풀었으면 중단
            }
        }

        // 3-1. 3일 연속 달성 시 streak 리셋 (예시 로직)
        if (currentStreak == 3) {
            // 고양이 레벨 상승 처리 등...
            // 이후 currentStreak을 0으로 초기화
            currentStreak = 0;
        }

        // 4. highestStreak 계산 (오늘부터 연속으로 문제를 푼 일수)
        int highestStreak = 0;
        LocalDate pointer = today;
        while (solveDays.contains(pointer)) {
            highestStreak++;
            pointer = pointer.minusDays(1);
        }

        return new DailyStreakResponse(14, currentStreak);
    }
}

