package br.com.kebos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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
    private String nomePessoaEmpresa ;
    private String cpfCnpj;
    private String emailprivate;
    private String nomeContato  ;
    private String telefone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<Item> items  ;

    private String valortotal ;
    private String observacao ;
    private String status;

    public void addItem(Item item){
        this.items.add(item);
    }
}
