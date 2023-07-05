package br.com.kebos.service;

import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.Recommendation;
import br.com.kebos.model.StatusRecommendationEnum;
import br.com.kebos.model.User;

import java.util.List;

public interface RecommendationService {

    List<Recommendation> listAllRecommendations();
    Recommendation listByIdRecommendation(long id);
    Recommendation saveRecommendation(RecommendationDTO recommendationDTO);
    List<RecommendationCardDto> listAllRecommendationsByStatus(StatusRecommendationEnum statusRecommendationEnum);
}
