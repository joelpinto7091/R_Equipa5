package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.Tarefa;
import com.upt.lp.rest_api.Service.TarefaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

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
}

