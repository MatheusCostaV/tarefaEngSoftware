package engsoft.tarefas;

import java.util.List;

/**
 * Estratégia que lista tarefas filtrando por um status específico.
 */
public class ListarPorStatusStrategy implements TaskListStrategy {
    
    private final TaskStatus statusFiltro;
    
    public ListarPorStatusStrategy(TaskStatus statusFiltro) {
        this.statusFiltro = statusFiltro;
    }
    
    @Override
    public List<Task> executar(List<Task> tasks) {
        return tasks.stream()
                .filter(t -> t.getStatus() == statusFiltro)
                .toList();
    }
    
    @Override
    public String getDescricao() {
        return "Tarefas com status: " + statusFiltro;
    }
}
