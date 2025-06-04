package util;

import java.security.SecureRandom;

/* Classe utilitária para geração de senhas seguras aleatórias. */
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final SecureRandom random = new SecureRandom();

    /**
     * Gera uma senha aleatória com o comprimento especificado.
     * @param length Comprimento da senha
     * @return Senha gerada
     */
    
    public static String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
