package br.com.kebos.dto;

import lombok.Data;

@Data
public class EmailDto {

    private String destinatario;
    private String assunto;
    private String mensagem;

    public EmailDto(String destinatario, String assunto, String mensagem) {
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }
}
