package jpabook.jpashop.service;

import jpabook.jpashop.repository.ProblemLogRepository;
import org.springframework.stereotype.Service;

@Service
public class CatStatusService {

    private final ProblemLogRepository problemLogRepository;

    public CatStatusService(ProblemLogRepository problemLogRepository) {
        this.problemLogRepository = problemLogRepository;
    }

    public CatStatus calculateCatStatus() {
        long totalLevel = problemLogRepository.count();
        int catCount = (int) (totalLevel / 5);
        int level = (int) (totalLevel % 5);
        return new CatStatus(catCount, level);
    }

    public static class CatStatus {
        private int catCount;
        private int level;

        public CatStatus(int catCount, int level) {
            this.catCount = catCount;
            this.level = level;
        }

        public int getCatCount() {
            return catCount;
        }

        public int getLevel() {
            return level;
        }
    }
}

