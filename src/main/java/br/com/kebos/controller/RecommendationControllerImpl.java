package br.com.kebos.controller;

import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.Recommendation;
import br.com.kebos.model.StatusRecommendationEnum;
import br.com.kebos.service.RecommendationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collector;

@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@SecurityRequirement(name = "kebosapi")
public class RecommendationControllerImpl implements RecommendationController{
    @Autowired
    private ModelMapper modelMapper;
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
    public Recommendation saveRecommendation(@Valid @RequestBody RecommendationDTO recommendationDTO) {
        return recommendationService.saveRecommendation(recommendationDTO);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{status}")
    public List<RecommendationCardDto> listAllRecommendationsByStatus(@PathVariable("status") StatusRecommendationEnum status) {
        return recommendationService.listAllRecommendationsByStatus(status);
    }

}
