package br.com.kebos.model;

import br.com.kebos.dto.SellerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    @OneToOne
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate created;

    public static Seller convert(SellerDTO sellerDTO) {
        Seller seller = new Seller();

        seller.setNome(sellerDTO.getNome());
        seller.setEmail(sellerDTO.getEmail());
        seller.setTelefone(sellerDTO.getTelefone());

        return seller;
    }



}
