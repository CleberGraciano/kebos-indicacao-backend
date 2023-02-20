package br.com.kebos.model;


import javax.persistence.Embeddable;

@Embeddable
public class FinanceData {

    private String pix;

    private String banco;

    private TipoContaEnum  tipoContaEnum;

    private String agencia;

    private String conta;

    private String digito;
}
