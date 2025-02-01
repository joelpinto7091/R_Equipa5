package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.TarefaDeCompras;
import com.upt.lp.rest_api.Service.TarefaDeComprasService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas-de-compras")
public class TarefaDeComprasController {

    @Autowired
    private TarefaDeComprasService tarefaDeComprasService;

    @GetMapping
    public List<TarefaDeCompras> selecionarTodas() {
        return tarefaDeComprasService.selecionarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDeCompras> procurarPorId(@PathVariable Long id) {
        return tarefaDeComprasService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/loja/{loja}")
    public List<TarefaDeCompras> procurarPorLoja(@PathVariable String loja) {
        return tarefaDeComprasService.procurarPorLoja(loja);
    }

    @PostMapping
    public TarefaDeCompras criar(@RequestBody TarefaDeCompras tarefaDeCompras) {
        return tarefaDeComprasService.guardar(tarefaDeCompras);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDeCompras> atualizar(@PathVariable Long id, @RequestBody TarefaDeCompras tarefaDeCompras) {
        if (!tarefaDeComprasService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaDeCompras.setId(id);
        return ResponseEntity.ok(tarefaDeComprasService.guardar(tarefaDeCompras));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!tarefaDeComprasService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarefaDeComprasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

