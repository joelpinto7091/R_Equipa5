package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.TarefaTrabalho;
import com.upt.lp.rest_api.Repository.TarefaTrabalhoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaTrabalhoService {

    @Autowired
    private TarefaTrabalhoRepository tarefaTrabalhoRepository;

    public List<TarefaTrabalho> selecionarTodas() {
        return tarefaTrabalhoRepository.findAll();
    }

    public Optional<TarefaTrabalho> procurarPorId(Long id) {
        return tarefaTrabalhoRepository.findById(id);
    }

    public List<TarefaTrabalho> procurarPorNomeProjeto(String nomeProjeto) {
        return tarefaTrabalhoRepository.findByNomeProjeto(nomeProjeto);
    }

    public TarefaTrabalho guardar(TarefaTrabalho tarefaTrabalho) {
        return tarefaTrabalhoRepository.save(tarefaTrabalho);
    }

    public void eliminar(Long id) {
        tarefaTrabalhoRepository.deleteById(id);
    }
}
