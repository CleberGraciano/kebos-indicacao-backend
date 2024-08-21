package br.com.kebos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ItemRecommendation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    @Getter
    @Setter
    private Item item;

    @Getter
    @Setter
    private int quantidade;

    @Getter
    @Setter
    private int totalBonus;
}
