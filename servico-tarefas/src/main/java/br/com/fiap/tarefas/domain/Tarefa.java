package br.com.fiap.tarefas.domain;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "tarefaSequence", sequenceName = "SQ_TAREFA", allocationSize = 1)
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarefaSequence")
    private Long codigo;
    @NotBlank(message = "Descricao da tarefa obrigatoria")
    @Size(min = 3, max = 50)
    private String descricao;
    private Boolean feita;
    @FutureOrPresent
    private LocalDate dataLimite;
    private LocalDate criadaEm = LocalDate.now();
    private LocalDate ultimaAtualizacao = LocalDate.now();

    public Tarefa() { }

    public Tarefa(String descricao, LocalDate dataLimite){
        this.descricao = descricao;
        this.dataLimite = dataLimite;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getFeita() {
        return feita;
    }

    public void setFeita(Boolean feita) {
        this.feita = feita;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public LocalDate getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(LocalDate criadaEm) {
        this.criadaEm = criadaEm;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}
