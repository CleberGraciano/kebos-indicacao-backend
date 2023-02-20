package br.com.kebos.model;

import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.dto.SellerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nomePessoaEmpresa;
    private String cpfCnpj;
    private String emailprivate;
    private String nomeContato;
    private String telefone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<Item> items  ;

    private double valortotal ;
    private String observacao ;
    private String status;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public static Seller convert(SellerDTO sellerDTO) {
        Seller seller = new Seller();

        seller.setNome(sellerDTO.getNome());
        seller.setEmail(sellerDTO.getEmail());
        seller.setTelefone(sellerDTO.getTelefone());

        return seller;
    }

    public static  Recommendation convert (RecommendationDTO recommendationDTO) {
        Recommendation recommendation = new Recommendation();

        recommendation.setNomePessoaEmpresa(recommendationDTO.getNomePessoaEmpresa());
        recommendation.setCpfCnpj(recommendationDTO.getCpfCnpj());
        recommendation.setEmailprivate(recommendationDTO.getEmailprivate());
        recommendation.setNomeContato(recommendationDTO.getNomeContato());
        recommendation.setTelefone(recommendationDTO.getTelefone());
        recommendation.setObservacao(recommendationDTO.getObservacao());

        return recommendation;
    }

}
