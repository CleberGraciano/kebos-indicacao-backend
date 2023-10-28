package br.com.kebos.model;

import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.dto.SellerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
    private String email;
    private String nomeContato;
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    private Seller seller;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "itemRecommendation_id")
    private List<ItemRecommendation> itemRecommendations;

    private double valortotal ;
    private String observacao ;
    private StatusRecommendationEnum status;



    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;


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
        recommendation.setEmail(recommendationDTO.getEmail());
        recommendation.setNomeContato(recommendationDTO.getNomeContato());
        recommendation.setTelefone(recommendationDTO.getTelefone());
        recommendation.setItemRecommendations(recommendationDTO.getItemRecommendations());
        recommendation.setObservacao(recommendationDTO.getObservacao());
        recommendation.setSeller(recommendationDTO.getSeller());
        recommendation.setValortotal(recommendationDTO.getValortotal());

        return recommendation;
    }

}
