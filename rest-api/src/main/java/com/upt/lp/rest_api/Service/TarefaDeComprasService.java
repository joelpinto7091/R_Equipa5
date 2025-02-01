package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.TarefaDeCompras;
import com.upt.lp.rest_api.Repository.TarefaDeComprasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaDeComprasService {

    @Autowired
    private TarefaDeComprasRepository tarefaDeComprasRepository;

    public List<TarefaDeCompras> selecionarTodas() {
        return tarefaDeComprasRepository.findAll();
    }

    public Optional<TarefaDeCompras> procurarPorId(Long id) {
        return tarefaDeComprasRepository.findById(id);
    }

    public List<TarefaDeCompras> procurarPorLoja(String loja) {
        return tarefaDeComprasRepository.findByLoja(loja);
    }

    public TarefaDeCompras guardar(TarefaDeCompras tarefaDeCompras) {
        return tarefaDeComprasRepository.save(tarefaDeCompras);
    }

    public void eliminar(Long id) {
        tarefaDeComprasRepository.deleteById(id);
    }
}
