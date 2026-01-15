package engsoft.tarefas;

import java.util.Scanner;
import java.util.List;

/**
 * Classe que cuida do menu e da interação pelo console.
 */
public class TaskApp {
    
    private final Scanner scanner = new Scanner(System.in);
    private final engsoft.tarefas.TaskRepository repo = engsoft.tarefas.TaskRepository.getInstance();
    private final engsoft.tarefas.TaskListProcessor processor = 
        new engsoft.tarefas.TaskListProcessor(new engsoft.tarefas.ListarTodasStrategy());
    
    /**
     * Método principal da aplicação de tarefas (loop do menu)
     */
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
    
    /**
     * Mostra o menu principal na tela
     */
    private void mostrarMenu() {
        System.out.println("\n ======== LISTA DE TAREFAS ========");
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar tarefas");
        System.out.println("3 - Excluir tarefa");
        System.out.println("4 - Alterar status");
        System.out.println("0 - Sair");
        System.out.println("==================================");
    }
    
    // -------- FUNCIONALIDADES --------
    
    /**
     * Lê os dados e cria uma nova tarefa
     */
    private void adicionarTarefa() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        repo.add(nome, descricao);
        System.out.println("✓ Tarefa adicionada com status DISPONIVEL.");
    }
    
    /**
     * Menu para decidir qual estratégia usar para listar as tarefas
     */
    private void listarTarefas() {
        if (repo.getAll().isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }
        
        System.out.println("\n--- Selecione como deseja listar ---");
        System.out.println("1 - Listar TODAS as tarefas");
        System.out.println("2 - Listar por STATUS específico");
        System.out.println("3 - Listar tarefas PENDENTES");
        System.out.println("4 - Listar tarefas FEITAS");
        
        int opcao = lerInt("Opção: ");
        
        switch (opcao) {
            case 1 -> exibirComEstrategia(new engsoft.tarefas.ListarTodasStrategy());
            case 2 -> {
                TaskStatus status = lerStatus();
                exibirComEstrategia(new engsoft.tarefas.ListarPorStatusStrategy(status));
            }
            case 3 -> exibirComEstrategia(new engsoft.tarefas.ListarTarefasPendentes());
            case 4 -> exibirComEstrategia(new engsoft.tarefas.ListarTarefasFeitas());
            default -> System.out.println("Opção inválida.");
        }
    }
    
    /**
     * Aplica uma estratégia e exibe as tarefas conforme o resultado
     */
    private void exibirComEstrategia(engsoft.tarefas.TaskListStrategy estrategia) {
        processor.setStrategy(estrategia);
        List<engsoft.tarefas.Task> resultado = processor.processar(repo.getAll());
        
        System.out.println("\n--- " + processor.getEstrategiaAtual() + " ---");
        if (resultado.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com essa filtragem.");
        } else {
            for (engsoft.tarefas.Task t : resultado) {
                System.out.println(t);
            }
        }
        System.out.println();
    }
    
    /**
     * Remove uma tarefa pelo ID
     */
    private void removerTarefa() {
        int id = lerInt("ID da tarefa: ");
        boolean removida = repo.remove(id);
        if (removida) {
            System.out.println("✓ Tarefa removida.");
        } else {
            System.out.println("✗ Tarefa não encontrada.");
        }
    }
    
    /**
     * Muda o status de uma tarefa pelo ID
     */
    private void alterarStatus() {
        int id = lerInt("ID da tarefa: ");
        engsoft.tarefas.TaskStatus status = lerStatus();
        boolean atualizada = repo.updateStatus(id, status);
        if (atualizada) {
            System.out.println("✓ Status atualizado.");
        } else {
            System.out.println("✗ Tarefa não encontrada.");
        }
    }
    
    /**
     * Lê um número inteiro do usuário (com validação simples)
     */
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
    
    /**
     * Mostra os status possíveis e devolve o escolhido
     */
    private engsoft.tarefas.TaskStatus lerStatus() {
        System.out.println("Escolha o status:");
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
