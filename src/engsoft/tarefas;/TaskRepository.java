package engsoft.tarefas;

import java.util.ArrayList;
import java.util.List;

// classe que guarda e gerencia a lista de tarefas (tipo um "banco" simples em memória)
public class TaskRepository {

    // instância única (singleton)
    private static TaskRepository instance;

    // lista com todas as tarefas
    private final List<engsoft.tarefas.Task> tasks = new ArrayList<>();

    // próximo id a ser usado
    private int nextId = 1;

    // construtor privado para ninguém criar direto com new
    private TaskRepository() {
    }

    // retorna a instância única do repositório
    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    // adiciona uma nova tarefa usando um id automático
    public void add(String nome, String descricao) {
        tasks.add(new engsoft.tarefas.Task(nextId++, nome, descricao));
    }

    // devolve a lista de todas as tarefas
    public List<engsoft.tarefas.Task> getAll() {
        return tasks;
    }

    // remove a tarefa com o id informado
    public boolean remove(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    // atualiza o status da tarefa com o id informado
    public boolean updateStatus(int id, engsoft.tarefas.TaskStatus status) {
        for (engsoft.tarefas.Task t : tasks) {
            if (t.getId() == id) {
                t.setStatus(status);
                return true;
            }
        }
        return false;
    }
}