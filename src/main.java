import java.util.*;

public class main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskRepository repo = TaskRepository.getInstance();

    public static void main(String[] args) {
        int opcao;

        do {
            menu();
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
    }

    private static void menu() {
        System.out.println("\n LISTA DE TAREFAS ");
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar tarefas");
        System.out.println("3 - Remover tarefa");
        System.out.println("4 - Alterar status");
        System.out.println("0 - Sair");
    }

    // FUNCIONALIDADES
    private static void adicionarTarefa() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        repo.add(nome, descricao);
        System.out.println("Tarefa adicionada com status DISPONIVEL.");
    }

    private static void listarTarefas() {
        if (repo.getAll().isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }

        System.out.println("1 - Listar todas");
        System.out.println("2 - Listar por status");
        int op = lerInt("Opção: ");

        TaskListStrategy strategy;

        if (op == 1) {
            strategy = tasks -> tasks.forEach(System.out::println);
        } else if (op == 2) {
            TaskStatus status = lerStatus();
            strategy = tasks -> tasks.stream()
                    .filter(t -> t.getStatus() == status)
                    .forEach(System.out::println);
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        strategy.list(repo.getAll());
    }

    private static void removerTarefa() {
        int id = lerInt("ID da tarefa: ");
        System.out.println(repo.remove(id)
                ? "Tarefa removida."
                : "Tarefa não encontrada.");
    }

    private static void alterarStatus() {
        int id = lerInt("ID da tarefa: ");
        TaskStatus status = lerStatus();

        System.out.println(repo.updateStatus(id, status)
                ? "Status atualizado."
                : "Tarefa não encontrada.");
    }

    //  UTILITÁRIOS
    private static int lerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Digite um número: ");
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    private static TaskStatus lerStatus() {
        System.out.println("1 - DISPONIVEL");
        System.out.println("2 - FAZENDO");
        System.out.println("3 - FEITA");

        return switch (lerInt("Opção: ")) {
            case 1 -> TaskStatus.DISPONIVEL;
            case 2 -> TaskStatus.FAZENDO;
            case 3 -> TaskStatus.FEITA;
            default -> {
                System.out.println("Opção inválida.");
                yield lerStatus();
            }
        };
    }

    // ENUM
    enum TaskStatus {
        DISPONIVEL,
        FAZENDO,
        FEITA
    }

    // TASK
    static class Task {
        private int id;
        private String nome;
        private String descricao;
        private TaskStatus status;

        public Task(int id, String nome, String descricao) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.status = TaskStatus.DISPONIVEL;
        }

        public int getId() {
            return id;
        }

        public TaskStatus getStatus() {
            return status;
        }

        public void setStatus(TaskStatus status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "[" + id + "] " + nome + " - " + descricao + " (" + status + ")";
        }
    }

    // SINGLETON
    static class TaskRepository {

        private static TaskRepository instance;
        private List<Task> tasks = new ArrayList<>();
        private int nextId = 1;

        private TaskRepository() {
        }

        public static TaskRepository getInstance() {
            if (instance == null) {
                instance = new TaskRepository();
            }
            return instance;
        }

        public void add(String nome, String descricao) {
            tasks.add(new Task(nextId++, nome, descricao));
        }

        public List<Task> getAll() {
            return tasks;
        }

        public boolean remove(int id) {
            return tasks.removeIf(t -> t.getId() == id);
        }

        public boolean updateStatus(int id, TaskStatus status) {
            for (Task t : tasks) {
                if (t.getId() == id) {
                    t.setStatus(status);
                    return true;
                }
            }
            return false;
        }
    }

    // STRATEGY
    @FunctionalInterface
    interface TaskListStrategy {
        void list(List<Task> tasks);
    }
}
