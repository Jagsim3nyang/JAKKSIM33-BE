package jpabook.jpashop.service;

import jpabook.jpashop.domain.Problem;
import jpabook.jpashop.dto.RandomProblemRequest;
import jpabook.jpashop.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public Optional<String> getRandomProblem(RandomProblemRequest request) {
        // DB에서 조건에 맞는 문제 검색
       /* return problemRepository
                .findAllByCategoryAndLanguageAndDifficulty(
                        request.getCategory(),
                        request.getLanguage(),
                        request.getDifficulty()
                )
                .stream().findFirst()
                .map(Problem::getDescription); // If found, map to description; otherwise, return empty
    */
        return problemRepository
                .findById(1L)
                .map(Problem::getDescription); // If found, map to description; otherwise, return empty
    }

}

