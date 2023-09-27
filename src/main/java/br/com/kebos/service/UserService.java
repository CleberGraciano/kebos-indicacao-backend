package br.com.kebos.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.kebos.dto.LocalUser;
import br.com.kebos.dto.SignUpRequest;
import br.com.kebos.model.Partner;
import br.com.kebos.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import br.com.kebos.exception.UserAlreadyExistAuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	User getPartnerById(Long id);

	List<User> listPartner();
	User updatePartner(User partner);

	void resetPassword(String email);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
