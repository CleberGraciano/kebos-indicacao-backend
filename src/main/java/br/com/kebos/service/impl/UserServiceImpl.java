package br.com.kebos.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import br.com.kebos.dto.EmailDto;
import br.com.kebos.dto.LocalUser;
import br.com.kebos.dto.SignUpRequest;
import br.com.kebos.dto.SocialProvider;
import br.com.kebos.model.*;
import br.com.kebos.repository.PartnerRepository;
import br.com.kebos.repository.PasswordResetTokenRepository;
import br.com.kebos.security.oauth2.user.OAuth2UserInfo;
import br.com.kebos.security.oauth2.user.OAuth2UserInfoFactory;
import br.com.kebos.service.UserService;
import br.com.kebos.util.GeneralUtils;
import br.com.kebos.util.Util;
import com.google.gson.Gson;
import javassist.NotFoundException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.kebos.exception.OAuth2AuthenticationProcessingException;
import br.com.kebos.exception.UserAlreadyExistAuthenticationException;
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

//	@Value("${url.email}")
//	private String urlEmail;

	@Value("${spring.mail.username}")
	private String emailFrom;

	@Autowired
	private JavaMailSender emailSender;

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
		user.setTermoUso(false);
		user = userRepository.save(user);
		//userRepository.flush();
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
	public List<User> listUsersByName(final String name){ return userRepository.findByDisplayNameContainingIgnoreCase(name);}

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
		existingUser.setTermoUso(false);
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

	public User updatePartner(Long userId, User newPartnerData) throws NotFoundException {
		User existingUser = null;
			existingUser = partnerRepository.findById(userId)
					.orElseThrow(() -> new NotFoundException("Partner not found"));
			// Update the existing partner's properties
			existingUser.setImagem(newPartnerData.getImagem());
			existingUser.setDataNascimento(newPartnerData.getDataNascimento());
			existingUser.setCpf(newPartnerData.getCpf());
			existingUser.setCep(newPartnerData.getCep());
			existingUser.setLogradouro(newPartnerData.getLogradouro());
			existingUser.setNumero(newPartnerData.getNumero());
			existingUser.setBairro(newPartnerData.getBairro());
			existingUser.setCidade(newPartnerData.getCidade());
			existingUser.setUf(newPartnerData.getUf());
			existingUser.setCelular(newPartnerData.getCelular());
			existingUser.setFoneFixo(newPartnerData.getFoneFixo());
			existingUser.setFoneComercial(newPartnerData.getFoneComercial());
			existingUser.setPix(newPartnerData.getPix());
			existingUser.setBanco(newPartnerData.getBanco());
			existingUser.setTipoContaEnum(newPartnerData.getTipoContaEnum());
			existingUser.setAgencia(newPartnerData.getAgencia());
			existingUser.setConta(newPartnerData.getConta());
			existingUser.setDigito(newPartnerData.getDigito());
			existingUser.setTermoUso(newPartnerData.isTermoUso());
			existingUser.setDisplayName(newPartnerData.getDisplayName());
			existingUser.setEmail(newPartnerData.getEmail());
			existingUser.setStatusCadastro(true);

			if (newPartnerData.hasValidPassword()) {
				existingUser.setPassword(passwordEncoder.encode(newPartnerData.getPassword()));
			}

			partnerRepository.save(existingUser);

		return existingUser;
	}



	@Override
	public User getPartnerById(Long id) {
		return partnerRepository.findById(id).get();
	}

	public List<User> listPartner(){
		return partnerRepository.findAll();
	}

@Override
public void resetPassword(String email) {

	User user = userRepository.findByEmail(email);

	if (user == null) {
			throw new RuntimeException("Email não encontrado");
	}

//	String token = UUID.randomUUID().toString();
	String token = Util.gerarSenha();
	PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
	passwordResetTokenRepository.save(passwordResetToken);
	user.setPassword(passwordEncoder.encode(token));
	userRepository.save(user);


//	OkHttpClient client = new OkHttpClient();
//	Gson gson = new Gson();

	EmailDto emailRequest = new EmailDto(
			email,
			"Recuperação de Senha Kebos",
			"Nova senha: "+token
	);

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailFrom);
			message.setTo(email);
			message.setSubject("Recuperação de Senha Kebos");
			message.setText("Nova senha: "+token);
			emailSender.send(message);
	}



//	String json = gson.toJson(emailRequest);
//	MediaType JSON = MediaType.get("application/json; charset=utf-8");
//	RequestBody body = RequestBody.create(json, JSON);

//	Request request = new Request.Builder()
//			.url(urlEmail)
//			.post(body)
//			.addHeader("Access-Control-Allow-Origin", "*")
//			.addHeader("Content-Type", "application/json")
//			.build();
//
//	try (Response response = client.newCall(request).execute()) {
//		if (response.isSuccessful()) {
//			System.out.println("Response Code: " + response.code());
//			System.out.println("Response Body: " + response.body().string());
//		} else {
//			System.err.println("Request failed: " + response.code());
//		}
//	} catch (IOException e) {
//		e.printStackTrace();
//	}

}
