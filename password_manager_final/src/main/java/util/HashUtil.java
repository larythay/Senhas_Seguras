package util;

import org.mindrot.jbcrypt.BCrypt;

/* Classe utilitária para hash e verificação de senhas usando BCrypt. */
public class HashUtil {
    /**
     * Gera o hash de uma senha usando BCrypt.
     * @param password Senha em texto puro
     * @return Hash gerado
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifica se a senha corresponde ao hash.
     * @param password Senha em texto puro
     * @param hash Hash armazenado
     * @return true se a senha corresponder ao hash, false caso contrário
     */
    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
