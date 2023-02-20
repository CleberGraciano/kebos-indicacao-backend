package br.com.kebos.model;


import javax.persistence.Embeddable;


@Embeddable
public class Address {

    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

}
