package br.com.kebos.service;

import br.com.kebos.model.Recommendation;

import java.util.List;

public interface RecommendationService {

    List<Recommendation> listAllRecommendations();
    Recommendation listByIdRecommendation(long id);
    Recommendation saveRecommendation(Recommendation recommendation);
}
