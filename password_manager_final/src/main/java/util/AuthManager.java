package util;

import model.User;

import java.util.HashMap;
import java.util.Map;

/* Classe responsável pelo gerenciamento de autenticação e registro de usuários. */
public class AuthManager {
    private Map<String, User> users = new HashMap<>();

    /**
     * Registra um novo usuário no sistema.
     * @param username Nome de usuário
     * @param password Senha em texto puro
     * @return true se o registro foi bem-sucedido, false se o usuário já existe
     */
    public boolean register(String username, String password) {
        if (users.containsKey(username)) return false;
        String hash = HashUtil.hashPassword(password);
        String secret = TOTPUtil.generateSecret();
        users.put(username, new User(username, hash, secret));
        return true;
    }
    
    /**
     * Realiza o login do usuário, verificando senha e código TOTP.
     * @param username Nome de usuário
     * @param password Senha em texto puro
     * @param totpCode Código TOTP informado
     * @return Usuário autenticado ou null se falhar
     */
    public User login(String username, String password, String totpCode) {
        User user = users.get(username);
        if (user != null &&
            HashUtil.verifyPassword(password, user.getPasswordHash()) &&
            TOTPUtil.verifyCode(user.getSecretKey(), totpCode)) {
            return user;
        }
        return null;
    }
}
