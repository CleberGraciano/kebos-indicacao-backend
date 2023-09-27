package br.com.kebos.repository;

import br.com.kebos.model.Partner;
import br.com.kebos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<User, Long> {

}
