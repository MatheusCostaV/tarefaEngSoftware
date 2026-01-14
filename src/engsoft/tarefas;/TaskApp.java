package engsoft.tarefas;

import java.util.Scanner;

// classe que cuida do menu e da interação pelo console
public class TaskApp {

    private final Scanner scanner = new Scanner(System.in);
    // uso o repositório para guardar e acessar as tarefas
    private final engsoft.tarefas.TaskRepository repo = engsoft.tarefas.TaskRepository.getInstance();

    // método principal da aplicação de tarefas (loop do menu)
    public void executar() {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> adicionarTarefa();
                case 2 -> listarTarefas();
                case 3 -> removerTarefa();
                case 4 -> alterarStatus();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    // mostra o menu na tela
    private void mostrarMenu() {
        System.out.println("\n LISTA DE TAREFAS ");
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar tarefas");
        System.out.println("3 - Excluir tarefa");
        System.out.println("4 - Alterar status");
        System.out.println("0 - Sair");
    }

    // -------- funcionalidades --------

    // lê os dados e cria uma nova tarefa
    private void adicionarTarefa() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        repo.add(nome, descricao);
        System.out.println("Tarefa adicionada com status DISPONIVEL.");
    }

    // decide se vai listar todas ou filtrar por status
    private void listarTarefas() {
        if (repo.getAll().isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }

        System.out.println("1 - Listar todas");
        System.out.println("2 - Listar por status");
        int op = lerInt("Opção: ");

        if (op == 1) {
            listarTodas();
        } else if (op == 2) {
            listarPorStatus();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // lista todas as tarefas cadastradas
    private void listarTodas() {
        for (engsoft.tarefas.Task t : repo.getAll()) {
            System.out.println(t);
        }
    }

    // lista só as tarefas com o status escolhido
    private void listarPorStatus() {
        engsoft.tarefas.TaskStatus status = lerStatus();
        for (engsoft.tarefas.Task t : repo.getAll()) {
            if (t.getStatus() == status) {
                System.out.println(t);
            }
        }
    }

    // remove uma tarefa pelo id
    private void removerTarefa() {
        int id = lerInt("ID da tarefa: ");
        boolean removida = repo.remove(id);

        if (removida) {
            System.out.println("Tarefa removida.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    // muda o status de uma tarefa pelo id
    private void alterarStatus() {
        int id = lerInt("ID da tarefa: ");
        engsoft.tarefas.TaskStatus status = lerStatus();

        boolean atualizada = repo.updateStatus(id, status);

        if (atualizada) {
            System.out.println("Status atualizado.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }


    // lê um número inteiro do usuário (com validação simples)
    private int lerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            scanner.next(); // descarta o que não é número
            System.out.print("Digite um número: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpa o \n que sobrou
        return valor;
    }

    // mostra os status possíveis e devolve o escolhido
    private engsoft.tarefas.TaskStatus lerStatus() {
        System.out.println("1 - DISPONIVEL");
        System.out.println("2 - FAZENDO");
        System.out.println("3 - FEITA");

        int opcao = lerInt("Opção: ");

        return switch (opcao) {
            case 1 -> engsoft.tarefas.TaskStatus.DISPONIVEL;
            case 2 -> engsoft.tarefas.TaskStatus.FAZENDO;
            case 3 -> engsoft.tarefas.TaskStatus.FEITA;
            default -> {
                System.out.println("Opção inválida, tente de novo.");
                yield lerStatus(); // chama de novo até o usuário acertar
            }
        };
    }
}