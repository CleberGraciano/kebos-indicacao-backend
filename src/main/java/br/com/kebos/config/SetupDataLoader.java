package br.com.kebos.config;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import br.com.kebos.dto.SocialProvider;
import br.com.kebos.model.Seller;
import br.com.kebos.model.User;
import br.com.kebos.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.kebos.model.Role;
import br.com.kebos.repository.RoleRepository;
import br.com.kebos.repository.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		// Create initial roles
		Role userRole = createRoleIfNotFound(Role.ROLE_USER);
		Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
		Role modRole = createRoleIfNotFound(Role.ROLE_MODERATOR);

		//create first user
		createUserIfNotFound("admin@kebos.com.br", Set.of(userRole, adminRole, modRole));

		//create/update sellers
		createUpdateSellerAsUserIfNotFound("nascimento@kebos.com.br", Set.of(userRole, adminRole, modRole), "José Nascimento Gonçalves Filho", "11 99191-8014");
		createUpdateSellerAsUserIfNotFound("jefferson@kebos.com.br", Set.of(userRole, modRole), "Jefferson Nascimento Gonçalves", "11 96905-0088");
		createUpdateSellerAsUserIfNotFound("comercial@kebos.com.br", Set.of(userRole, modRole), "Julia Gomes do Nascimento", "11 94179-4786");


		alreadySetup = true;
	}

	@Transactional
	private final User createUserIfNotFound(final String email, Set<Role> roles) {
		User user;
		if (userRepository.existsByEmail(email)) {
			user = userRepository.findByEmail(email);
			return user;
		}
		user = new User();
		user.setDisplayName("Admin");
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode("kebos@Admin"));
		user.setRoles(roles);
		user.setProvider(SocialProvider.LOCAL.getProviderType());
		user.setStatusCadastro(false);
		user.setEnabled(true);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		return user;
	}

	@Transactional
	private final User createSellerUser(Seller seller, Set<Role> roles, String nome, String tel, final String email) {
		User userAtualizado;
		if(userRepository.existsByEmail(seller.getEmail())) {
			userAtualizado = userRepository.findByEmail(seller.getEmail());
			userAtualizado.setDisplayName(nome);
			userAtualizado.setCelular(tel);
			userAtualizado.setRoles(roles);
			userAtualizado.setProvider(SocialProvider.LOCAL.getProviderType());
			userAtualizado.setStatusCadastro(true);
			userAtualizado.setEnabled(true);
			userAtualizado.setTermoUso(true);
		} else {
			userAtualizado = new User();
			userAtualizado.setDisplayName(nome);
			userAtualizado.setEmail(email);
			userAtualizado.setPassword(passwordEncoder.encode("senha123"));
			userAtualizado.setRoles(roles);
			userAtualizado.setProvider(SocialProvider.LOCAL.getProviderType());
			userAtualizado.setStatusCadastro(true);
			userAtualizado.setEnabled(true);
			userAtualizado.setTermoUso(true);
			Date now = Calendar.getInstance().getTime();
			userAtualizado.setCreatedDate(now);
			userAtualizado.setModifiedDate(now);
			userAtualizado.setCelular(tel);
		}

		return userAtualizado;
	}

	@Transactional
	private final Seller createUpdateSellerAsUserIfNotFound(final String email, Set<Role> roles, String nome, String tel){
		Seller seller;
		if (sellerRepository.existsByEmail(email)){
			seller = sellerRepository.findByEmail(email);
		} else {

			seller = new Seller();
			seller.setNome(nome);
			seller.setTelefone(tel);
			seller.setEmail(email);
			seller.setCreated(LocalDate.now());

			User userAtualizado = createSellerUser(seller, roles, nome, tel, email);

			seller.setUser(userAtualizado);
			//salvando o Seller
			seller = sellerRepository.save(seller);
		}

		return seller;
	}

	@Transactional
	private final Role createRoleIfNotFound(final String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = roleRepository.save(new Role(name));
		}
		return role;
	}
}