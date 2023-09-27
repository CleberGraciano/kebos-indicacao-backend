package br.com.kebos.dto;

public enum SocialProvider {

	FACEBOOK("facebook"), LINKEDIN("linkedin"), GOOGLE("google"), LOCAL("local");

	private String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}

}
