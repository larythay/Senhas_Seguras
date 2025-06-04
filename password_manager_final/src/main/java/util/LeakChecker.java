package util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

/* Classe utilitária para verificar se uma senha já foi vazada usando a API do Have I Been Pwned. */

public class LeakChecker {
    /* Verifica se a senha fornecida já foi vazada.
     Utiliza a API do Have I Been Pwned para verificar se a senha SHA-1 já foi exposta. */
    public static boolean isPasswordLeaked(String password) {
        try {
            String sha1 = sha1Hex(password).toUpperCase();
            String prefix = sha1.substring(0, 5);
            String suffix = sha1.substring(5);

            URL url = new URL("https://api.pwnedpasswords.com/range/" + prefix);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith(suffix)) {
                    in.close();
                    return true;
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Erro ao verificar vazamento: " + e.getMessage());
        }
        return false;
    }

    /* Calcula o hash SHA-1 de uma string e retorna em formato hexadecimal. */
    private static String sha1Hex(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = md.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
