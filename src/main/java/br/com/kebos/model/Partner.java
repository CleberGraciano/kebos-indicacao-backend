package br.com.kebos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Partner extends User  {

    private String imagem;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    private String cpf;

    @Embedded
    private Address address;

    @Embedded
    private Contact contact;

    @Embedded
    private FinanceData financeData;

    private boolean termoUso;

}
