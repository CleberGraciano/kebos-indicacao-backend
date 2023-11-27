package br.com.kebos.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import br.com.kebos.dto.LocalUser;
import br.com.kebos.dto.SignUpRequest;
import br.com.kebos.model.User;
import javassist.NotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import br.com.kebos.exception.UserAlreadyExistAuthenticationException;

public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	User getPartnerById(Long id);

	List<User> listPartner();

	List<User> listUsersByName(String name);

	User updatePartner(Long partnerId, User partner) throws NotFoundException;

	void resetPassword(String email);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
