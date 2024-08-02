package br.com.kebos.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EmailModel {
    private static final long serialVersionUID = 1L;

    private UUID emailId;
    private String destinatario;
    private String assunto;
    private String mensagem;
    private LocalDateTime dataEnvioEmail;
    private StatusEmail statusEmail;
}
