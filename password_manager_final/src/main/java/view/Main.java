package view;

import model.Credential;
import util.*;

import java.util.*;

/**
 * Classe principal do sistema de Gerenciador de Senhas.
 * Responsável pela interface de linha de comando, autenticação inicial e menu de operações.
 */

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Credential> credentials = new ArrayList<>();
    private static boolean offlineMode = false;

    /* Método principal. Realiza autenticação 2FA e exibe o menu principal. */
    public static void main(String[] args) {
        // Autenticação TOTP (2FA)
        String secret = "JBSWY3DPEHPK3PXP";
        System.out.println("Digite a seguinte chave no Google Authenticator: JBSWY3DPEHPK3PXP");
        System.out.print("Agora digite o código correspondente: ");
        String code = scanner.nextLine();

        // Verifica o código TOTP informado
        if (!TOTPUtil.verifyCode(secret, code)) {
            System.out.println("Código 2FA inválido.");
            return;
        }

        System.out.println("Autenticação bem-sucedida!");
        System.out.println("Bem-vindo ao Gerenciador de Senhas");
        System.out.print("Deseja iniciar em modo offline? (s/n): ");
        offlineMode = scanner.nextLine().trim().equalsIgnoreCase("s");

        // Loop principal do menu
        while (true) {
            exibirMenu();

            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    adicionarSenha();
                    break;
                case "2":
                    listarSenhas();
                    break;
                case "3":
                    gerarSenhaSegura();
                    break;
                case "4":
                    if (offlineMode) {
                        sair();
                    } else {
                        verificarVazamentoSenha();
                    }
                    break;
                case "5":
                    if (!offlineMode) {
                        sair();
                        break;
                    }
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    /* Exibe o menu principal com as opções disponíveis. */
    private static void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Adicionar senha");
        System.out.println("2. Listar senhas");
        System.out.println("3. Gerar senha segura");
        if (!offlineMode) {
            System.out.println("4. Verificar vazamento de senha");
            System.out.println("5. Sair");
        } else {
            System.out.println("4. Sair");
        }
    }

    /* Adiciona uma nova credencial à lista. */
    private static void adicionarSenha() {
        System.out.print("Nome do serviço: ");
        String service = scanner.nextLine();
        System.out.print("Nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        credentials.add(new Credential(service, username, password));
        System.out.println("Senha adicionada com sucesso.");
    }

    /* Lista todas as credenciais cadastradas. */
    private static void listarSenhas() {
        if (credentials.isEmpty()) {
            System.out.println("Nenhuma credencial cadastrada.");
        } else {
            for (Credential c : credentials) {
                System.out.println("Serviço: " + c.getService());
                System.out.println("Usuário: " + c.getUsername());
                System.out.println("Senha: " + c.getEncryptedPassword());
                System.out.println("-----------------------------");
            }
        }
    }

    /* Gera e exibe uma senha segura aleatória. */
    private static void gerarSenhaSegura() {
        System.out.print("Comprimento da senha: ");
        int length = Integer.parseInt(scanner.nextLine());
        String senhaGerada = PasswordGenerator.generate(length);
        System.out.println("Senha gerada: " + senhaGerada);
    }

    /* Verifica se uma senha foi vazada usando a API do Have I Been Pwned. */
    private static void verificarVazamentoSenha() {
        System.out.print("Digite a senha a ser verificada: ");
        String senha = scanner.nextLine();

        boolean vazou = LeakChecker.isPasswordLeaked(senha);
        if (vazou) {
            System.out.println("Atenção: esta senha já foi vazada anteriormente!");
        } else {
            System.out.println("Senha segura (não encontrada em vazamentos conhecidos).");
        }
    }

    /* Encerra o programa. */
    private static void sair() {
        System.out.println("Saindo do sistema...");
        System.exit(0);
    }
}