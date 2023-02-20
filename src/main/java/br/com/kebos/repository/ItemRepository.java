package br.com.kebos.repository;

import br.com.kebos.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findById(long id);

    List<Item> findByNomeLike(String nome);
}
