package br.com.kebos.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class User implements Serializable {

	private static final long serialVersionUID = 65981149772133526L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "PROVIDER_USER_ID")
	private String providerUserId;

	private String email;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	private boolean enabled;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@JsonIgnore
	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String provider;

	@JsonIgnore
	private boolean statusCadastro;

	//Bi-direcional associação muitos para muitos para Role
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;


	private String imagem;

	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate dataNascimento;

	private String cpf;

	private String cep;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;


	private String celular;
	private String foneFixo;
	private String foneComercial;


	private String pix;

	private String banco;

	private TipoContaEnum  tipoContaEnum;

	private String agencia;

	private String conta;

	private String digito;

	private boolean termoUso;
}