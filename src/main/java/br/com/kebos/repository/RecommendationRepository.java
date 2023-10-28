package br.com.kebos.repository;

import br.com.kebos.model.Recommendation;
import br.com.kebos.model.Seller;
import br.com.kebos.model.StatusRecommendationEnum;
import br.com.kebos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

//    List<Recommendation> findByItems(Item item);
//    Recommendation findByCpfCnpj(String cpfCnpj);
    List<Recommendation> findByUserAndStatusLike(User user, StatusRecommendationEnum status);

    List<Recommendation> findByStatusLike(StatusRecommendationEnum status);

    List<Recommendation> findAllByUser(User user);

    Recommendation findByUserAndId(User user, long id);
    Recommendation findAllBySeller(Seller seller);



}
