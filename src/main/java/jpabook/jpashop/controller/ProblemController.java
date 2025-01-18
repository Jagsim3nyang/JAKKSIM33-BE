package jpabook.jpashop.controller;

import jpabook.jpashop.dto.RandomProblemRequest;
import jpabook.jpashop.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping(value = "/api/random-problem", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getRandomProblem(@RequestBody RandomProblemRequest request) {
        return problemService.getRandomProblem(request)
                .map(ResponseEntity::ok) // If found, return 200 OK with the problem description
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }
}
