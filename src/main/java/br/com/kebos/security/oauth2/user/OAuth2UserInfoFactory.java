package br.com.kebos.security.oauth2.user;

import java.util.Map;

import br.com.kebos.dto.SocialProvider;
import br.com.kebos.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.FACEBOOK.getProviderType())) {
			return new FacebookOAuth2UserInfo(attributes);
		}  else if (registrationId.equalsIgnoreCase(SocialProvider.LINKEDIN.getProviderType())) {
			return new LinkedinOAuth2UserInfo(attributes);
		}  else {
			throw new OAuth2AuthenticationProcessingException("Desculpe! Logar com " + registrationId + " ainda não é suportado.");
		}
	}
}