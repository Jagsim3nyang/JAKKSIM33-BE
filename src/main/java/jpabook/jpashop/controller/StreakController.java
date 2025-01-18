package jpabook.jpashop.controller;

import jpabook.jpashop.dto.DailyStreakResponse;
import jpabook.jpashop.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @GetMapping("/api/daily-streak")
    public DailyStreakResponse getStreakInfo() {
        return streakService.getDailyStreak();
    }
}
