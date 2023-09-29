package br.com.kebos.repository;

import br.com.kebos.model.Recommendation;
import br.com.kebos.model.StatusRecommendationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

//    List<Recommendation> findByItems(Item item);
//    Recommendation findByCpfCnpj(String cpfCnpj);
    List<Recommendation> findByStatusLike(StatusRecommendationEnum status);


}
