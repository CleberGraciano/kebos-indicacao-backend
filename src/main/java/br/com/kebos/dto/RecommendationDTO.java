package br.com.kebos.dto;

import br.com.kebos.model.Item;
import br.com.kebos.model.ItemRecommendation;
import br.com.kebos.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
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
    private List<ItemRecommendation> itemRecommendations;
    private String observacao;
    private Seller seller;
    private double valortotal;




}
