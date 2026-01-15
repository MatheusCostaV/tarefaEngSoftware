package engsoft.tarefas;

import java.util.List;

/**
 * Estratégia que lista apenas as tarefas PENDENTES (DISPONÍVEL ou FAZENDO).
 */
public class ListarTarefasPendentes implements TaskListStrategy {
    
    @Override
    public List<Task> executar(List<Task> tasks) {
        return tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DISPONIVEL || 
                            t.getStatus() == TaskStatus.FAZENDO)
                .toList();
    }
    
    @Override
    public String getDescricao() {
        return "Tarefas pendentes (não concluídas)";
    }
}
