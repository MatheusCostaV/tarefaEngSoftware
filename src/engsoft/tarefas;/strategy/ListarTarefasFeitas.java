package engsoft.tarefas;

import java.util.List;

/**
 * Estratégia que lista apenas as tarefas FEITAS.
 */
public class ListarTarefasFeitas implements TaskListStrategy {
    
    @Override
    public List<Task> executar(List<Task> tasks) {
        return tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.FEITA)
                .toList();
    }
    
    @Override
    public String getDescricao() {
        return "Tarefas já concluídas";
    }
}
