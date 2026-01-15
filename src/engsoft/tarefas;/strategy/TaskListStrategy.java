package engsoft.tarefas;

import java.util.List;

/**
 * Interface Strategy para diferentes formas de listar tarefas.
 * Define o contrato que todas as estratégias devem implementar.
 */
public interface TaskListStrategy {
    
    /**
     * Executa a estratégia de listagem
     * @param tasks lista de todas as tarefas
     * @return lista filtrada/ordenada conforme a estratégia
     */
    List<Task> executar(List<Task> tasks);
    
    /**
     * Retorna uma descrição da estratégia
     */
    String getDescricao();
}