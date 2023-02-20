package br.com.kebos.controller;

import br.com.kebos.model.Recommendation;
import br.com.kebos.service.RecommendationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@SecurityRequirement(name = "kebosapi")
public class RecommendationControllerImpl implements RecommendationController{

    @Autowired
    private RecommendationService recommendationService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Recommendation> listAllRecommendations() {
        return recommendationService.listAllRecommendations();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Recommendation listByIdRecommendation(long id) {
        return recommendationService.listByIdRecommendation(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Recommendation saveRecommendation(@Valid @RequestBody Recommendation recommendation) {
        return recommendationService.saveRecommendation(recommendation);
    }
}
