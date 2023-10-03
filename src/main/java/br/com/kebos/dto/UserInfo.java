package br.com.kebos.dto;

import java.util.List;

import br.com.kebos.model.User;
import lombok.Value;

@Value
public class UserInfo {
	private String id, displayName, email;
	private boolean statusCadastro;
	private List<String> roles;

}

