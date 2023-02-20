package br.com.kebos.dto;

import br.com.kebos.model.Item;
import br.com.kebos.model.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RecommendationDTO {

    private String nomePessoaEmpresa;
    private String cpfCnpj;
    private String emailprivate;
    private String nomeContato;
    private String telefone;
    private HashMap<Long, Integer> items;
    private String observacao;

    public static RecommendationDTO convert (Recommendation recommendation) {
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        HashMap<Long, Integer> ids = new HashMap<>();

        recommendationDTO.setNomePessoaEmpresa(recommendation.getNomePessoaEmpresa());
        recommendationDTO.setCpfCnpj(recommendation.getCpfCnpj());
        recommendationDTO.setEmailprivate(recommendation.getEmailprivate());
        recommendationDTO.setNomeContato(recommendation.getNomeContato());
        recommendationDTO.setTelefone(recommendation.getTelefone());

        for (Item item : recommendation.getItems()) {
            ids.put(item.getId(), item.getQuantidade());
        }
        recommendationDTO.setItems(ids);
        recommendationDTO.setObservacao(recommendation.getObservacao());

        return recommendationDTO;
    }

}
