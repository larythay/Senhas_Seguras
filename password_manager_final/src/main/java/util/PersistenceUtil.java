
package util;

import model.Credential;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.List;

/* Classe utilitária para persistência de credenciais em arquivo criptografado. */
public class PersistenceUtil {

    private static final String FILE_PATH = "credentials.dat";
    private static final String SECRET_KEY = "MySuperSecretKey"; // deve ter 16 chars (128 bits)

    /* Retorna a chave secreta para criptografia AES. */
    private static SecretKeySpec getSecretKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    }

    /**
     * Salva a lista de credenciais em arquivo, criptografando os dados.
     * @param credentials Lista de credenciais a serem salvas
     */
    public static void saveCredentials(List<Credential> credentials) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(credentials);
            byte[] serializedData = baos.toByteArray();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encryptedData = cipher.doFinal(serializedData);

            String base64Data = Base64.getEncoder().encodeToString(encryptedData);
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(base64Data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de credenciais do arquivo, descriptografando os dados.
     * @return Lista de credenciais carregadas
     */
    public static List<Credential> loadCredentials() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new java.util.ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String base64Data = reader.readLine();
            byte[] encryptedData = Base64.getDecoder().decode(base64Data);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] serializedData = cipher.doFinal(encryptedData);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return (List<Credential>) ois.readObject();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}
