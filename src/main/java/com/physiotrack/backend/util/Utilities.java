package com.physiotrack.backend.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Class responsible for random utilities
 */
@Component
public class Utilities {

    public String gerarSenhaAleatoria(int tamanho) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < tamanho; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

}
