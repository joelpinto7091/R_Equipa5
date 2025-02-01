package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.TarefaTrabalho;
import com.upt.lp.rest_api.Service.TarefaTrabalhoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas-trabalho")
public class TarefaTrabalhoController {

    @Autowired
    private TarefaTrabalhoService tarefaTrabalhoService;

    @GetMapping
    public List<TarefaTrabalho> selecionarTodas() {
        return tarefaTrabalhoService.selecionarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaTrabalho> procurarPorId(@PathVariable Long id) {
        return tarefaTrabalhoService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projeto/{nomeProjeto}")
    public List<TarefaTrabalho> procurarPorNomeProjeto(@PathVariable String nomeProjeto) {
        return tarefaTrabalhoService.procurarPorNomeProjeto(nomeProjeto);
    }

    @PostMapping
    public TarefaTrabalho criar(@RequestBody TarefaTrabalho tarefaTrabalho) {
        return tarefaTrabalhoService.guardar(tarefaTrabalho);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaTrabalho> atualizar(@PathVariable Long id, @RequestBody TarefaTrabalho tarefaTrabalho) {
        if (!tarefaTrabalhoService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaTrabalho.setId(id);
        return ResponseEntity.ok(tarefaTrabalhoService.guardar(tarefaTrabalho));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!tarefaTrabalhoService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaTrabalhoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
