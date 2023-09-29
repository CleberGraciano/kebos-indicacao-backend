package br.com.kebos.dto;

import br.com.kebos.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RecommendationDTO {

    private String nomePessoaEmpresa;
    private String cpfCnpj;
    private String email;
    private String nomeContato;
    private String telefone;
    private HashMap<Long, Integer> items;
    private String observacao;
    private Seller seller;

}
