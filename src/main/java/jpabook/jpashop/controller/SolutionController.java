package jpabook.jpashop.controller;

import jpabook.jpashop.dto.SubmitSolutionRequest;
import jpabook.jpashop.dto.SubmitSolutionResponse;
import jpabook.jpashop.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/api/submit-solution")
    public SubmitSolutionResponse submitSolution(@RequestBody SubmitSolutionRequest request) {
        return solutionService.evaluateSolution(request);
    }
}


