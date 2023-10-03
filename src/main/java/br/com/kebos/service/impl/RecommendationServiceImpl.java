package br.com.kebos.service.impl;

import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.*;
import br.com.kebos.repository.ItemRepository;
import br.com.kebos.repository.RecommendationRepository;
import br.com.kebos.repository.UserRepository;
import br.com.kebos.service.RecommendationService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {


    private RecommendationRepository recommendationRepository;


    private UserRepository userRepository;


    private ItemRepository itemRepository;


    private final ModelMapper mapper;
    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, UserRepository userRepository, ItemRepository itemRepository, ModelMapper mapper) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }


    @Override
    public List<Recommendation> listAllRecommendations() {
        return recommendationRepository.findAll();
    }

    @Override
    public Recommendation listByIdRecommendation(Long id) {
        return recommendationRepository.findById(id).get();
    }

    @Override
    public Recommendation saveRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = Recommendation.convert(recommendationDTO);
        User user = userRepository.findByEmail(getUserLogged());
        List<Item> items = new ArrayList<>();
        List<Double> valoresBonus = new ArrayList<>();
        user.setStatusCadastro(true);
        userRepository.save(user);
        recommendation.setUser(user);
        recommendation.setStatus(StatusRecommendationEnum.ENVIADO);
        recommendation.setCreatedDate(new Date());
        recommendation.setModifiedDate(new Date());


        recommendationDTO.getItems().forEach((id, quantidade) -> {

            Item item =  itemRepository.findById(id).get();
            valoresBonus.add(item.getBonus()*quantidade);
            items.add(item);
        });

        recommendation.setItems(items);
        recommendation.setValortotal(valoresBonus.stream().mapToDouble(Double::doubleValue).sum());

        return recommendationRepository.save(recommendation);
    }

    @Override
    public List<RecommendationCardDto> listAllRecommendationsByStatus(StatusRecommendationEnum status) {
        return recommendationRepository.findByStatusLike(status)
                .stream().map(recommendation -> mapper.map(recommendation, RecommendationCardDto.class))
                .collect(Collectors.toList());
    }

    private String getUserLogged() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (user instanceof UserDetails) {
            username = ((UserDetails)user).getUsername();
        } else {
            username = user.toString();
        }
        return username;
    }

    @Override
    public Recommendation updateRecommendationStatus(Long recommendationId, StatusRecommendationEnum statusRecommendationEnum) throws NotFoundException {
        Recommendation existingRecommendation = null;
        existingRecommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new NotFoundException("Recommendation not found"));
        // Update the existing partner's properties
        existingRecommendation.setStatus(statusRecommendationEnum);


        return recommendationRepository.save(existingRecommendation);
    }
}
