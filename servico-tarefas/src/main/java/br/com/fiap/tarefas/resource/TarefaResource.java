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
import java.net.URI;
import java.util.List;

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
        Tarefa tarefa = tarefaRepository.getOne(id);
        return ResponseEntity.ok(new TarefaDTO(tarefa));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TarefaDTO> salva(@RequestBody Tarefa tarefa, UriComponentsBuilder uriBuilder){
        tarefaRepository.save(tarefa);

        URI uri = uriBuilder
                .path("/tarefas/{id}")
                .buildAndExpand(tarefa.getCodigo())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new TarefaDTO(tarefa));
    }
}
