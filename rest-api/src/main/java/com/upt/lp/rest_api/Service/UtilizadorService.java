package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.Utilizador;
import com.upt.lp.rest_api.Repository.UtilizadorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizadorService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    public List<Utilizador> selecionarTodos() {
        return utilizadorRepository.findAll();
    }

    public Optional<Utilizador> procurarPorId(Long id) {
        return utilizadorRepository.findById(id);
    }

    public Optional<Utilizador> procurarPorEmail(String email) {
        return utilizadorRepository.findByEmail(email);
    }

    public Utilizador guardar(Utilizador utilizador) {
        return utilizadorRepository.save(utilizador);
    }

    public void eliminar(Long id) {
        utilizadorRepository.deleteById(id);
    }
}
