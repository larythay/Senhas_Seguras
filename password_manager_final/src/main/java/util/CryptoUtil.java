
package util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/* Classe utilitária para criptografia e descriptografia de dados usando AES. */
public class CryptoUtil {

    private static final String ALGORITHM = "AES";
    private static final byte[] SECRET = "MySuperSecretKey".getBytes(); // 16 bytes = 128 bits

    /**
     * Criptografa uma string usando AES.
     *
     * @param data A string a ser criptografada.
     * @return A string criptografada em Base64.
     */

    public static String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar", e);
        }
    }

    /**
     * Descriptografa uma string criptografada em Base64 usando AES.
     * @param encryptedData Texto criptografado em Base64
     * @return Texto puro descriptografado
     */
    
    public static String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar", e);
        }
    }
}
