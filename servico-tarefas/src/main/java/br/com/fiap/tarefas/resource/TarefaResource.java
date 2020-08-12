package br.com.fiap.tarefas.resource;


import br.com.fiap.tarefas.domain.Tarefa;
import br.com.fiap.tarefas.domain.dto.TarefaDTO;
import br.com.fiap.tarefas.repository.TarefaRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tarefas")
public class TarefaResource {

    @Autowired
    TarefaRepository tarefaRepository;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TarefaDTO> lista(String descricao){
        List<Tarefa> tarefas = descricao == null ?
                tarefaRepository.findAll() :
                tarefaRepository.findByDescricaoContaining(descricao);
        return TarefaDTO.converter(tarefas);
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaDTO> detalhe(@PathVariable Long id){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa
                .map(t -> ResponseEntity.ok(new TarefaDTO(t)))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @Transactional
    public ResponseEntity<TarefaDTO> salva(@RequestBody @Valid Tarefa tarefa, UriComponentsBuilder uriBuilder){
        tarefaRepository.save(tarefa);

        URI uri = uriBuilder
                .path("/tarefas/{id}")
                .buildAndExpand(tarefa.getCodigo())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new TarefaDTO(tarefa));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<TarefaDTO> atualiza(
        @PathVariable Long id,
        @RequestBody @Valid Tarefa tarefaAtualizaa
    ){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa
                .map(t -> {
                    t.setDescricao(tarefaAtualizaa.getDescricao());
                    t.setFeita(tarefaAtualizaa.getFeita());
                    t.setDataLimite(tarefaAtualizaa.getDataLimite());
                    t.setUltimaAtualizacao(LocalDate.now());
                    tarefaRepository.save(t);
                    return ResponseEntity.ok(new TarefaDTO(t));
                })
                    .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa
                .map(t -> {
                    tarefaRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
