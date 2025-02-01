package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.TarefaPessoal;
import com.upt.lp.rest_api.Repository.TarefaPessoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaPessoalService {

    @Autowired
    private TarefaPessoalRepository tarefaPessoalRepository;

    public List<TarefaPessoal> selecionarTodas() {
        return tarefaPessoalRepository.findAll();
    }

    public Optional<TarefaPessoal> procurarPorId(Long id) {
        return tarefaPessoalRepository.findById(id);
    }

    public List<TarefaPessoal> procurarPorLocalizacao(String localizacao) {
        return tarefaPessoalRepository.findByLocalizacao(localizacao);
    }

    public TarefaPessoal guardar(TarefaPessoal tarefaPessoal) {
        return tarefaPessoalRepository.save(tarefaPessoal);
    }

    public void eliminar(Long id) {
        tarefaPessoalRepository.deleteById(id);
    }
}
