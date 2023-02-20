package br.com.kebos.service;

import br.com.kebos.model.Recommendation;
import br.com.kebos.repository.ItemRepository;
import br.com.kebos.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService{

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Recommendation> listAllRecommendations() {
        return recommendationRepository.findAll();
    }

    @Override
    public Recommendation listByIdRecommendation(long id) {
        return recommendationRepository.findById(id).get();
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }
}
