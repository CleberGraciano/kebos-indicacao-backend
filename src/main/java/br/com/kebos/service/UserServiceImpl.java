package br.com.kebos.service;

import java.util.*;

import br.com.kebos.dto.LocalUser;
import br.com.kebos.dto.SignUpRequest;
import br.com.kebos.dto.SocialProvider;
import br.com.kebos.model.Partner;
import br.com.kebos.model.PasswordResetToken;
import br.com.kebos.model.User;
import br.com.kebos.repository.PartnerRepository;
import br.com.kebos.repository.PasswordResetTokenRepository;
import br.com.kebos.security.oauth2.user.OAuth2UserInfo;
import br.com.kebos.security.oauth2.user.OAuth2UserInfoFactory;
import br.com.kebos.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.kebos.exception.OAuth2AuthenticationProcessingException;
import br.com.kebos.exception.UserAlreadyExistAuthenticationException;
import br.com.kebos.model.Role;
import br.com.kebos.repository.RoleRepository;
import br.com.kebos.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private EmailService emailService;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("Id do usuário " + signUpRequest.getUserID() + " já existente");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("Email do usuário " + signUpRequest.getEmail() + " já existente");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		userRepository.flush();
		return user;
	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setEnabled(true);
		user.setProviderUserId(formDTO.getProviderUserId());
		return user;
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Nome não encontrado no provedor OAuth2");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("E-mail não encontrado do provedor OAuth2");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Parece que você se inscreveu com a conta do  " + user.getProvider() + ". Por favor, logue com sua conta do " + user.getProvider() + ".");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	public Partner updatePartner(Partner partner){
		User user = userRepository.findByEmail(partner.getEmail());
		if (user!=null) {
			partner.setStatusCadastro(true);
			return partnerRepository.save(partner);
		}else {
			user.setStatusCadastro(false);
			throw new RuntimeException("Usuario não encontrado");
		}
	}

	@Override
	public Partner getPartnerById(Long id) {
		return partnerRepository.findById(id).get();
	}

	public List<Partner> listPartner(){
		return partnerRepository.findAll();
	}

	@Override
	public void resetPassword(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("Usuário não encontrado");
		}

		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(passwordResetToken);

		String resetLink = "http://seusite.com/reset-password?token=" + token;
		emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
	}

}
