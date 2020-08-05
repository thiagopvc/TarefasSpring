package br.com.fiap.tarefas.repository;

import br.com.fiap.tarefas.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByDescricaoContaining(String descricao);
}
