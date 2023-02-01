package br.com.kebos.repository;

import br.com.kebos.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

//    List<Recommendation> findByItems(Item item);
//    Recommendation findByCpfCnpj(String cpfCnpj);
//    List<Recommendation> findByNomeContatoLike(String nome);


}
