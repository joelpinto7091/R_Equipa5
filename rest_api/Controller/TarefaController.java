package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.Tarefa;
import com.upt.lp.rest_api.Service.TarefaService;
import com.upt.lp.rest_api.Repository.TarefaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    public List<Tarefa> selecionarTodas() {
        return tarefaService.selecionarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> procurarPorId(@PathVariable Long id) {
        return tarefaService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilizador/{userId}")
    public List<Tarefa> procurarPorUtilizador(@PathVariable Long userId) {
        return tarefaService.procurarPorUtilizador(userId);
    }

    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return tarefaService.guardar(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        if (!tarefaService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefa.setId(id);
        return ResponseEntity.ok(tarefaService.guardar(tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!tarefaService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/concluir")
    public Tarefa marcarComoConcluida(@PathVariable Long id) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setEstado("concluida");
            return tarefaRepository.save(tarefa);
        }
        return null;
    }

    @GetMapping("/pendentes")
    public List<Tarefa> listarPendentes() {
        return tarefaRepository.findAll().stream()
                .filter(tarefa -> "pendente".equals(tarefa.getEstado()))
                .collect(Collectors.toList());
    }

    @GetMapping("/concluidas")
    public List<Tarefa> listarConcluidas() {
        return tarefaRepository.findAll().stream()
                .filter(tarefa -> "concluida".equals(tarefa.getEstado()))
                .collect(Collectors.toList());
    }
}

