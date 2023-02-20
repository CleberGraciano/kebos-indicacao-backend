package br.com.kebos.model;

public enum TipoContaEnum {

    CORRENTE("conta corrente"), POUPANCA ("conta poupanca");


    private String value;

    TipoContaEnum(String value) {
        this.value = value;
    }

}
