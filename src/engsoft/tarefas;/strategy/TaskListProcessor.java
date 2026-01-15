package engsoft.tarefas;

import java.util.List;

/**
 * Classe que aplica diferentes estratégias de listagem de tarefas.
 * Usa o padrão Strategy para permitir múltiplas formas de filtrar/exibir tarefas.
 */
public class TaskListProcessor {
    
    private TaskListStrategy strategy;
    
    /**
     * Construtor que recebe a estratégia
     */
    public TaskListProcessor(TaskListStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Define uma nova estratégia (permite trocar em tempo de execução)
     */
    public void setStrategy(TaskListStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Processa a lista usando a estratégia atual
     */
    public List<Task> processar(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return List.of();
        }
        return strategy.executar(tasks);
    }
    
    /**
     * Retorna a descrição da estratégia atual
     */
    public String getEstrategiaAtual() {
        return strategy.getDescricao();
    }
}
