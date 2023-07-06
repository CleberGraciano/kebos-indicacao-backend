package br.com.kebos.model;

public enum StatusRecommendationEnum {

    ENVIADO("enviado"),
    NEGOCIANDO ("negociando"),
    VENDIDO ("vendido"),
    ENCERRADO ("encerrado"),
    CANCELADO ("cancelado");

    private String value;

    StatusRecommendationEnum(String value) {
        this.value = value;
    }
}
