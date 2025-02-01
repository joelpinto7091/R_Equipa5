package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.TarefaPessoal;
import com.upt.lp.rest_api.Service.TarefaPessoalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas-pessoais")
public class TarefaPessoalController {

    @Autowired
    private TarefaPessoalService tarefaPessoalService;

    @GetMapping
    public List<TarefaPessoal> selecionarTodas() {
        return tarefaPessoalService.selecionarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaPessoal> procurarPorId(@PathVariable Long id) {
        return tarefaPessoalService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/localizacao/{localizacao}")
    public List<TarefaPessoal> procurarPorLocalizacao(@PathVariable String localizacao) {
        return tarefaPessoalService.procurarPorLocalizacao(localizacao);
    }

    @PostMapping
    public TarefaPessoal criar(@RequestBody TarefaPessoal tarefaPessoal) {
        return tarefaPessoalService.guardar(tarefaPessoal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaPessoal> atualizar(@PathVariable Long id, @RequestBody TarefaPessoal tarefaPessoal) {
        if (!tarefaPessoalService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaPessoal.setId(id);
        return ResponseEntity.ok(tarefaPessoalService.guardar(tarefaPessoal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!tarefaPessoalService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaPessoalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
