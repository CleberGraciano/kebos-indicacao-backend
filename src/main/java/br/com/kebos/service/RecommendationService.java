package br.com.kebos.service;

import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.Recommendation;
import br.com.kebos.model.StatusRecommendationEnum;
import javassist.NotFoundException;

import java.util.List;

public interface RecommendationService {

    List<Recommendation> listAllRecommendations();
    Recommendation listByIdRecommendation(Long id);
    Recommendation saveRecommendation(RecommendationDTO recommendationDTO);
    List<RecommendationCardDto> listAllRecommendationsByStatus(StatusRecommendationEnum statusRecommendationEnum);

    Recommendation updateRecommendationStatus(Long recommendationId, StatusRecommendationEnum statusRecommendationEnum) throws NotFoundException;
}
