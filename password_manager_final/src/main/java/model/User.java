package model;


import java.util.ArrayList;
import java.util.List;

/**
  Classe que representa um usuário do sistema.
  Armazena nome de usuário, hash da senha, chave secreta TOTP e lista de credenciais.
 */

public class User {
    private String username;
    private String passwordHash;
    private String secretKey; // chave para TOTP
    private List<Credential> credentials;

    /* Construtor do usuário. */

    public User(String username, String passwordHash, String secretKey) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.secretKey = secretKey;
        this.credentials = new ArrayList<>();
    }

    /* Getters para acessar os atributos do usuário. */
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    /* Adiciona uma nova credencial à lista de credenciais do usuário. */
    public void addCredential(Credential credential) {
        credentials.add(credential);
    }

    /**
     * Remove uma credencial da lista de credenciais do usuário com base no nome do serviço.
     * Se houver múltiplas credenciais com o mesmo serviço, remove todas.
     */
    public void removeCredential(String service) {
        credentials.removeIf(c -> c.getService().equalsIgnoreCase(service));
    }

    /*
        Busca uma credencial pelo nome do serviço. 
        Retorna a primeira credencial encontrada ou null se não houver.
     */

    public Credential getCredential(String service) {
        return credentials.stream()
                .filter(c -> c.getService().equalsIgnoreCase(service))
                .findFirst()
                .orElse(null);
    }
}
