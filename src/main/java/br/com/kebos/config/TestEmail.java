package br.com.kebos.config;

import br.com.kebos.dto.EmailDto;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.security.SecureRandom;

public class TestEmail {
    public static void main(String[] args) {
        // Conjunto de caracteres que inclui dígitos e letras (maiúsculas e minúsculas)
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(6);

        // Gera uma senha de 6 caracteres
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        System.out.println("Senha gerada: " + password.toString());
    }
}
