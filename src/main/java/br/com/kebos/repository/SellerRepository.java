package br.com.kebos.repository;

import br.com.kebos.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByNomeLike(String nome);

}
