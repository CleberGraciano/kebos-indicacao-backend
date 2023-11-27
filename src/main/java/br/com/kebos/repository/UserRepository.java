package br.com.kebos.repository;

import br.com.kebos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	boolean existsByEmail(String email);

	List<User> findByDisplayNameContainingIgnoreCase(String name);

}
