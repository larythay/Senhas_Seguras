package util;

import com.warrenstrange.googleauth.GoogleAuthenticator;

/* Classe utilitária para operações com TOTP (Time-based One-Time Password). */
public class TOTPUtil {
    /**
     * Verifica se o código TOTP informado é válido para a chave secreta.
     * @param secret Chave secreta TOTP
     * @param codeStr Código informado pelo usuário
     * @return true se o código for válido, false caso contrário
     */
    public static boolean verifyCode(String secret, String codeStr) {
        try {
            int code = Integer.parseInt(codeStr);
            GoogleAuthenticator gAuth = new GoogleAuthenticator();
            return gAuth.authorize(secret, code);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Exemplo opcional: gerar uma nova chave secreta
    public static String generateSecret() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.createCredentials().getKey();
    }
}