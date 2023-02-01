package br.com.kebos.service;

import java.util.Map;
import java.util.Optional;

import br.com.kebos.dto.LocalUser;
import br.com.kebos.dto.SignUpRequest;
import br.com.kebos.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import br.com.kebos.exception.UserAlreadyExistAuthenticationException;

public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
