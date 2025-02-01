package com.upt.lp.rest_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upt.lp.rest_api.Model.Utilizador;
import com.upt.lp.rest_api.Service.UtilizadorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilizadores")
public class UtilizadorController {

    @Autowired
    private UtilizadorService utilizadorService;

    @GetMapping
    public List<Utilizador> selecionarTodos() {
        return utilizadorService.selecionarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilizador> procurarPorId(@PathVariable Long id) {
        return utilizadorService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilizador criar(@RequestBody Utilizador utilizador) {
        return utilizadorService.guardar(utilizador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilizador> atualizar(@PathVariable Long id, @RequestBody Utilizador utilizador) {
        if (!utilizadorService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        utilizador.setId(id);
        return ResponseEntity.ok(utilizadorService.guardar(utilizador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!utilizadorService.procurarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        utilizadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

