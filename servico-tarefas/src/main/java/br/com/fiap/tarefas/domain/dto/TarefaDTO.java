package br.com.fiap.tarefas.domain.dto;

import br.com.fiap.tarefas.domain.Tarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaDTO {

    private Long codigo;
    private String descricao;
    private Boolean feita;
    private LocalDate dataLimite;

    public TarefaDTO(Tarefa tarefa){
        this.codigo = tarefa.getCodigo();
        this.descricao = tarefa.getDescricao();
        this.feita = tarefa.getFeita();
        this.dataLimite= tarefa.getDataLimite();
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getFeita() {
        return feita;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public static  List<TarefaDTO> converter(List<Tarefa> tarefas){
        return tarefas.stream()
                .map(TarefaDTO::new)
                .collect(Collectors.toList());
    }
}
