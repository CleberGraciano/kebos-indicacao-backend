package br.com.kebos.service;

import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.*;
import br.com.kebos.repository.ItemRepository;
import br.com.kebos.repository.RecommendationRepository;
import br.com.kebos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService{

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

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
    public Recommendation saveRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = Recommendation.convert(recommendationDTO);
        User user = userRepository.findByEmail(getUserLogged());
        List<Item> items = new ArrayList<>();
        List<Double> valoresBonus = new ArrayList<>();


        recommendation.setUser(user);
        recommendation.setStatus("encaminhado");

        recommendationDTO.getItems().forEach((id, quantidade) -> {

            Item item =  itemRepository.findById(id).get();

            item.setQuantidade(quantidade);
            valoresBonus.add(item.getBonus()*item.getQuantidade());
            items.add(item);
        });

        recommendation.setItems(items);
        recommendation.setValortotal(valoresBonus.stream().mapToDouble(Double::doubleValue).sum());

        return recommendationRepository.save(recommendation);
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
}
