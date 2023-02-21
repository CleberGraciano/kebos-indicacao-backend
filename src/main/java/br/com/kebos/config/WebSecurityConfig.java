package br.com.kebos.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import br.com.kebos.security.jwt.TokenAuthenticationFilter;
import br.com.kebos.security.oauth2.CustomOAuth2UserService;
import br.com.kebos.security.oauth2.CustomOidcUserService;
import br.com.kebos.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import br.com.kebos.security.oauth2.OAuth2AccessTokenResponseConverterWithDefaults;
import br.com.kebos.security.oauth2.OAuth2AuthenticationFailureHandler;
import br.com.kebos.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	CustomOidcUserService customOidcUserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.cors()
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint())
				.and()
			.authorizeRequests()
				.antMatchers("/swagger-ui/**", "/", "/error", "/api/all", "/api/auth/**", "/oauth2/**", "/h2-console/**").permitAll()
			.anyRequest()
				.authenticated()
				.and()
			.oauth2Login()
				.authorizationEndpoint()
					.authorizationRequestRepository(cookieAuthorizationRequestRepository())
					.and()
				.redirectionEndpoint()
					.and()
				.userInfoEndpoint()
					.oidcUserService(customOidcUserService)
					.userService(customOAuth2UserService)
					.and()
				.tokenEndpoint()
					.accessTokenResponseClient(authorizationCodeTokenResponseClient())
					.and()
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler);

		// filtro de autenticação personalizado baseado em token
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	/*
	 * Por padrão, o Spring OAuth2 usa HttpSessionOAuth2AuthorizationRequestRepository
	 * para salvar a autorização na requisição.
	 * Mas, como esse serviço não tem estado, Não é possivél salvar ele dentro da
	 * sessão. A request será salva em cookie codificado em Base64.
	 */
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	// Esse bean carrega os dados especifico do usuario  quando o formulario de login for usado
	@Override
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
		OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
		tokenResponseHttpMessageConverter.setTokenResponseConverter(new OAuth2AccessTokenResponseConverterWithDefaults());
		RestTemplate restTemplate = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
		DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
		tokenResponseClient.setRestOperations(restTemplate);
		return tokenResponseClient;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/v3/api-docs/**",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui/**",
				"/webjars/**",
				"/h2-console/**");
	}
}
