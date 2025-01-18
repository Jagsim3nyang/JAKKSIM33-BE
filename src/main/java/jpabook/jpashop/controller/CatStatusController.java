package jpabook.jpashop.controller;

import jpabook.jpashop.service.CatStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class CatStatusController {

    private final CatStatusService catStatusService;

    public CatStatusController(CatStatusService catStatusService) {
        this.catStatusService = catStatusService;
    }

    @GetMapping("/api/cat-status")
    public Map<String, Object> getCatStatus() {
        CatStatusService.CatStatus catStatus = catStatusService.calculateCatStatus();
        return Map.of(
                "cats", Map.of(
                        "catCount", catStatus.getCatCount(),
                        "level", catStatus.getLevel()
                )
        );
    }
}
