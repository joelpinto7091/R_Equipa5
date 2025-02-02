package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.TarefaDeEstudo;
import com.upt.lp.rest_api.Repository.TarefaDeEstudoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaDeEstudoService {

    @Autowired
    private TarefaDeEstudoRepository tarefaDeEstudoRepository;

    public List<TarefaDeEstudo> selecionarTodas() {
        return tarefaDeEstudoRepository.findAll();
    }

    public Optional<TarefaDeEstudo> procurarPorId(Long id) {
        return tarefaDeEstudoRepository.findById(id);
    }

    public List<TarefaDeEstudo> procurarPorDisciplina(String disciplina) {
        return tarefaDeEstudoRepository.findByDisciplina(disciplina);
    }

    public TarefaDeEstudo guardar(TarefaDeEstudo tarefaDeEstudo) {
        return tarefaDeEstudoRepository.save(tarefaDeEstudo);
    }

    public void eliminar(Long id) {
        tarefaDeEstudoRepository.deleteById(id);
    }
}

