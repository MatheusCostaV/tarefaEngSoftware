package engsoft.tarefas;

import java.util.List;

/**
 * Estratégia que lista TODAS as tarefas.
 */
public class ListarTodasStrategy implements TaskListStrategy {
    
    @Override
    public List<Task> executar(List<Task> tasks) {
        return List.copyOf(tasks);
    }
    
    @Override
    public String getDescricao() {
        return "Todas as tarefas";
    }
}
