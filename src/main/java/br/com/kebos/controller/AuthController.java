package br.com.kebos.controller;

import javax.validation.Valid;

import br.com.kebos.exception.UserAlreadyExistAuthenticationException;
import br.com.kebos.model.User;
import br.com.kebos.security.jwt.TokenProvider;
import br.com.kebos.service.UserService;
import br.com.kebos.util.GeneralUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import br.com.kebos.dto.ApiResponse;
import br.com.kebos.dto.JwtAuthenticationResponse;
import br.com.kebos.dto.LocalUser;
import br.com.kebos.dto.LoginRequest;
import br.com.kebos.dto.SignUpRequest;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@SecurityRequirement(name = "kebosapi")
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	TokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);
		LocalUser localUser = (LocalUser) authentication.getPrincipal();
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			userService.registerNewUser(signUpRequest);
		} catch (UserAlreadyExistAuthenticationException e) {
			log.error("Exceção ocorrida ", e);
			return new ResponseEntity<>(new ApiResponse(false, "Endereço de email já está em uso!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(new ApiResponse(true, "Usuário registrado com sucesso"));
	}
	@PutMapping("/partner/{id}")
	public ResponseEntity<?> savePartner(@PathVariable(name = "id") Long id, @RequestBody User partner){
		try {
			userService.updatePartner(id, partner);
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok().body(partner);
	}

	@GetMapping("/partnes")
	public List<User> listPartner(){
		return userService.listPartner();
	}


	@GetMapping("/partner/{id}")
	public User getPartnerById(@PathVariable(name = "id") long id){
		return userService.getPartnerById(id);
	}
}