# lista de Tarefas em Java

projeto de console em Java para gerenciar uma lista de tarefas.

## o que dá pra fazer

- Adicionar tarefa (nome e descrição)
- Listar tarefas
- Remover tarefa
- Mudar o status da tarefa

status possíveis:
- DISPONIVEL
- FAZENDO
- FEITA

## padrões de Projeto

- **Singleton**  
  Usado na classe `TaskRepository`.  
  Ela é a “única fonte de verdade” das tarefas no sistema.

- **Strategy**  
  Usado para listar tarefas.  
  Dá pra listar todas ou listar só por um status, trocando a estratégia.

## como rodar

1. Compile:
   javac src/Main.java
