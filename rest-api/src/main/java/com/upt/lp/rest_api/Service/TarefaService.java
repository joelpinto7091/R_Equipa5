package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.Tarefa;
import com.upt.lp.rest_api.Repository.TarefaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> selecionarTodas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> procurarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public List<Tarefa> procurarPorUtilizador(Long userId) {
        return tarefaRepository.findByUtilizadorId(userId);
    }

    public Tarefa guardar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void eliminar(Long id) {
        tarefaRepository.deleteById(id);
    }
}
