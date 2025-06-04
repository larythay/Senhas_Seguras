
package model;

import util.CryptoUtil;

import java.io.Serializable;

/**
 * Classe que representa uma credencial de acesso a um serviço.
 * Armazena o nome do serviço, nome de usuário e a senha criptografada.
 * Implementa Serializable para permitir persistência em arquivo.
 */

public class Credential implements Serializable {
    private String service;
    private String username;
    private String encryptedPassword;

    /* Construtor da credencial */
    public Credential(String service, String username, String password) {
        this.service = service;
        this.username = username;
        this.encryptedPassword = CryptoUtil.encrypt(password);
    }

    /* Getters para acessar os atributos da credencial */
    public String getService() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getDecryptedPassword() {
        return CryptoUtil.decrypt(encryptedPassword);
    }

    /**
        Retorna uma representação em texto da credencial,  mostrando o serviço, usuário e a senha descriptografada.
     */

    @Override
    public String toString() {
        return "Serviço: " + service + ", Usuário: " + username + ", Senha: " + getDecryptedPassword();
    }
}
