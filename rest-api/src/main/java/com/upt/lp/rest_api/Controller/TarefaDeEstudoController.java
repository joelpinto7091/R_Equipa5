package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.TarefaDeEstudo;
import com.upt.lp.rest_api.Service.TarefaDeEstudoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas-de-estudo")
public class TarefaDeEstudoController {

    @Autowired
    private TarefaDeEstudoService tarefaDeEstudoService;

    @GetMapping
    public List<TarefaDeEstudo> selecionarTodas() {
        return tarefaDeEstudoService.selecionarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDeEstudo> procurarPorId(@PathVariable Long id) {
        return tarefaDeEstudoService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disciplina/{disciplina}")
    public List<TarefaDeEstudo> procurarPorDisciplina(@PathVariable String disciplina) {
        return tarefaDeEstudoService.procurarPorDisciplina(disciplina);
    }

    @PostMapping
    public TarefaDeEstudo criar(@RequestBody TarefaDeEstudo tarefaDeEstudo) {
        return tarefaDeEstudoService.guardar(tarefaDeEstudo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDeEstudo> atualizar(@PathVariable Long id, @RequestBody TarefaDeEstudo tarefaDeEstudo) {
        if (!tarefaDeEstudoService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaDeEstudo.setId(id);
        return ResponseEntity.ok(tarefaDeEstudoService.guardar(tarefaDeEstudo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!tarefaDeEstudoService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaDeEstudoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}