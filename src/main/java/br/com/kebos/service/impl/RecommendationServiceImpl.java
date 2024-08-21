package br.com.kebos.service.impl;

import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.*;
import br.com.kebos.repository.*;
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


    private SellerRepository sellerRepository;


    private final ModelMapper mapper;
    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, UserRepository userRepository, SellerRepository sellerRepository, ModelMapper mapper) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
    }


    @Override
    public List<Recommendation> listAllRecommendations() {

        User user = userRepository.findByEmail(getUserLogged());
        if(user.getRoles().toString().contains("MODERATOR") || user.getRoles().toString().contains("ADMIN")){
            return recommendationRepository.findAll();
        } else {
            return recommendationRepository.findAllByUser(user);
        }
    }

    @Override
    public Recommendation listByIdRecommendation(Long id) {
        User user = userRepository.findByEmail(getUserLogged());

        if(user.getRoles().toString().contains("MODERATOR") || user.getRoles().toString().contains("ADMIN")){
            return recommendationRepository.findById(id).get();
        } else {
            return recommendationRepository.findByUserAndId(user, id);
        }

    }

    @Override
    public Recommendation saveRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = Recommendation.convert(recommendationDTO);
        User user = userRepository.findByEmail(getUserLogged());
        recommendation.setUser(user);
        recommendation.setStatus(StatusRecommendationEnum.ENVIADO);
        recommendation.setCreatedDate(new Date());
        recommendation.setModifiedDate(new Date());
        recommendation.setItemRecommendations(recommendation.getItemRecommendations());
        return recommendationRepository.save(recommendation);
    }

    @Override
    public List<RecommendationCardDto> listAllRecommendationsByStatus(StatusRecommendationEnum status) {
        User user = userRepository.findByEmail(getUserLogged());

        if(user.getRoles().toString().contains("MODERATOR") || user.getRoles().toString().contains("ADMIN")){
            return recommendationRepository.findByStatusLike(status)
                    .stream().map(recommendation -> mapper.map(recommendation, RecommendationCardDto.class))
                    .collect(Collectors.toList());
        } else {
            return recommendationRepository.findByUserAndStatusLike(user, status)
                    .stream().map(recommendation -> mapper.map(recommendation, RecommendationCardDto.class))
                    .collect(Collectors.toList());
        }

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
