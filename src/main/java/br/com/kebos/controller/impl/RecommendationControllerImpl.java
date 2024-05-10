package br.com.kebos.controller.impl;

import br.com.kebos.controller.RecommendationController;
import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.Recommendation;
import br.com.kebos.model.StatusRecommendationEnum;
import br.com.kebos.service.RecommendationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@SecurityRequirement(name = "kebosapi")
public class RecommendationControllerImpl implements RecommendationController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RecommendationService recommendationService;

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @GetMapping
    public List<Recommendation> listAllRecommendations() {
        return recommendationService.listAllRecommendations();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @GetMapping("/{id}")
    public Recommendation listByIdRecommendation(@PathVariable(name = "id") Long id) {
        return recommendationService.listByIdRecommendation(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @PostMapping
    public Recommendation saveRecommendation(@Valid @RequestBody RecommendationDTO recommendationDTO) {
        return recommendationService.saveRecommendation(recommendationDTO);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @GetMapping("/status/{status}")
    public List<RecommendationCardDto> listAllRecommendationsByStatus(@PathVariable("status") StatusRecommendationEnum status) {
        return recommendationService.listAllRecommendationsByStatus(status);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecommendationStatus(@PathVariable(name = "id") Long id, @RequestParam StatusRecommendationEnum statusRecommendationEnum){
        try {
            recommendationService.updateRecommendationStatus(id, statusRecommendationEnum);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body("Status changed");
    }

}
