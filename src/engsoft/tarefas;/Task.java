package engsoft.tarefas;

// classe que representa uma tarefa da lista
public class Task {


    private final int id;


    private final String nome;


    private final String descricao;

    // status atual da tarefa
    private engsoft.tarefas.TaskStatus status;

    // quando crio a tarefa, ela começa como DISPONIVEL
    public Task(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.status = engsoft.tarefas.TaskStatus.DISPONIVEL;
    }

    public int getId() {
        return id;
    }

    public engsoft.tarefas.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(engsoft.tarefas.TaskStatus status) {
        this.status = status;
    }

    // forma como a tarefa aparece no System.out.println
    @Override
    public String toString() {
        return "[" + id + "] " + nome + " - " + descricao + " (" + status + ")";
    }
}